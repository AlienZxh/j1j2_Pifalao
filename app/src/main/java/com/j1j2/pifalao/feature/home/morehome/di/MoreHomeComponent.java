package com.j1j2.pifalao.feature.home.morehome.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.morehome.MoreHomeActivity;
import com.j1j2.pifalao.feature.home.storestylehome.di.StoreStyleHomeModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-4-12.
 */
@ActivityScope
@Subcomponent(modules = {MoreHomeModule.class})
public interface MoreHomeComponent {
    void inject(MoreHomeActivity moreHomeActivity);
}
