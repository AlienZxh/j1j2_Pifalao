package com.j1j2.pifalao.feature.orderdetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.j1j2.data.model.OrderSimple;
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
import in.workarounds.bundler.annotations.Required;

/**
 * Created by alienzxh on 16-3-22.
 */
@RequireBundler
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener, OrderDetailParamsFragment.OrderDetailParamsFragmentListener {

    public static final int TIMELINE = 0;
    public static final int PARAM = 1;

    ActivityOrderdetailBinding binding;

    @Arg
    @Required(false)
    OrderSimple orderSimple;

    @Arg
    int orderId;

    @Arg
    int selectPage;

    @Inject
    OrderDetailViewModel orderDetailViewModel;


    OrderDetailParamsFragment orderDetailParamsFragment;
    OrderDetailTimeLineFragment orderDetailTimeLineFragment;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orderdetail);
        binding.setOrderDetailViewModel(orderDetailViewModel);

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
        if (orderSimple != null) {
            orderDetailViewModel.setOrderDetailObservableField(orderSimple);
            orderDetailViewModel.queryServicePoint(orderSimple.getServicePointId());
        } else {
            orderDetailViewModel.queryOrderDetail(orderId);
        }
    }

    public void initFragment() {
        orderDetailParamsFragment.initParams(orderDetailViewModel.orderDetailObservableField.get(), orderDetailViewModel.servicePointObservableField.get());
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new OrderDetailModule(this)).inject(this);
    }


    public void showDeleteDialog(final int orderId) {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setNegativeButton("取消", null)
                .setMessage("确认删除该订单吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderDetailViewModel.cancleOrder(orderId);
                    }
                })
                .create();
        messageDialog.show();
    }

    @Subscribe
    public void onOrderStateChangeEvent(OrderStateChangeEvent event) {
        orderDetailViewModel.queryOrderDetail(orderId);
        orderDetailTimeLineFragment.queryOrderStateHistory();
        if (event.getNewOrderState() == Constant.OrderType.ORDERTYPE_WAITFORRATE) {
            navigate.navigateToOrderRate(this, null, false, orderDetailViewModel.orderDetailObservableField.get());
        }
    }

    private void showCallDialog() {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setNegativeButton("取消", null)
                .setMessage("确认拨打： " + orderDetailViewModel.servicePointObservableField.get().getMobile() + "？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PackageManager pkm = getPackageManager();
                        boolean has_permission = (PackageManager.PERMISSION_GRANTED
                                == pkm.checkPermission("android.permission.CALL_PHONE", "com.j1j2.pifalao"));
                        if (has_permission) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + orderDetailViewModel.servicePointObservableField.get().getMobile()));
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
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.servicepoint)
            navigateToCatServicePoint();
        if (v == binding.cancel) {
            showDeleteDialog(orderId);
        }
        if (v == binding.receive) {
            orderDetailViewModel.receiveOrder(orderId);
        }
        if (v == binding.comment) {
            navigate.navigateToOrderRate(this, null, false, orderDetailViewModel.orderDetailObservableField.get());
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
        if (orderSimple != null)
            navigate.navigateToCatServicePoint(this, null, false, orderSimple.getServicePointId());
        else
            navigate.navigateToCatServicePoint(this, null, false, orderDetailViewModel.orderDetailObservableField.get().getServicePointId());
    }
}
