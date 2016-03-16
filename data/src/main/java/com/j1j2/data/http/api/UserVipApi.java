package com.j1j2.data.http.api;

import com.j1j2.data.model.requestbody.ChangeUserPwdBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserVipApi {

    @POST("UserVip/RenewVip")
    Observable<String> renewVip(@Query("code")  String code);

    @POST("UserVip/QueryLoginDimensionalCode")
    Observable<String> queryLoginDimensionalCode();

    @POST("UserVip/ChangeUserPwd")
    Observable<String> changeUserPwd(@Body ChangeUserPwdBody changeUserPwdBody);

}
