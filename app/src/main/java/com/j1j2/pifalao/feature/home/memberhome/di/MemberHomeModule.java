package com.j1j2.pifalao.feature.home.memberhome.di;

import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserMessageApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.memberhome.MemberHomeActivity;
import com.j1j2.pifalao.feature.main.MainActivity;
import com.j1j2.pifalao.feature.main.MainActivityViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-8-24.
 */
@Module
public class MemberHomeModule {
    private MemberHomeActivity activity;

    public MemberHomeModule( MemberHomeActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    UserLoginApi userLoginApi(Retrofit retrofit) {
        return retrofit.create(UserLoginApi.class);
    }

    @Provides
    @ActivityScope
    UserMessageApi userMessageApi(Retrofit retrofit) {
        return retrofit.create(UserMessageApi.class);
    }

    @Provides
    @ActivityScope
    MemberHomeActivity activity() {
        return activity;
    }



}
