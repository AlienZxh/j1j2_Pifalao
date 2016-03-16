package com.j1j2.pifalao.feature.products;

import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductSort;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityProductsBinding;
import com.j1j2.pifalao.feature.products.di.ProductsModule;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler
public class ProductsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, ProductsAdapter.OnProductsClickListener {


    ActivityProductsBinding binding;

    @Arg
    ProductSort productSort;
    @Arg
    Module module;

    @Inject
    ProductsViewModel productsViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products);
        binding.setProductViewModel(productsViewModel);
        binding.productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productList.getRecyclerView().setItemAnimator(new SlideInUpAnimator());
        binding.productList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .drawable(R.drawable.item_products_divider)
                .size(AutoUtils.getPercentHeightSize(10))
                .build());
        binding.productList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.productList.setRefreshListener(this);
        binding.productList.setOnMoreListener(this);
    }

    @Override
    protected void initViews() {
        productsViewModel.queryProductyBySortId(true);
    }

    public void setProdutsAdapter(ProductsAdapter productsAdapter) {
        binding.productList.setAdapter(productsAdapter);
        productsAdapter.setOnProductsClickListener(this);
    }

    public void setLoadMoreEnable(boolean is) {
        binding.productList.setLoadingMore(is);
    }

    public void setLoadMoreFinish() {
        binding.productList.hideMoreProgress();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ProductsModule(this, productSort, module)).inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onRefresh() {
        productsViewModel.queryProductyBySortId(true);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        productsViewModel.queryProductyBySortId(false);
    }

    @Override
    public void onItemClickListener(View view, ProductSimple productSimple, int position) {
        navigate.navigateToProductDetailActivity(ProductsActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, productSimple);
    }

    @Override
    public void onAddIconClickListener(View view, ProductSimple productSimple, int position) {

    }
}
