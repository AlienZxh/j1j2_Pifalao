package com.j1j2.pifalao.feature.confirmorder.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.confirmorder.ConfirmOrderActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-21.
 */
@ActivityScope
@Subcomponent(modules = {ConfirmOrderModule.class})
public interface ConfirmOrderComponent {

    void inject(ConfirmOrderActivity confirmOrderActivity);

}

