package com.j1j2.pifalao.feature.addaddress;

import android.widget.Toast;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.UserAddressApi;
import com.j1j2.data.model.City;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.EditorUserRecivingAddressBody;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.AddressListChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-31.
 */
public class AddAddressViewModel {
    private AddAddressActivity addAddressActivity;
    private UserAddressApi userAddressApi;
    private ServicePointApi servicePointApi;

    public AddAddressViewModel(AddAddressActivity addAddressActivity, UserAddressApi userAddressApi, ServicePointApi servicePointApi) {
        this.addAddressActivity = addAddressActivity;
        this.userAddressApi = userAddressApi;
        this.servicePointApi = servicePointApi;
    }

    public void addUserAddress(EditorUserRecivingAddressBody editorUserRecivingAddressBody) {
        userAddressApi.insertOrUpdateUserAddress(editorUserRecivingAddressBody)
                .compose(addAddressActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        EventBus.getDefault().post(new AddressListChangeEvent());
                        addAddressActivity.finish();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(addAddressActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryDistrict(int cityId) {
        servicePointApi.queryCityDistric(cityId)
                .compose(addAddressActivity.<WebReturn<List<City>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<City>>() {
                    @Override
                    public void onWebReturnSucess(List<City> cities) {
                        addAddressActivity.initDistrict(cities);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public AddAddressActivity getAddAddressActivity() {
        return addAddressActivity;
    }
}
