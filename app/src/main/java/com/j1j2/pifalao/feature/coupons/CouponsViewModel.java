package com.j1j2.pifalao.feature.coupons;

import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-23.
 */
public class CouponsViewModel {
    private CouponsActivity couponsActivity;
    private UserCouponApi userCouponApi;


    public CouponsViewModel(CouponsActivity couponsActivity, UserCouponApi userCouponApi) {
        this.couponsActivity = couponsActivity;
        this.userCouponApi = userCouponApi;
    }

    public void queryAllCoupons(int couponType, ShopSubscribeService shopSubscribeService) {
//        userCouponApi.queryUserCoupon(couponType, "" + (null == shopSubscribeService ? "" : shopSubscribeService.getServiceId()))
//                .compose(couponsActivity.<WebReturn<List<Coupon>>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new WebReturnSubscriber<List<Coupon>>() {
//                    @Override
//                    public void onWebReturnSucess(List<Coupon> coupons) {
//                        couponsActivity.initCoupons(coupons);
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
        couponsActivity.initCoupons(new ArrayList<Coupon>());
    }

}
