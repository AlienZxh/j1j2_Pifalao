package com.j1j2.pifalao.feature.home.storestylehome.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.storestylehome.StoreStyleHomeActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-14.
 */
@ActivityScope
@Subcomponent(modules = {StoreStyleHomeModule.class})
public interface StoreStyleHomeComponent {

    void inject(StoreStyleHomeActivity storeStyleHomeActivity);
}
