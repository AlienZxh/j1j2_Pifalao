package com.j1j2.pifalao.feature.home.deliveryhome.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.deliveryhome.DeliveryHomeActivity;
import com.j1j2.pifalao.feature.home.deliveryhome.DeliveryHomeFragment;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualCenterModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-30.
 */
@ActivityScope
@Subcomponent(modules = {DeliveryHomeModule.class})
public interface DeliveryHomeComponent {
    void inject(DeliveryHomeActivity deliveryHomeActivity);


    void inject(DeliveryHomeFragment deliveryHomeFragment);
}
