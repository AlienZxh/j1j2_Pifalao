package com.j1j2.data.http.api;

import com.j1j2.data.model.requestbody.ProductSaleOrderRateBody;
import com.j1j2.data.model.requestbody.SetOrderReadStateBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserOrderApi {
    @POST("UserOrder/QueryOrders")
    Observable<String> queryOrders(@Query("pageIndex") String pageIndex, @Query("size") String size, @Query("stateStr") String stateStr);


    @POST("UserOrder/QueryProductOrderStatistics")
    Observable<String> queryProductOrderStatistics();


    @POST("UserOrder/SetOrderReadState")
    Observable<String> setOrderReadState(@Body SetOrderReadStateBody setOrderReadStateBody);

    @POST("UserOrder/QueryOrderByOrderId")
    Observable<String> queryOrderByOrderId(@Query("orderIdStr") String orderIdStr);

    @POST("UserOrder/QueryOrderProudctDetails")
    Observable<String> queryOrderProudctDetails(@Query("orderIdStr") String orderIdStr);

    @POST("UserOrder/CancleOrder")
    Observable<String> cancleOrder(@Query("orderId") int orderId);

    @POST("UserOrder/ConfrimReceive")
    Observable<String> confrimReceive(@Query("orderId") int orderId);

    @POST("UserOrder/OrderRate")
    Observable<String> orderRate(@Body ProductSaleOrderRateBody productSaleOrderRateBody);

    @POST("UserOrder/QueryOrderRate")
    Observable<String> queryOrderRate(@Query("orderId") int orderId);

    @POST("UserOrder/QueryOfflineOrders")
    Observable<String> queryOfflineOrders(@Query("pageIndex") int pageIndex);

    @POST("UserOrder/QueryOfflineOrderDetails")
    Observable<String> queryOfflineOrderDetails(@Query("offlineOrderId") int offlineOrderId);
}
