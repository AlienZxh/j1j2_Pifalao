package com.j1j2.data.http.api;

import com.j1j2.data.model.HotKey;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.Product;
import com.j1j2.data.model.ProductBuyRecord;
import com.j1j2.data.model.ProductCategory;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.QueryProductParams;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface ProductApi {

    @POST("Product/QueryProductCategoryTrees")
    Observable<WebReturn<List<ProductCategory>>> queryProductCategoryTrees(@Query("serviceId") int serviceId, @Query("shopId") int shopId);

    @POST("Product/QueryProductCategoryTree")
    Observable<WebReturn<List<ProductCategory>>> queryProductCategoryTree(@Query("parentId") int parentId);

    @POST("Product/QueryProducts")
    Observable<WebReturn<PagerManager<Product>>> queryProducts(@Body QueryProductParams params);

    @POST("Product/QueryProductDetails")
    Observable<WebReturn<Product>> queryProductDetails(@Query("mainId") int mainId);

    @POST("Product/QueryHotKeys")
    Observable<WebReturn<List<HotKey>>> queryHotKeys(@Query("moduleId") int moduleId);

    @POST("Product/QueryHotCategories")
    Observable<WebReturn<List<ProductCategory>>> queryHotCategories(@Query("serviceId") int serviceId, @Query("shopId") int shopId);

    @POST("Product/QueryProductBuyRecords")
    Observable<WebReturn<PagerManager<ProductBuyRecord>>> queryProductBuyRecords(@Query("productId") int productId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @POST("Product/UpdateProductViews")
    Observable<WebReturn<String>> updateProductViews(@Query("productId") int productId);
    //__________________________________________________________________________________________

    @POST("Product/QueryProductAllUnit")
    Observable<String> queryProductAllUnit(@Query("mainId") int mainId);

    @POST("Product/QueryProductRemains")
    Observable<String> queryProductRemains(@Query("productId") int productId);

}
