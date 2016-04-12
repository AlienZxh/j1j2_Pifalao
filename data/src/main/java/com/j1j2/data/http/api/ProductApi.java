package com.j1j2.data.http.api;

import com.j1j2.data.model.HotKey;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.ProductBuyRecord;
import com.j1j2.data.model.ProductDetail;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductSort;
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

    @POST("Product/PuzzyQueryProduct")
    Observable<WebReturn<PagerManager<ProductSimple>>> puzzyQueryProduct(@Query("moduleId") int moduleId, @Query("index") String index, @Query("size") String size, @Query("key") String key);

    @POST("Product/QueryHotKeys")
    Observable<WebReturn<List<HotKey>>> queryHotKeys(@Query("moduleId") int moduleId);

    @POST("Product/QueryHotSort")
    Observable<WebReturn<List<ProductSort>>> queryHotSort(@Query("moduleId") int moduleId);

    //productSearchType  0:只查询一般性的商品 不包括 促销商品和新品  1:只查询促销商品 2:只查询新品 3:只查询新品
    @POST("Product/QueryActivityProducts")
    Observable<WebReturn<PagerManager<ProductSimple>>> queryActivityProducts(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Query("moduleId") int moduleId, @Query("productSearchType") int productSearchType, @Query("key") String key, @Query("activityId") String activityId);

    @POST("Product/QueryProductBuyRecords")
    Observable<WebReturn<PagerManager<ProductBuyRecord>>> queryProductBuyRecords(@Query("productId") int productId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    //__________________________________________________________________________________________


    @POST("Product/QueryProductAllUnit")
    Observable<String> queryProductAllUnit(@Query("mainId") int mainId);

    @POST("Product/QueryProductRemains")
    Observable<String> queryProductRemains(@Query("productId") int productId);

    @POST("Product/UpdateProductViews")
    Observable<String> updateProductViews(@Query("productId") int productId);

    @POST("Product/QueryProductCount")
    Observable<String> queryProductCount();

    @POST("Product/CreateSuggestProduct")
    Observable<String> createSuggestProduct();


}
