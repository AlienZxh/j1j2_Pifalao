package com.j1j2.pifalao.feature.home.viphome.di;

import com.j1j2.data.http.api.UserMessageApi;
import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.viphome.VipHomeActivity;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualActivityModule;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-13.
 */
@ActivityScope
@Subcomponent(modules = {VipHomeModule.class})
public interface VipHomeComponent {
    void inject(VipHomeActivity vipHomeActivity);
}
