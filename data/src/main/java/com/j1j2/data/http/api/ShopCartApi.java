package com.j1j2.data.http.api;

import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.WebReturn;
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


    @POST("ShopCart/QueryShopCart")
    Observable<WebReturn<List<ShopCartItem>>> queryShopCart(@Query("moduleId") int moduleId);

    //______________________________________________________________________________________________

    @POST("ShopCart/QueryUserServicePoint")
    Observable<String> queryUserServicePoint();

    @POST("ShopCart/QueryValidFreight")
    Observable<String> queryValidFreight();


    @POST("ShopCart/AddItemToShopCart")
    Observable<String> addItemToShopCart(@Query("productId") int productId, @Query("quantity") int quantity, @Query("moduleId") int moduleId);

    @POST("ShopCart/UpdateShopCart")
    Observable<String> updateShopCart();//参数待完善

    @POST("ShopCart/RemoveShopCartItem")
    Observable<String> removeShopCartItem(@Query("productId") int productId);

    @POST("ShopCart/ClearShopCart")
    Observable<String> clearShopCart(@Query("moduleId") int moduleId);

    @POST("ShopCart/SubmitOrder")
    Observable<String> submitOrder(@Body OrderSubmitBody orderSubmitBody);


    @POST("ShopCart/ValidateSubmitOrder")
    Observable<String> validateSubmitOrder();


    @POST("ShopCart/QueryCouponEffective")
    Observable<String> queryCouponEffective(@Query("guid") int guid, @Query("moduleId") int moduleId);


}
