package com.j1j2.pifalao.feature.collects.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.collects.CollectsActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-24.
 */
@ActivityScope
@Subcomponent(modules = {CollectsModule.class})
public interface CollectsComponent {

    void inject(CollectsActivity collectsActivity);

}
