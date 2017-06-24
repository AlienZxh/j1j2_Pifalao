package com.j1j2.pifalao.feature.servicepoint.di;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.Shop;
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
    private Shop shop;


    public ServicePointModule(ServicePointActivity servicePointActivity, Shop shop) {
        this.servicePointActivity = servicePointActivity;
        this.shop = shop;
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
    Shop servicePoint() {
        return shop;
    }

    @Provides
    @ActivityScope
    ServicePointViewModel servicePointViewModel(ServicePointActivity servicePointActivity, ServicePointApi servicePointApi, Shop shop) {
        return new ServicePointViewModel(servicePointActivity, servicePointApi, shop);
    }
}
