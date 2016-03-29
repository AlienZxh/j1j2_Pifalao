package com.j1j2.pifalao.feature.products.di;

import com.j1j2.data.http.api.CountDownApi;
import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.SystemAssistApi;
import com.j1j2.data.model.ProductSort;
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
    private ProductSort productSort;
    private com.j1j2.data.model.Module module;
    private String key;

    public ProductsModule(ProductsActivity productsActivity, ProductSort productSort, com.j1j2.data.model.Module module, String key) {
        this.productsActivity = productsActivity;
        this.productSort = productSort;
        this.module = module;
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
    com.j1j2.data.model.Module module() {
        return module;
    }

    @Provides
    @ActivityScope
    ProductSort productSort() {
        return productSort;
    }

    @Provides
    @ActivityScope
    String key() {
        return key;
    }

    @Provides
    @ActivityScope
    ProductsViewModel productsViewModel(ProductsActivity productsActivity, ProductSort productSort, com.j1j2.data.model.Module module, ProductApi productApi, CountDownApi countDownApi, String key, ShopCartApi shopCartApi) {
        return new ProductsViewModel(productsActivity, productSort, module, productApi, countDownApi, key, shopCartApi);
    }


}
