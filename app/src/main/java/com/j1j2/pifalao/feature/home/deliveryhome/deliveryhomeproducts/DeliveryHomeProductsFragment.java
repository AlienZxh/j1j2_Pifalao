package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Size;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.quantityview.StateQuantityView;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.common.view.scrollablelayout.ScrollableHelper;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.data.model.SecondarySort;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;
import com.j1j2.pifalao.databinding.FragmentDeliveryhomeProductsBinding;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.di.DeliveryProductsModule;
import com.j1j2.common.util.Toastor;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.orhanobut.logger.Logger;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-30.
 */
@RequireBundler
public class DeliveryHomeProductsFragment extends BaseFragment implements DeliverySortAdapter.OnSortClickListener, SwipeRefreshLayout.OnRefreshListener, OnMoreListener, DeliveryProductsAdapter.OnProductClickListener, ScrollableHelper.ScrollableContainer {

    @Override
    protected String getFragmentName() {
        return "DeliveryHomeProductsFragment";
    }

    public interface DeliveryHomeProductsFragmentListener {
        void navigateToProductDetail(View view, ProductSimple productSimple, int position);

        void navigateToShopCart(View view, Module module);

        void navigateToLogin(View view);

        void showAddShopCartAnim(@Size(2) int[] startLocation);
    }

    private DeliveryHomeProductsFragmentListener listener;

    FragmentDeliveryhomeProductsBinding binding;

    @Arg
    Module module;

    SingleSelector singleSelector = new SingleSelector();

    @Inject
    DeliveryProductsViewModel deliveryProductsViewModel;

    @Inject
    public Toastor toastor;


    int position;
    ProductSort parentSort;
    ProductSort childSort;

    ShopCart shopCart;
    Handler updateShopCartHandler = new Handler();
    Runnable updateShopCartRunnable;
    boolean isOnBackGround = false;

    int[] startLocation = new int[2];

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (DeliveryHomeProductsFragmentListener) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        isOnBackGround = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isOnBackGround = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (updateShopCartHandler != null)
            if (updateShopCartRunnable != null) {
                updateShopCartHandler.removeCallbacks(updateShopCartRunnable);
            }
        updateShopCartRunnable = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updateShopCartHandler = null;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deliveryhome_products, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setDeliveryProductsViewModel(deliveryProductsViewModel);
        binding.parentSortList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.idStickynavlayoutInnerscrollview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.idStickynavlayoutInnerscrollview.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.colorGrayEDEDED).sizeResId(R.dimen.height_1px).showLastDivider().build());
        binding.idStickynavlayoutInnerscrollview.getRecyclerView().setClipToPadding(false);
        binding.idStickynavlayoutInnerscrollview.getRecyclerView().setPadding(0, 0, 0, AutoUtils.getPercentHeightSize(110));
        binding.idStickynavlayoutInnerscrollview.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
//        binding.childSortList.setRefreshListener(this);

        deliveryProductsViewModel.queryProductSort(module.getWareHouseModuleId());
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(getContext()).getAppComponent().plus(new DeliveryProductsModule(this)).inject(this);
    }

    public void initList(SecondarySort secondarySort) {
        DeliverySortAdapter deliverySortAdapter = new DeliverySortAdapter(secondarySort, singleSelector);
        binding.parentSortList.setAdapter(deliverySortAdapter);
        deliverySortAdapter.setOnSortClickListener(this);
        singleSelector.setSelected(0, deliverySortAdapter.getItemId(0), true);
//______________________________________________________________________
        parentSort = secondarySort.getParentProductSort();
        childSort = secondarySort.getParentProductSort();
        position = 0;
        queryProducts(true, parentSort, childSort, position);
    }


    public void setAdapter(DeliveryProductsAdapter deliveryProductsAdapter) {
        binding.idStickynavlayoutInnerscrollview.setAdapter(deliveryProductsAdapter);
        deliveryProductsAdapter.setOnProductClickListener(this);
    }

    public void setShopCart(List<ShopCartItem> shopCartItems) {
        if (null == shopCart)
            return;
        shopCart.setShopCartItemList(shopCartItems);
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            shopCart = MainAplication.get(getContext()).getUserComponent().shopCart();
            deliveryProductsViewModel.getDeliveryProductsAdapter().setShopCart(shopCart);
            deliveryProductsViewModel.queryShopcart(module.getWareHouseModuleId());
        } else {
            if (shopCart != null)
                shopCart.clear();
        }
    }

//    public void addShopCart(ProductUnit unit, int Quantity) {
//        shopCart.addUnitWitQuantity(unit, Quantity);
//    }

    public void updateShopCart(ProductUnit unit, int Quantity) {
        shopCart.updateUnitWithQuantity(unit, Quantity);
    }

