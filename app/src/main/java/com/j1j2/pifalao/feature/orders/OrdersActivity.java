package com.j1j2.pifalao.feature.orders;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.j1j2.data.model.OrderSimple;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.NavigateToHomeEvent;
import com.j1j2.pifalao.app.event.OrderCancelEvent;
import com.j1j2.pifalao.databinding.ActivityOrdersBinding;
import com.j1j2.pifalao.feature.orders.di.OrdersModule;
import com.j1j2.pifalao.feature.products.ProductsAdapter;
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

    public static final int ORDERTYPE_ALL = 0;//全部
    public static final int ORDERTYPE_SUBMIT = 1;//已下单
    public static final int ORDERTYPE_EXECUTING = 4;//处理中
    public static final int ORDERTYPE_CLIENTWAITFORRECEVIE = 16;//待收货
    public static final int ORDERTYPE_WAITFORRATE = 32;//待评价
    public static final int ORDERTYPE_COMPLETE = 64;//已完成
    public static final int ORDERTYPE_INVALID = 256;//已退订

    ActivityOrdersBinding binding;

    @Arg
    int orderType;

    @Inject
    OrdersViewModel ordersViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orders);
        binding.setOrdersViewModel(ordersViewModel);
        binding.orderList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.orderList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .drawable(R.drawable.item_products_divider)
                .size(AutoUtils.getPercentHeightSize(8))
                .build());
        binding.orderList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.orderList.setRefreshListener(this);
        binding.orderList.setOnMoreListener(this);
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

    public void setLoadMoreEnable(boolean is) {
        binding.orderList.setLoadingMore(is);
    }

    public void setLoadMoreFinish() {
        binding.orderList.hideMoreProgress();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
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
        ordersViewModel.cancleOrder(orderSimple.getOrderId());
    }

    @Override
    public void onCommentPointIconClickListener(View view, OrderSimple orderSimple, int position) {

    }


    @Subscribe
    public void onOrderCancelEvent(OrderCancelEvent event) {
        orderType = ORDERTYPE_INVALID;
        ordersViewModel.queryOrders(true, orderType);
    }
}
