package com.j1j2.pifalao.feature.register.stepone.di;

import com.j1j2.data.http.api.ClientRegisterApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.register.stepone.RegisterStepOneActivity;
import com.j1j2.pifalao.feature.register.stepone.RegisterStepOneViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-28.
 */
@Module
public class RegisterStepOneModule {

    private RegisterStepOneActivity registerStepOneActivity;

    public RegisterStepOneModule(RegisterStepOneActivity registerStepOneActivity) {
        this.registerStepOneActivity = registerStepOneActivity;
    }

    @Provides
    @ActivityScope
    RegisterStepOneActivity registerStepOneActivity() {
        return registerStepOneActivity;
    }

    @Provides
    @ActivityScope
    ClientRegisterApi clientRegisterApi(Retrofit retrofit) {
        return retrofit.create(ClientRegisterApi.class);
    }

    @Provides
    @ActivityScope
    RegisterStepOneViewModel registerStepOneViewModel(RegisterStepOneActivity registerStepOneActivity, ClientRegisterApi clientRegisterApi) {
        return new RegisterStepOneViewModel(registerStepOneActivity, clientRegisterApi);
    }
}
