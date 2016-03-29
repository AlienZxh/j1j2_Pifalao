package com.j1j2.data.http.api;

import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.OrderStatistics;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
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
    Observable<WebReturn<PagerManager<OrderSimple>>> queryOrders(@Query("pageIndex") String pageIndex, @Query("size") String size, @Query("stateStr") String stateStr);

    @POST("UserOrder/QueryProductOrderStatistics")
    Observable<WebReturn<OrderStatistics>> queryProductOrderStatistics();

    @POST("UserOrder/QueryOrderByOrderId")
    Observable<WebReturn<OrderSimple>> queryOrderByOrderId(@Query("orderIdStr") String orderIdStr);

    @POST("UserOrder/SetOrderReadState")
    Observable<WebReturn<String>> setOrderReadState(@Body SetOrderReadStateBody setOrderReadStateBody);

    @POST("UserOrder/CancleOrder")
    Observable<WebReturn<String>> cancleOrder(@Query("orderId") int orderId);
    //_____________________________________________________________________________


    @POST("UserOrder/QueryOrderProudctDetails")
    Observable<String> queryOrderProudctDetails(@Query("orderIdStr") String orderIdStr);


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
