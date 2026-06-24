package com.kusl.myweather.data.remote

import com.kusl.myweather.core.SystemTimeSource
import com.kusl.myweather.core.Telemetry
import com.kusl.myweather.core.TimeSource
import com.kusl.myweather.data.local.dao.HttpCacheDao
import com.kusl.myweather.data.local.entity.HttpCacheEntity
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger

/**
 * A universal, Room-backed HTTP cache implemented as an OkHttp **application**
 * interceptor. It sits in front of every NWS call (current and future) and is
 * the project's single transport-level request shield.
 *
 * Why an interceptor (and not just the repository's own Room cache)? Because it
 * keys on the *fully-resolved* `"$method $url"`, it transparently:
 *  - **De-duplicates by endpoint.** Two lookups whose coordinates round to the
 *    same NWS grid/point produce the identical URL, so the second is served
 *    from cache with no socket opened. (`GeoPoint` already rounds to 4 dp, ~11 m;
 *    this closes the gap for *every* endpoint, including ones the repository
 *    doesn't model.)
 *  - **Negative-caches.** NWS returns cacheable `404`s (e.g. "Marine Forecast
 *    Not Supported"); we remember those too, so we don't keep asking.
 *  - **Serves stale-on-error.** If the network is down we return the last body
 *    we stored, so new endpoints get offline behaviour for free.
 *  - **Revalidates conditionally.** When we hold an `ETag` we attach
 *    `If-None-Match`; a `304` just renews the TTL.
 *
 * ### Deliberate divergence from strict HTTP caching
 * The goal is to make **as few NWS requests as possible**, so this cache is more
 * aggressive than RFC 9111: it stores responses even when `Cache-Control` says
 * `no-store`/`no-cache`, and it caches error statuses. NWS data is public and
 * re-fetchable, the freshness windows below are short, and a user-driven Refresh
 * always re-validates, so the trade is safe and intentional.
 *
 * ### Threading
 * Runs on OkHttp's background dispatcher (Retrofit `suspend` calls are
 * enqueued), so the **blocking** [HttpCacheDao] methods are fine here and must
 * never be invoked from the main thread. [time] is injected so the policy is
 * deterministic under test.
 *
 * The synthetic responses we build carry an `X-MyWeather-Cache` header
 * (`HIT` | `REVALIDATED` | `STALE` | `NETWORK`) purely for observability.
 */
