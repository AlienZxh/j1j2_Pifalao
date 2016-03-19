package com.j1j2.pifalao.feature.city;

import android.databinding.ObservableInt;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.City;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-12.
 */
public class CityViewModel {

    private List<City> cities;

    private CityAdapter cityAdapter;

    private ObservableInt viewState = new ObservableInt();

    ServicePointApi servicePointApi;

    CityActivity cityActivity;

    public CityViewModel(ServicePointApi servicePointApi, CityActivity cityActivity) {
        this.servicePointApi = servicePointApi;
        this.cityActivity = cityActivity;
        cities = new ArrayList<>();
        cityAdapter = new CityAdapter(cityActivity, cities);
    }

    public void queryServiceCity() {
        servicePointApi.queryServiceCity()
                .compose(cityActivity.<WebReturn<List<City>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<City>>() {
                    @Override
                    public void onWebReturnSucess(List<City> cities) {
                        cityAdapter.addAll(cities);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public List<City> getCities() {
        return cities;
    }

    public ObservableInt getViewState() {
        return viewState;
    }

    public CityAdapter getCityAdapter() {
        return cityAdapter;
    }
}
