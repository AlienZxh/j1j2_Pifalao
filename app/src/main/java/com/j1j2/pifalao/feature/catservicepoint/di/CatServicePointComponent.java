package com.j1j2.pifalao.feature.catservicepoint.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.catservicepoint.CatServicePointActivity;
import com.j1j2.pifalao.feature.changepassword.di.ChangePasswordModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-29.
 */
@ActivityScope
@Subcomponent(modules = {CatServicePointModule.class})
public interface CatServicePointComponent {

    void inject(CatServicePointActivity catServicePointActivity);

}
