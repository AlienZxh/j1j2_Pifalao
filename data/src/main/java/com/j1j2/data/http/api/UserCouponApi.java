package com.j1j2.data.http.api;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserCouponApi {

    @POST("UserCoupon/QueryUserCoupon")
    Observable<String> queryUserCoupon(@Query("couponType") int couponType, @Query("moduleId") int moduleId);
}
