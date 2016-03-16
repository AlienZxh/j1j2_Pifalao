package com.j1j2.pifalao.feature.servicepoint.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.servicepoint.ServicePointActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-12.
 */
@ActivityScope
@Subcomponent(modules = {ServicePointModule.class})
public interface ServicePointComponent {

    void inject(ServicePointActivity servicePointActivity);
}
