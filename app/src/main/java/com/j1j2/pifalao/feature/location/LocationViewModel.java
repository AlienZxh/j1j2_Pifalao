package com.j1j2.pifalao.feature.location;

import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.City;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.RxBus;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.constants.RxBusTag;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-12.
 */
public class LocationViewModel {

    Observable locationOb;

    ServicePointApi servicePointApi;

    LocationActivity locationActivity;

    City city;

    private List<City> districts;

    private List<ServicePoint> servicePoints;

    private LocationDistrictAdapter locationDistrictAdapter;

    private LocationServicePointAdapter locationServicePointAdapter;

    private BDLocation location;


    public LocationViewModel(ServicePointApi servicePointApi, LocationActivity locationActivity, City city) {
        this.servicePointApi = servicePointApi;
        this.locationActivity = locationActivity;
        this.city = city;
        this.servicePoints = new ArrayList<>();
        this.districts = new ArrayList<>();
        this.locationDistrictAdapter = new LocationDistrictAdapter(districts);
        this.locationServicePointAdapter = new LocationServicePointAdapter(servicePoints);
        this.locationOb = RxBus.get().register(RxBusTag.LOCATION_EVENT, BDLocation.class);


    }


    public void onCreate() {
        locationOb
                .compose(locationActivity.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .first()
                .flatMap(new Func1<BDLocation, Observable<WebReturn<List<City>>>>() {
                    @Override
                    public Observable<WebReturn<List<City>>> call(BDLocation mlocation) {
                        location = mlocation;
                        return servicePointApi.queryCityDistric(city.getPCCId());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<WebReturn<List<City>>>() {
                    @Override
                    public void call(WebReturn<List<City>> listWebReturn) {
                        districts.addAll(listWebReturn.getDetail());
                        locationDistrictAdapter.notifyDataSetChanged();
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<WebReturn<List<City>>, Observable<WebReturn<List<ServicePoint>>>>() {
                    @Override
                    public Observable<WebReturn<List<ServicePoint>>> call(WebReturn<List<City>> listWebReturn) {
                        return servicePointApi.queryServicePointInCity(city.getPCCId());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WebReturn<List<ServicePoint>>>() {
                    @Override
                    public void onNext(WebReturn<List<ServicePoint>> listWebReturn) {
                        super.onNext(listWebReturn);
                        servicePoints.clear();
                        servicePoints.addAll(listWebReturn.getDetail());
                        locationServicePointAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void onDestory() {
        RxBus.get().unregister(RxBusTag.LOCATION_EVENT, locationOb);
    }

    public void queryInDistrict(City mCity) {
        servicePointApi.queryServicePointInCity(mCity.getPCCId())
                .compose(locationActivity.<WebReturn<List<ServicePoint>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WebReturn<List<ServicePoint>>>() {
                    @Override
                    public void onNext(WebReturn<List<ServicePoint>> listWebReturn) {
                        super.onNext(listWebReturn);
                        servicePoints.clear();
                        servicePoints.addAll(listWebReturn.getDetail());
                        locationServicePointAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void queryNearByServicePoint() {

        locationOb
                .compose(locationActivity.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .first()
                .flatMap(new Func1<BDLocation, Observable<WebReturn<List<ServicePoint>>>>() {
                    @Override
                    public Observable<WebReturn<List<ServicePoint>>> call(BDLocation location) {
                        Logger.d(location.getLocationDescribe());
                        return servicePointApi.queryServicePointInCity(city.getPCCId());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WebReturn<List<ServicePoint>>>() {
                    @Override
                    public void onNext(WebReturn<List<ServicePoint>> listWebReturn) {
                        super.onNext(listWebReturn);
                        servicePoints.clear();
                        servicePoints.addAll(listWebReturn.getDetail());
                        locationServicePointAdapter.notifyDataSetChanged();
                    }
                });
    }

    public List<ServicePoint> getServicePoints() {
        return servicePoints;
    }

    public LocationDistrictAdapter getLocationDistrictAdapter() {
        return locationDistrictAdapter;
    }

    public LocationServicePointAdapter getLocationServicePointAdapter() {
        return locationServicePointAdapter;
    }

    public City getCity() {
        return city;
    }

    public LocationActivity getLocationActivity() {
        return locationActivity;
    }
}
