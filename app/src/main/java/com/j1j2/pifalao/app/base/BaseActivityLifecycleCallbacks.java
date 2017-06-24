package com.j1j2.pifalao.app.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.j1j2.pifalao.app.MainAplication;
import com.orhanobut.logger.Logger;

/**
 * Created by alienzxh on 16-10-15.
 */

public class BaseActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private int activityCount;
    private LocationService locationService;

    public BaseActivityLifecycleCallbacks(LocationService locationService) {
        this.locationService = locationService;
        this.activityCount = 0;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityCount++;
        if (activityCount == 1) {
            locationService.registerListener();
            locationService.start();
            Logger.d("定位开始了");
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityCount--;
        if (activityCount <= 0) {
            locationService.unregisterListener();
            locationService.stop();
            Logger.d("定位结束了");

            MainAplication.get(activity.getApplicationContext()).getDb().close();
            Logger.d("数据库关闭");
        }
    }

}
