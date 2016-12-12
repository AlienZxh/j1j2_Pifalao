package com.j1j2.pifalao.feature.home.viphome.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.viphome.VipHomeActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-4-13.
 */
@ActivityScope
@Subcomponent(modules = {VipHomeModule.class})
public interface VipHomeComponent {
    void inject(VipHomeActivity vipHomeActivity);
}
