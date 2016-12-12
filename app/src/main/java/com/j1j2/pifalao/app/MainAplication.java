package com.j1j2.pifalao.app;

import android.content.Context;
import android.os.Environment;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.j1j2.common.util.Toastor;
import com.j1j2.data.model.User;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivityLifecycleCallbacks;
import com.j1j2.pifalao.app.base.LocationService;
import com.j1j2.pifalao.app.di.AppComponent;
import com.j1j2.pifalao.app.di.AppModule;
import com.j1j2.pifalao.app.di.DaggerAppComponent;
import com.j1j2.pifalao.app.di.UserComponent;
import com.j1j2.pifalao.app.di.UserModule;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.feature.launch.LaunchActivity;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.ui.UILifecycleListener;
import com.tencent.bugly.beta.upgrade.UpgradeListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.tencent.bugly.crashreport.CrashReport;
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


/**
 * Created by alienzxh on 16-3-4.
 */
public class MainAplication extends MultiDexApplication {

    public interface AppUpgradeListener {

        void onUpgradeFailed(boolean isManual);

        void onUpgradeSuccess(boolean isManual);

        void onUpgradeNoVersion(boolean isManual);

        void onUpgrading(boolean isManual);

        void onUpgradeDialogCreate(Context context, View view, UpgradeInfo upgradeInfo);

        void onUpgradeDialogDestory(Context context, View view, UpgradeInfo upgradeInfo);
    }

    private AppUpgradeListener appUpgradeListener;


    public void registerAppUpgradeListener(AppUpgradeListener appUpgradeListener) {
        this.appUpgradeListener = appUpgradeListener;
    }

    public void clearAppUpgradeListener() {
        this.appUpgradeListener = null;
    }

    //_______________________________________________
    private RefWatcher refWatcher;
    private AppComponent appComponent;
    private UserComponent userComponent;

    private LocationService locationService;

    @Inject
    UserLoginPreference userLoginPreference;
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    Toastor toastor;

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
            initStrictMode();
            initBugly();
            initDefendeleak();
            initLogger();
            initBaiduMap();
            initComponent();
            initUmeng();
            initJPush();
            initLeakCanary();
            initOkHttpUtil();
            initGalleryFinal();
            registerActivityLifecycleCallbacks(new BaseActivityLifecycleCallbacks(locationService));
        }
    }

    private void initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
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
        locationService = new LocationService(getApplicationContext());
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
                .setForceCrop(true)
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
        PlatformConfig.setSinaWeibo("3170884849", "3acdf3a8db1e704b0b9de0418a951e52");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1103829290", "BAgqsmAnuefshU3a");
        // QQ和Qzone appid appkey
    }


    private void initBugly() {
        Beta.autoInit = true;//自动初始化开关
        Beta.autoCheckUpgrade = false;//true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
        //只允许在***上显示更新弹窗，其他activity上不显示弹窗; 不设置会默认所有activity都可以显示弹窗;
        Beta.canShowUpgradeActs.add(LaunchActivity.class);
        // 监听更新的各个状态，可以替换SDK内置的toast提示
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeFailed(boolean isManual) {
                Logger.d("MainAplication onUpgradeFailed");
                if (appUpgradeListener != null)
                    appUpgradeListener.onUpgradeFailed(isManual);
            }

            @Override
            public void onUpgradeSuccess(boolean isManual) {
                Logger.d("MainAplication onUpgradeSuccess");
                if (appUpgradeListener != null)
                    appUpgradeListener.onUpgradeSuccess(isManual);
            }

            @Override
            public void onUpgradeNoVersion(boolean isManual) {
                Logger.d("MainAplication onUpgradeNoVersion");
                if (appUpgradeListener != null)
                    appUpgradeListener.onUpgradeNoVersion(isManual);
            }

            @Override
            public void onUpgrading(boolean isManual) {
                Logger.d("MainAplication onUpgrading");
                if (appUpgradeListener != null)
                    appUpgradeListener.onUpgrading(isManual);

            }
        };

        /**
         *  如果想监听升级对话框的生命周期事件，可以通过设置OnUILifecycleListener接口
         *  回调参数解释：
         *  context - 当前弹窗上下文对象
         *  view - 升级对话框的根布局视图，可通过这个对象查找指定view控件
         *  upgradeInfo - 升级信息
         */
        Beta.upgradeDialogLifecycleListener = new UILifecycleListener<UpgradeInfo>() {
            @Override
            public void onCreate(Context context, View view, UpgradeInfo upgradeInfo) {
                if (appUpgradeListener != null)
                    appUpgradeListener.onUpgradeDialogCreate(context, view, upgradeInfo);
            }

            @Override
            public void onStart(Context context, View view, UpgradeInfo upgradeInfo) {
            }

            @Override
            public void onResume(Context context, View view, UpgradeInfo upgradeInfo) {
            }

            @Override
            public void onPause(Context context, View view, UpgradeInfo upgradeInfo) {
            }

            @Override
            public void onStop(Context context, View view, UpgradeInfo upgradeInfo) {
            }

            @Override
            public void onDestroy(Context context, View view, UpgradeInfo upgradeInfo) {
                if (appUpgradeListener != null)
                    appUpgradeListener.onUpgradeDialogDestory(context, view, upgradeInfo);
            }
        };
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);//设置sd卡的Download为更新资源存储目录
        BuglyStrategy strategy = new BuglyStrategy();
        strategy.setAppChannel(BuildConfig.DEBUG ? "pifalao-debug" : "pifalao");
        Bugly.init(getApplicationContext(), Constant.BUGLY_ID, BuildConfig.DEBUG, strategy);
    }


    public AppComponent getAppComponent() {
        return appComponent;
    }

    public void releaseUserComponent() {
        userComponent = null;
    }

    public UserComponent createUserComponent(User user) {
        //设置bugly上传用户信息
        CrashReport.setUserId("" + user.getUserId());
        CrashReport.putUserData(getApplicationContext(), "loginaccount", user.getLoginAccount());
        userComponent = appComponent.plus(new UserModule(user));
        return userComponent;
    }

    public UserComponent getUserComponent() {
        return userComponent;
    }


    public boolean isLogin() {
        Logger.d("isLogin Cookie " + (null != userLoginPreference.getLoginCookie(null)));
        Logger.d("isLogin userComponent " + (null != userComponent));
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

    public LocationService getLocationService() {
        return locationService;
    }
}
