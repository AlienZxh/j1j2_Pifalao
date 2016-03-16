package com.j1j2.pifalao.feature.services.di;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.services.ServicesActivity;
import com.j1j2.pifalao.feature.services.ServicesViewModule;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-12.
 */
@Module
public class ServicesModule {

    private ServicesActivity servicesActivity;

    private ServicePoint servicePoint;

    public ServicesModule(ServicesActivity servicesActivity, ServicePoint servicePoint) {
        this.servicesActivity = servicesActivity;
        this.servicePoint = servicePoint;
    }

    @Provides
    @ActivityScope
    ServicesActivity servicesActivity() {
        return servicesActivity;
    }

    @Provides
    @ActivityScope
    ServicePointApi servicePointApi(Retrofit retrofit) {
        return retrofit.create(ServicePointApi.class);
    }

    @Provides
    @ActivityScope
    ServicePoint servicePoint() {
        return servicePoint;
    }

    @Provides
    @ActivityScope
    ServicesViewModule servicesViewModule(ServicesActivity servicesActivity, ServicePointApi servicePointApi, ServicePoint servicePoint) {
        return new ServicesViewModule(servicesActivity, servicePointApi, servicePoint);
    }
}