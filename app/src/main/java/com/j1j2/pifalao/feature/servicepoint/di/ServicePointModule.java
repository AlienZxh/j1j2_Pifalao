package com.j1j2.pifalao.feature.servicepoint.di;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.pifalao.app.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-12.
 */
@Module
public class ServicePointModule {

    @Provides
    @ActivityScope
    ServicePointApi servicePointApi(Retrofit retrofit) {
        return retrofit.create(ServicePointApi.class);
    }
}
