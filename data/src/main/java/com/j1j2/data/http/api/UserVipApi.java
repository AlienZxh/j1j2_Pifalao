package com.j1j2.data.http.api;

import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ChangeUserPwdBody;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserVipApi {

    @POST("UserVip/QueryLoginDimensionalCode")
    Observable<WebReturn<String>> queryLoginDimensionalCode();

    @POST("UserVip/ChangeUserPwd")
    Observable<WebReturn<String>> changeUserPwd(@Body ChangeUserPwdBody changeUserPwdBody);

    @POST("UserVip/RenewVip")
    Observable<WebReturn<String>> renewVip(@Query("code") String code);
    //_____________________________________________________________________


}
