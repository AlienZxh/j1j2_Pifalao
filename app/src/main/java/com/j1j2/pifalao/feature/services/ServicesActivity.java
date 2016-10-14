package com.j1j2.pifalao.feature.services;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

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
import com.j1j2.common.util.QRCodeUtils;
import com.j1j2.common.util.ScreenUtils;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.SystemNotice;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseMapActivity;
import com.j1j2.pifalao.app.event.LocationEvent;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.event.LoginCookieTimeoutEvent;
import com.j1j2.pifalao.app.event.NavigateToMemberHomeEvent;
import com.j1j2.pifalao.app.event.NavigateToPrizeDetailEvent;
import com.j1j2.pifalao.app.service.BackGroundService;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityServicesBinding;
import com.j1j2.pifalao.databinding.ViewQrcodeBinding;
import com.j1j2.pifalao.feature.home.viphome.VipHomeActivity;
import com.j1j2.pifalao.feature.main.MainActivity;
import com.j1j2.pifalao.feature.services.di.ServicesModule;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.orhanobut.logger.Logger;
import com.zhy.autolayout.utils.AutoUtils;

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
        ServicesAdapter.OnItemClickListener
        , View.OnClickListener
        , OnDismissListener {

    ActivityServicesBinding binding;

    @Inject
    ServicePoint servicePoint;

    @Inject
    ServicesViewModule servicesViewModule;

    @Inject
    UserRelativePreference userRelativePreference;

    MarkerOptions servicepointMarkOptions;

    ViewQrcodeBinding qrBinding;
    DialogPlus qrDialog;


    float rememberScreenBrightness = 0.0f;

    Bitmap appBitmap;
    Bitmap qrBitMap;

    List<Module> modules;

    BDLocation location;

    UnReadInfoManager unReadInfoManager = null;

    HighLight highLight;

    WebView webView;
    FrameLayout webviewContainer;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();

        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ServicesModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {

        binding = DataBindingUtil.setContentView(ServicesActivity.this, R.layout.activity_services);
        binding.setSercivepoint(servicePoint);

    }

    @Override
    protected void initViews() {
        if (userRelativePreference.getShowHighLight(true)) {
            showHighlight();
            userRelativePreference.setShowHighLight(false);
        }
        //_________________________________________________________________________________
        Window window = getWindow();
        WindowManager.LayoutParams windowLp = window.getAttributes();
        rememberScreenBrightness = windowLp.screenBrightness;
        //________________________________________________________________________________
        appBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        //______________________________________________________________________________________
        qrBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_qrcode, null, false);
        qrDialog = DialogPlus.newDialog(this).setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setInAnimation(android.R.anim.fade_in)
                .setOutAnimation(android.R.anim.fade_out)
                .setContentHolder(new ViewHolder(qrBinding.getRoot()))
                .setContentHeight(AutoUtils.getPercentHeightSize(675))
                .setContentWidth(AutoUtils.getPercentHeightSize(675))
                .setOnDismissListener(this)
                .setContentBackgroundResource(R.color.colorTransparent)
                .create();
        qrBinding.dialogClose.setOnClickListener(this);
        //_______________________________________________________________________________________
        binding.moduleList.setLayoutManager(new GridLayoutManager(this, 3));
        ScaleInAnimator scaleInAnimator = new ScaleInAnimator();
        scaleInAnimator.setAddDuration(300);
        binding.moduleList.setItemAnimator(scaleInAnimator);


        binding.moduleList.setAdapter(servicesViewModule.getServicesAdapter());
        servicesViewModule.getServicesAdapter().setOnItemClickListener(this);
        servicesViewModule.queryServicePointServiceModules();
        binding.setOnClick(this);
        //__________________________________________________________________________________________
        servicesViewModule.querySystemNotice();
        //_______________________________________________________________
    }

    public void showLocationDialog(final ServicePoint servicePoint) {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setMessage(servicePoint.getName() + "离您更近，是否进行切换？")
                .setNegativeButton("取消", null)
                .setPositiveButton("切换", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userRelativePreference.setSelectedServicePoint(servicePoint);
                        userRelativePreference.setShowDeliveryArea(true);
                        userRelativePreference.setShowLocation(true);
                        navigate.navigateToServicesActivity(ServicesActivity.this, null, true);
                    }
                })
                .create();
        messageDialog.show();
    }

    public void showInvalidDialog() {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setMessage("无效的商品")
                .setPositiveButton("知道了", null)
                .create();
        messageDialog.show();
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

        showMarker(servicePoint);

    }


    public void setQRCode(final String str) {
        qrBinding.qrcodeImg.post(new Runnable() {
            @Override
            public void run() {
                toastor.showSingletonToast("特权码已刷新");
                qrBitMap = QRCodeUtils.createQRCodeWithLogo(str, qrBinding.qrcodeImg.getHeight(), appBitmap);
                qrBinding.qrcodeImg.setImageBitmap(qrBitMap);
            }
        });
    }


    public void showFoldRedPacket(int count) {
        startActivity(Bundler.briberyMoneyRemindActivity(count)
                .intent(this)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//        navigate.navigateToBriberyMoneyRemindActivity(this, null, false, count);
    }

    public void showQRDialog() {
        initWindows();
        servicesViewModule.queryQRCode();
        qrDialog.show();
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
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();

        if (systemNotice.isState()) {
            View systemNoticeView = getLayoutInflater().inflate(
                    R.layout.view_systemnotice, null);

            webviewContainer = (FrameLayout) systemNoticeView
                    .findViewById(R.id.webviewContainer);

            if (webView == null) {
                webView = new WebView(this);
                webView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                webviewContainer.addView(webView);
            }

            WebSettings webSettings = webView.getSettings();
            webSettings
                    .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webSettings.setUseWideViewPort(false);
            webView.loadDataWithBaseURL(
                    "http://218.244.128.140:7171",
                    systemNotice.getNotifyText(),
                    "text/html",
                    "utf-8", null);
            messageDialog = new AlertDialog.Builder(this)
                    .setCancelable(true)
                    .setTitle("通知")
                    .setNegativeButton("知道了", null)
                    .setView(systemNoticeView, ScreenUtils.dpToPx(15), 0, ScreenUtils.dpToPx(15), 0)
                    .create();
            messageDialog.show();
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
    public void onClick(View v) {
        if (v == binding.loginBtn) {
            if (MainAplication.get(this).isLogin())
                navigate.navigateToIndividualCenter(this, null, false);
            else
                navigate.navigateToLogin(this, null, false);
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
        if (v == qrBinding.dialogClose) {
            if (qrDialog.isShowing())
                qrDialog.dismiss();
        }
        if (v == binding.servicepointBtn)
            navigate.navigateToServicePointActivity(this, null, false, servicePoint, location);
    }

    @Override
    public void onDismiss(DialogPlus dialog) {
        rememberScreenBrightness();
        servicesViewModule.stopQueryQRCode();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            String jpushId = JPushInterface.getRegistrationID(this);
            servicesViewModule.updateUserTerminalDetail(jpushId);
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
            binding.setUnReadInfoManager(unReadInfoManager);
            BackGroundService.updateUnRead(this);
            if (userRelativePreference.getShowBriberyMoney(true)) {
                servicesViewModule.queryFoldRedPacketCount();
                userRelativePreference.setShowBriberyMoney(false);
            }
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
        location = event.getLocation();
        if (isLocationSuccess(location)) {
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
                userRelativePreference.setShowLocation(false);
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

    private void rememberScreenBrightness() {
        Window window = getWindow();
        WindowManager.LayoutParams windowLp = window.getAttributes();
        windowLp.screenBrightness = rememberScreenBrightness;
        window.setAttributes(windowLp);
    }

    private void initWindows() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        WindowManager.LayoutParams windowLp = window.getAttributes();
        windowLp.screenBrightness = 1.0f;
        window.setAttributes(windowLp);
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
    protected void onDestroy() {
        super.onDestroy();
        if (webviewContainer != null && webView != null) {
            webviewContainer.removeAllViews();
            webView.destroy();
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
