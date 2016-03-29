package com.j1j2.pifalao.feature.register.steptwo.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.register.stepone.di.RegisterStepOneModule;
import com.j1j2.pifalao.feature.register.steptwo.RegisterStepTwoActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-28.
 */
@ActivityScope
@Subcomponent(modules = {RegisterStepTwoModule.class})
public interface RegisterStepTwoComponent {
    void inject(RegisterStepTwoActivity registerStepTwoActivity);
}
