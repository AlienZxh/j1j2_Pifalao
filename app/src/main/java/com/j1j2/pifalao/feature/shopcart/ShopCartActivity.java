package com.j1j2.pifalao.feature.shopcart;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.model.Module;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityShopcartBinding;
import com.j1j2.pifalao.feature.shopcart.di.ShopCartModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-21.
 */
@RequireBundler
public class ShopCartActivity extends BaseActivity implements View.OnClickListener {

    ActivityShopcartBinding binding;

    @Arg
    Module module;

    @Inject
    ShopCartViewModel shopCartViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopcart);
        binding.setShopCartViewModel(shopCartViewModel);
    }

    @Override
    protected void initViews() {
        binding.shopcartlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.shopcartlist.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .drawable(R.drawable.item_products_divider)
                .size(AutoUtils.getPercentHeightSize(8))
                .build());
        shopCartViewModel.queryShopCart();

    }

    public void setAdapter(ShopCartAdapter adapter) {
        binding.shopcartlist.setAdapter(adapter);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new ShopCartModule(this, module)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.confirmOrder) {
            navigate.navigateToConfirmOrder(this, null, false, module);
        }
    }
}
