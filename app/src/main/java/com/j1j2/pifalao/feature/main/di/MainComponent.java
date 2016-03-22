package com.j1j2.pifalao.feature.main.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.storestylehome.StoreStyleHomeFragment;
import com.j1j2.pifalao.feature.home.storestylehome.di.StoreStyleHomeModule;
import com.j1j2.pifalao.feature.main.MainActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-16.
 */
@ActivityScope
@Subcomponent(modules = {MainModule.class})
public interface MainComponent {

    void inject(MainActivity mainActivity);

}
