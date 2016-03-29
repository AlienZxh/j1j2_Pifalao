package com.j1j2.pifalao.feature.shopcart;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.j1j2.data.model.Module;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.ConfirmOrderSuccessEvent;
import com.j1j2.pifalao.app.event.RegisterSuccessEvent;
import com.j1j2.pifalao.databinding.ActivityShopcartBinding;
import com.j1j2.pifalao.feature.shopcart.di.ShopCartModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by alienzxh on 16-3-21.
 */
@RequireBundler
public class ShopCartActivity extends BaseActivity implements View.OnClickListener, ShopCartAdapter.OnShopCartChangeListener {

    ActivityShopcartBinding binding;

    @Arg
    Module module;

    @Inject
    ShopCartViewModel shopCartViewModel;

    public ObservableBoolean isLogin = new ObservableBoolean(true);

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
        binding.shopcartlist.getRecyclerView().setClipToPadding(false);
        binding.shopcartlist.getRecyclerView().setPadding(0, 0, 0, AutoUtils.getPercentHeightSize(100));
        shopCartViewModel.queryShopCart();
        shopCartViewModel.CountDown();
    }

    public void setAdapter(ShopCartAdapter adapter) {
        binding.shopcartlist.setAdapter(adapter);
        adapter.setOnShopCartChangeListener(this);
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
            navigate.navigateToConfirmOrder(this, null, false, module, shopCartViewModel.getShopCartItems());
        }
        if (v == binding.backBtn) {
            onBackPressed();
        }
    }

    @Override
    public void onRemoveBtnClickListener(View view, ShopCartItem shopCart, int position) {
        shopCartViewModel.removeShopCartItem(shopCart.getProductId());
    }

    @Override
    public void onQuantityChangeListener(View view, ShopCartItem shopCartItem, int quantity, int position) {
        if (quantity == shopCartViewModel.getShopCartItems().get(position).getQuantity())
            return;
        shopCartViewModel.getShopCartItems().get(position).setQuantity(quantity);
        shopCartViewModel.updateShopCart();
    }

    @Subscribe
    public void onConfirmOrderSuccessEvent(ConfirmOrderSuccessEvent event) {
        finish();
    }
}


