package com.j1j2.pifalao.feature.couponselect;

import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-4-3.
 */
public class CouponSelectViewModel {

    private CouponSelectActivity couponSelectActivity;
    private UserCouponApi userCouponApi;

    public CouponSelectViewModel(CouponSelectActivity couponSelectActivity, UserCouponApi userCouponApi) {
        this.couponSelectActivity = couponSelectActivity;
        this.userCouponApi = userCouponApi;
    }

    public void queryCoupons(int couponType, int moduleId) {
//        userCouponApi.queryUserCoupon(couponType, "" + moduleId)
//                .compose(couponSelectActivity.<WebReturn<List<Coupon>>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new WebReturnSubscriber<List<Coupon>>() {
//                    @Override
//                    public void onWebReturnSucess(List<Coupon> couponList) {
//
//                        couponSelectActivity.setAdapter(couponList);
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
        couponSelectActivity.setAdapter(new ArrayList<Coupon>());
    }
}
