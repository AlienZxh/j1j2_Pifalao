package com.j1j2.data.http.api;

import com.j1j2.data.model.UserDeliveryTime;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ClientRegisterStepOneBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface CountDownApi {

    @POST("CountDown/QueryUserDeliveryTime")
    Observable<WebReturn<UserDeliveryTime>> queryUserDeliveryTime();

    //__________________________________________________________________________________________

    @POST("CountDown/QueryServiceTimeOfDliveryType")
    Observable<String> queryServiceTimeOfDliveryType(@Query("deliveryType") int deliveryType, @Query("moduleId") int moduleId);


}
