// Root build script. No plugins are applied here directly; we only declare them
// (apply false) so the version is resolved once from the catalog and shared by
// every module. Module scripts then apply them by alias.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
}
