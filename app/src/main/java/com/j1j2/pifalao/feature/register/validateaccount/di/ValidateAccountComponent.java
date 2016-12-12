package com.j1j2.pifalao.feature.register.validateaccount.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.register.steptwo.di.RegisterStepTwoModule;
import com.j1j2.pifalao.feature.register.validateaccount.ValidateAccountActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-10-21.
 */
@ActivityScope
@Subcomponent(modules = {ValidateAccountModule.class})
public interface ValidateAccountComponent {
    void inject(ValidateAccountActivity validateAccountActivity);
}
