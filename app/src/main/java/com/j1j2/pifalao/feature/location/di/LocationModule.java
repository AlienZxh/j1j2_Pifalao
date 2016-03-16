package com.j1j2.pifalao.feature.location.di;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.City;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.location.LocationActivity;
import com.j1j2.pifalao.feature.location.LocationViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-12.
 */
@Module
public class LocationModule {

    private LocationActivity locationActivity;
    private City city;

    public LocationModule(LocationActivity locationActivity, City city) {
        this.locationActivity = locationActivity;
        this.city = city;
    }

    @Provides
    @ActivityScope
    LocationActivity locationActivity() {
        return this.locationActivity;
    }

    @Provides
    @ActivityScope
    City city() {
        return this.city;
    }

    @Provides
    @ActivityScope
    ServicePointApi servicePointApi(Retrofit retrofit) {
        return retrofit.create(ServicePointApi.class);
    }

    @Provides
    @ActivityScope
    LocationViewModel locationViewModel(LocationActivity locationActivity, City city, ServicePointApi servicePointApi) {
        return new LocationViewModel(servicePointApi, locationActivity, city);

    }


}
