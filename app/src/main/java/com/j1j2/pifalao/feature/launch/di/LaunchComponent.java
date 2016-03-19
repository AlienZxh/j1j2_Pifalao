package com.j1j2.pifalao.feature.launch.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.launch.LaunchActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-19.
 */
@ActivityScope
@Subcomponent(modules = {LaunchModule.class})
public interface LaunchComponent {

    void inject(LaunchActivity launchActivity);
}
