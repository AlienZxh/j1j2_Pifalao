package com.j1j2.pifalao.feature.home.offlinemodulehome.di;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.pifalao.app.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-5-11.
 */
@Module
public class OfflineModuleHomeModule {

    @Provides
    @ActivityScope
    ServicePointApi servicePointApi(Retrofit retrofit) {
        return retrofit.create(ServicePointApi.class);
    }
}
