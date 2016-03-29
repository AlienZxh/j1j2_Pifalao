package com.j1j2.pifalao.feature.changepassword.di;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.changepassword.ChangePasswordActivity;
import com.j1j2.pifalao.feature.city.di.CityModule;

import dagger.Provides;
import dagger.Subcomponent;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-28.
 */
@ActivityScope
@Subcomponent(modules = {ChangePasswordModule.class})
public interface ChangePasswordComponent {

    void inject(ChangePasswordActivity changePasswordActivity);
}
