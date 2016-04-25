package com.j1j2.pifalao.feature.findpsw.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.findpsw.FindPSWActivity;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualActivityModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-4-22.
 */
@ActivityScope
@Subcomponent(modules = {FindPSWModule.class})
public interface FindPSWComponent {
    void inject(FindPSWActivity findPSWActivity);
}
