package com.j1j2.pifalao.feature.services.di;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.SystemAssistApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserVipApi;
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
    UserVipApi userVipApi(Retrofit retrofit) {
        return retrofit.create(UserVipApi.class);
    }

    @Provides
    @ActivityScope
    UserLoginApi userLoginApi(Retrofit retrofit) {
        return retrofit.create(UserLoginApi.class);
    }

    @Provides
    @ActivityScope
    ServicePoint servicePoint() {
        return servicePoint;
    }

    @Provides
    @ActivityScope
    SystemAssistApi systemAssistApi(Retrofit retrofit) {
        return retrofit.create(SystemAssistApi.class);
    }

    @Provides
    @ActivityScope
    ServicesViewModule servicesViewModule(ServicesActivity servicesActivity, ServicePointApi servicePointApi, ServicePoint servicePoint, UserVipApi userVipApi, UserLoginApi userLoginApi,SystemAssistApi systemAssistApi) {
        return new ServicesViewModule(servicesActivity, servicePointApi, servicePoint, userVipApi, userLoginApi,systemAssistApi);
    }
}
