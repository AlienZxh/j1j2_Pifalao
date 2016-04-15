package com.j1j2.pifalao.feature.offlineorders.di;

import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.offlineorders.OfflineOrdersActivity;
import com.j1j2.pifalao.feature.offlineorders.OfflineOrdersViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-14.
 */
@Module
public class OfflineOrdersModule {

    private OfflineOrdersActivity offlineOrdersActivity;

    public OfflineOrdersModule(OfflineOrdersActivity offlineOrdersActivity) {
        this.offlineOrdersActivity = offlineOrdersActivity;
    }

    @Provides
    @ActivityScope
    UserOrderApi userOrderApi(Retrofit retrofit) {
        return retrofit.create(UserOrderApi.class);
    }

    @Provides
    @ActivityScope
    OfflineOrdersActivity offlineOrdersActivity() {
        return offlineOrdersActivity;
    }

    @Provides
    @ActivityScope
    OfflineOrdersViewModel offlineOrdersViewModel(OfflineOrdersActivity offlineOrdersActivity, UserOrderApi userOrderApi) {
        return new OfflineOrdersViewModel(offlineOrdersActivity, userOrderApi);
    }
}
