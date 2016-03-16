package com.j1j2.pifalao.feature.location.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.city.CityActivity;
import com.j1j2.pifalao.feature.location.LocationActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-12.
 */
@ActivityScope
@Subcomponent(modules = {LocationModule.class})
public interface LocationComponent {

    void inject(LocationActivity locationActivity);
}
