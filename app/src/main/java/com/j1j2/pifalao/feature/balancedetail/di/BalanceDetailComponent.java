package com.j1j2.pifalao.feature.balancedetail.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.balancedetail.BalanceDetailActivity;
import com.j1j2.pifalao.feature.catservicepoint.di.CatServicePointModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-5-12.
 */
@ActivityScope
@Subcomponent(modules = {BalanceDetailModule.class})
public interface BalanceDetailComponent {

    void inject(BalanceDetailActivity balanceDetailActivity);
}
