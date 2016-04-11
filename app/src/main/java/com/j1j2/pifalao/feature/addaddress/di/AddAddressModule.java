package com.j1j2.pifalao.feature.addaddress.di;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.UserAddressApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.addaddress.AddAddressActivity;
import com.j1j2.pifalao.feature.addaddress.AddAddressViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-31.
 */
@Module
public class AddAddressModule {
    private AddAddressActivity addAddressActivity;

    public AddAddressModule(AddAddressActivity addAddressActivity) {
        this.addAddressActivity = addAddressActivity;
    }

    @Provides
    @ActivityScope
    UserAddressApi userAddressApi(Retrofit retrofit) {
        return retrofit.create(UserAddressApi.class);
    }

    @Provides
    @ActivityScope
    ServicePointApi servicePointApi(Retrofit retrofit) {
        return retrofit.create(ServicePointApi.class);
    }


    @Provides
    @ActivityScope
    AddAddressActivity addAddressActivity() {
        return addAddressActivity;
    }

    @Provides
    @ActivityScope
    AddAddressViewModel addAddressViewModel(AddAddressActivity addAddressActivity, UserAddressApi userAddressApi, ServicePointApi servicePointApi) {
        return new AddAddressViewModel(addAddressActivity, userAddressApi, servicePointApi);
    }
}
