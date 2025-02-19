# ProGuard rules for shrinking and obfuscation

# Keep required classes
-keep class com.audioiconography.app.** { *; }
-keep class android.** { *; }

# Remove unused code
-dontwarn androidx.**
-dontwarn kotlin.**
-dontwarn java.**
-dontwarn javax.**
