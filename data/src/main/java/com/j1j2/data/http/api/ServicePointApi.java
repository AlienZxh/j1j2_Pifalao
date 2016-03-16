package com.j1j2.data.http.api;

import com.j1j2.data.model.City;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
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
    Observable<WebReturn<List<ServicePoint>>> queryServicePointInCity(@Query("city") int city);

    @POST("ServicePoint/QueryCityDistric")
    Observable<WebReturn<List<City>>> queryCityDistric(@Query("cityId") int cityId);

    @POST("ServicePoint/QueryServicePointWithOutDistanceInArea")
    Observable<WebReturn<List<ServicePoint>>> queryServicePointWithOutDistanceInArea(@Query("minLat") double minLat, @Query("maxLat") double maxLat, @Query("minLng") double minLng, @Query("maxLng") double maxLng);

    @POST("ServicePoint/QueryServicePointServiceModules")
    Observable<WebReturn<List<Module>>> queryServicePointServiceModules(@Query("servicePointId") int servicePointId);
    //__________________________________________________________________________________

    @POST("ServicePoint/QueryServicePointInArea")
    Observable<String> queryServicePointInArea(@Query("minLat") double minLat, @Query("maxLat") double maxLat, @Query("minLng") double minLng, @Query("maxLng") double maxLng);

    @POST("ServicePoint/QueryServicePointWithDistanceInArea")
    Observable<String> queryServicePointWithDistanceInArea(@Query("minLat") double minLat, @Query("maxLat") double maxLat, @Query("minLng") double minLng, @Query("maxLng") double maxLng, @Query("endLat") double endLat, @Query("endLng") double endLng);

    @POST("ServicePoint/QueryServicePointWithDistanceInAreaV3")
    Observable<String> queryServicePointWithDistanceInAreaV3(@Query("city") int city, @Query("beginLat") double beginLat, @Query("beginLng") double beginLng);

    @POST("ServicePoint/QueryServicePointById")
    Observable<String> queryServicePointById(@Query("servicePointId") int servicePointId);

    @POST("ServicePoint/QueryServiceArea")
    Observable<String> queryServiceArea(@Query("cityId") int cityId);


}
