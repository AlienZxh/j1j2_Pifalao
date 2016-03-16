package com.j1j2.pifalao.feature.products.di;

import com.j1j2.data.http.api.CountDownApi;
import com.j1j2.data.http.api.ProductApi;
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

    public ProductsModule(ProductsActivity productsActivity, ProductSort productSort, com.j1j2.data.model.Module module) {
        this.productsActivity = productsActivity;
        this.productSort = productSort;
        this.module = module;
    }

    @Provides
    @ActivityScope
    ProductApi productApi(Retrofit retrofit) {
        return retrofit.create(ProductApi.class);
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
    ProductsViewModel productsViewModel(ProductsActivity productsActivity, ProductSort productSort, com.j1j2.data.model.Module module, ProductApi productApi, CountDownApi countDownApi) {
        return new ProductsViewModel(productsActivity, productSort, module, productApi, countDownApi);
    }


}
