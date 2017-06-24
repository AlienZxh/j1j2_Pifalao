package com.j1j2.data.http.api;

import com.j1j2.data.model.DeliveryServiceTime;
import com.j1j2.data.model.UserDeliveryTime;
import com.j1j2.data.model.WebReturn;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface CountDownApi {

    @POST("CountDown/QueryDeliveryCountDownOfModule")
    Observable<WebReturn<UserDeliveryTime>> QueryDeliveryCountDownOfModule(@Query("serviceId") int serviceId);

    @POST("CountDown/QueryUserDeliveryTime")
    Observable<WebReturn<UserDeliveryTime>> queryUserDeliveryTime();

    @POST("CountDown/QueryServiceTimeOfDliveryType")
    Observable<WebReturn<List<DeliveryServiceTime>>> queryServiceTimeOfDliveryType(@Query("freightId") int freightId);
    //__________________________________________________________________________________________


}
