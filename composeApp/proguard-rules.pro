# proguard-rules.pro

# Domain modellerini koru
-keep class com.barisproduction.kargo.domain.model.** { *; }

# Navigation argümanlarında Enum koruma
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Serialization Kuralları
-keepattributes *Annotation*, InnerClasses
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class * {
    @kotlinx.serialization.Serializable <init>(...);
}