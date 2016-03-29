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


    public WalletManagerModule(WalletManagerActivity walletManagerActivity) {
        this.walletManagerActivity = walletManagerActivity;

    }

    @Provides
    @ActivityScope
    WalletManagerActivity walletManagerActivity() {
        return walletManagerActivity;
    }


    @Provides
    @ActivityScope
    WalletManagerViewModel walletManagerViewModel(WalletManagerActivity walletManagerActivity, UserCouponApi userCouponApi) {
        return new WalletManagerViewModel(walletManagerActivity, userCouponApi);
    }

    @Provides
    @ActivityScope
    UserCouponApi userCouponApi(Retrofit retrofit) {
        return retrofit.create(UserCouponApi.class);
    }
}
