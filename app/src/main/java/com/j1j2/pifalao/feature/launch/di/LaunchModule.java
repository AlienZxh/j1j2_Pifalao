package com.j1j2.pifalao.feature.launch.di;

import com.google.gson.Gson;
import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.feature.launch.LaunchActivity;
import com.j1j2.pifalao.feature.launch.LaunchViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-19.
 */
@Module
public class LaunchModule {

    private LaunchActivity launchActivity;

    public LaunchModule(LaunchActivity launchActivity) {
        this.launchActivity = launchActivity;
    }

    @Provides
    @ActivityScope
    UserLoginApi userLoginApi(Retrofit retrofit) {
        return retrofit.create(UserLoginApi.class);
    }

    @Provides
    @ActivityScope
    LaunchActivity launchActivity() {
        return launchActivity;
    }

    @Provides
    @ActivityScope
    LaunchViewModel launchViewModel(UserLoginApi userLoginApi, LaunchActivity launchActivity, UserLoginPreference userLoginPreference, Gson gson) {
        return new LaunchViewModel(userLoginApi, launchActivity, gson, userLoginPreference);
    }
}
