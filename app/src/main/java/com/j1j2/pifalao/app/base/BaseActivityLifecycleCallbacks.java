package com.j1j2.pifalao.app.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

/**
 * Created by alienzxh on 16-10-15.
 */

public class BaseActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private int locationActivityCount;
    private LocationService locationService;

    public BaseActivityLifecycleCallbacks(LocationService locationService) {
        this.locationService = locationService;
        this.locationActivityCount = 0;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity instanceof BaseLocationActivity) {
            locationActivityCount++;
            if (locationActivityCount == 1) {
                locationService.registerListener();
                locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                locationService.start();
                Logger.d("定位开始了");
            }
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
        if (activity instanceof BaseLocationActivity) {
            locationActivityCount--;
            if (locationActivityCount <= 0) {
                locationService.unregisterListener();
                locationService.stop();
                Logger.d("定位结束了");
            }
        }

    }


}
