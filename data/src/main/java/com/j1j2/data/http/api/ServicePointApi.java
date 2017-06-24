package com.j1j2.data.http.api;

import com.j1j2.data.model.City;
import com.j1j2.data.model.Shop;
import com.j1j2.data.model.ShopExpressConfig;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.data.model.WebReturn;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface ServicePointApi {

    @POST("ServicePoint/QueryServiceCity")
    Observable<WebReturn<List<City>>> queryServiceCity();

    @POST("ServicePoint/QueryServicePointInCity")
    Observable<WebReturn<List<Shop>>> queryServicePointInCity(@Query("city") int city);

    @POST("ServicePoint/QueryCityDistric")
    Observable<WebReturn<List<City>>> queryCityDistric(@Query("cityId") int cityId);

    @POST("ServicePoint/QueryShopsInArea")
    Observable<WebReturn<List<Shop>>> queryShopsInArea(@Query("minLat") double minLat, @Query("maxLat") double maxLat, @Query("minLng") double minLng, @Query("maxLng") double maxLng);

    @POST("ServicePoint/QueryServicePointServiceModules")
    Observable<WebReturn<List<ShopSubscribeService>>> queryServicePointServiceModules(@Query("shopId") int shopId);

    @POST("ServicePoint/QueryShopById")
    Observable<WebReturn<Shop>> queryShopById(@Query("shopId") int shopId);

    @POST("ServicePoint/QueryShopExpressConfig")
    Observable<WebReturn<ShopExpressConfig>> queryShopExpressConfig(@Query("shopId") int shopId);
    //__________________________________________________________________________________

    @POST("ServicePoint/QueryServicePointWithDistanceInArea")
    Observable<String> queryServicePointWithDistanceInArea(@Query("minLat") double minLat, @Query("maxLat") double maxLat, @Query("minLng") double minLng, @Query("maxLng") double maxLng, @Query("endLat") double endLat, @Query("endLng") double endLng);

    @POST("ServicePoint/QueryServiceArea")
    Observable<String> queryServiceArea(@Query("cityId") int cityId);


}
