package com.j1j2.pifalao.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.j1j2.data.model.User;
import com.j1j2.pifalao.BuildConfig;
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
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by alienzxh on 16-3-4.
 */
public class MainAplication extends Application {

    private RefWatcher refWatcher;
    private AppComponent appComponent;
    private UserComponent userComponent;

    @Inject
    UserLoginPreference userLoginPreference;

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
            initLogger();
            initBaiduMap();
            initComponent();
            initJPush();
            initLeakCanary();
            initFresco();
            initOkHttpUtil();
        }
    }



    private void initOkHttpUtil() {
        if (BuildConfig.DEBUG)
            OkHttpUtils.getInstance().debug("pifalao");
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

    private void initFresco() {
        Fresco.initialize(this);
        Logger.d("Fresco初始化完成");
    }


    private void initBaiduMap() {
        SDKInitializer.initialize(getApplicationContext());
        Logger.d("BaiduMap初始化完成");
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
        userLoginPreference.setIsAutoLogin(false)
                .removeLoginCookie()
                .removeUserInfo()
                .removePassWord();
    }

}
