package com.j1j2.pifalao.feature.guide.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.guide.GuideActivity;
import com.j1j2.pifalao.feature.launch.di.LaunchModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-25.
 */
@ActivityScope
@Subcomponent(modules = {GuideModule.class})
public interface GuideComponent {
    void inject(GuideActivity guideActivity);
}
