package com.j1j2.pifalao.app;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.j1j2.data.model.User;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.app.base.RxBus;
import com.j1j2.pifalao.app.di.AppComponent;
import com.j1j2.pifalao.app.di.AppModule;
import com.j1j2.pifalao.app.di.DaggerAppComponent;
import com.j1j2.pifalao.app.di.UserComponent;
import com.j1j2.pifalao.app.di.UserModule;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created by alienzxh on 16-3-4.
 */
public class MainAplication extends Application {

    private RefWatcher refWatcher;
    private AppComponent appComponent;
    private UserComponent userComponent;
    private String TAG = " MainAplication ";

    public static RefWatcher getRefWatcher(Context context) {
        return get(context).refWatcher;
    }

    public static MainAplication get(Context context) {
        return (MainAplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
        initRxBus();
        initComponent();
        initLeakCanary();
        initFresco();
        initBaiduMap();
    }

    private void initRxBus() {
        if (BuildConfig.DEBUG)
            RxBus.DEBUG = BuildConfig.DEBUG;

        Logger.d("RxBus初始化完成");

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

    private void initComponent() {
        this.appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
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

}
