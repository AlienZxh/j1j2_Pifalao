package com.j1j2.data.http.api;

import com.j1j2.data.model.CollectedProduct;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.RemoveUserFavoritesBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserFavoriteApi {

    @POST("UserFavorite/QueryUserFavorites")
    Observable<WebReturn<PagerManager<CollectedProduct>>> queryUserFavorites(@Query("pageIndex") String pageIndex, @Query("size") String size);

    //______________________________________________________________________________________________

    @POST("UserFavorite/AddProductToFavorites")
    Observable<WebReturn<String>> addProductToFavorites(@Query("mainId") int mainId);

    @POST("UserFavorite/RemoveItemFromUserFavorites")
    Observable<WebReturn<String>> removeItemFromUserFavorites(@Body RemoveUserFavoritesBody removeUserFavoritesBody);

    @POST("UserFavorite/QueryProductHasBeenCollected")
    Observable<WebReturn<String>> queryProductHasBeenCollected(@Query("mainId") int mainId);

}
