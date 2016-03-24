package com.j1j2.pifalao.feature.walletmanager.di;

import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.walletmanager.WalletManagerActivity;
import com.j1j2.pifalao.feature.walletmanager.WalletManagerViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-23.
 */
@Module
public class WalletManagerModule {
    private WalletManagerActivity walletManagerActivity;
    private com.j1j2.data.model.Module module;

    public WalletManagerModule(WalletManagerActivity walletManagerActivity, com.j1j2.data.model.Module module) {
        this.walletManagerActivity = walletManagerActivity;
        this.module = module;
    }

    @Provides
    @ActivityScope
    WalletManagerActivity walletManagerActivity() {
        return walletManagerActivity;
    }

    @Provides
    @ActivityScope
    com.j1j2.data.model.Module module() {
        return module;
    }

    @Provides
    @ActivityScope
    WalletManagerViewModel walletManagerViewModel(WalletManagerActivity walletManagerActivity, UserCouponApi userCouponApi, com.j1j2.data.model.Module module) {
        return new WalletManagerViewModel(walletManagerActivity, userCouponApi, module);
    }

    @Provides
    @ActivityScope
    UserCouponApi userCouponApi(Retrofit retrofit) {
        return retrofit.create(UserCouponApi.class);
    }
}
