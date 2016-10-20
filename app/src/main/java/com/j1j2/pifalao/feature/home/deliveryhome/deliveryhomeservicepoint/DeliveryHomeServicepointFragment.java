package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.j1j2.common.util.Toastor;
import com.j1j2.common.view.scrollablelayout.ScrollableHelper;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.dialog.impl.IDialogPositiveButtonClickListener;
import com.j1j2.pifalao.databinding.FragmentDeliveryhomeServicepointBinding;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.di.DeliveryServicepointModule;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-30.
 */
@RequireBundler
public class DeliveryHomeServicepointFragment extends BaseFragment implements View.OnClickListener
        , ScrollableHelper.ScrollableContainer
,IDialogPositiveButtonClickListener{

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

    private String callDialogTag = "CALLDIALOG";

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

    public void showCallDialog() {
        showMessageDialogDuplicate(true, callDialogTag, "提示", "确认拨打： " + servicePoint.getMobile() + "？", "取消", "确定");
    }

    @Override
    public void onClick(View v) {
        if (v == binding.callBtn) {
            showCallDialog();
        } else if (v == binding.navigationBtn) {
            BDLocation location = MainAplication.get(getContext()).getLocationService().getSuccessLocation();
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


    @Override
    public void onDialogPositiveClick(String fragmentTag) {
        if (fragmentTag.equals(callDialogTag)) {
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
    }

    public void setFreightType(FreightType freightType) {
        binding.setFreightType(freightType);
    }

    @Override
    public View getScrollableView() {
        return binding.scrollview;
    }
}
