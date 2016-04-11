package com.j1j2.pifalao.feature.productdetail.di;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserFavoriteApi;
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

    public ProductDetailModule(ProductDetailActivity productDetailActivity) {
        this.productDetailActivity = productDetailActivity;
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
    UserFavoriteApi userFavoriteApi(Retrofit retrofit) {
        return retrofit.create(UserFavoriteApi.class);
    }

    @Provides
    @ActivityScope
    ProductDetailActivity productDetailActivity() {
        return productDetailActivity;
    }

    @Provides
    @ActivityScope
    ProductDetailViewModel productDetailViewModel(ProductDetailActivity productDetailActivity, ProductApi productApi, ShopCartApi shopCartApi, UserFavoriteApi userFavoriteApi) {
        return new ProductDetailViewModel(productDetailActivity, productApi, shopCartApi, userFavoriteApi);
    }

}
