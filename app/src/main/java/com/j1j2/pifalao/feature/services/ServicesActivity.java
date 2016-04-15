package com.j1j2.pifalao.feature.services;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Circle;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.j1j2.common.util.QRCodeUtils;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseMapActivity;
import com.j1j2.pifalao.app.event.LocationEvent;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.event.LoginCookieTimeoutEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityServicesBinding;
import com.j1j2.pifalao.databinding.ViewQrcodeBinding;
import com.j1j2.pifalao.feature.main.MainActivity;
import com.j1j2.pifalao.feature.services.di.ServicesModule;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler
public class ServicesActivity extends BaseMapActivity implements ServicesAdapter.OnItemClickListener, View.OnClickListener, OnDismissListener, OnGetRoutePlanResultListener {

    ActivityServicesBinding binding;

    @Arg
    ServicePoint servicePoint;

    @Inject
    ServicesViewModule servicesViewModule;

    @Inject
    UserRelativePreference userRelativePreference;

    Marker servicepointMark;
    MarkerOptions servicepointMarkOptions;

    ViewQrcodeBinding qrBinding;
    DialogPlus qrDialog;

    RoutePlanSearch routePlanSearch = RoutePlanSearch.newInstance();
    PlanNode enNode;
    int nodeIndex = -1; // 节点索引,供浏览节点时使用
    RouteLine route = null;
    OverlayManager routeOverlay = null;

    CircleOptions circleOverlayOptions = null;
    Circle circleOverlay = null;

    float rememberScreenBrightness = 0.0f;

    Bitmap appBitmap;
    Bitmap qrBitMap;

