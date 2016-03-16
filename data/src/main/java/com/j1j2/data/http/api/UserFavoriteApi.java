package com.j1j2.data.http.api;

import com.j1j2.data.model.requestbody.RemoveUserFavoritesBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserFavoriteApi {

    @POST("UserFavorite/AddProductToFavorites")
    Observable<String> addProductToFavorites(@Query("mainId") int mainId);

    @POST("UserFavorite/QueryUserFavorites")
    Observable<String> queryUserFavorites(@Query("pageIndex") String pageIndex, @Query("size") String size);

    @POST("UserFavorite/RemoveItemFromUserFavorites")
    Observable<String> removeItemFromUserFavorites(@Body RemoveUserFavoritesBody removeUserFavoritesBody);

    @POST("UserFavorite/QueryProductHasBeenCollected")
    Observable<String> queryProductHasBeenCollected(@Query("mainId") int mainId);

}
