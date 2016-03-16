package com.j1j2.pifalao.feature.products.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.products.ProductsActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-15.
 */
@ActivityScope
@Subcomponent(modules = {ProductsModule.class})
public interface ProductsComponent {
    void inject(ProductsActivity productsActivity);
}
