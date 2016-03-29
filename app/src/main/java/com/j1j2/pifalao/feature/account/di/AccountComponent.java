package com.j1j2.pifalao.feature.account.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.account.AccountActivity;
import com.j1j2.pifalao.feature.changepassword.di.ChangePasswordModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-28.
 */
@ActivityScope
@Subcomponent(modules = {AccountModule.class})
public interface AccountComponent {
    void inject(AccountActivity accountActivity);
}
