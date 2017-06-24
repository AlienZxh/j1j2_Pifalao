package com.j1j2.pifalao.feature.shopcart;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.model.Shop;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.ConfirmOrderSuccessEvent;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;
import com.j1j2.pifalao.app.sharedpreferences.FreightTypePrefrence;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityShopcartBinding;
import com.j1j2.pifalao.feature.shopcart.di.ShopCartModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-21.
 */
@RequireBundler
public class ShopCartActivity extends BaseActivity implements View.OnClickListener, ShopCartAdapter.OnShopCartChangeListener {

    ActivityShopcartBinding binding;

    @Arg
    int moduleId;

    @Inject
    ShopCartViewModel shopCartViewModel;

    public ObservableBoolean isLogin = new ObservableBoolean(true);

    @Inject
    UserRelativePreference userRelativePreference;
    @Inject
    FreightTypePrefrence freightTypePrefrence;

    ShopSubscribeService shopSubscribeService;

    private String deleteDialogTag = "DELETEDIALOG";
    ShopCartItem deleteShopCart;

    private Shop shop;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopcart);
        binding.setShopCartViewModel(shopCartViewModel);

        binding.setShopSubscribeService(shopSubscribeService);

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
        if (shop != null)
            shopCartViewModel.queryShopCart(shopSubscribeService.getServiceType(), shop.getShopId());
        if (shopSubscribeService.getServiceType() == Constant.ModuleType.DELIVERY)
            binding.setFreightType(freightTypePrefrence.getDeliveryFreightType(null));
        else
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
        MainAplication.get(this).getUserComponent().plus(new ShopCartModule(this, moduleId)).inject(this);
        shopSubscribeService = userRelativePreference.getSelectedModule(null);
        shop = userRelativePreference.getSelectedServicePoint(null);
    }

    public void showDeleteDialog(final ShopCartItem shopCart) {
        this.deleteShopCart = shopCart;
        showMessageDialogDuplicate(true, deleteDialogTag, "提示", "确认删除该商品吗？", "取消", "确定");
    }

    @Override
    public void onClick(View v) {
        if (v == binding.confirmOrder || v == binding.shopConfirmOrder) {
            if (shopCartViewModel.getShopCartItems() == null) {
                toastor.showSingletonToast("请等待购物车加载完成");
                return;
            }
            if (shopCartViewModel.getShopCartItems().size() <= 0) {
                toastor.showSingletonToast("您还未添加商品");
                return;
            }
            navigate.navigateToConfirmOrder(this, null, false, moduleId, shopCartViewModel.getShopCartItems());
        }
        if (v == binding.backBtn) {
            onBackPressed();
        }
    }

    @Override
    public void onLayoutClickListener(View view, ShopCartItem shopCart, int position) {
//        navigate.navigateToProductDetailActivity(this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, shopCart.getProductMainId());
    }

    @Override
    public void onRemoveBtnClickListener(View view, ShopCartItem shopCart, int position) {
        showDeleteDialog(shopCart);
    }

    @Override
    public void onQuantityChangeListener(View view, ShopCartItem shopCartItem, int quantity, int position) {
        if (quantity == shopCartViewModel.getShopCartItems().get(position).getQuantity())
            return;
        shopCartViewModel.getShopCartItems().get(position).setQuantity(quantity);
        shopCartViewModel.updateShopCart(shopSubscribeService.getServiceId(), shop.getShopId());
    }

    @Subscribe
    public void onConfirmOrderSuccessEvent(ConfirmOrderSuccessEvent event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onShopCartChangeEvent(ShopCartChangeEvent event) {
        if (shop != null)
            shopCartViewModel.queryShopCart(shopSubscribeService.getServiceType(), shop.getShopId());
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {

        } else {
            navigate.navigateToLogin(this, null, true);
        }
    }

    @Override
    public void onDialogPositiveClick(String fragmentTag) {
        super.onDialogPositiveClick(fragmentTag);
        if (fragmentTag.equals(deleteDialogTag)) {
            shopCartViewModel.removeShopCartItem(deleteShopCart.getProductId(), shopSubscribeService.getServiceId(), shop.getShopId());
        }
    }
}


