package com.j1j2.data.http.api;

import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.ProductDetail;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.SecondarySort;
import com.j1j2.data.model.WebReturn;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface ProductApi {

    @POST("Product/QueryProductSort")
    Observable<WebReturn<List<SecondarySort>>> queryProductSort(@Query("parentSortId") String parentSortId, @Query("moduleId") int moduleId);

    //orderby 0：默认 1:销量  2：价格  3：人气
    @POST("Product/QueryProductyBySortId")
    Observable<WebReturn<PagerManager<ProductSimple>>> queryProductyBySortId(@Query("sortIdStr") String sortIdStr, @Query("index") String index, @Query("size") String size, @Query("isParentStr") String isParentStr, @Query("oderBy") int oderBy);

    @POST("Product/QueryProductyBySortId")
    Observable<WebReturn<PagerManager<ProductSimple>>> searchProductyBySortIdAndKey(@Query("sortIdStr") String sortIdStr, @Query("index") String index, @Query("size") String size, @Query("key") String key, @Query("isParentStr") String isParentStr, @Query("oderBy") int oderBy);

    @POST("Product/QueryProductDetails")
    Observable<WebReturn<ProductDetail>> queryProductDetails(@Query("mainId") int mainId);
    //__________________________________________________________________________________________

    @POST("Product/QueryHotSort")
    Observable<String> queryHotSort(@Query("moduleId") int moduleId);

    @POST("Product/QueryActivityProducts")
    Observable<String> queryActivityProducts(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Query("pageSize") int moduleId, @Query("productSearchType") int productSearchType, @Query("key") String key, @Query("activityId") int activityId);

    @POST("Product/PuzzyQueryProduct")
    Observable<String> puzzyQueryProduct(@Query("moduleId") int moduleId, @Query("index") String index, @Query("size") String size, @Query("key") String key);

    @POST("Product/QueryProductAllUnit")
    Observable<String> queryProductAllUnit(@Query("mainId") int mainId);

    @POST("Product/QueryProductRemains")
    Observable<String> queryProductRemains(@Query("productId") int productId);

    @POST("Product/QueryProductBuyRecords")
    Observable<String> queryProductBuyRecords(@Query("productId") int productId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @POST("Product/UpdateProductViews")
    Observable<String> updateProductViews(@Query("productId") int productId);

    @POST("Product/QueryProductCount")
    Observable<String> queryProductCount();

    @POST("Product/CreateSuggestProduct")
    Observable<String> createSuggestProduct();

}
