package com.j1j2.pifalao.feature.home.storestylehome;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSort;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityStoreStyleHomeBinding;
import com.j1j2.pifalao.feature.home.storestylehome.di.StoreStyleHomeModule;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

@RequireBundler
public class StoreStyleHomeActivity extends BaseActivity implements StoreStyleHomeAdapter.OnSortItemClickListener {

    ActivityStoreStyleHomeBinding binding;

    @Inject
    ProductApi productApi;

    @Arg
    Module module;

    @Inject
    StoreStyleHomeViewModel storeStyleHomeViewModel;

    GridLayoutManager manager;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_style_home);
        manager = new GridLayoutManager(this, 3);
        binding.sortList.setLayoutManager(manager);
    }

    public void setListAdapter(final StoreStyleHomeAdapter storeStyleHomeAdapter) {
        binding.sortList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                outRect.set(storeStyleHomeAdapter.geItemRect(position));

            }
        });
        binding.sortList.setAdapter(storeStyleHomeAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return storeStyleHomeAdapter.isItemHeader(position) ? manager.getSpanCount() : 1;
            }
        });
        storeStyleHomeViewModel.getStoreStyleHomeAdapter().setOnSortItemClickListener(this);
    }

    @Override
    protected void initViews() {
        storeStyleHomeViewModel.onCreate();
//        binding.tab.setCustomTabView(new MainTab(this,
//                new String[]{getResources().getString(R.string.icon_home), getResources().getString(R.string.icon_supplier), getResources().getString(R.string.icon_shopcart), getResources().getString(R.string.icon_mine)},
//                new String[]{getResources().getString(R.string.icon_home_fill), getResources().getString(R.string.icon_supplier_fill), getResources().getString(R.string.icon_shopcart_fill), getResources().getString(R.string.icon_mine_fill)},
//                new String[]{"首页", "供应商", "购物车", "我的"}));

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new StoreStyleHomeModule(this, module)).inject(this);
    }

    @Override
    public void onSortItemClickListener(View view, ProductSort productSort, int position) {
        navigate.navigateToProductsActivityFromSort(StoreStyleHomeActivity.this, null, false, productSort, module);
    }
}
