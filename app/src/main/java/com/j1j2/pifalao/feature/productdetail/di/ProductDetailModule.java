package com.j1j2.pifalao.feature.productdetail.di;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.productdetail.ProductDetailActivity;
import com.j1j2.pifalao.feature.productdetail.ProductDetailViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-16.
 */
@Module
public class ProductDetailModule {

    private ProductDetailActivity productDetailActivity;
    private ProductSimple productSimple;

    public ProductDetailModule(ProductDetailActivity productDetailActivity, ProductSimple productSimple) {
        this.productDetailActivity = productDetailActivity;
        this.productSimple = productSimple;
    }

    @Provides
    @ActivityScope
    ProductApi productApi(Retrofit retrofit) {
        return retrofit.create(ProductApi.class);
    }

    @Provides
    @ActivityScope
    ProductDetailActivity productDetailActivity() {
        return productDetailActivity;
    }

    @Provides
    @ActivityScope
    ProductSimple productSimple() {
        return productSimple;
    }

    @Provides
    @ActivityScope
    ProductDetailViewModel productDetailViewModel(ProductDetailActivity productDetailActivity, ProductApi productApi, ProductSimple productSimple) {
        return new ProductDetailViewModel(productDetailActivity, productApi, productSimple);
    }

}
