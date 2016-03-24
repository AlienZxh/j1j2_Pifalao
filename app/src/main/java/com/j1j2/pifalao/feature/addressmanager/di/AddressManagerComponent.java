package com.j1j2.pifalao.feature.addressmanager.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.addressmanager.AddressManagerActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-23.
 */
@ActivityScope
@Subcomponent(modules = {AddressManagerModule.class})
public interface AddressManagerComponent {
    void inject(AddressManagerActivity addressManagerActivity);
}
