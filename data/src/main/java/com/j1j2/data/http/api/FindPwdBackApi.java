package com.j1j2.data.http.api;

import com.j1j2.data.model.WebReturn;
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

    @POST("FindPwdBack/FindPassWordStepTwo")
    Observable<WebReturn<Integer>> findPassWordStepTwo(@Body FindPwdStepTwoBody findPwdStepTwoBody);

    @POST("FindPwdBack/FindPassWordStepThird")
    Observable<WebReturn<String>> findPassWordStepThird(@Body RestUserPwdBody restUserPwdBody);

    @POST("FindPwdBack/QuerySmsCode")
    Observable<WebReturn<String>> querySmsCode(@Query("phone") String phone);
    //___________________________________________________________________________
    @POST("FindPwdBack/FindPassWordStepOne")
    Observable<String> findPassWordStepOne(@Body FindPwdStepOneBody findPwdStepOneBody);


}
