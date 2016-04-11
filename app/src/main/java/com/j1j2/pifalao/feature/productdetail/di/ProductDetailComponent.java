package com.j1j2.pifalao.feature.productdetail.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.productdetail.ProductDetailActivity;
import com.j1j2.pifalao.feature.productdetail.record.ProductDetailRecordFragment;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-16.
 */
@ActivityScope
@Subcomponent(modules = {ProductDetailModule.class})
public interface ProductDetailComponent {
    void inject(ProductDetailActivity productDetailActivity);

    void inject(ProductDetailRecordFragment productDetailRecordFragment);
}
