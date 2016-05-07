package com.j1j2.pifalao.feature.location;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.City;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
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

    private List<ServicePoint> servicePoints;

    private LocationDistrictAdapter locationDistrictAdapter;

    private LocationServicePointAdapter locationServicePointAdapter;


    public LocationViewModel(ServicePointApi servicePointApi, LocationActivity locationActivity, City city) {
        this.servicePointApi = servicePointApi;
        this.locationActivity = locationActivity;
        this.city = city;
        this.servicePoints = new ArrayList<>();
        this.districts = new ArrayList<>();
        this.locationDistrictAdapter = new LocationDistrictAdapter(districts);
        this.locationServicePointAdapter = new LocationServicePointAdapter(servicePoints);
    }

    private Observable<WebReturn<List<ServicePoint>>> queryNearByWithLocation(BDLocation location) {
        if (locationActivity.isLocationSuccess(location)) {
            LatLng northeast = locationActivity.getNortheast(location);
            LatLng southwest = locationActivity.getSouthwest(location);
            return servicePointApi.queryServicePointWithOutDistanceInArea(southwest.latitude, northeast.latitude, southwest.longitude, northeast.longitude);
        } else {
            return servicePointApi.queryServicePointInCity(city.getPCCId());
        }
    }

    public void onCreate(final BDLocation location) {
        if (servicePoints.size() > 0)
            servicePoints.clear();
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
                .flatMap(new Func1<WebReturn<List<City>>, Observable<WebReturn<List<ServicePoint>>>>() {
                    @Override
                    public Observable<WebReturn<List<ServicePoint>>> call(WebReturn<List<City>> listWebReturn) {
                        return queryNearByWithLocation(location);
                    }
                })
                .compose(locationActivity.<WebReturn<List<ServicePoint>>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WebReturn<List<ServicePoint>>>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                        locationActivity.hideLoad();
                        locationServicePointAdapter.setShownear(false);
                        locationServicePointAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(WebReturn<List<ServicePoint>> listWebReturn) {
                        super.onNext(listWebReturn);
                        locationActivity.hideLoad();
                        refreshServicePointData(location, listWebReturn.getDetail(), true);
                    }
                });
    }

    public void queryNearByServicePoint(final BDLocation location) {
        if (servicePoints.size() > 0)
            servicePoints.clear();
        locationActivity.showLoad();
        queryNearByWithLocation(location)
                .compose(locationActivity.<WebReturn<List<ServicePoint>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WebReturn<List<ServicePoint>>>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        locationActivity.hideLoad();
                        locationServicePointAdapter.setShownear(false);
                        locationServicePointAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(WebReturn<List<ServicePoint>> listWebReturn) {
                        super.onNext(listWebReturn);
                        locationActivity.hideLoad();
                        refreshServicePointData(location, listWebReturn.getDetail(), true);
                    }
                });
    }

    public void queryInDistrict(final BDLocation location, City mCity) {
        if (servicePoints.size() > 0)
            servicePoints.clear();
        locationActivity.showLoad();
        servicePointApi.queryServicePointInCity(mCity.getPCCId())
                .compose(locationActivity.<WebReturn<List<ServicePoint>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WebReturn<List<ServicePoint>>>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        locationActivity.hideLoad();
                        locationServicePointAdapter.setShownear(false);
                        locationServicePointAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(WebReturn<List<ServicePoint>> listWebReturn) {
                        super.onNext(listWebReturn);
                        locationActivity.hideLoad();
                        refreshServicePointData(location, listWebReturn.getDetail(), false);
                    }
                });
    }


    private void refreshServicePointData(BDLocation location, List<ServicePoint> mServicePoints, boolean shownear) {
        if (mServicePoints == null || mServicePoints.size() <= 0) {
            locationServicePointAdapter.notifyDataSetChanged();
            return;
        }
        if (locationActivity.isLocationSuccess(location)) {
            LatLng mPoint = new LatLng(location.getLatitude(), location.getLongitude());
            LatLng mServicePoint = null;
            for (ServicePoint servicePoint : mServicePoints) {
                mServicePoint = new LatLng(servicePoint.getLat(), servicePoint.getLng());
                servicePoint.setDistance(DistanceUtil.getDistance(mPoint, mServicePoint));
            }
            Collections.sort(mServicePoints, new Comparator<ServicePoint>() {
                @Override
                public int compare(ServicePoint lhs, ServicePoint rhs) {
                    if (lhs.getDistance() - rhs.getDistance() > 0)
                        return 1;
                    else if (lhs.getDistance() - rhs.getDistance() == 0)
                        return 0;
                    else
                        return -1;
                }
            });
        }
        if (servicePoints.size() > 0)
            servicePoints.clear();
        servicePoints.addAll(mServicePoints);
        locationServicePointAdapter.setShownear(shownear);
        locationServicePointAdapter.notifyDataSetChanged();
        locationActivity.addServicePointOverlay(mServicePoints);
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
