package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.di;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.DeliveryHomeServicepointFragment;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.DeliveryServicepointViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-8.
 */
@Module
public class DeliveryServicepointModule {
    private DeliveryHomeServicepointFragment deliveryHomeServicepointFragment;
    private ServicePoint servicePoint;


    public DeliveryServicepointModule(DeliveryHomeServicepointFragment deliveryHomeServicepointFragment, ServicePoint servicePoint) {
        this.deliveryHomeServicepointFragment = deliveryHomeServicepointFragment;
        this.servicePoint = servicePoint;
    }

    @Provides
    @ActivityScope
    ServicePointApi servicePointApi(Retrofit retrofit) {
        return retrofit.create(ServicePointApi.class);
    }

    @Provides
    @ActivityScope
    DeliveryHomeServicepointFragment deliveryHomeServicepointFragment() {
        return deliveryHomeServicepointFragment;
    }

    @Provides
    @ActivityScope
    ServicePoint servicePoint() {
        return servicePoint;
    }

    @Provides
    @ActivityScope
    DeliveryServicepointViewModel deliveryServicepointViewModel(DeliveryHomeServicepointFragment deliveryHomeServicepointFragment, ServicePointApi servicePointApi, ServicePoint servicePoint) {
        return new DeliveryServicepointViewModel(deliveryHomeServicepointFragment, servicePointApi, servicePoint);
    }
}
