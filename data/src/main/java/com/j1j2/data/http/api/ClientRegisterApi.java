package com.j1j2.data.http.api;

import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ClientRegisterSetpTwoBody;
import com.j1j2.data.model.requestbody.ClientRegisterStepOneBody;
import com.j1j2.data.model.requestbody.LoginBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface ClientRegisterApi {

    @POST("ClientRegister/ValidatePhone")
    Observable<WebReturn<String>> validatePhone(@Query("phone") String phone);

    @POST("ClientRegister/QuerySmsCode")
    Observable<WebReturn<String>> querySmsCode(@Query("phone") String phone);

    @POST("ClientRegister/ValidatePhoneSmsCode")
    Observable<WebReturn<String>> validatePhoneSmsCode(@Body ClientRegisterStepOneBody clientRegisterStepOneBody);

    @POST("ClientRegister/ClientRegisterStepOne")
    Observable<WebReturn<String>> clientRegisterStepOne(@Body ClientRegisterStepOneBody clientRegisterStepOneBody);

    @POST("ClientRegister/ClientRegisterStepTwo")
    Observable<WebReturn<String>> ClientRegisterStepTwo(@Body ClientRegisterSetpTwoBody clientRegisterSetpTwoBody);




}
