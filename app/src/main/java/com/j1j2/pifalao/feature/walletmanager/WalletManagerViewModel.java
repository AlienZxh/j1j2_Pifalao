package com.j1j2.pifalao.feature.walletmanager;

import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.ShopSubscribeService;

import java.util.ArrayList;

/**
 * Created by alienzxh on 16-3-23.
 */
public class WalletManagerViewModel {
    private WalletManagerActivity walletManagerActivity;
    private UserCouponApi userCouponApi;

    public WalletManagerViewModel(WalletManagerActivity walletManagerActivity, UserCouponApi userCouponApi) {
        this.walletManagerActivity = walletManagerActivity;
        this.userCouponApi = userCouponApi;
    }

    public void queryAllCoupons(ShopSubscribeService shopSubscribeService) {
//        userCouponApi.queryUserCoupon(0, "" + (null == shopSubscribeService ? "" : shopSubscribeService.getServiceId()))
//                .compose(walletManagerActivity.<WebReturn<List<Coupon>>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(new WebReturnSubscriber<List<Coupon>>() {
//                    @Override
//                    public void onWebReturnSucess(List<Coupon> coupons) {
//                        walletManagerActivity.initCoupons(coupons);
//                    }
//
//                    @Override
//                    public void onWebReturnFailure(String errorMessage) {
//
//                    }
//
//                    @Override
//                    public void onWebReturnCompleted() {
//
//                    }
//                });
        walletManagerActivity.initCoupons(new ArrayList<Coupon>());
    }

    public WalletManagerActivity getWalletManagerActivity() {
        return walletManagerActivity;
    }
}
