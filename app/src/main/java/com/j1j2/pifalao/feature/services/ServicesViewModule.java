package com.j1j2.pifalao.feature.services;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.SystemAssistApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.City;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
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

    private List<Module> modules;

    ServicePoint servicePoint;

    ServicePointApi servicePointApi;
    UserLoginApi userLoginApi;
    SystemAssistApi systemAssistApi;
    ActivityApi activityApi;

    ServicesActivity servicesActivity;

    private ServicesAdapter servicesAdapter;

    public ServicesViewModule(ServicesActivity servicesActivity, ServicePointApi servicePointApi, ServicePoint servicePoint, UserLoginApi userLoginApi, SystemAssistApi systemAssistApi, ActivityApi activityApi) {
        this.servicePointApi = servicePointApi;
        this.servicesActivity = servicesActivity;
        this.servicePoint = servicePoint;
        this.userLoginApi = userLoginApi;
        this.systemAssistApi = systemAssistApi;
        this.activityApi = activityApi;
        this.modules = new ArrayList<>();
        this.servicesAdapter = new ServicesAdapter(modules);
    }

    public void queryServicePointServiceModules() {
        servicePointApi.queryServicePointServiceModules(servicePoint.getServicePointId())
                .compose(servicesActivity.<WebReturn<List<Module>>>bindToLifecycle())
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<WebReturn<List<Module>>, Observable<Module>>() {
                    @Override
                    public Observable<Module> call(WebReturn<List<Module>> listWebReturn) {
                        servicesActivity.setModules(listWebReturn.getDetail());

                        List<Module> moduleList = new ArrayList<Module>();
                        moduleList.addAll(listWebReturn.getDetail());
                        Module module = new Module();
                        module.setModuleName("更多");
                        module.setSubscribed(true);
                        module.setWareHouseModuleId(36);
                        module.setModuleType(Constant.ModuleType.MORE);
                        moduleList.add(5, module);
                        return Observable.from(moduleList);
                    }
                })
                .compose(servicesActivity.<Module>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Module>() {

                    @Override
                    public void onNext(Module module) {
                        super.onNext(module);
                        servicesAdapter.add(module);
                        if (module.getModuleType() == Constant.ModuleType.MORE) {
                            servicesActivity.launchFromUrl();
                            onCompleted();
                        }

                    }
                });
    }

    public void queryServicepointInCity(final BDLocation location, City city, final ServicePoint selectServicePoint) {
        if (city == null || selectServicePoint == null || location == null)
            return;
        servicePointApi.queryServicePointInCity(city.getPCCId())
                .compose(servicesActivity.<WebReturn<List<ServicePoint>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<WebReturn<List<ServicePoint>>, List<ServicePoint>>() {
                    @Override
                    public List<ServicePoint> call(WebReturn<List<ServicePoint>> listWebReturn) {
                        LatLng mypoint = new LatLng(location.getLatitude(), location.getLongitude());
                        LatLng point;
                        for (ServicePoint servicepoint : listWebReturn.getDetail()) {
                            point = new LatLng(servicepoint.getLat(), servicepoint.getLng());
                            servicepoint.setDistance(DistanceUtil.getDistance(mypoint, point));
                        }
                        Collections.sort(listWebReturn.getDetail(), new Comparator<ServicePoint>() {
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
                        return listWebReturn.getDetail();
                    }
                })
                .compose(servicesActivity.<List<ServicePoint>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<ServicePoint>>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(List<ServicePoint> servicePoints) {
                        super.onNext(servicePoints);
                        if (servicePoints == null || servicePoints.size() <= 0)
                            return;
                        if (selectServicePoint.getServicePointId() != servicePoints.get(0).getServicePointId()) {
                            servicesActivity.showLocationDialog(servicePoints.get(0));

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
