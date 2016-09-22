package com.j1j2.pifalao.feature.prizeconfirm.di;

import android.app.Activity;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.http.api.ActivityShopCartApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.memberhome.MemberHomeActivity;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-9-8.
 */

@Module
public class PrizeConfirmModule {
    private Activity activity;

    public PrizeConfirmModule(Activity activity) {
        this.activity = activity;
    }


    @Provides
    @ActivityScope
    ActivityShopCartApi activityShopCartApi(Retrofit retrofit) {
        return retrofit.create(ActivityShopCartApi.class);
    }

    @Provides
    @ActivityScope
    ActivityApi activityApi(Retrofit retrofit) {
        return retrofit.create(ActivityApi.class);
    }

    @Provides
    @ActivityScope
    UserLoginApi userLoginApi(Retrofit retrofit) {
        return retrofit.create(UserLoginApi.class);
    }

    @Provides
    @ActivityScope
    Activity activity() {
        return activity;
    }
}
