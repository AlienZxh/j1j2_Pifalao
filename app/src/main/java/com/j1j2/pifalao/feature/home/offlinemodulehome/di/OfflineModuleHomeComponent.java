package com.j1j2.pifalao.feature.home.offlinemodulehome.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.offlinemodulehome.OfflineModuleHomeActivity;
import com.j1j2.pifalao.feature.servicepoint.di.ServicePointModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-5-11.
 */
@ActivityScope
@Subcomponent(modules = {OfflineModuleHomeModule.class})
public interface OfflineModuleHomeComponent {
    void inject(OfflineModuleHomeActivity offlineModuleHomeActivity);
}
