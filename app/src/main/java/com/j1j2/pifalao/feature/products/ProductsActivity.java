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
import in.workarounds.bundler.annotations.Required;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler(requireAll = false)
public class ProductsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, ProductsAdapter.OnProductsClickListener {

    public static final int PRODUCTS_TYPE_SORT = 1;
    public static final int PRODUCTS_TYPE_SEARCH = 2;

    ActivityProductsBinding binding;

    @Arg
    @Required(true)
    Module module;
    @Arg
    @Required(true)
    int activityType;
    @Arg
    @Required(false)
    ProductSort productSort;
    @Arg
    @Required(false)
    String key;


    @Inject
    ProductsViewModel productsViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products);
        productsViewModel.title.set(activityType == PRODUCTS_TYPE_SORT ? productSort.getSortName() : key);
        binding.setProductViewModel(productsViewModel);
        binding.productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productList.getRecyclerView().setItemAnimator(new SlideInUpAnimator());
        binding.productList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .drawable(R.drawable.item_products_divider)
                .size(AutoUtils.getPercentHeightSize(8))
                .build());
        binding.productList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.productList.setRefreshListener(this);
        binding.productList.setOnMoreListener(this);
    }

    @Override
    protected void initViews() {
        queryProducts(true);
    }

    public void queryProducts(boolean isRefresh) {
        if (activityType == PRODUCTS_TYPE_SORT)
            productsViewModel.queryProductyBySortId(isRefresh);
        else if (activityType == PRODUCTS_TYPE_SEARCH)
            productsViewModel.queryProductyByKey(isRefresh);
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
        MainAplication.get(this).getAppComponent().plus(new ProductsModule(this
                , null == productSort ? new ProductSort() : productSort
                , module, null == key ? "" : key)).inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onRefresh() {
        queryProducts(true);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        queryProducts(false);
    }

    @Override
    public void onItemClickListener(View view, ProductSimple productSimple, int position) {
        navigate.navigateToProductDetailActivity(ProductsActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, productSimple);
    }

    @Override
    public void onAddIconClickListener(View view, ProductSimple productSimple, int position) {

    }
}
