package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.DeliveryHomeProductsFragment;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.di.DeliveryServicepointModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-4-8.
 */
@ActivityScope
@Subcomponent(modules = {DeliveryProductsModule.class})
public interface DeliveryProductsComponent {

    void inject(DeliveryHomeProductsFragment deliveryHomeProductsFragment);
}
