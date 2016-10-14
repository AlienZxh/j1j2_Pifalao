package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.j1j2.common.view.scrollablelayout.ScrollableHelper;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.event.LocationEvent;
import com.j1j2.pifalao.databinding.FragmentDeliveryhomeServicepointBinding;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.di.DeliveryServicepointModule;
import com.j1j2.common.util.Toastor;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-30.
 */
@RequireBundler
public class DeliveryHomeServicepointFragment extends BaseFragment implements View.OnClickListener, ScrollableHelper.ScrollableContainer {

    @Override
    protected String getFragmentName() {
        return "DeliveryHomeServicepointFragment";
    }

    FragmentDeliveryhomeServicepointBinding binding;

    @Arg
    ServicePoint servicePoint;

    @Inject
    DeliveryServicepointViewModel deliveryServicepointViewModel;

    @Inject
    Toastor toastor;

    BDLocation location;

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deliveryhome_servicepoint, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setDeliveryServicepointViewModel(deliveryServicepointViewModel);
        deliveryServicepointViewModel.queryModule();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(getContext()).getAppComponent().plus(new DeliveryServicepointModule(this, servicePoint)).inject(this);
    }

    private void showCallDialog() {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .setTitle("提示")
                .setNegativeButton("取消", null)
                .setMessage("确认拨打： " + servicePoint.getMobile() + "？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PackageManager pkm = getActivity().getPackageManager();
                        boolean has_permission = (PackageManager.PERMISSION_GRANTED
                                == pkm.checkPermission("android.permission.CALL_PHONE", "com.j1j2.pifalao"));
                        if (has_permission) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + servicePoint.getMobile()));
                            startActivity(intent);
                        } else {
                            toastor.showSingletonToast("没有拨打电话权限");
                        }
                    }
                })
                .create();
        messageDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.callBtn) {
            showCallDialog();
        } else if (v == binding.navigationBtn) {
            if (location == null) {
                toastor.showSingletonToast("定位失败");
                return;
            }
            NaviParaOption naviParaOption = new NaviParaOption()
                    .startPoint(new LatLng(location.getLatitude(), location.getLongitude()))
                    .endPoint(new LatLng(servicePoint.getLat(), servicePoint.getLng()));
            BaiduMapNavigation.setSupportWebNavi(true);
            BaiduMapNavigation.openBaiduMapNavi(naviParaOption, getContext());
        }
    }

    public void setFreightType(FreightType freightType) {
        binding.setFreightType(freightType);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocationEvent(LocationEvent event) {
        location = event.getLocation();

    }

    @Override
    public View getScrollableView() {
        return binding.scrollview;
    }
}
