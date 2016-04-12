package com.j1j2.pifalao.feature.home.morehome.di;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.pifalao.app.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-12.
 */
@Module
public class MoreHomeModule {

    @Provides
    @ActivityScope
    ServicePointApi servicePointApi(Retrofit retrofit) {
        return retrofit.create(ServicePointApi.class);
    }
}
