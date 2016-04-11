package com.j1j2.pifalao.feature.vegetablesort.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.shopcart.di.ShopCartModule;
import com.j1j2.pifalao.feature.vegetablesort.VegetableSortFragment;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-4-6.
 */
@ActivityScope
@Subcomponent(modules = {VegetableSortModule.class})
public interface VegetableSortComponent {
    void inject(VegetableSortFragment vegetableSortFragment);
}
