package com.j1j2.pifalao.feature.orderdetail;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

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

/**
 * Created by alienzxh on 16-3-22.
 */
@RequireBundler
public class OrderDetailActivity extends BaseActivity {
    ActivityOrderdetailBinding binding;

    @Arg
    OrderSimple orderSimple;

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
    }

    @Override
    protected void initViews() {
        orderDetailViewModel.queryOrderDetail();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new OrderDetailModule(this, orderSimple)).inject(this);
    }
}