    List<Module> modules;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(ServicesActivity.this, R.layout.activity_services);
        binding.setSercivepoint(servicePoint);
        enNode = PlanNode.withLocation(new LatLng(servicePoint.getLat(), servicePoint.getLng()));
    }

    @Override
    protected void initViews() {
        Window window = getWindow();
        WindowManager.LayoutParams windowLp = window.getAttributes();
        rememberScreenBrightness = windowLp.screenBrightness;
        //________________________________________________________________________________
        appBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        //______________________________________________________________________________________
        qrBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_qrcode, null, false);
        qrDialog = DialogPlus.newDialog(this).setGravity(Gravity.CENTER)
                .setCancelable(false)
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
        scaleInAnimator.setAddDuration(800);
        binding.moduleList.setItemAnimator(scaleInAnimator);
        binding.moduleList.setAdapter(servicesViewModule.getServicesAdapter());
        servicesViewModule.getServicesAdapter().setOnItemClickListener(this);
        servicesViewModule.queryServicePointServiceModules();
        binding.setOnClick(this);
        //__________________________________________________________________________________________

    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setNegativeButton("取消", null)
                .setMessage("确认退出吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        showExitDialog();
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public void stopSearchAnimate() {
        binding.radarImg.postDelayed(new Runnable() {
            @Override
            public void run() {
                clearAnimImg();
            }
        }, 1800);
    }

    private void clearAnimImg() {
//        binding.radarImg.stopRotate(false);
//        binding.radarImg.setVisibility(View.GONE);
//        binding.radarBgImg.setVisibility(View.GONE);
        binding.radarImg.stopRotate(false);
        if (binding.radarImg != null)
            binding.imgLayout.removeView(binding.radarImg);
        if (binding.radarBgImg != null)
            binding.imgLayout.removeView(binding.radarBgImg);

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ServicesModule(this, servicePoint)).inject(this);
    }

    @Override
    protected void initMap() {
        getMapView().showZoomControls(false);
        getMapView().showScaleControl(true);

        LatLng point = new LatLng(28.175983, 113.023015);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, 17.0f);
        mBaiduMap.setMapStatus(u);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层

        routePlanSearch.setOnGetRoutePlanResultListener(this);
    }


    public void setQRCode(final String str) {
        qrBinding.qrcodeImg.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "特权码已刷新", Toast.LENGTH_SHORT).show();
                qrBitMap = QRCodeUtils.createQRCodeWithLogo(str, qrBinding.qrcodeImg.getHeight(), appBitmap);
                qrBinding.qrcodeImg.setImageBitmap(qrBitMap);
            }
        });

    }

    public void showQRDialog() {
        initWindows();
        servicesViewModule.queryQRCode();
        qrDialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        clearAnimImg();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        routePlanSearch.destroy();
    }

    @Override
    protected MapView getMapView() {
        return binding.mapView;
    }


    @Override
    public void onItemClickListener(View view, Module module, int position) {
        if (module.getModuleType() == Constant.ModuleType.DELIVERY && module.isSubscribed()) {
            navigate.navigateToDeliveryHomeActivity(this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, servicePoint, module);
            userRelativePreference.setSelectedModule(module);
        } else if (module.getModuleType() == Constant.ModuleType.SHOPSERVICE && module.isSubscribed()) {
            navigate.navigateToMainActivity(ServicesActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, module, MainActivity.STORESTYLE);
            userRelativePreference.setSelectedModule(module);
        } else if (module.getModuleType() == Constant.ModuleType.VEGETABLE && module.isSubscribed()) {
            navigate.navigateToMainActivity(ServicesActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, module, MainActivity.VEGETABLE);
            userRelativePreference.setSelectedModule(module);
        } else if (module.getModuleType() == Constant.ModuleType.MORE && module.isSubscribed()) {
            navigate.navigateToMoreModule(ServicesActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, modules);
            userRelativePreference.setSelectedModule(module);
        } else if (module.getModuleType() == Constant.ModuleType.VIP && module.isSubscribed()) {
            if (MainAplication.get(this).isLogin()) {
                navigate.navigateToVipHome(ServicesActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false);
                userRelativePreference.setSelectedModule(module);
            } else {
                navigate.navigateToLogin(this, null, false);
            }
        }
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
        if (v == binding.dragView) {
            if (MainAplication.get(this).isLogin())
                showQRDialog();
            else
                navigate.navigateToLogin(this, null, false);
        }
        if (v == qrBinding.dialogClose) {
            if (qrDialog.isShowing())
                qrDialog.dismiss();
        }
    }

    @Override
    public void onDismiss(DialogPlus dialog) {
        rememberScreenBrightness();
        servicesViewModule.stopQueryQRCode();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            servicesViewModule.updateUserTerminalDetail(JPushInterface.getRegistrationID(this));
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
        if (isLocationSuccess(location)) {
            // 地图显示我的位置
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())// 定位精度
                    .direction(100)// GPS定位时方向角度,顺时针0-360
                    .latitude(location.getLatitude())// 百度纬度坐标
                    .longitude(location.getLongitude())// 百度经度坐标
                    .speed(location.getSpeed())// GPS定位时速度
                    .satellitesNum(location.getSatelliteNumber())// GPS定位时卫星数目
                    .build();
            if (mBaiduMap != null)
                mBaiduMap.setMyLocationData(locData);
            circleOverlay(location);
            if (routeOverlay == null) {
                PlanNode stNode = PlanNode.withLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                routePlanSearch.drivingSearch((new DrivingRoutePlanOption())
                        .from(stNode).to(enNode));
            }
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

    public void circleOverlay(BDLocation location) {
        if (circleOverlay != null) {
            circleOverlay.remove();
        }
        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
        circleOverlayOptions = new CircleOptions();
        circleOverlayOptions.center(point);
        circleOverlayOptions.radius(2000);
        circleOverlayOptions.fillColor(0x3300ffff);
        circleOverlayOptions.stroke(new Stroke(1, 0xff00c8c8));
        circleOverlay = (Circle) mBaiduMap.addOverlay(circleOverlayOptions);
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
        //获取步行线路规划结果
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
        //获取公交换乘路径规划结果
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
        //获取驾车线路规划结果
        if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            if (routeOverlay != null) {
                routeOverlay.removeFromMap();
            }
            nodeIndex = -1;
            route = drivingRouteResult.getRouteLines().get(0);
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
            routeOverlay = overlay;
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(drivingRouteResult.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
        //自行车
    }

    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {

            return null;

        }

        @Override
        public BitmapDescriptor getTerminalMarker() {

            return BitmapDescriptorFactory.fromResource(R.drawable.icon_servicepoint);

        }
    }
}
