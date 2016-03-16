package com.j1j2.pifalao.feature.city.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.city.CityActivity;
import com.j1j2.pifalao.feature.city.CityViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-11.
 */
@Module
public class CityModule {

    private CityActivity cityActivity;

    public CityModule(CityActivity cityActivity) {
        this.cityActivity = cityActivity;
    }

    @Provides
    @ActivityScope
    CityActivity cityActivity() {
        return this.cityActivity;
    }


    @Provides
    @ActivityScope
    ServicePointApi servicePointApi(Retrofit retrofit) {
        return retrofit.create(ServicePointApi.class);
    }

    @Provides
    @ActivityScope
    CityViewModel cityViewModel(ServicePointApi servicePointApi, CityActivity cityActivity) {
        return new CityViewModel(servicePointApi, cityActivity);
    }

}
