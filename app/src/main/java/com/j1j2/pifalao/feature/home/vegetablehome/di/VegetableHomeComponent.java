package com.j1j2.pifalao.feature.home.vegetablehome.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.storestylehome.di.StoreStyleHomeModule;
import com.j1j2.pifalao.feature.home.vegetablehome.VegetableHomeFragment;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-4-5.
 */
@ActivityScope
@Subcomponent(modules = {VegetableHomeModule.class})
public interface VegetableHomeComponent {
    void inject(VegetableHomeFragment vegetableHomeFragment);
}
