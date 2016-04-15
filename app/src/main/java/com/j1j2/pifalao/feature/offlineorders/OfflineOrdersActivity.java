package com.j1j2.pifalao.feature.offlineorders;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.model.OfflineOrderSimple;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityOfflineOrdersBinding;
import com.j1j2.pifalao.feature.offlineorders.di.OfflineOrdersModule;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-14.
 */
@RequireBundler
public class OfflineOrdersActivity extends BaseActivity implements View.OnClickListener, OfflineOrdersAdapter.OnOfflineOrderClickListener, SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    ActivityOfflineOrdersBinding binding;

    @Inject
    OfflineOrdersViewModel offlineOrdersViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offline_orders);
        binding.backBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
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
        offlineOrdersViewModel.queryOfflineOrders(true);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new OfflineOrdersModule(this)).inject(this);
    }

    public void setLoadMoreBegin() {
        binding.orderList.setupMoreListener(this, 1);
    }

    public void setLoadMoreComplete() {
        binding.orderList.hideMoreProgress();
        binding.orderList.removeMoreListener();
    }

    public void setAdapter(OfflineOrdersAdapter offlineOrdersAdapter) {
        binding.orderList.setAdapter(offlineOrdersAdapter);
        offlineOrdersAdapter.setOnOfflineOrderClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public void onOfflineOrderClick(View v, OfflineOrderSimple offlineOrderSimple, int position) {
        navigate.navigateToOfflineOrderDetail(this, null, false, offlineOrderSimple);
    }

    @Override
    public void onRefresh() {
        offlineOrdersViewModel.queryOfflineOrders(true);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        offlineOrdersViewModel.queryOfflineOrders(false);
    }
}
