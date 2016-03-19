package com.j1j2.pifalao.feature.login.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.location.LocationActivity;
import com.j1j2.pifalao.feature.location.di.LocationModule;
import com.j1j2.pifalao.feature.login.LoginActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-18.
 */
@ActivityScope
@Subcomponent(modules = {LoginModule.class})
public interface LoginComponent {

    void inject(LoginActivity loginActivity);

}
