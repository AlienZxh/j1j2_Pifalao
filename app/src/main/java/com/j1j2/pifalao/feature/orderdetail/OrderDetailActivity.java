package com.j1j2.pifalao.feature.orderdetail;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.model.OrderProductDetail;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityOrderdetailBinding;
import com.j1j2.pifalao.feature.orderdetail.di.OrderDetailModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;

/**
 * Created by alienzxh on 16-3-22.
 */
@RequireBundler
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener, OrderProductsAdapter.OnItemClickListener {
    ActivityOrderdetailBinding binding;

    @Arg
    @Required(false)
    OrderSimple orderSimple;

    @Arg
    int orderId;

    @Inject
    OrderDetailViewModel orderDetailViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orderdetail);
        binding.setOrderDetailViewModel(orderDetailViewModel);
        binding.orderProductList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.orderProductList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.colorGrayF0F0F0)
                .margin(AutoUtils.getPercentWidthSize(20), 0).build());
    }

    public void setOrderProductListAdapter(OrderProductsAdapter orderProductListAdapter) {
        binding.orderProductList.setAdapter(orderProductListAdapter);
        orderProductListAdapter.setOnItemClickListener(this);

    }

    @Override
    protected void initViews() {
        if (orderSimple != null) {
            orderDetailViewModel.setOrderDetailObservableField(orderSimple);
            OrderProductsAdapter orderProductsAdapter = new OrderProductsAdapter(orderSimple.getProductDetails());
            setOrderProductListAdapter(orderProductsAdapter);
            orderDetailViewModel.queryServiceoint(orderSimple.getServicePointId());
        } else {
            orderDetailViewModel.queryOrderDetail(orderId);
        }
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new OrderDetailModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.catservicepoint || v == binding.servicepoint)
            if (orderSimple != null)
                navigate.navigateToCatServicePoint(this, null, false, orderSimple.getServicePointId());
            else
                navigate.navigateToCatServicePoint(this, null, false, orderDetailViewModel.orderDetailObservableField.get().getServicePointId());
        if (v == binding.cancel) {
            orderDetailViewModel.cancleOrder(orderId);
        }
        if (v == binding.receive) {

        }
        if (v == binding.comment) {

        }

    }

    @Override
    public void onItemClickListener(View v, OrderProductDetail orderProductDetail, int position) {
        navigate.navigateToProductDetailActivity(this, null, false, orderProductDetail.getProductMainId());
    }
}
