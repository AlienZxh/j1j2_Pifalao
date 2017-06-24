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
import com.j1j2.data.model.Shop;
import com.j1j2.data.model.ShopSubscribeService;
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
public class ServicesActivity extends BaseMapActivity implements
        ServicesAdapter.OnItemClickListener,
        View.OnClickListener,
        QRDialogFragment.QRDialogFragmentListener {

    ActivityServicesBinding binding;

    ServicesComponent servicesComponent;

    @Inject
    Shop shop;

    @Inject
    ServicesViewModule servicesViewModule;

    @Inject
    UserRelativePreference userRelativePreference;

    MarkerOptions servicepointMarkOptions;

    List<ShopSubscribeService> shopSubscribeServices;

    UnReadInfoManager unReadInfoManager = null;

    HighLight highLight;

    private String locationDialogTag = "LOCATIONDIALOG";
    private String invalidDialogTag = "INVALIDDIALOG";

    Shop selectShop;

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
        binding.setSercivepoint(shop);
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
        binding.shopSubscribeServiceList.setLayoutManager(new GridLayoutManager(this, 3));
        ScaleInAnimator scaleInAnimator = new ScaleInAnimator();
        scaleInAnimator.setAddDuration(400);
        scaleInAnimator.setInterpolator(new EaseBounceOutInterpolator());
        binding.shopSubscribeServiceList.setItemAnimator(scaleInAnimator);


        binding.shopSubscribeServiceList.setAdapter(servicesViewModule.getServicesAdapter());
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

    public void showLocationDialog(final Shop shop) {
        selectShop = shop;
        showMessageDialogDuplicate(true, locationDialogTag, "提示", shop.getName() + "离您更近，是否进行切换？", "取消", "切换");
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

    public void setShopSubscribeServices(List<ShopSubscribeService> shopSubscribeServices) {
        this.shopSubscribeServices = shopSubscribeServices;
    }

    public void launchFromUrl() {
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            Logger.e("moduleId " + uri.getQueryParameter("moduleId"));

            int moduleId = Integer.parseInt(uri.getQueryParameter("moduleId"));


            ShopSubscribeService selectShopSubscribeService = null;
            int size = shopSubscribeServices.size();
            for (int i = 0; i < size; i++) {
                if (shopSubscribeServices.get(i).getServiceId() == moduleId) {
                    selectShopSubscribeService = shopSubscribeServices.get(i);
                    break;
                }
            }
            if (selectShopSubscribeService == null || !selectShopSubscribeService.isSubscribed()) {
                showInvalidDialog();

            } else {
                if (selectShopSubscribeService.getServiceType() == Constant.ModuleType.MEMBER) {
                    if (MainAplication.get(this).isLogin()) {
                        int activityId = Integer.parseInt(uri.getQueryParameter("activityId"));
                        Intent[] intents = {getModuleIntent(selectShopSubscribeService), Bundler.prizeDetailActivity(activityId).intent(this)};
                        startActivities(intents);
                    } else {
                        navigate.navigateToLogin(this, null, false);
                    }

                } else if (selectShopSubscribeService.getServiceType() == Constant.ModuleType.SHOPSERVICE) {
                    int productMainId = Integer.parseInt(uri.getQueryParameter("productMainId"));
                    if (MainAplication.get(this).isLogin()) {
                        if (MainAplication.get(this).getUserComponent().user().getRoleId() == 10002) {
                            Intent[] intents = {getModuleIntent(selectShopSubscribeService), Bundler.productDetailActivity(productMainId).intent(this)};
                            startActivities(intents);
                        } else {
                            navigate.navigateToModulePermissionDeniedActivity(this, null, false, selectShopSubscribeService);
                        }

                    } else {
                        navigate.navigateToLogin(this, null, false);
                    }
                } else {
                    int productMainId = Integer.parseInt(uri.getQueryParameter("productMainId"));
                    Intent[] intents = {getModuleIntent(selectShopSubscribeService), Bundler.productDetailActivity(productMainId).intent(this)};
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
        showMarker(shop);
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
    public void onItemClickListener(View view, ShopSubscribeService shopSubscribeService, int position) {
        if (shopSubscribeService.isSubscribed()) {
            if (shopSubscribeService.getServiceType() == Constant.ModuleType.DELIVERY) {
                navigate.navigateToDeliveryHomeActivity(this, null, false, shop, shopSubscribeService);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.SHOPSERVICE) {
                if (MainAplication.get(this).isLogin()) {
                    if (MainAplication.get(this).getUserComponent().user().getRoleId() == 10002) {
                        navigate.navigateToMainActivity(ServicesActivity.this, null, false, shopSubscribeService, MainActivity.STORESTYLE);
                        userRelativePreference.setSelectedModule(shopSubscribeService);
                    } else {
                        navigate.navigateToModulePermissionDeniedActivity(this, null, false, shopSubscribeService);
                    }
                } else {
                    navigate.navigateToLogin(this, null, false);
                }
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.VEGETABLE) {
                navigate.navigateToMainActivity(ServicesActivity.this, null, false, shopSubscribeService, MainActivity.VEGETABLE);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.MORE) {
                navigate.navigateToMoreModule(ServicesActivity.this, null, false, shopSubscribeServices);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.VIP) {
                if (MainAplication.get(this).isLogin()) {
                    navigate.navigateToVipHome(ServicesActivity.this, null, false, VipHomeActivity.VIPHOME);
                    userRelativePreference.setSelectedModule(shopSubscribeService);
                } else {
                    navigate.navigateToLogin(this, null, false);
                }
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.HOUSEKEEPING) {
                navigate.navigateToHouseKeeping(ServicesActivity.this, null, false);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.MEMBER) {
                navigate.navigateToMemberHomeActivity(this, null, false);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.SPECIALOFFER) {
                navigate.navigateToSpecialOfferActivity(this, null, false);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            }
        } else {
            navigate.navigateToUnsubscribeModule(this, null, false, shopSubscribeService);
        }

    }

    public Intent getModuleIntent(ShopSubscribeService shopSubscribeService) {
        if (shopSubscribeService.isSubscribed()) {
            userRelativePreference.setSelectedModule(shopSubscribeService);
            if (shopSubscribeService.getServiceType() == Constant.ModuleType.DELIVERY) {
                return Bundler.newDeliveryHomeActivity(shop.getShopId(), shopSubscribeService).intent(this);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.SHOPSERVICE) {
                return Bundler.mainActivity(shopSubscribeService, MainActivity.STORESTYLE).intent(this);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.VEGETABLE) {
                return Bundler.mainActivity(shopSubscribeService, MainActivity.VEGETABLE).intent(this);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.MORE) {
                return Bundler.moreHomeActivity(shopSubscribeServices).intent(this);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.MEMBER)
                return Bundler.memberHomeActivity().intent(this);
            else if (shopSubscribeService.getServiceType() == Constant.ModuleType.SPECIALOFFER)
                return Bundler.specialOfferActivity().intent(this);
        }
        return Bundler.unsubscribeModuleActivity(shopSubscribeService).intent(this);
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
            navigate.navigateToServicePointActivity(this, null, false, shop);
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
        ShopSubscribeService shopSubscribeService = null;
        for (int i = 0; i < shopSubscribeServices.size(); i++) {
            if (shopSubscribeServices.get(i).getServiceType() == Constant.ModuleType.MEMBER) {
                shopSubscribeService = shopSubscribeServices.get(i);
                break;
            }
        }
        if (shopSubscribeService != null)
            if (shopSubscribeService.isSubscribed()) {
                navigate.navigateToMemberHomeActivity(this, null, false);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            } else {
                navigate.navigateToUnsubscribeModule(this, null, false, shopSubscribeService);
            }
    }

    @Subscribe
    public void onNavigateToPrizeDetailEvent(NavigateToPrizeDetailEvent event) {
        ShopSubscribeService shopSubscribeService = null;
        for (int i = 0; i < shopSubscribeServices.size(); i++) {
            if (shopSubscribeServices.get(i).getServiceType() == Constant.ModuleType.MEMBER) {
                shopSubscribeService = shopSubscribeServices.get(i);
                break;
            }
        }
        if (shopSubscribeService != null)
            if (shopSubscribeService.isSubscribed()) {
                if (MainAplication.get(this).isLogin()) {
                    userRelativePreference.setSelectedModule(shopSubscribeService);
                    Intent[] intents = {getModuleIntent(shopSubscribeService), Bundler.prizeDetailActivity(event.getActivityProductId()).intent(this)};
                    startActivities(intents);
                } else {
                    navigate.navigateToLogin(this, null, false);
                }
            } else {
                navigate.navigateToUnsubscribeModule(this, null, false, shopSubscribeService);
            }
    }


    public void showMarker(Shop shop) {
        BitmapDescriptor markIcon = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_servicepoint);
        LatLng point = new LatLng(shop.getLat(), shop.getLng());
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
            userRelativePreference.setSelectedServicePoint(selectShop);
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
