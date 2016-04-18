package com.j1j2.pifalao.feature.orders;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.model.OrderSimple;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.OrderStateChangeEvent;
import com.j1j2.pifalao.databinding.ActivityOrdersBinding;
import com.j1j2.pifalao.feature.orderproducts.OrderProductsActivity;
import com.j1j2.pifalao.feature.orders.di.OrdersModule;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-22.
 */
@RequireBundler
public class OrdersActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, OrdersAdapter.OnOrdersClickListener, View.OnClickListener {


    ActivityOrdersBinding binding;

    @Arg
    int orderType;

    @Inject
    OrdersViewModel ordersViewModel;

    AlertDialog deleteOrderDialog;


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orders);
        binding.setOrdersViewModel(ordersViewModel);
        binding.orderList.getRecyclerView().setClipToPadding(false);
        binding.orderList.getRecyclerView().setPadding(0, AutoUtils.getPercentHeightSize(10), 0, AutoUtils.getPercentHeightSize(10));
        binding.orderList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.orderList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .colorResId(R.color.colorTransparent)
                .size(AutoUtils.getPercentHeightSize(8))
                .build());
        binding.orderList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.orderList.setRefreshListener(this);
    }

    @Override
    protected void initViews() {
        ordersViewModel.queryOrders(true, orderType);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new OrdersModule(this)).inject(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (deleteOrderDialog != null && deleteOrderDialog.isShowing())
            deleteOrderDialog.cancel();
    }

    public void showDeleteDialog(final int orderId) {
        deleteOrderDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setNegativeButton("取消", null)
                .setMessage("确认删除该订单吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ordersViewModel.cancleOrder(orderId);
                    }
                })
                .create();
        deleteOrderDialog.show();
    }


    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        ordersViewModel.queryOrders(false, orderType);
    }

    @Override
    public void onRefresh() {
        ordersViewModel.queryOrders(true, orderType);
    }

    public void setOrdersAdapter(OrdersAdapter ordersAdapter) {
        binding.orderList.setAdapter(ordersAdapter);
        ordersAdapter.setOnOrdersClickListener(this);
    }

    public void setLoadMoreBegin() {
        binding.orderList.setupMoreListener(this, 1);
    }

    public void setLoadMoreComplete() {
        binding.orderList.hideMoreProgress();
        binding.orderList.removeMoreListener();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }

    @Override
    public void onReceiveClickListener(View view, OrderSimple orderSimple, int position) {
        ordersViewModel.receiveOrder(orderSimple.getOrderId());
    }

    @Override
    public void onDetailClickListener(View view, OrderSimple orderSimple, int position) {
        navigate.navigateToOrderDetail(this, null, false, orderSimple, orderSimple.getOrderId());
    }

    @Override
    public void onServicePointIconClickListener(View view, OrderSimple orderSimple, int position) {
        navigate.navigateToCatServicePoint(this, null, false, orderSimple.getServicePointId());
    }

    @Override
    public void onCancelPointIconClickListener(View view, OrderSimple orderSimple, int position) {
        showDeleteDialog(orderSimple.getOrderId());
    }

    @Override
    public void onCommentPointIconClickListener(View view, OrderSimple orderSimple, int position) {

    }

    @Override
    public void onOrderProductClickListener(View view, OrderSimple orderSimple, int position) {
        navigate.navigateToOrderProducts(this, null, false, OrderProductsActivity.FROM_ORDERS, orderSimple.getModuleId(), null, orderSimple.getProductDetails());
    }

    @Subscribe
    public void onOrderStateChangeEvent(OrderStateChangeEvent event) {
        orderType = event.getNewOrderState();
        ordersViewModel.queryOrders(true, orderType);
    }
}
