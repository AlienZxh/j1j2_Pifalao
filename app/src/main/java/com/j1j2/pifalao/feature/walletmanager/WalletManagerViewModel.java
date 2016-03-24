package com.j1j2.pifalao.feature.walletmanager;

import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.List;

import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-23.
 */
public class WalletManagerViewModel {
    private WalletManagerActivity walletManagerActivity;
    private UserCouponApi userCouponApi;
    private Module module;

    public WalletManagerViewModel(WalletManagerActivity walletManagerActivity, UserCouponApi userCouponApi, Module module) {
        this.walletManagerActivity = walletManagerActivity;
        this.userCouponApi = userCouponApi;
        this.module = module;
    }

    public void queryAllCoupons() {
        userCouponApi.queryUserCoupon(0, module.getWareHouseModuleId())
                .compose(walletManagerActivity.<WebReturn<List<Coupon>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new WebReturnSubscriber<List<Coupon>>() {
                    @Override
                    public void onWebReturnSucess(List<Coupon> coupons) {
                        walletManagerActivity.initCoupons(coupons);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public WalletManagerActivity getWalletManagerActivity() {
        return walletManagerActivity;
    }
}
