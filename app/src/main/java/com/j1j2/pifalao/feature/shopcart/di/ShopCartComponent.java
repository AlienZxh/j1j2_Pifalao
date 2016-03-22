package com.j1j2.pifalao.feature.shopcart.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.main.di.MainModule;
import com.j1j2.pifalao.feature.shopcart.ShopCartActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-21.
 */
@ActivityScope
@Subcomponent(modules = {ShopCartModule.class})
public interface ShopCartComponent {

    void inject(ShopCartActivity shopCartActivity);
}
