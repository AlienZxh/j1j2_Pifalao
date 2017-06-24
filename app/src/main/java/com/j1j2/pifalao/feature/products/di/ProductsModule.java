package com.j1j2.pifalao.feature.products.di;

import com.j1j2.data.http.api.CountDownApi;
import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.model.ProductCategory;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.products.ProductsActivity;
import com.j1j2.pifalao.feature.products.ProductsViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-15.
 */
@Module
public class ProductsModule {


    private ProductsActivity productsActivity;
    private ProductCategory productCategory;
    private ShopSubscribeService shopSubscribeService;
    private String key;

    public ProductsModule(ProductsActivity productsActivity, ProductCategory productCategory, ShopSubscribeService shopSubscribeService, String key) {
        this.productsActivity = productsActivity;
        this.productCategory = productCategory;
        this.shopSubscribeService = shopSubscribeService;
        this.key = key;
    }

    @Provides
    @ActivityScope
    ProductApi productApi(Retrofit retrofit) {
        return retrofit.create(ProductApi.class);
    }

    @Provides
    @ActivityScope
    ShopCartApi shopCartApi(Retrofit retrofit) {
        return retrofit.create(ShopCartApi.class);
    }

    @Provides
    @ActivityScope
    CountDownApi countDownApi(Retrofit retrofit) {
        return retrofit.create(CountDownApi.class);
    }

    @Provides
    @ActivityScope
    ProductsActivity productsActivity() {
        return productsActivity;
    }

    @Provides
    @ActivityScope
    ShopSubscribeService module() {
        return shopSubscribeService;
    }

    @Provides
    @ActivityScope
    ProductCategory productCategory() {
        return productCategory;
    }

    @Provides
    @ActivityScope
    String key() {
        return key;
    }

    @Provides
    @ActivityScope
    ProductsViewModel productsViewModel(ProductsActivity productsActivity, ProductCategory productCategory, ShopSubscribeService shopSubscribeService, ProductApi productApi, CountDownApi countDownApi, String key, ShopCartApi shopCartApi) {
        return new ProductsViewModel(productsActivity, productCategory, shopSubscribeService, productApi, countDownApi, key, shopCartApi);
    }


}
