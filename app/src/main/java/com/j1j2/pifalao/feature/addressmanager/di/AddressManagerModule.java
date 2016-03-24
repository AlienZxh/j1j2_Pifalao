package com.j1j2.pifalao.feature.addressmanager.di;

import com.j1j2.data.http.api.UserAddressApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.addressmanager.AddressManagerActivity;
import com.j1j2.pifalao.feature.addressmanager.AddressManagerViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-23.
 */
@Module
public class AddressManagerModule {

    private AddressManagerActivity addressManagerActivity;

    public AddressManagerModule(AddressManagerActivity addressManagerActivity) {
        this.addressManagerActivity = addressManagerActivity;
    }

    @Provides
    @ActivityScope
    UserAddressApi userAddressApi(Retrofit retrofit) {
        return retrofit.create(UserAddressApi.class);
    }

    @Provides
    @ActivityScope
    AddressManagerActivity addressManagerActivity() {
        return addressManagerActivity;
    }

    @Provides
    @ActivityScope
    AddressManagerViewModel addressManagerViewModel(AddressManagerActivity addressManagerActivity, UserAddressApi userAddressApi) {
        return new AddressManagerViewModel(addressManagerActivity, userAddressApi);
    }
}
