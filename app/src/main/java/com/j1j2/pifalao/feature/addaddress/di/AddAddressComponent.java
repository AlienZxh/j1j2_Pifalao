package com.j1j2.pifalao.feature.addaddress.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.addaddress.AddAddressActivity;
import com.j1j2.pifalao.feature.addressmanager.di.AddressManagerModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-31.
 */
@ActivityScope
@Subcomponent(modules = {AddAddressModule.class})
public interface AddAddressComponent {
    void inject(AddAddressActivity addAddressActivity);
}
