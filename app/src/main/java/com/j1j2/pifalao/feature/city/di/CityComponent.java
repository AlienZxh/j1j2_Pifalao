package com.j1j2.pifalao.feature.city.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.city.CityActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-11.
 */

@ActivityScope
@Subcomponent(modules = {CityModule.class})
public interface CityComponent {

    void inject(CityActivity cityActivity);
}
