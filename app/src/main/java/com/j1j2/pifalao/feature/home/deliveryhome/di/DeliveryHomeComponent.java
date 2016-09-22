package com.j1j2.pifalao.feature.home.deliveryhome.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.deliveryhome.DeliveryHomeActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-30.
 */
@ActivityScope
@Subcomponent(modules = {DeliveryHomeModule.class})
public interface DeliveryHomeComponent {
    void inject(DeliveryHomeActivity deliveryHomeActivity);

}
