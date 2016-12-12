package com.j1j2.pifalao.feature.services;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.RectF;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.j1j2.common.util.LocationUtils;
import com.j1j2.common.util.ScreenUtils;
import com.j1j2.common.view.interpolater.EaseBounceOutInterpolator;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.SystemNotice;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseMapActivity;
import com.j1j2.pifalao.app.dialog.QRDialogFragment;
import com.j1j2.pifalao.app.dialog.QRDialogFragmentBundler;
import com.j1j2.pifalao.app.dialog.SystemNoticeDialogFragmentBundler;
import com.j1j2.pifalao.app.event.LocationEvent;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.event.LoginCookieTimeoutEvent;
import com.j1j2.pifalao.app.event.NavigateToMemberHomeEvent;
import com.j1j2.pifalao.app.event.NavigateToPrizeDetailEvent;
import com.j1j2.pifalao.app.service.BackGroundService;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityServicesBinding;
import com.j1j2.pifalao.feature.home.viphome.VipHomeActivity;
import com.j1j2.pifalao.feature.main.MainActivity;
import com.j1j2.pifalao.feature.services.di.ServicesComponent;
import com.j1j2.pifalao.feature.services.di.ServicesModule;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.RequireBundler;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import zhy.com.highlight.HighLight;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler
public class ServicesActivity extends BaseMapActivity implements ServicesAdapter.OnItemClickListener,
        View.OnClickListener, QRDialogFragment.QRDialogFragmentListener {

    ActivityServicesBinding binding;

    ServicesComponent servicesComponent;

    @Inject
    ServicePoint servicePoint;

    @Inject
    ServicesViewModule servicesViewModule;

    @Inject
    UserRelativePreference userRelativePreference;

    MarkerOptions servicepointMarkOptions;

    List<Module> modules;

    UnReadInfoManager unReadInfoManager = null;

    HighLight highLight;

    private String locationDialogTag = "LOCATIONDIALOG";
    private String invalidDialogTag = "INVALIDDIALOG";

    ServicePoint selectServicePoint;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        servicesComponent = MainAplication.get(this).getAppComponent().plus(new ServicesModule(this));
        servicesComponent.inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(ServicesActivity.this, R.layout.activity_services);
        binding.setSercivepoint(servicePoint);
    }

    @Override
    protected void initViews() {
        binding.setOnClick(this);
        //_______________________________________________________________
        if (userRelativePreference.getShowHighLight(true)) {
            showHighlight();
            userRelativePreference.setShowHighLight(false);
        }
        //_______________________________________________________________________________________
        binding.moduleList.setLayoutManager(new GridLayoutManager(this, 3));
        ScaleInAnimator scaleInAnimator = new ScaleInAnimator();
        scaleInAnimator.setAddDuration(400);
        scaleInAnimator.setInterpolator(new EaseBounceOutInterpolator());
        binding.moduleList.setItemAnimator(scaleInAnimator);


        binding.moduleList.setAdapter(servicesViewModule.getServicesAdapter());
        servicesViewModule.getServicesAdapter().setOnItemClickListener(this);
        servicesViewModule.queryServicePointServiceModules();
        //__________________________________________________________________________________________
        servicesViewModule.querySystemNotice();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MainAplication.get(this).isLogin() && userRelativePreference.getShowBriberyMoney(true)) {
            servicesViewModule.queryFoldRedPacketCount();
        }
    }

    public void showLocationDialog(final ServicePoint servicePoint) {
        selectServicePoint = servicePoint;
        showMessageDialogDuplicate(true, locationDialogTag, "提示", servicePoint.getName() + "离您更近，是否进行切换？", "取消", "切换");
        userRelativePreference.setShowLocation(false);
    }

    public void showInvalidDialog() {
        showMessageDialogDuplicate(true, invalidDialogTag, "提示", "无效的商品", null, "知道了");
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (highLight != null) {
            highLight.remove();
            highLight = null;
        } else
            exitBy2Click();
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public void launchFromUrl() {
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            Logger.e("moduleId " + uri.getQueryParameter("moduleId"));

            int moduleId = Integer.parseInt(uri.getQueryParameter("moduleId"));


            Module selectModule = null;
            int size = modules.size();
            for (int i = 0; i < size; i++) {
                if (modules.get(i).getWareHouseModuleId() == moduleId) {
                    selectModule = modules.get(i);
                    break;
                }
            }
            if (selectModule == null || !selectModule.isSubscribed()) {
                showInvalidDialog();

            } else {
                if (selectModule.getModuleType() == Constant.ModuleType.MEMBER) {
                    if (MainAplication.get(this).isLogin()) {
                        int activityId = Integer.parseInt(uri.getQueryParameter("activityId"));
                        Intent[] intents = {getModuleIntent(selectModule), Bundler.prizeDetailActivity(activityId).intent(this)};
                        startActivities(intents);
                    } else {
                        navigate.navigateToLogin(this, null, false);
                    }

                } else if (selectModule.getModuleType() == Constant.ModuleType.SHOPSERVICE) {
                    int productMainId = Integer.parseInt(uri.getQueryParameter("productMainId"));
                    if (MainAplication.get(this).isLogin()) {
                        if (MainAplication.get(this).getUserComponent().user().getRoleId() == 10002) {
                            Intent[] intents = {getModuleIntent(selectModule), Bundler.productDetailActivity(productMainId).intent(this)};
                            startActivities(intents);
                        } else {
                            navigate.navigateToModulePermissionDeniedActivity(this, null, false, selectModule);
                        }

                    } else {
                        navigate.navigateToLogin(this, null, false);
                    }
                } else {
                    int productMainId = Integer.parseInt(uri.getQueryParameter("productMainId"));
                    Intent[] intents = {getModuleIntent(selectModule), Bundler.productDetailActivity(productMainId).intent(this)};
                    startActivities(intents);
                }

            }

        }
    }


    @Override
    protected void initMap() {
        getMapView().showZoomControls(false);
        getMapView().showScaleControl(true);

//        LatLng point = new LatLng(28.175983, 113.023015);
//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, 17.0f);
//        mBaiduMap.setMapStatus(u);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Logger.d("onMapLoaded");
                BDLocation location = MainAplication.get(ServicesActivity.this).getLocationService().getSuccessLocation();
                if (LocationUtils.isLocationSuccess(location)) {
                    if (mBaiduMap != null) {
                        // 地图显示我的位置
                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(location.getRadius())// 定位精度
                                .direction(100)// GPS定位时方向角度,顺时针0-360
                                .latitude(location.getLatitude())// 百度纬度坐标
                                .longitude(location.getLongitude())// 百度经度坐标
                                .speed(location.getSpeed())// GPS定位时速度
                                .satellitesNum(location.getSatelliteNumber())// GPS定位时卫星数目
                                .build();
                        mBaiduMap.setMyLocationData(locData);
                    }
                    if (userRelativePreference.getShowLocation(false)) {
                        servicesViewModule.queryServicepointInCity(location,
                                userRelativePreference.getSelectedCity(null),
                                userRelativePreference.getSelectedServicePoint(null));
                    }
                }

            }

        });
        showMarker(servicePoint);
    }

    public void showFoldRedPacket(int count) {
        userRelativePreference.setShowBriberyMoney(false);
        startActivity(Bundler.briberyMoneyRemindActivity(count)
                .intent(this)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//        navigate.navigateToBriberyMoneyRemindActivity(this, null, false, count);
    }

    public void showQRDialog() {
        QRDialogFragmentBundler.build().create().show(getSupportFragmentManager(), "QRDIALOG");
    }

    public void showHighlight() {
        highLight = new HighLight(this)//
                .anchor(findViewById(R.id.rootlayout))//
                .addHighLight(R.id.qrView, R.layout.view_qr_highlight,
                        new HighLight.OnPosCallback() {
                            @Override
                            public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                                Logger.d("zg rightMargin " + rightMargin);
                                Logger.d("zg bottomMargin " + bottomMargin);
                                Logger.d("zg rectF " + rectF.toString());
//                                marginInfo.rightMargin = rightMargin + rectF.right - rectF.left;
//                                marginInfo.topMargin = bottomMargin;
                                marginInfo.rightMargin = ScreenUtils.dpToPx(50);
                                marginInfo.topMargin = ScreenUtils.dpToPx(300);
                                Logger.d("zg  marginInfo.rightMargin " + marginInfo.rightMargin);
                                Logger.d("zg  marginInfo.topMargin " + marginInfo.topMargin);
                            }
                        });
        highLight.show();
    }

    public void showSystemNotice(SystemNotice systemNotice) {
        if (systemNotice.isState()) {
            SystemNoticeDialogFragmentBundler.build()
                    .notifyText(systemNotice.getNotifyText()).create()
                    .show(getSupportFragmentManager(), "SYSTEMNOTICEDIALOG");
        }
    }


    @Override
    protected MapView getMapView() {
        return binding.mapView;
    }


    @Override
    public void onItemClickListener(View view, Module module, int position) {
        if (module.isSubscribed()) {
            if (module.getModuleType() == Constant.ModuleType.DELIVERY) {
                navigate.navigateToDeliveryHomeActivity(this, null, false, servicePoint, module);
                userRelativePreference.setSelectedModule(module);
            } else if (module.getModuleType() == Constant.ModuleType.SHOPSERVICE) {
                if (MainAplication.get(this).isLogin()) {
                    if (MainAplication.get(this).getUserComponent().user().getRoleId() == 10002) {
                        navigate.navigateToMainActivity(ServicesActivity.this, null, false, module, MainActivity.STORESTYLE);
                        userRelativePreference.setSelectedModule(module);
                    } else {
                        navigate.navigateToModulePermissionDeniedActivity(this, null, false, module);
                    }
                } else {
                    navigate.navigateToLogin(this, null, false);
                }
            } else if (module.getModuleType() == Constant.ModuleType.VEGETABLE) {
                navigate.navigateToMainActivity(ServicesActivity.this, null, false, module, MainActivity.VEGETABLE);
                userRelativePreference.setSelectedModule(module);
            } else if (module.getModuleType() == Constant.ModuleType.MORE) {
                navigate.navigateToMoreModule(ServicesActivity.this, null, false, modules);
                userRelativePreference.setSelectedModule(module);
            } else if (module.getModuleType() == Constant.ModuleType.VIP) {
                if (MainAplication.get(this).isLogin()) {
                    navigate.navigateToVipHome(ServicesActivity.this, null, false, VipHomeActivity.VIPHOME);
                    userRelativePreference.setSelectedModule(module);
                } else {
                    navigate.navigateToLogin(this, null, false);
                }
            } else if (module.getModuleType() == Constant.ModuleType.HOUSEKEEPING) {
                navigate.navigateToHouseKeeping(ServicesActivity.this, null, false);
                userRelativePreference.setSelectedModule(module);
            } else if (module.getModuleType() == Constant.ModuleType.MEMBER) {
                navigate.navigateToMemberHomeActivity(this, null, false);
                userRelativePreference.setSelectedModule(module);
            } else if (module.getModuleType() == Constant.ModuleType.SPECIALOFFER) {
                navigate.navigateToSpecialOfferActivity(this, null, false);
                userRelativePreference.setSelectedModule(module);
            }
        } else {
            navigate.navigateToUnsubscribeModule(this, null, false, module);
        }

    }

    public Intent getModuleIntent(Module module) {
        if (module.isSubscribed()) {
            userRelativePreference.setSelectedModule(module);
            if (module.getModuleType() == Constant.ModuleType.DELIVERY) {
                return Bundler.deliveryHomeActivity(servicePoint, module).intent(this);
            } else if (module.getModuleType() == Constant.ModuleType.SHOPSERVICE) {
                return Bundler.mainActivity(module, MainActivity.STORESTYLE).intent(this);
            } else if (module.getModuleType() == Constant.ModuleType.VEGETABLE) {
                return Bundler.mainActivity(module, MainActivity.VEGETABLE).intent(this);
            } else if (module.getModuleType() == Constant.ModuleType.MORE) {
                return Bundler.moreHomeActivity(modules).intent(this);
            } else if (module.getModuleType() == Constant.ModuleType.MEMBER)
                return Bundler.memberHomeActivity().intent(this);
            else if (module.getModuleType() == Constant.ModuleType.SPECIALOFFER)
                return Bundler.specialOfferActivity().intent(this);
        }
        return Bundler.unsubscribeModuleActivity(module).intent(this);
    }

    @Override
    public ServicesComponent getComponent() {
        return servicesComponent;
    }

    @Override
    public void onClick(View v) {
        if (v == binding.loginBtn) {
            navigate.navigateToIndividualCenter(this, null, false);
        }
        if (v == binding.servicepoint) {
            if (null != userRelativePreference.getSelectedCity(null))
                navigate.navigateToLocationActivity(this, null, true, userRelativePreference.getSelectedCity(null));
            else
                navigate.navigateToCityActivity(this, null, true);
        }
        if (v == binding.qrView) {
            if (MainAplication.get(this).isLogin())
                showQRDialog();
            else
                navigate.navigateToLogin(this, null, false);
        }
        if (v == binding.servicepointBtn)
            navigate.navigateToServicePointActivity(this, null, false, servicePoint);
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            String jpushId = JPushInterface.getRegistrationID(this);
            servicesViewModule.updateUserTerminalDetail(jpushId);
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
            binding.setUnReadInfoManager(unReadInfoManager);
            BackGroundService.updateUnRead(this);
        }

    }


    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onLoginCookieTimeoutEvent(LoginCookieTimeoutEvent event) {
        if (MainAplication.get(this).isLogin()) {
            MainAplication.get(this).loginOut();
            EventBus.getDefault().postSticky(new LogStateEvent(false));
        }
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocationEvent(LocationEvent event) {
        BDLocation location = event.getLocation();
        if (LocationUtils.isLocationSuccess(location)) {
            if (mBaiduMap != null) {
                // 地图显示我的位置
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())// 定位精度
                        .direction(100)// GPS定位时方向角度,顺时针0-360
                        .latitude(location.getLatitude())// 百度纬度坐标
                        .longitude(location.getLongitude())// 百度经度坐标
                        .speed(location.getSpeed())// GPS定位时速度
                        .satellitesNum(location.getSatelliteNumber())// GPS定位时卫星数目
                        .build();
                mBaiduMap.setMyLocationData(locData);
            }
        }
    }

    @Subscribe
    public void onNavigateToMemberHomeEvent(NavigateToMemberHomeEvent event) {
        Module module = null;
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getModuleType() == Constant.ModuleType.MEMBER) {
                module = modules.get(i);
                break;
            }
        }
        if (module != null)
            if (module.isSubscribed()) {
                navigate.navigateToMemberHomeActivity(this, null, false);
                userRelativePreference.setSelectedModule(module);
            } else {
                navigate.navigateToUnsubscribeModule(this, null, false, module);
            }
    }

    @Subscribe
    public void onNavigateToPrizeDetailEvent(NavigateToPrizeDetailEvent event) {
        Module module = null;
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getModuleType() == Constant.ModuleType.MEMBER) {
                module = modules.get(i);
                break;
            }
        }
        if (module != null)
            if (module.isSubscribed()) {
                if (MainAplication.get(this).isLogin()) {
                    userRelativePreference.setSelectedModule(module);
                    Intent[] intents = {getModuleIntent(module), Bundler.prizeDetailActivity(event.getActivityProductId()).intent(this)};
                    startActivities(intents);
                } else {
                    navigate.navigateToLogin(this, null, false);
                }
            } else {
                navigate.navigateToUnsubscribeModule(this, null, false, module);
            }
    }


    public void showMarker(ServicePoint servicePoint) {
        BitmapDescriptor markIcon = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_servicepoint);
        LatLng point = new LatLng(servicePoint.getLat(), servicePoint.getLng());
        servicepointMarkOptions = new MarkerOptions()//
                .position(point)//
                .icon(markIcon)//
                .draggable(false)//
                .animateType(MarkerOptions.MarkerAnimateType.drop);
        mBaiduMap.addOverlay(servicepointMarkOptions);

        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, 17.0f);
        mBaiduMap.setMapStatus(u);

        markIcon.recycle();
    }

    @Override
    public void onDialogPositiveClick(String fragmentTag) {
        super.onDialogPositiveClick(fragmentTag);
        if (fragmentTag.equals(locationDialogTag)) {
            userRelativePreference.setSelectedServicePoint(selectServicePoint);
            userRelativePreference.setShowDeliveryArea(true);
            userRelativePreference.setShowLocation(true);
            navigate.navigateToServicesActivity(ServicesActivity.this, null, true);
        }
    }


    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true; // 准备退出
            toastor.showSingletonToast("再按一次退出程序");

            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
        }
    }

}
