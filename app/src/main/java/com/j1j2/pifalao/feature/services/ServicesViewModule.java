package com.j1j2.pifalao.feature.services;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.SystemAssistApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.City;
import com.j1j2.data.model.Shop;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.data.model.SystemNotice;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.LoginBody;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-14.
 */
public class ServicesViewModule {

    private List<ShopSubscribeService> shopSubscribeServices;

    Shop shop;

    ServicePointApi servicePointApi;
    UserLoginApi userLoginApi;
    SystemAssistApi systemAssistApi;
    ActivityApi activityApi;

    ServicesActivity servicesActivity;

    private ServicesAdapter servicesAdapter;

    public ServicesViewModule(ServicesActivity servicesActivity, ServicePointApi servicePointApi, Shop shop, UserLoginApi userLoginApi, SystemAssistApi systemAssistApi, ActivityApi activityApi) {
        this.servicePointApi = servicePointApi;
        this.servicesActivity = servicesActivity;
        this.shop = shop;
        this.userLoginApi = userLoginApi;
        this.systemAssistApi = systemAssistApi;
        this.activityApi = activityApi;
        this.shopSubscribeServices = new ArrayList<>();
        this.servicesAdapter = new ServicesAdapter(shopSubscribeServices);
    }

    public void queryServicePointServiceModules() {
        servicePointApi.queryServicePointServiceModules(shop.getShopId())
                .compose(servicesActivity.<WebReturn<List<ShopSubscribeService>>>bindToLifecycle())
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<WebReturn<List<ShopSubscribeService>>, Observable<ShopSubscribeService>>() {
                    @Override
                    public Observable<ShopSubscribeService> call(WebReturn<List<ShopSubscribeService>> listWebReturn) {
                        servicesActivity.setShopSubscribeServices(listWebReturn.getDetail());

                        List<ShopSubscribeService> shopSubscribeServiceList = new ArrayList<ShopSubscribeService>();
                        shopSubscribeServiceList.addAll(listWebReturn.getDetail());
                        ShopSubscribeService shopSubscribeService = new ShopSubscribeService();
                        shopSubscribeService.setName("更多");
                        shopSubscribeService.setSubscribed(true);
                        shopSubscribeService.setServiceId(36);
                        shopSubscribeService.setServiceType(Constant.ModuleType.MORE);
                        shopSubscribeServiceList.add(5, shopSubscribeService);
                        return Observable.from(shopSubscribeServiceList);
                    }
                })
                .compose(servicesActivity.<ShopSubscribeService>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<ShopSubscribeService>() {

                    @Override
                    public void onNext(ShopSubscribeService shopSubscribeService) {
                        super.onNext(shopSubscribeService);
                        servicesAdapter.add(shopSubscribeService);
                        if (shopSubscribeService.getServiceType() == Constant.ModuleType.MORE) {
                            servicesActivity.launchFromUrl();
                            onCompleted();
                        }

                    }
                });
    }

    public void queryServicepointInCity(final BDLocation location, City city, final Shop selectShop) {
        if (city == null || selectShop == null || location == null)
            return;
        servicePointApi.queryServicePointInCity(city.getPCCId())
                .compose(servicesActivity.<WebReturn<List<Shop>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<WebReturn<List<Shop>>, List<Shop>>() {
                    @Override
                    public List<Shop> call(WebReturn<List<Shop>> listWebReturn) {
                        LatLng mypoint = new LatLng(location.getLatitude(), location.getLongitude());
                        LatLng point;
                        for (Shop servicepoint : listWebReturn.getDetail()) {
                            point = new LatLng(servicepoint.getLat(), servicepoint.getLng());
                            servicepoint.setDistance(DistanceUtil.getDistance(mypoint, point));
                        }
                        Collections.sort(listWebReturn.getDetail(), new Comparator<Shop>() {
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
                        return listWebReturn.getDetail();
                    }
                })
                .compose(servicesActivity.<List<Shop>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<Shop>>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(List<Shop> shops) {
                        super.onNext(shops);
                        if (shops == null || shops.size() <= 0)
                            return;
                        if (selectShop.getShopId() != shops.get(0).getShopId()) {
                            servicesActivity.showLocationDialog(shops.get(0));

                        }
                    }
                });

    }


    public void updateUserTerminalDetail(final String deviceToken) {
        LoginBody loginBody = new LoginBody();
        loginBody.setTerminalType(1);
        loginBody.setDeviceToken(deviceToken);
        userLoginApi.updateUserTerminalDetail(loginBody)
                .compose(servicesActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void querySystemNotice() {
        systemAssistApi.querySystemNotice()
                .compose(servicesActivity.<WebReturn<SystemNotice>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<SystemNotice>() {
                    @Override
                    public void onWebReturnSucess(SystemNotice systemNotice) {
                        if (systemNotice != null && systemNotice.isState())
                            servicesActivity.showSystemNotice(systemNotice);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryFoldRedPacketCount() {
        activityApi.queryFoldRedPacketCount()
                .delay(500, TimeUnit.MILLISECONDS)
                .compose(servicesActivity.<WebReturn<Integer>>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<Integer>() {
                    @Override
                    public void onWebReturnSucess(Integer integer) {
                        if (integer > 0) {
                            servicesActivity.showFoldRedPacket(integer);
                        }
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public ServicesAdapter getServicesAdapter() {
        return servicesAdapter;
    }
}
