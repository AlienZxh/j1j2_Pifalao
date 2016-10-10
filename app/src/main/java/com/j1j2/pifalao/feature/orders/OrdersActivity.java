package com.j1j2.pifalao.feature.orders;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.OfflineOrderSimple;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.OrderStateChangeEvent;
import com.j1j2.pifalao.databinding.ActivityOrdersBinding;
import com.j1j2.pifalao.feature.offlineorders.OfflineOrdersAdapter;
import com.j1j2.pifalao.feature.orderproducts.OrderProductsActivity;
import com.j1j2.pifalao.feature.orders.di.OrdersModule;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-22.
 */
@RequireBundler
public class OrdersActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OfflineOrdersAdapter.OnOfflineOrderClickListener, OnMoreListener, OrdersAdapter.OnOrdersClickListener, View.OnClickListener, OrdersTypeAdapter.OrdersTypeAdapterListener {


    ActivityOrdersBinding binding;

    @Arg
    int orderType;

    @Inject
    OrdersViewModel ordersViewModel;

    ObservableInt typeObservableInt;

    PopupWindow popupWindow;
    RecyclerView orderTypesView;
    OrdersTypeAdapter ordersTypeAdapter;
    SingleSelector singleSelector;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orders);

        typeObservableInt = new ObservableInt(orderType);
        binding.setOrderType(typeObservableInt);

        binding.setOrdersViewModel(ordersViewModel);
        binding.orderList.getRecyclerView().setClipToPadding(false);
        binding.orderList.getRecyclerView().setPadding(0, AutoUtils.getPercentHeightSize(20), 0, AutoUtils.getPercentHeightSize(20));
        binding.orderList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.orderList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .colorResId(R.color.colorTransparent)
                .size(AutoUtils.getPercentHeightSize(20))
                .build());
        binding.orderList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.orderList.setRefreshListener(this);

    }

    @Override
    protected void initViews() {
        ordersViewModel.queryOrders(true, typeObservableInt.get());
        //_____________________________________________________________________________-
        orderTypesView = new RecyclerView(this);
        orderTypesView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final GridLayoutManager gridLayoutManager;
        gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 8)
                    return 3;
                else
                    return 1;
            }
        });
        orderTypesView.setLayoutManager(gridLayoutManager);
        orderTypesView.setBackgroundColor(0xffffffff);
        orderTypesView.setAdapter(ordersTypeAdapter = new OrdersTypeAdapter(singleSelector = new SingleSelector()));


        if (orderType == Constant.OrderType.ORDERTYPE_UNPAY)
            singleSelector.setSelected(1, ordersTypeAdapter.getItemId(1), true);
        if (orderType == Constant.OrderType.ORDERTYPE_SUBMIT)
            singleSelector.setSelected(2, ordersTypeAdapter.getItemId(2), true);
        if (orderType == Constant.OrderType.ORDERTYPE_EXECUTING)
            singleSelector.setSelected(3, ordersTypeAdapter.getItemId(3), true);
        if (orderType == Constant.OrderType.ORDERTYPE_CLIENTWAITFORRECEVIE)
            singleSelector.setSelected(4, ordersTypeAdapter.getItemId(4), true);
        if (orderType == Constant.OrderType.ORDERTYPE_WAITFORRATE)
            singleSelector.setSelected(5, ordersTypeAdapter.getItemId(5), true);
        if (orderType == Constant.OrderType.ORDERTYPE_ALL)
            singleSelector.setSelected(7, ordersTypeAdapter.getItemId(7), true);

        ordersTypeAdapter.setListener(this);
        popupWindow = new PopupWindow(orderTypesView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x44000000));
