package com.j1j2.pifalao.feature.individualcenter.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.storestylehome.di.StoreStyleHomeModule;
import com.j1j2.pifalao.feature.individualcenter.IndividualCenterFragment;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-21.
 */
@ActivityScope
@Subcomponent(modules = {IndividualCenterModule.class})
public interface IndividualCenterComponent {

    void inject(IndividualCenterFragment individualCenterFragment);
}
