package com.j1j2.pifalao.feature.balancedetail.di;

import com.j1j2.data.http.api.UserFinancialApi;
import com.j1j2.pifalao.app.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-5-12.
 */
@Module
public class BalanceDetailModule {
    @Provides
    @ActivityScope
    UserFinancialApi userFinancialApi(Retrofit retrofit) {
        return retrofit.create(UserFinancialApi.class);
    }
}
