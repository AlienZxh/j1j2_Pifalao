package com.j1j2.data.http.api;

import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.OnlinePayResult;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.SubmitOrderReturn;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.OrderOnlinePayBody;
import com.j1j2.data.model.requestbody.OrderSubmitBody;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface ShopCartApi {


    @POST("ShopCart/QueryValidFreight")
    Observable<WebReturn<List<FreightType>>> queryValidFreight(@Query("serviceId") int serviceId, @Query("shopId") int shopId);

    @POST("ShopCart/QueryShopCart")
    Observable<WebReturn<List<ShopCartItem>>> queryShopCart(@Query("serviceId") int serviceId, @Query("shopId") int shopId);

    @POST("ShopCart/AddItemToShopCart")
    Observable<WebReturn<String>> addItemToShopCart(@Query("productId") int productId, @Query("quantity") int quantity, @Query("serviceId") int serviceId, @Query("shopId") int shopId);

    @POST("ShopCart/UpdateShopCart")
    Observable<WebReturn<String>> updateShopCart(@Body List<ShopCartItem> shopCartItems, @Query("serviceId") int serviceId, @Query("shopId") int shopId);

    @POST("ShopCart/RemoveShopCartItem")
    Observable<WebReturn<String>> removeShopCartItem(@Query("productId") int productId, @Query("serviceId") int serviceId,@Query("shopId") int shopId);

    @POST("ShopCart/SubmitOrderV6")
    Observable<WebReturn<SubmitOrderReturn>> submitOrder(@Body OrderSubmitBody orderSubmitBody);

    @POST("ShopCart/DoPayOrder")
    Observable<WebReturn<OnlinePayResult>> doPayOrder(@Body OrderOnlinePayBody orderOnlinePayBody);

    @POST("ShopCart/QueryPayState")
    Observable<WebReturn<String>> queryPayState(@Query("orderNO") String orderNo, @Query("payType") int payType);
    //______________________________________________________________________________________________

    @POST("ShopCart/ClearShopCart")
    Observable<String> clearShopCart(@Query("serviceId") int serviceId,@Query("shopId") int shopId);

    @POST("ShopCart/ValidateSubmitOrder")
    Observable<String> validateSubmitOrder();

    @POST("ShopCart/QueryCouponEffective")
    Observable<String> queryCouponEffective(@Query("guid") int guid, @Query("serviceId") int serviceId,@Query("shopId") int shopId);

}