class HttpCacheInterceptor(
    private val cacheDao: HttpCacheDao,
    private val time: TimeSource = SystemTimeSource,
) : Interceptor {

    private val callsSincePrune = AtomicInteger(0)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Only GETs are cacheable here; everything else passes straight through.
        if (!request.method.equals("GET", ignoreCase = true)) {
            return chain.proceed(request)
        }

        maybePrune()

        val key = cacheKey(request.method, request.url.toString())
        val now = time.nowMs()
        val cached = runCatching { cacheDao.getByKey(key) }.getOrNull()

        // 1. Fresh hit — no network at all.
        if (cached != null && now < cached.expiresAtEpochMs) {
            Telemetry.d("HttpCache", "HIT ${request.url} (code=${cached.statusCode})")
            return cached.toResponse(request, CACHE_HIT)
        }

        // 2. Stale or missing. If we have an ETag and the caller didn't bring its
        //    own conditional header, attach ours so the server can answer 304.
        val callerHasConditional = request.header("If-None-Match") != null
        val outbound = if (cached?.etag != null && !callerHasConditional) {
            request.newBuilder().header("If-None-Match", cached.etag).build()
        } else {
            request
        }

        val networkResponse = try {
            chain.proceed(outbound)
        } catch (io: IOException) {
            // 3. Offline: serve the last copy we have, however old.
            if (cached != null) {
                Telemetry.w("HttpCache", "offline; serving STALE ${request.url}")
                return cached.toResponse(request, CACHE_STALE)
            }
            throw io
        }

        // 4. Not Modified: renew the entry's lifetime cheaply.
        if (networkResponse.code == 304 && cached != null) {
            val renewed = cached.copy(
                etag = networkResponse.header("ETag") ?: cached.etag,
                retrievedAtEpochMs = now,
                expiresAtEpochMs = now + ttlMsFor(networkResponse),
            )
            runCatching { cacheDao.upsert(renewed) }
            return if (callerHasConditional) {
                // The caller asked for revalidation itself (the repository does
                // this for forecasts) — let it see the real 304.
                Telemetry.d("HttpCache", "304 passthrough ${request.url}")
                networkResponse.newBuilder().header(CACHE_HEADER, CACHE_REVALIDATED).build()
            } else {
                // We added the conditional header; the caller expects a body.
                networkResponse.close()
                Telemetry.d("HttpCache", "304 -> cached body ${request.url}")
                renewed.toResponse(request, CACHE_REVALIDATED)
            }
        }

        // 5. A real response (2xx, 404, 4xx, 5xx). Read the body once, cache the
        //    bytes verbatim, and hand a fresh copy back to the caller.
        val bodyBytes = networkResponse.body.bytes()
        val contentType = networkResponse.body.contentType()

        if (bodyBytes.size <= MAX_BODY_BYTES) {
            val entity = HttpCacheEntity(
                cacheKey = key,
                url = request.url.toString(),
                method = request.method,
                statusCode = networkResponse.code,
                statusMessage = networkResponse.message,
                contentType = contentType?.toString(),
                body = bodyBytes.toString(Charsets.UTF_8),
                etag = networkResponse.header("ETag"),
                retrievedAtEpochMs = now,
                expiresAtEpochMs = now + ttlMsFor(networkResponse),
            )
            runCatching { cacheDao.upsert(entity) }
            Telemetry.d("HttpCache", "NETWORK ${request.url} -> ${networkResponse.code} (cached)")
        } else {
            Telemetry.d("HttpCache", "NETWORK ${request.url} -> ${networkResponse.code} (too large; not cached)")
        }

        return networkResponse.newBuilder()
            .header(CACHE_HEADER, CACHE_NETWORK)
            .body(bodyBytes.toResponseBody(contentType))
            .build()
    }

    /** Rebuild a stored entry as a real OkHttp [Response] without touching the network. */
    private fun HttpCacheEntity.toResponse(request: okhttp3.Request, cacheState: String): Response {
        val mediaType = contentType?.toMediaTypeOrNull()
        val builder = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(statusCode)
            .message(statusMessage)
            .body(body.toByteArray(Charsets.UTF_8).toResponseBody(mediaType))
            .header(CACHE_HEADER, cacheState)
        contentType?.let { builder.header("Content-Type", it) }
        etag?.let { builder.header("ETag", it) }
        return builder.build()
    }

    /**
     * Opportunistically drop rows that expired more than [STALE_RETENTION_MS] ago.
     * Merely-expired rows are kept on purpose — they are the offline fallback.
     * Runs only every [PRUNE_EVERY] calls to keep the hot path cheap.
     */
    private fun maybePrune() {
        if (callsSincePrune.incrementAndGet() < PRUNE_EVERY) return
        callsSincePrune.set(0)
        runCatching { cacheDao.deleteExpiredBefore(time.nowMs() - STALE_RETENTION_MS) }
    }

    companion object {
        const val CACHE_HEADER = "X-MyWeather-Cache"
        const val CACHE_HIT = "HIT"
        const val CACHE_REVALIDATED = "REVALIDATED"
        const val CACHE_STALE = "STALE"
        const val CACHE_NETWORK = "NETWORK"

        /** Don't cache bodies larger than this (NWS payloads are far smaller). */
        const val MAX_BODY_BYTES = 2 * 1024 * 1024

        private const val PRUNE_EVERY = 50
        private val STALE_RETENTION_MS = 7L * 24 * 60 * 60 * 1000 // keep stale rows a week

        // Freshness windows. Short by design; Refresh always revalidates.
        private val POSITIVE_FLOOR_MS = 15L * 1000               // never thrash on tiny max-ages
        private val POSITIVE_CEIL_MS = 6L * 60 * 60 * 1000       // 6 hours
        private val DEFAULT_POSITIVE_MS = 30L * 60 * 1000        // 30 minutes
        private val NEGATIVE_404_TTL_MS = 24L * 60 * 60 * 1000   // a cacheable "no" lasts a day
        private val TRANSIENT_DEFAULT_MS = 60L * 1000            // 408/429/5xx without Retry-After
        private val TRANSIENT_MAX_MS = 10L * 60 * 1000           // cap any backoff at 10 min
        private val CLIENT_ERROR_TTL_MS = 60L * 60 * 1000        // other 4xx: an hour

        private val MAX_AGE_REGEX = Regex("""max-age\s*=\s*(\d+)""", RegexOption.IGNORE_CASE)

        internal fun cacheKey(method: String, url: String): String = "${method.uppercase()} $url"

        /** Parse the integer-seconds form of `Cache-Control: max-age`; null otherwise. */
        internal fun parseMaxAgeSeconds(cacheControl: String?): Long? =
            cacheControl?.let { MAX_AGE_REGEX.find(it)?.groupValues?.getOrNull(1)?.toLongOrNull() }

        /** Parse the integer (delta-seconds) form of `Retry-After`; HTTP-date form is ignored. */
        internal fun parseRetryAfterSeconds(retryAfter: String?): Long? =
            retryAfter?.trim()?.toLongOrNull()?.takeIf { it >= 0 }

        /** The TTL policy, exposed for unit tests. */
        internal fun ttlMsForStatus(code: Int, cacheControl: String?, retryAfter: String?): Long = when {
            code == 304 || code in 200..299 -> {
                val maxAge = parseMaxAgeSeconds(cacheControl)
                if (maxAge != null) {
                    (maxAge * 1000L).coerceIn(POSITIVE_FLOOR_MS, POSITIVE_CEIL_MS)
                } else {
                    DEFAULT_POSITIVE_MS
                }
            }

            code == 404 || code == 410 -> NEGATIVE_404_TTL_MS

            code == 408 || code == 425 || code == 429 || code in 500..599 -> {
                val retry = parseRetryAfterSeconds(retryAfter)
                val ttl = if (retry != null && retry > 0) retry * 1000L else TRANSIENT_DEFAULT_MS
                ttl.coerceIn(POSITIVE_FLOOR_MS, TRANSIENT_MAX_MS)
            }

            code in 400..499 -> CLIENT_ERROR_TTL_MS

            else -> CLIENT_ERROR_TTL_MS // 3xx (rarely reaches us; redirects are followed) / anything else
        }
    }

    private fun ttlMsFor(response: Response): Long = ttlMsForStatus(
        code = response.code,
        cacheControl = response.header("Cache-Control"),
        retryAfter = response.header("Retry-After"),
    )
}
