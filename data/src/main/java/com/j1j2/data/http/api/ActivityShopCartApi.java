package com.j1j2.data.http.api;

import com.j1j2.data.model.ActivityOrderSimple;
import com.j1j2.data.model.OnlinePayResult;
import com.j1j2.data.model.SubmitOrderReturn;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ActivityOrderSubmitBody;
import com.j1j2.data.model.requestbody.OrderOnlinePayBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-9-7.
 */
public interface ActivityShopCartApi {
    @POST("ActivityShopCart/SubmitActivityOrders")
    Observable<WebReturn<SubmitOrderReturn>> submitActivityOrders(@Body ActivityOrderSubmitBody activityOrderSubmitBody);


    @POST("ActivityShopCart/PayAcitityOrder")
    Observable<WebReturn<OnlinePayResult>> payAcitityOrder(@Body OrderOnlinePayBody orderOnlinePayBody);

    @POST("ActivityShopCart/QueryActivityOrder")
    Observable<WebReturn<ActivityOrderSimple>> queryActivityOrder(@Query("orderNO") String orderNO);

    @POST("ActivityShopCart/CancleActivityOrder")
    Observable<WebReturn<String>> cancleActivityOrder(@Query("orderNO") String orderNO);
}
