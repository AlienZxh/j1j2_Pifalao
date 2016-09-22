package com.j1j2.pifalao.feature.prize.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.prize.PrizeActivity;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-9-18.
 */
@ActivityScope
@Subcomponent(modules = {PrizeModule.class})
public interface PrizeComponent {
    void inject(PrizeActivity prizeActivity);
}
