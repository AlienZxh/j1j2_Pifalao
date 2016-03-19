package com.j1j2.pifalao.feature.login.di;

import com.google.gson.Gson;
import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.feature.login.LoginActivity;
import com.j1j2.pifalao.feature.login.LoginViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-18.
 */
@Module
public class LoginModule {

    private LoginActivity loginActivity;

    public LoginModule(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Provides
    @ActivityScope
    LoginActivity loginActivity() {
        return loginActivity;
    }

    @Provides
    @ActivityScope
    UserLoginApi userLoginApi(Retrofit retrofit) {
        return retrofit.create(UserLoginApi.class);
    }


    @Provides
    @ActivityScope
    LoginViewModel loginViewModel(LoginActivity loginActivity, UserLoginApi userLoginApi, UserLoginPreference userLoginPreference, Gson gson) {
        return new LoginViewModel(loginActivity, userLoginApi, userLoginPreference, gson);
    }
}
