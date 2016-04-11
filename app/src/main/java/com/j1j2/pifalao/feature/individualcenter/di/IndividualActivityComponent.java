package com.j1j2.pifalao.feature.individualcenter.di;

import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserMessageApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.individualcenter.IndividualCenterActivity;

import dagger.Provides;
import dagger.Subcomponent;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-8.
 */
@ActivityScope
@Subcomponent(modules = {IndividualActivityModule.class})
public interface IndividualActivityComponent {
    void inject(IndividualCenterActivity individualCenterActivity);
}