//_________________________________________
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new OrdersModule(this)).inject(this);
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
                        ordersViewModel.cancleOrder(orderId);
                    }
                })
                .create();
        messageDialog.show();
    }


    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        ordersViewModel.queryOrders(false, typeObservableInt.get());
    }

    @Override
    public void onRefresh() {
        ordersViewModel.queryOrders(true, typeObservableInt.get());
    }

    public void setOrdersAdapter(OrdersAdapter ordersAdapter) {
        binding.orderList.setAdapter(ordersAdapter);
        ordersAdapter.setOnOrdersClickListener(this);
    }


    public void setOfflineOrdersAdapter(OfflineOrdersAdapter offlineOrdersAdapter) {
        binding.orderList.setAdapter(offlineOrdersAdapter);
        offlineOrdersAdapter.setOnOfflineOrderClickListener(this);
    }

    public void setLoadMoreBegin() {
        binding.orderList.setupMoreListener(this, 1);
    }

    public void setLoadMoreComplete() {
        binding.orderList.hideMoreProgress();
        binding.orderList.removeMoreListener();
    }

    public void showProgress() {

        binding.orderList.showProgress();
    }

    public void onLoadDataError() {
        binding.orderList.setRefreshing(false);
        binding.orderList.hideMoreProgress();
        binding.orderList.removeMoreListener();
        binding.orderList.showRecycler();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.actionBarTitle)
            popupWindow.showAsDropDown(binding.toolbar);
    }

    @Override
    public void onReceiveClickListener(View view, OrderSimple orderSimple, int position) {
        ordersViewModel.receiveOrder(orderSimple);
    }

    @Override
    public void onDetailClickListener(View view, OrderSimple orderSimple, int position, int selectpage) {
        navigate.navigateToOrderDetail(this, null, false, orderSimple, orderSimple.getOrderId(), selectpage);
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
    public void onOrderPayClickListener(View view, OrderSimple orderSimple, int position) {
        navigate.navigateToOnlineOrderPay(this, null, false, orderSimple.getOrderId(), orderSimple.getOrderNO(), false, false);
    }

    @Override
    public void onOrderProductClickListener(View view, OrderSimple orderSimple, int position) {
        navigate.navigateToOrderProducts(this, null, false, OrderProductsActivity.FROM_ORDERS, orderSimple.getModuleId(), null, orderSimple.getProductDetails());
    }

    public void navigateToOrderRate(OrderSimple orderSimple) {
        navigate.navigateToOrderRate(this, null, false, orderSimple);
    }

    @Subscribe
    public void onOrderStateChangeEvent(OrderStateChangeEvent event) {
        if (!event.isOnlyOrderDetail()) {
            orderType = event.getNewOrderState();
            typeObservableInt.set(orderType);
            onRefresh();
        } else {
            finish();
        }
        toastor.showSingletonToast(event.getNewOrderState());
    }

    @Override
    public void onOrderTypeClick(int position) {
        if (position == 1)
            typeObservableInt.set(Constant.OrderType.ORDERTYPE_UNPAY);
        if (position == 2)
            typeObservableInt.set(Constant.OrderType.ORDERTYPE_SUBMIT);
        if (position == 3)
            typeObservableInt.set(Constant.OrderType.ORDERTYPE_EXECUTING);
        if (position == 4)
            typeObservableInt.set(Constant.OrderType.ORDERTYPE_DELIVERY);
        if (position == 5)
            typeObservableInt.set(Constant.OrderType.ORDERTYPE_WAITFORRATE);
        if (position == 6)
            typeObservableInt.set(Constant.OrderType.ORDERTYPE_INVALID);
        if (position == 7)
            typeObservableInt.set(Constant.OrderType.ORDERTYPE_ALL);
        if (position == 9)
            typeObservableInt.set(Constant.OrderType.ORDERTYPE_OFFLINE);
        onRefresh();
        popupWindow.dismiss();
    }

    @Override
    public void onOfflineOrderClick(View v, OfflineOrderSimple offlineOrderSimple, int position) {
        navigate.navigateToOfflineOrderDetail(this, null, false, offlineOrderSimple);
    }
}
