package com.j1j2.pifalao.feature.servicepoint.di;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.servicepoint.ServicePointActivity;
import com.j1j2.pifalao.feature.servicepoint.ServicePointViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-12.
 */
@Module
public class ServicePointModule {

    private ServicePointActivity servicePointActivity;
    private ServicePoint servicePoint;


    public ServicePointModule(ServicePointActivity servicePointActivity, ServicePoint servicePoint) {
        this.servicePointActivity = servicePointActivity;
        this.servicePoint = servicePoint;
    }

    @Provides
    @ActivityScope
    ServicePointApi servicePointApi(Retrofit retrofit) {
        return retrofit.create(ServicePointApi.class);
    }

    @Provides
    @ActivityScope
    ServicePointActivity servicePointActivity() {
        return servicePointActivity;
    }

    @Provides
    @ActivityScope
    ServicePoint servicePoint() {
        return servicePoint;
    }

    @Provides
    @ActivityScope
    ServicePointViewModel servicePointViewModel(ServicePointActivity servicePointActivity, ServicePointApi servicePointApi, ServicePoint servicePoint) {
        return new ServicePointViewModel(servicePointActivity, servicePointApi, servicePoint);
    }
}
