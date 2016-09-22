package com.j1j2.pifalao.feature.showorders.di;

import android.app.Activity;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.http.api.ActivityShopCartApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.pifalao.app.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-9-18.
 */
@Module
public class ShowOrdersModule {
    private Activity activity;

    public ShowOrdersModule(Activity activity) {
        this.activity = activity;
    }


    @Provides
    @ActivityScope
    ActivityApi activityApi(Retrofit retrofit) {
        return retrofit.create(ActivityApi.class);
    }


    @Provides
    @ActivityScope
    Activity activity() {
        return activity;
    }
}
