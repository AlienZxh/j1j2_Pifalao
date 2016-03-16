package com.j1j2.data.http.api;

import com.j1j2.data.model.requestbody.FindPwdStepOneBody;
import com.j1j2.data.model.requestbody.FindPwdStepTwoBody;
import com.j1j2.data.model.requestbody.RestUserPwdBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface FindPwdBackApi {
    @POST("FindPwdBack/FindPassWordStepOne")
    Observable<String> findPassWordStepOne(@Body FindPwdStepOneBody findPwdStepOneBody);

    @POST("FindPwdBack/FindPassWordStepTwo")
    Observable<String> findPassWordStepTwo(@Body FindPwdStepTwoBody findPwdStepTwoBody);

    @POST("FindPwdBack/FindPassWordStepThird")
    Observable<String> findPassWordStepThird(@Body RestUserPwdBody restUserPwdBody);

    @POST("FindPwdBack/QuerySmsCode")
    Observable<String> querySmsCode(@Query("phone") String phone);
}
