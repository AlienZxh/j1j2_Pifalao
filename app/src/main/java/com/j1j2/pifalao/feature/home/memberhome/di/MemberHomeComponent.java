package com.j1j2.pifalao.feature.home.memberhome.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.memberhome.MemberHomeActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-8-24.
 */
@ActivityScope
@Subcomponent(modules = {MemberHomeModule.class})
public interface MemberHomeComponent {
    void inject(MemberHomeActivity memberHomeActivity);


}
