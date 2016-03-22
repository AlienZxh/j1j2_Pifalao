package com.j1j2.data.http.api;

import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.WebReturn;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserCouponApi {

    @POST("UserCoupon/QueryUserCoupon")
    Observable<WebReturn<List<Coupon>>> queryUserCoupon(@Query("couponType") int couponType, @Query("moduleId") int moduleId);
}
