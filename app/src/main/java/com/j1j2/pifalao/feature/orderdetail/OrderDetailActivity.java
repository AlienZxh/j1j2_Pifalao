package com.j1j2.pifalao.feature.orderdetail;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.j1j2.data.model.OrderDetail;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.OrderStateChangeEvent;
import com.j1j2.pifalao.databinding.ActivityOrderdetailBinding;
import com.j1j2.pifalao.feature.home.deliveryhome.DeliveryHomeTabAdapter;
import com.j1j2.pifalao.feature.orderdetail.di.OrderDetailModule;
import com.j1j2.pifalao.feature.orderdetail.orderdetailparams.OrderDetailParamsFragment;
import com.j1j2.pifalao.feature.orderdetail.orderdetailtimeline.OrderDetailTimeLineFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by alienzxh on 16-3-22.
 */
@RequireBundler
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener,
        OrderDetailParamsFragment.OrderDetailParamsFragmentListener,
        EasyPermissions.PermissionCallbacks {

    public static final int TIMELINE = 0;
    public static final int PARAM = 1;

    ActivityOrderdetailBinding binding;

    OrderDetail orderDetail;

    @Arg
    int orderId;

    @Arg
    int selectPage;

    @Inject
    OrderDetailViewModel orderDetailViewModel;

    OrderDetailParamsFragment orderDetailParamsFragment;
    OrderDetailTimeLineFragment orderDetailTimeLineFragment;

    private String callDialogTag = "CALLDIALOG";
    private String deleteDialogTag = "DELETEDIALOG";

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orderdetail);
        binding.setOrderDetailViewModel(orderDetailViewModel);
        binding.setOnClick(this);
    }

    @Override
    protected void initViews() {
        orderDetailParamsFragment = Bundler.orderDetailParamsFragment().create();
        orderDetailTimeLineFragment = Bundler.orderDetailTimeLineFragment(orderId).create();
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(orderDetailTimeLineFragment);
        fragments.add(orderDetailParamsFragment);
        String[] titles = {"订单状态", "订单详情"};
        binding.viewpager.setAdapter(new DeliveryHomeTabAdapter(getSupportFragmentManager(), fragments, titles));
        binding.tab.setViewPager(binding.viewpager);
        binding.viewpager.setCurrentItem(selectPage);
        //________________________________________________________________________

        orderDetailViewModel.queryOrderDetail(orderId);

    }

    public void initFragment() {
        //_________________________________________________________________________________
        orderDetailParamsFragment.initParams(orderDetailViewModel.orderDetailObservableField.get());
    }


    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new OrderDetailModule(this)).inject(this);
    }


    public void showDeleteDialog() {
        showMessageDialogDuplicate(true, deleteDialogTag, "提示", "确认取消该订单吗？", "取消", "确定");

    }

    @Subscribe
    public void onOrderStateChangeEvent(OrderStateChangeEvent event) {
        if (event.getNewOrderState() == Constant.OrderType.ORDERTYPE_INVALID)
            return;
        orderDetailViewModel.queryOrderDetail(orderId);
        orderDetailTimeLineFragment.queryOrderStateHistory();
        if (event.getNewOrderState() == Constant.OrderType.ORDERTYPE_WAITFORRATE) {
            navigate.navigateToOrderRate(this, null, false, orderDetailViewModel.orderDetailObservableField.get().getOrderBaseInfo().getOrderId());
        }
    }


    public void showCallDialog() {
        showMessageDialogDuplicate(true, callDialogTag, "提示", "确认拨打： " + orderDetailViewModel.orderDetailObservableField.get().getOrderShopInfo().getShopMobile() + "？", "取消", "确定");
    }


    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.servicepoint)
            navigateToCatServicePoint();
        if (v == binding.cancel) {
            showDeleteDialog();
        }
        if (v == binding.pay) {
            navigate.navigateToOnlineOrderPay(this, null, false, orderId, orderDetailViewModel.orderDetailObservableField.get().getOrderBaseInfo().getOrderNO(), true, false);
        }
        if (v == binding.receive) {
            orderDetailViewModel.receiveOrder(orderId);
        }
        if (v == binding.comment) {
            navigate.navigateToOrderRate(this, null, false, orderDetailViewModel.orderDetailObservableField.get().getOrderBaseInfo().getOrderId());
        }
        if (v == binding.callBtn) {
            showCallDialog();
        }


    }

    @Override
    public void navigateToProductDetailActivity(int mainId) {
        navigate.navigateToProductDetailActivity(this, null, false, mainId);
    }

    @Override
    public void navigateToCatServicePoint() {
        navigate.navigateToCatServicePoint(this, null, false, orderDetailViewModel.orderDetailObservableField.get().getOrderShopInfo().getShopId());
    }

    @Override
    public void onDialogPositiveClick(String fragmentTag) {
        super.onDialogPositiveClick(fragmentTag);
        if (fragmentTag.equals(callDialogTag)) {
            callServicePoint();
        }
        if (fragmentTag.equals(deleteDialogTag)) {
            orderDetailViewModel.cancleOrder(orderId);
        }
    }

    @SuppressWarnings("all")
    @AfterPermissionGranted(RC_CALLPHONE_PERM)
    private void callServicePoint() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +  orderDetailViewModel.orderDetailObservableField.get().getOrderShopInfo().getShopMobile()));
            startActivity(intent);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "联系服务点，缺少拨打电话权限。",
                    RC_CALLPHONE_PERM, Manifest.permission.CALL_PHONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == RC_CALLPHONE_PERM && EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "联系服务点，缺少拨打电话权限。")
                    .setTitle("缺少权限")
                    .setPositiveButton("设置")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SETTINGS_SCREEN) {
            // Do something after user returned from app settings screen, like showing a Toast.

        }
    }
}
