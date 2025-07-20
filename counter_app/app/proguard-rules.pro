# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Camera and MediaPipe related rules
-keep class com.google.mediapipe.examples.handlandmarker.fragment.** { *; }
-keep class com.google.mediapipe.examples.handlandmarker.** { *; }
-keep class androidx.camera.** { *; }
-keep class com.google.mediapipe.** { *; }

# Keep CameraX classes
-keep class androidx.camera.core.** { *; }
-keep class androidx.camera.lifecycle.** { *; }
-keep class androidx.camera.view.** { *; }

# Keep MediaPipe classes
-keep class com.google.mediapipe.tasks.** { *; }
-keep class com.google.mediapipe.framework.** { *; }

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep ViewBinding
-keep class * implements androidx.viewbinding.ViewBinding {
    public static *** inflate(android.view.LayoutInflater);
    public static *** inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
    public static *** bind(android.view.View);
}

# Keep Fragment classes
-keep class * extends androidx.fragment.app.Fragment { *; }

# Keep Activity classes
-keep class * extends androidx.appcompat.app.AppCompatActivity { *; }

# Protobuf related rules
-keep class com.google.protobuf.** { *; }
-keep class com.google.protobuf.GeneratedMessageLite { *; }
-keep class com.google.protobuf.GeneratedMessageLite$Builder { *; }
-keep class com.google.protobuf.GeneratedMessageLite$ExtendableBuilder { *; }
-keep class com.google.protobuf.GeneratedMessageLite$ExtendableMessage { *; }
-keep class com.google.protobuf.GeneratedMessageLite$ExtendableMessageOrBuilder { *; }
-keep class com.google.protobuf.GeneratedMessageLite$GeneratedExtension { *; }
-keep class com.google.protobuf.GeneratedMessageLite$MethodToInvoke { *; }
-keep class com.google.protobuf.GeneratedMessageV3 { *; }
-keep class com.google.protobuf.GeneratedMessageV3$Builder { *; }
-keep class com.google.protobuf.GeneratedMessageV3$FieldAccessorTable { *; }
-keep class com.google.protobuf.AbstractMessageLite { *; }
-keep class com.google.protobuf.AbstractMessageLite$Builder { *; }
-keep class com.google.protobuf.AbstractParser { *; }
-keep class com.google.protobuf.Any { *; }
-keep class com.google.protobuf.Any$Builder { *; }
-keep class com.google.protobuf.AnyOrBuilder { *; }
-keep class com.google.protobuf.ByteString { *; }
-keep class com.google.protobuf.CodedInputStream { *; }
-keep class com.google.protobuf.CodedOutputStream { *; }
-keep class com.google.protobuf.Descriptors { *; }
-keep class com.google.protobuf.Descriptors$Descriptor { *; }
-keep class com.google.protobuf.Descriptors$FileDescriptor { *; }
-keep class com.google.protobuf.ExtensionLite { *; }
-keep class com.google.protobuf.ExtensionRegistryLite { *; }
-keep class com.google.protobuf.Internal { *; }
-keep class com.google.protobuf.Internal$EnumLite { *; }
-keep class com.google.protobuf.Internal$EnumLiteMap { *; }
-keep class com.google.protobuf.Internal$EnumVerifier { *; }
-keep class com.google.protobuf.Internal$FloatList { *; }
-keep class com.google.protobuf.Internal$IntList { *; }
-keep class com.google.protobuf.Internal$ProtobufList { *; }
-keep class com.google.protobuf.InvalidProtocolBufferException { *; }
-keep class com.google.protobuf.MapEntryLite { *; }
-keep class com.google.protobuf.MapFieldLite { *; }
-keep class com.google.protobuf.Message { *; }
-keep class com.google.protobuf.MessageLite { *; }
-keep class com.google.protobuf.MessageLite$Builder { *; }
-keep class com.google.protobuf.MessageLiteOrBuilder { *; }
-keep class com.google.protobuf.MessageOrBuilder { *; }
-keep class com.google.protobuf.Parser { *; }
-keep class com.google.protobuf.UninitializedMessageException { *; }
-keep class com.google.protobuf.UnknownFieldSet { *; }
-keep class com.google.protobuf.WireFormat { *; }
-keep class com.google.protobuf.WireFormat$FieldType { *; }

# gRPC related rules
-keep class io.grpc.** { *; }
-keep class io.grpc.netty.** { *; }
-keep class io.grpc.protobuf.** { *; }
-keep class io.grpc.stub.** { *; }

# Logging related rules
-keep class org.slf4j.** { *; }
-keep class org.apache.logging.** { *; }
-keep class org.apache.log4j.** { *; }
-keep class org.eclipse.jetty.** { *; }
-keep class reactor.** { *; }

# AutoValue related rules
-keep class autovalue.** { *; }
-keep class com.google.auto.** { *; }

# javax.lang.model related rules
-keep class javax.lang.model.** { *; }

# Keep all classes in the grpc module
-keep class com.example.grpc.** { *; }

# Keep all generated protobuf classes
-keep class **$Builder { *; }
-keep class **$OrBuilder { *; }

# Keep all enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep all serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep all classes that might be used by reflection
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keepattributes InnerClasses

# Keep all classes that might be used by JNI
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep all classes that might be used by reflection
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# Keep all classes in the app package
-keep class com.google.mediapipe.examples.handlandmarker.** { *; }