//    public void removeShopCartItem(ProductUnit unit) {
//        shopCart.removeUnit(unit);
//    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onShopCartChangeEvent(ShopCartChangeEvent event) {
        deliveryProductsViewModel.queryShopcart(module.getWareHouseModuleId());
    }


    @Override
    public void onSortClick(View view, ProductSort mParentSort, ProductSort mChildSort, int mPosition) {
        parentSort = mParentSort;
        childSort = mChildSort;
        position = mPosition;
        queryProducts(true, parentSort, childSort, position);
    }

    public void showLoad() {
        binding.idStickynavlayoutInnerscrollview.showProgress();
    }

    public void hideLoad() {
        binding.idStickynavlayoutInnerscrollview.hideProgress();
        binding.idStickynavlayoutInnerscrollview.showRecycler();
    }

    public void queryProducts(boolean isRefresh, ProductSort parentSort, ProductSort childSort, int position) {
        if (position == 0) {
            deliveryProductsViewModel.querySellsProducts(isRefresh, parentSort.getSortId());
        } else if (position == 1) {
            deliveryProductsViewModel.queryActivityProducts(isRefresh, module.getWareHouseModuleId(), position);
        } else if (position == 2) {
            deliveryProductsViewModel.queryActivityProducts(isRefresh, module.getWareHouseModuleId(), position);
        } else
            deliveryProductsViewModel.queryProductyBySortId(isRefresh, childSort.getSortId());
    }

    public void setLoadMoreBegin() {
        binding.idStickynavlayoutInnerscrollview.setupMoreListener(this, 1);
    }

    public void setLoadMoreComplete() {
        binding.idStickynavlayoutInnerscrollview.hideMoreProgress();
        binding.idStickynavlayoutInnerscrollview.removeMoreListener();
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        queryProducts(false, parentSort, childSort, position);
    }

    @Override
    public void onRefresh() {
        queryProducts(true, parentSort, childSort, position);
    }

    @Override
    public void onProductClick(View view, ProductSimple productSimple, int position) {
        listener.navigateToProductDetail(view, productSimple, position);
    }

    public void updateShopCart(final boolean showAnim) {
        if (updateShopCartRunnable != null)
            updateShopCartHandler.removeCallbacks(updateShopCartRunnable);
        updateShopCartRunnable = new Runnable() {
            public void run() {
                deliveryProductsViewModel.updateShopCart(showAnim);
            }
        };
        updateShopCartHandler.postDelayed(updateShopCartRunnable, 800);
    }

    @Override
    public void onQuantityChange(StateQuantityView view, ProductSimple productSimple, int position, int quantity) {
        if (productSimple.getProductUnits() == null || productSimple.getProductUnits().size() <= 0)
            return;
        if (quantity == 0)
            return;
        if (null == deliveryProductsViewModel.getShopCartItems() || deliveryProductsViewModel.getShopCartItems().size() <= 0)
            return;
        if (isOnBackGround)
            return;
        boolean shouldUpdate = false;
        boolean showAnim = false;

        for (ShopCartItem shopCartItem : deliveryProductsViewModel.getShopCartItems()) {
            if (shopCartItem.getProductMainId() == productSimple.getMainId()) {
                if (quantity == shopCartItem.getQuantity()) {
                    return;
                } else if (quantity < shopCartItem.getQuantity()) {
                    shopCartItem.setQuantity(quantity);
                    shouldUpdate = true;
                } else if (quantity > shopCartItem.getQuantity()) {
                    shopCartItem.setQuantity(quantity);
                    showAnim = true;
                    shouldUpdate = true;
                }
                break;
            }
        }
        if (shouldUpdate) {
//            deliveryProductsViewModel.updateShopCart(showAnim);
            updateShopCart(productSimple.getProductUnits().get(0), quantity);
            updateShopCart(showAnim);

            if (showAnim) {
                view.getLocationOnScreen(startLocation);
                listener.showAddShopCartAnim(startLocation);
            }

        }
        Logger.d("deliveryhome  position " + position + " quantity " + quantity);

    }

    @Override
    public void onEnableStateChange(StateQuantityView view, ProductSimple productSimple, int position, boolean isEnable) {
        if (shopCart == null)
            return;
        if (productSimple.getProductUnits() == null || productSimple.getProductUnits().size() <= 0)
            return;
        if (isEnable && shopCart.getShopCartItemBaseUnitNum().get(productSimple.getMainId()) == null) {
            deliveryProductsViewModel.addItemToShopCart(productSimple.getProductUnits().get(0), 1, module.getWareHouseModuleId());
            view.getLocationOnScreen(startLocation);
            listener.showAddShopCartAnim(startLocation);
        }
        if (!isEnable && shopCart.getShopCartItemBaseUnitNum().get(productSimple.getMainId()) != null) {
            deliveryProductsViewModel.removeShopCartItem(productSimple.getProductUnits().get(0));
        }

        Logger.d("deliveryhome  position " + position + " isEnable " + isEnable);
    }

    @Override
    public boolean canEnable(StateQuantityView view) {
        if (!MainAplication.get(getContext()).isLogin())
            listener.navigateToLogin(view);
        return MainAplication.get(getContext()).isLogin();
    }

    @Override
    public View getScrollableView() {
        return null;
    }


}
