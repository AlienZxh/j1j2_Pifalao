package com.j1j2.pifalao.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.j1j2.data.model.User;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.di.AppComponent;
import com.j1j2.pifalao.app.di.AppModule;
import com.j1j2.pifalao.app.di.DaggerAppComponent;
import com.j1j2.pifalao.app.di.UserComponent;
import com.j1j2.pifalao.app.di.UserModule;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;


/**
 * Created by alienzxh on 16-3-4.
 */
public class MainAplication extends MultiDexApplication {

    private RefWatcher refWatcher;
    private AppComponent appComponent;
    private UserComponent userComponent;

    @Inject
    UserLoginPreference userLoginPreference;
    @Inject
    OkHttpClient okHttpClient;

    public static RefWatcher getRefWatcher(Context context) {
        return get(context).refWatcher;
    }

    public static MainAplication get(Context context) {
        return (MainAplication) context.getApplicationContext();
    }

    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getProcessName();
        if (!TextUtils.isEmpty(processName) && processName.equals(this.getPackageName())) {//判断进程名，保证只有主进程运行
            initDefendeleak();
            initLogger();
//            initAndroidDevMetrics();
            initBaiduMap();
            initComponent();
            initUmeng();
            initJPush();
            initLeakCanary();
            initOkHttpUtil();
            initGalleryFinal();

        }
    }


    private void initDefendeleak() {
        try {
            Class cls = Class.forName("android.sec.clipboard.ClipboardUIManager");
            Method m = cls.getDeclaredMethod("getInstance", Context.class);
            m.setAccessible(true);
            m.invoke(null, this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void initOkHttpUtil() {
        if (BuildConfig.DEBUG)
            OkHttpUtils.getInstance(okHttpClient).debug("pifalao");
        else
            OkHttpUtils.getInstance(okHttpClient);
        Logger.d("OkHttpUtil初始化完成");
    }

    private void initLogger() {
        if (BuildConfig.DEBUG)
            Logger.init(" pifalao debug ")
                    .logLevel(LogLevel.FULL);
        else
            Logger.init(" pifalao debug ")
                    .logLevel(LogLevel.NONE);
        Logger.d("Logger初始化完成");

    }

//    private void initAndroidDevMetrics(){
//        if (BuildConfig.DEBUG) {
//            AndroidDevMetrics.initWith(this);
//        }
//        Logger.d("AndroidDevMetrics初始化完成");
//    }

    private void initJPush() {
        if (BuildConfig.DEBUG)
            JPushInterface.setDebugMode(true);
        else
            JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        Logger.d("JPush初始化完成");
    }

    private void initComponent() {
        this.appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        this.appComponent.inject(this);
        Logger.d("AppComponent初始化完成");
    }


    private void initLeakCanary() {
        if (BuildConfig.DEBUG)
            refWatcher = LeakCanary.install(this);
        else
            refWatcher = RefWatcher.DISABLED;
        Logger.d("LeakCanary初始化完成");
    }


    private void initBaiduMap() {
        SDKInitializer.initialize(getApplicationContext());
        Logger.d("BaiduMap初始化完成");
    }

    private void initGalleryFinal() {
        //设置主题
//ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(getResources().getColor(R.color.colorPrimary))
                .build();
//配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        GlideImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .setEditPhotoCacheFolder(new File(Constant.FilePath.editPhotoCacheFolder))
                .setTakePhotoFolder(new File(Constant.FilePath.takePhotoFolder))
                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, true))
                .build();
        GalleryFinal.init(coreConfig);
        Logger.d("GalleryFinal初始化完成");
    }

    private void initUmeng() {
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        //_________________________________________
        PlatformConfig.setWeixin("wxaaf65494c086b0d3", "5acdc3455a4bcbbdd4610177188af355");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("3170884849","3acdf3a8db1e704b0b9de0418a951e52");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1103829290", "BAgqsmAnuefshU3a");
        // QQ和Qzone appid appkey
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public void releaseUserComponent() {
        userComponent = null;
    }

    public UserComponent createUserComponent(User user) {
        userComponent = appComponent.plus(new UserModule(user));
        return userComponent;
    }

    public UserComponent getUserComponent() {
        return userComponent;
    }


    public boolean isLogin() {
        return null != userLoginPreference.getLoginCookie(null) && null != userComponent;
    }


    public void login(User user) {
        createUserComponent(user);
        userLoginPreference.setUserInfo(user);
    }

    public void loginOut() {
        releaseUserComponent();
        userLoginPreference
                .setIsAutoLogin(false)
                .removeLoginCookie()
                .removeUserInfo();
    }

}
