package com.j1j2.pifalao.feature.guide.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.guide.GuideActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alienzxh on 16-3-25.
 */
@Module
public class GuideModule {

    private GuideActivity guideActivity;

    public GuideModule(GuideActivity guideActivity) {
        this.guideActivity = guideActivity;
    }

    @Provides
    @ActivityScope
    GuideActivity guideActivity() {
        return guideActivity;
    }
}
