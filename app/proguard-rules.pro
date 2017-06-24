# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/alienzxh/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-ignorewarnings
-optimizationpasses 7  #指定代码的压缩级别 0 - 7
-dontusemixedcaseclassnames  #是否使用大小写混合
-dontskipnonpubliclibraryclasses  #如果应用程序引入的有jar包，并且想混淆jar包里面的class
-dontpreverify  #混淆时是否做预校验（可去掉加快混淆速度）
-dontoptimize #不优化输入的类文件
-dontshrink
-verbose #混淆时是否记录日志（混淆后生产映射文件 map 类名 -> 转化后类名的映射
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  #淆采用的算法


-keep public class * extends android.app.Activity  #所有activity的子类不要去混淆
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService #指定具体类不要去混淆
-keep public class com.google.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;  #保持 native 的方法不去混淆
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);  #保持自定义控件类不被混淆，指定格式的构造方法不去混淆
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View); #保持指定规则的方法不被混淆（Android layout 布局文件中为控件配置的onClick方法不能混淆）
}

-keep public class * extends android.view.View {  #保持自定义控件指定规则的方法不被混淆
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclassmembers enum * {  #保持枚举 enum 不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {  #保持 Parcelable 不被混淆（aidl文件不能去混淆）
    public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable #需要序列化和反序列化的类不能被混淆（注：Java反射用到的类也不能被混淆）

-keepclassmembers class * implements java.io.Serializable { #保护实现接口Serializable的类中，指定规则的类成员不被混淆
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepattributes Signature  #过滤泛型（不写可能会出现类型转换错误，一般情况把这个加上就是了）

-keepattributes *Annotation*  #假如项目中有用到注解，应加入这行配置

-keepattributes Exceptions  #过滤异常

-keepattributes InnerClasses　#过滤内部类

-keepattributes EnclosingMethod

-keep class **.R$* { *; }  #保持R文件不被混淆，否则，你的反射是获取不到资源id的

-keep class **.Webview2JsInterface { *; }  #保护WebView对HTML页面的API不被混淆
-keepclassmembers class * extends android.webkit.WebViewClient {  #如果你的项目中用到了webview的复杂操作 ，最好加入
     public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
     public boolean *(android.webkit.WebView,java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebChromeClient {  #如果你的项目中用到了webview的复杂操作 ，最好加入
     public void *(android.webkit.WebView,java.lang.String);
}
#对WebView的简单说明下：经过实战检验,做腾讯QQ登录，如果引用他们提供的jar，若不加防止WebChromeClient混淆的代码，oauth认证无法回调，反编译基代码后可看到他们有用到WebChromeClient，加入此代码即可。

#-keepclassmembernames class com.j1j2.vo.** { *; }   #转换JSON的JavaBean，类成员名称保护，使其不被混淆

##################################################################
# 下面都是项目中引入的第三方 jar 包。第三方 jar 包中的代码不是我们的目标和关心的对象，故而对此我们全部忽略不进行混淆。
##################################################################
##---------------Begin: proguard configuration for leakcanary ----------
-dontwarn com.squareup.leakcanary.**
-keep class org.eclipse.mat.** { *; }
-keep class com.squareup.leakcanary.** { *; }
##---------------End: proguard configuration for leakcanary ----------

##---------------Begin: proguard configuration for 百度地图SDK ----------
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-keep class com.sinovoice.** {*;}
-keep class pvi.com.** {*;}
-dontwarn com.baidu.**
-dontwarn vi.com.**
-dontwarn pvi.com.**
##---------------End: proguard configuration for 百度地图SDK  ----------

#---------------Begin: proguard configuration for android v4 v7扩展包 ----------
-dontwarn android.support.**
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
#---------------End: proguard configuration for android v4 v7扩展包 ----------

##---------------Begin: proguard configuration for okio、okhttp、okhttputils ----------
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep interface okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}

#okhttputils
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}
-keep interface com.zhy.http.**{*;}
##---------------End: proguard configuration for okio、okhttp、okhttputils  ----------

##---------------Begin: proguard configuration for retrofit2  ----------
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
##---------------End: proguard configuration for retrofit2  ----------

##---------------Begin: proguard configuration for Gson  ----------
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
# Gson uses generic type information stored in a class file when working with fields. Proguard
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.j1j2.data.model.** { *; }  ##这里需要改成解析到哪个  javabean
-keep class com.j1j2.pifalao.app.sharedpreferences.** { *; }
##---------------End: proguard configuration for Gson  ----------

##---------------Begin: proguard configuration for rxjava  ----------
 -dontwarn rx.**
 -keep class rx.** { *; }
##---------------End: proguard configuration for rxjava  ----------

##---------------Begin: proguard configuration for SuperRecyclerView  ----------
-dontwarn com.malinskiy.superrecyclerview.SwipeDismissRecyclerViewTouchListener*
##---------------End: proguard configuration for SuperRecyclerView  ----------

##---------------Begin: proguard configuration for StoreBox  ----------
-keep class net.orange_box.storebox.** { *; }
-dontwarn net.jodah.typetools.TypeResolver
##---------------End: proguard configuration for StoreBox  ----------


##---------------Begin: proguard configuration for zxing  ----------
-keep class com.google.zxing.** { *; }
-dontwarn  com.google.zxing.**
##---------------End: proguard configuration for zxing  ----------

##---------------Begin: proguard configuration for nineold  ----------
-keep class com.nineoldandroids.** { *; }
-dontwarn  com.nineoldandroids.**
##---------------End: proguard configuration for nineold  ----------

##---------------Begin: proguard configuration for eventbus  ----------
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#
-keep class com.j1j2.pifalao.app.event.** { *; }
##---------------End: proguard configuration for eventbus  ----------

##---------------Begin: proguard configuration for jpush  ----------
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
#==================protobuf======================
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}
##---------------End: proguard configuration for jpush  ----------

##---------------Begin: proguard configuration for realm  ----------
#-keep class io.realm.annotations.RealmModule
#-keep @io.realm.annotations.RealmModule class *
#-keep class io.realm.internal.Keep
#-keep @io.realm.internal.Keep class * { *; }
#-dontwarn javax.**
#-dontwarn io.realm.**
##---------------End: proguard configuration for realm  ----------

##---------------Begin: proguard configuration for jsbridge  ----------
-keep class com.github.lzyzsd.jsbridge.** { *; }
-dontwarn com.github.lzyzsd.jsbridge.**
##---------------End: proguard configuration for jsbridge  ----------

##---------------Begin: proguard configuration for finalteam:galleryfinal  ----------
-keep class cn.finalteam.galleryfinal.widget.*{*;}
-keep class cn.finalteam.galleryfinal.widget.crop.*{*;}
-keep class cn.finalteam.galleryfinal.widget.zoonview.*{*;}
##---------------End: proguard configuration for finalteam:galleryfinal  ----------

##---------------Begin: proguard configuration for Glide  ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
##---------------End: proguard configuration for Glide  ----------

##---------------Begin: proguard configuration for shapeimageview  ----------
-keep class com.github.siyamed.** { *; }
-dontwarn com.github.siyamed.**
##---------------End: proguard configuration for shapeimageview  ----------

##---------------Begin: proguard configuration for alipay  ----------
-dontwarn com.alipay.**
-dontwarn HttpUtils.HttpFetcher
-dontwarn com.ta.utdid2.**
-dontwarn com.ut.device.**
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.mobilesecuritysdk.*
-keep class com.ut.*
##---------------Begin: proguard configuration for alipay  ----------

##---------------Begin: proguard configuration for weixin  ----------
-keep class com.tencent.** { *; }
-dontwarn com.tencent.**
##---------------End: proguard configuration for weixin  ----------


##---------------Begin: proguard configuration for umengAnalytics  ----------
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
##---------------End: proguard configuration for umengAnalytics  ----------

##---------------Begin: proguard configuration for umengShare  ----------
#-dontshrink
#-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
#-keepattributes Exceptions,InnerClasses,Signature
#-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}


-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

-dontwarn twitter4j.**
-keep class twitter4j.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep public class com.umeng.soexample.R$*{
    public static final int *;
}
-keep public class com.umeng.soexample.R$*{
    public static final int *;
}
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keep class com.linkedin.** { *; }
#  -keepattributes Signature
##---------------End: proguard configuration for umengShare  ----------

##---------------Begin: proguard configuration for bugly  ----------
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
##---------------End: proguard configuration for bugly  ----------

##---------------Begin: proguard configuration for spotdialog  ----------
-keep class dmax.dialog.** {
    *;
}
##---------------End: proguard configuration for spotdialog  ----------