package com.j1j2.pifalao.feature.location;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.j1j2.common.util.LocationUtils;
import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.City;
import com.j1j2.data.model.Shop;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.DefaultSubscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-12.
 */
public class LocationViewModel {

    ServicePointApi servicePointApi;

    LocationActivity locationActivity;

    City city;

    private List<City> districts;

    private List<Shop> shops;

    private LocationDistrictAdapter locationDistrictAdapter;

    private LocationServicePointAdapter locationServicePointAdapter;


    public LocationViewModel(ServicePointApi servicePointApi, LocationActivity locationActivity, City city) {
        this.servicePointApi = servicePointApi;
        this.locationActivity = locationActivity;
        this.city = city;
        this.shops = new ArrayList<>();
        this.districts = new ArrayList<>();
        this.locationDistrictAdapter = new LocationDistrictAdapter(districts);
        this.locationServicePointAdapter = new LocationServicePointAdapter(shops);
    }

    private Observable<WebReturn<List<Shop>>> queryNearByWithLocation(BDLocation location) {
        if (LocationUtils.isLocationSuccess(location)) {
            LatLng northeast = locationActivity.getNortheast(location);
            LatLng southwest = locationActivity.getSouthwest(location);
            return servicePointApi.queryShopsInArea(southwest.latitude, northeast.latitude, southwest.longitude, northeast.longitude);
        } else {
            return servicePointApi.queryServicePointInCity(city.getPCCId());
        }
    }

    public void onCreate(final BDLocation location) {
        if (shops.size() > 0)
            shops.clear();
        locationActivity.showLoad();
        servicePointApi.queryCityDistric(city.getPCCId())
                .compose(locationActivity.<WebReturn<List<City>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<WebReturn<List<City>>>() {
                    @Override
                    public void call(WebReturn<List<City>> listWebReturn) {
                        if (districts.size() > 0)
                            districts.clear();
                        districts.addAll(listWebReturn.getDetail());
                        locationDistrictAdapter.notifyDataSetChanged();
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<WebReturn<List<City>>, Observable<WebReturn<List<Shop>>>>() {
                    @Override
                    public Observable<WebReturn<List<Shop>>> call(WebReturn<List<City>> listWebReturn) {
                        return queryNearByWithLocation(location);
                    }
                })
                .compose(locationActivity.<WebReturn<List<Shop>>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WebReturn<List<Shop>>>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                        locationActivity.hideLoad();
                        locationServicePointAdapter.setShownear(false);
                        locationServicePointAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(WebReturn<List<Shop>> listWebReturn) {
                        super.onNext(listWebReturn);
                        locationActivity.hideLoad();
                        refreshServicePointData(location, listWebReturn.getDetail(), true);
                    }
                });
    }

    public void queryNearByServicePoint(final BDLocation location) {
        if (shops.size() > 0)
            shops.clear();
        locationActivity.showLoad();
        queryNearByWithLocation(location)
                .compose(locationActivity.<WebReturn<List<Shop>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WebReturn<List<Shop>>>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        locationActivity.hideLoad();
                        locationServicePointAdapter.setShownear(false);
                        locationServicePointAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(WebReturn<List<Shop>> listWebReturn) {
                        super.onNext(listWebReturn);
                        locationActivity.hideLoad();
                        refreshServicePointData(location, listWebReturn.getDetail(), true);
                    }
                });
    }

    public void queryInDistrict(final BDLocation location, City mCity) {
        if (shops.size() > 0)
            shops.clear();
        locationActivity.showLoad();
        servicePointApi.queryServicePointInCity(mCity.getPCCId())
                .compose(locationActivity.<WebReturn<List<Shop>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WebReturn<List<Shop>>>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        locationActivity.hideLoad();
                        locationServicePointAdapter.setShownear(false);
                        locationServicePointAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(WebReturn<List<Shop>> listWebReturn) {
                        super.onNext(listWebReturn);
                        locationActivity.hideLoad();
                        refreshServicePointData(location, listWebReturn.getDetail(), false);
                    }
                });
    }


    private void refreshServicePointData(BDLocation location, List<Shop> mShops, boolean shownear) {
        if (mShops == null || mShops.size() <= 0) {
            locationServicePointAdapter.notifyDataSetChanged();
            return;
        }
        if (LocationUtils.isLocationSuccess(location)) {
            LatLng mPoint = new LatLng(location.getLatitude(), location.getLongitude());
            LatLng mServicePoint = null;
            for (Shop shop : mShops) {
                mServicePoint = new LatLng(shop.getLat(), shop.getLng());
                shop.setDistance(DistanceUtil.getDistance(mPoint, mServicePoint));
            }
            Collections.sort(mShops, new Comparator<Shop>() {
                @Override
                public int compare(Shop lhs, Shop rhs) {
                    if (lhs.getDistance() - rhs.getDistance() > 0)
                        return 1;
                    else if (lhs.getDistance() - rhs.getDistance() == 0)
                        return 0;
                    else
                        return -1;
                }
            });
        }
        if (shops.size() > 0)
            shops.clear();
        shops.addAll(mShops);
        locationServicePointAdapter.setShownear(shownear);
        locationServicePointAdapter.notifyDataSetChanged();
        locationActivity.addServicePointOverlay(mShops);
    }

    public List<Shop> getShops() {
        return shops;
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
