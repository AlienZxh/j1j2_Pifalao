package com.j1j2.pifalao.feature.catservicepoint.di;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.catservicepoint.CatServicePointActivity;
import com.j1j2.pifalao.feature.catservicepoint.CatServicePointViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-29.
 */
@Module
public class CatServicePointModule {

    private CatServicePointActivity catServicePointActivity;

    public CatServicePointModule(CatServicePointActivity catServicePointActivity) {
        this.catServicePointActivity = catServicePointActivity;
    }

    @Provides
    @ActivityScope
    CatServicePointActivity catServicePointActivity() {
        return catServicePointActivity;
    }

    @Provides
    @ActivityScope
    ServicePointApi servicePointApi(Retrofit retrofit) {
        return retrofit.create(ServicePointApi.class);
    }


    @Provides
    @ActivityScope
    CatServicePointViewModel catServicePointViewModel(CatServicePointActivity catServicePointActivity, ServicePointApi servicePointApi) {
        return new CatServicePointViewModel(catServicePointActivity, servicePointApi);
    }
}
