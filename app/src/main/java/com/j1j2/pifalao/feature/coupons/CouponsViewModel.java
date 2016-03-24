package com.j1j2.pifalao.feature.coupons;

import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.data.model.Module;

/**
 * Created by alienzxh on 16-3-23.
 */
public class CouponsViewModel {
    private CouponsActivity couponsActivity;
    private UserCouponApi userCouponApi;
    private Module module;

    public CouponsViewModel(CouponsActivity couponsActivity, UserCouponApi userCouponApi, Module module) {
        this.couponsActivity = couponsActivity;
        this.userCouponApi = userCouponApi;
        this.module = module;
    }



}
