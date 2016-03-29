package com.j1j2.pifalao.feature.register.steptwo.di;

import com.j1j2.data.http.api.ClientRegisterApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.feature.register.steptwo.RegisterStepTwoActivity;
import com.j1j2.pifalao.feature.register.steptwo.RegisterStepTwoViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-28.
 */
@Module
public class RegisterStepTwoModule {

    private RegisterStepTwoActivity registerStepTwoActivity;

    public RegisterStepTwoModule(RegisterStepTwoActivity registerStepTwoActivity) {
        this.registerStepTwoActivity = registerStepTwoActivity;
    }

    @Provides
    @ActivityScope
    RegisterStepTwoActivity registerStepTwoActivity() {
        return registerStepTwoActivity;
    }

    @Provides
    @ActivityScope
    ClientRegisterApi clientRegisterApi(Retrofit retrofit) {
        return retrofit.create(ClientRegisterApi.class);
    }


    @Provides
    @ActivityScope
    UserLoginApi userLoginApi(Retrofit retrofit) {
        return retrofit.create(UserLoginApi.class);
    }


    @Provides
    @ActivityScope
    RegisterStepTwoViewModel registerStepTwoViewModel(RegisterStepTwoActivity registerStepTwoActivity, ClientRegisterApi clientRegisterApi, UserLoginApi userLoginApi, UserLoginPreference userLoginPreference) {

        return new RegisterStepTwoViewModel(registerStepTwoActivity, clientRegisterApi, userLoginApi, userLoginPreference);
    }

}
