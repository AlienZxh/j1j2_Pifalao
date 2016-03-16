package com.j1j2.data.http.api;

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
    @POST("ClientRegister/ValidatePhoneSmsCode")
    Observable<String> validatePhoneSmsCode(@Body ClientRegisterStepOneBody clientRegisterStepOneBody);

    @POST("ClientRegister/ClientRegisterStepOne")
    Observable<String> clientRegisterStepOne(@Body ClientRegisterStepOneBody clientRegisterStepOneBody);

    @POST("ClientRegister/ClientRegisterStepTwo")
    Observable<String> ClientRegisterStepTwo(@Body ClientRegisterSetpTwoBody clientRegisterSetpTwoBody);

    @POST("ClientRegister/QuerySmsCode")
    Observable<String> querySmsCode(@Query("phone") String phone);

    @POST("ClientRegister/ValidatePhone")
    Observable<String> validatePhone(@Query("phone") String phone);
}
