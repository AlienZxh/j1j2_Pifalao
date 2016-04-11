package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.DeliveryHomeServicepointFragment;
import com.j1j2.pifalao.feature.servicepoint.di.ServicePointModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-4-8.
 */
@ActivityScope
@Subcomponent(modules = {DeliveryServicepointModule.class})
public interface DeliveryServicepointComponent {
    void inject(DeliveryHomeServicepointFragment deliveryHomeServicepointFragment);
}
