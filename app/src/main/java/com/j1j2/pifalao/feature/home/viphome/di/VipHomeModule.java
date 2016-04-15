package com.j1j2.pifalao.feature.home.viphome.di;

import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.pifalao.app.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-13.
 */
@Module
public class VipHomeModule {
    @Provides
    @ActivityScope
    UserVipApi userVipApi(Retrofit retrofit) {
        return retrofit.create(UserVipApi.class);
    }
}
