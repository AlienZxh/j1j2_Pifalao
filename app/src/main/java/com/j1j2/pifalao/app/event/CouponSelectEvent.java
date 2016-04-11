package com.j1j2.pifalao.app.event;

import com.j1j2.data.model.Coupon;

/**
 * Created by alienzxh on 16-4-7.
 */
public class CouponSelectEvent {
    private final int couponType;
    private final boolean hasSelected;
    private final Coupon selectedCoupon;

    public CouponSelectEvent(int couponType, boolean hasSelected, Coupon selectedCoupon) {
        this.couponType = couponType;
        this.hasSelected = hasSelected;
        this.selectedCoupon = selectedCoupon;
    }

    public int getCouponType() {
        return couponType;
    }

    public boolean isHasSelected() {
        return hasSelected;
    }

    public Coupon getSelectedCoupon() {
        return selectedCoupon;
    }
}
