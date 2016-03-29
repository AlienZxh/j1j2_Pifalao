package com.j1j2.pifalao.feature.register.stepone.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.login.di.LoginModule;
import com.j1j2.pifalao.feature.register.stepone.RegisterStepOneActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-28.
 */
@ActivityScope
@Subcomponent(modules = {RegisterStepOneModule.class})
public interface RegisterStepOneComponent {
    void inject(RegisterStepOneActivity registerStepOneActivity);
}
