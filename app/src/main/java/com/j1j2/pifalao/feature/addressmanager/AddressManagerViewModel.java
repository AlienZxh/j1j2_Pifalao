package com.j1j2.pifalao.feature.addressmanager;

import android.widget.Toast;

import com.j1j2.data.http.api.UserAddressApi;
import com.j1j2.data.model.Address;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-23.
 */
public class AddressManagerViewModel {
    private AddressManagerActivity addressManagerActivity;
    private UserAddressApi userAddressApi;
    private AddressManagerAdapter addressManagerAdapter;

    public AddressManagerViewModel(AddressManagerActivity addressManagerActivity, UserAddressApi userAddressApi) {
        this.addressManagerActivity = addressManagerActivity;
        this.userAddressApi = userAddressApi;
    }

    public void queryAddress() {
        userAddressApi.queryUserReceiveAddress()
                .compose(addressManagerActivity.<WebReturn<List<Address>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<Address>>() {
                    @Override
                    public void onWebReturnSucess(List<Address> addresses) {
                        addressManagerAdapter = new AddressManagerAdapter(addresses);
                        addressManagerActivity.setAddressAdapter(addressManagerAdapter);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void setDefaultAddress(int addressId) {
        userAddressApi.setUserDefaultAddress(addressId)
                .compose(addressManagerActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        queryAddress();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(addressManagerActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void deleteAddress(int addressId, final int position) {
        userAddressApi.deleteUserAddress(addressId)
                .compose(addressManagerActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        addressManagerAdapter.deletePosition(position);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(addressManagerActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public AddressManagerActivity getAddressManagerActivity() {
        return addressManagerActivity;
    }
}
