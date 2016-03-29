package com.j1j2.pifalao.feature.changepassword.di;

import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.changepassword.ChangePasswordActivity;
import com.j1j2.pifalao.feature.changepassword.ChangePasswordViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-28.
 */
@Module
public class ChangePasswordModule {

    private ChangePasswordActivity changePasswordActivity;

    public ChangePasswordModule(ChangePasswordActivity changePasswordActivity) {
        this.changePasswordActivity = changePasswordActivity;
    }

    @Provides
    @ActivityScope
    UserVipApi userVipApi(Retrofit retrofit) {
        return retrofit.create(UserVipApi.class);
    }

    @Provides
    @ActivityScope
    ChangePasswordActivity changePasswordActivity() {
        return changePasswordActivity;
    }

    @Provides
    @ActivityScope
    ChangePasswordViewModel changePasswordViewModel(ChangePasswordActivity changePasswordActivity, UserVipApi userVipApi) {
        return new ChangePasswordViewModel(changePasswordActivity, userVipApi);
    }
}
