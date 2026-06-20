# ─────────────────────────────────────────────────────────────────────────────
# R8 / ProGuard rules for release builds.
# Most libraries here ship their own consumer rules; these cover our own
# kotlinx.serialization models and a couple of reflective edges.
# ─────────────────────────────────────────────────────────────────────────────

# kotlinx.serialization — keep generated serializers and @Serializable metadata.
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.**
-keepclassmembers class **$$serializer { *; }
-keepclasseswithmembers,allowshrinking @kotlinx.serialization.Serializable class * { *; }
-keep,includedescriptorclasses class com.kusl.myweather.**$$serializer { *; }
-keepclassmembers class com.kusl.myweather.** {
    *** Companion;
    kotlinx.serialization.KSerializer serializer(...);
}

# Our @Serializable DTO/model classes (data layer + domain models persisted as JSON).
-keep @kotlinx.serialization.Serializable class com.kusl.myweather.** { *; }

# Retrofit (most rules ship with the artifact; these are belt-and-braces).
-keepattributes Signature, Exceptions
-dontwarn javax.annotation.**
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# OkHttp / Okio quiet warnings.
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
