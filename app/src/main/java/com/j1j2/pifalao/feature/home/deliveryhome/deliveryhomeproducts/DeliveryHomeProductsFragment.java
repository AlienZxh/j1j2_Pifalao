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

import com.j1j2.common.util.Toastor;
import com.j1j2.common.view.quantityview.StateQuantityView;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.common.view.scrollablelayout.ScrollableHelper;
import com.j1j2.data.model.Product;
import com.j1j2.data.model.ProductCategory;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.data.model.Shop;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.ShopExpressConfig;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;
import com.j1j2.pifalao.databinding.FragmentDeliveryhomeProductsBinding;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.di.DeliveryProductsModule;
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
        void navigateToProductDetail(View view, Product productSimple, int position);

        void navigateToShopCart(View view, ShopSubscribeService shopSubscribeService);

        void navigateToLogin(View view);

        void showAddShopCartAnim(@Size(2) int[] startLocation);

        ShopExpressConfig getShopExpressConfig();
    }

    private DeliveryHomeProductsFragmentListener listener;

    FragmentDeliveryhomeProductsBinding binding;

    @Arg
    ShopSubscribeService shopSubscribeService;

    SingleSelector singleSelector = new SingleSelector();

    @Inject
    DeliveryProductsViewModel deliveryProductsViewModel;

    @Inject
    public Toastor toastor;


    int position;
    ProductCategory productCategory;

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

        binding.parentSortList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.childProductList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.childProductList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.colorGrayEDEDED).sizeResId(R.dimen.height_1px).showLastDivider().build());
        binding.childProductList.getRecyclerView().setClipToPadding(false);
        binding.childProductList.getRecyclerView().setPadding(0, 0, 0, AutoUtils.getPercentHeightSize(110));
        binding.childProductList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
//        binding.childProductList.setRefreshListener(this);

        deliveryProductsViewModel.queryDeliveryProductSort(shopSubscribeService.getServiceId(), listener.getShopExpressConfig().getShopId());
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(getContext()).getAppComponent().plus(new DeliveryProductsModule(this)).inject(this);
    }

    public void initList(List<ProductCategory> productCategories) {
        DeliverySortAdapter deliverySortAdapter = new DeliverySortAdapter(productCategories, singleSelector);
        binding.parentSortList.setAdapter(deliverySortAdapter);
        deliverySortAdapter.setOnSortClickListener(this);
        singleSelector.setSelected(0, deliverySortAdapter.getItemId(0), true);
//______________________________________________________________________
        position = 0;
        queryProducts(true, null, position);
    }


    public void setAdapter(DeliveryProductsAdapter deliveryProductsAdapter) {
        binding.childProductList.setAdapter(deliveryProductsAdapter);
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
            deliveryProductsViewModel.queryShopcart(shopSubscribeService.getServiceId(), listener.getShopExpressConfig().getShopId());
        } else {
            if (shopCart != null)
                shopCart.clear();
        }
    }


    public void updateShopCart(ProductUnit unit, int Quantity) {
        shopCart.updateUnitWithQuantity(unit, Quantity);
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onShopCartChangeEvent(ShopCartChangeEvent event) {
        deliveryProductsViewModel.queryShopcart(shopSubscribeService.getServiceId(), listener.getShopExpressConfig().getShopId());
    }


    @Override
    public void onSortClick(View view, ProductCategory mProductCategory, int mPosition) {
        position = mPosition;
        productCategory = mProductCategory;
        queryProducts(true, productCategory, position);
    }

    public void showLoad() {
        binding.childProductList.showProgress();
    }

    public void hideLoad() {
        binding.childProductList.hideProgress();
        binding.childProductList.showRecycler();
    }

    public void queryProducts(boolean isRefresh, ProductCategory productCategory, int position) {
        if (position == 0) {
            deliveryProductsViewModel.querySellsProducts(isRefresh, shopSubscribeService.getServiceId(), listener.getShopExpressConfig().getShopId());
        } else if (position == 1) {
            deliveryProductsViewModel.queryActivityProducts(isRefresh, shopSubscribeService.getServiceId(), listener.getShopExpressConfig().getShopId(), position);
        } else if (position == 2) {
            deliveryProductsViewModel.queryActivityProducts(isRefresh, shopSubscribeService.getServiceId(), listener.getShopExpressConfig().getShopId(), position);
        } else
            deliveryProductsViewModel.queryProductyBySortId(isRefresh, shopSubscribeService.getServiceId(), listener.getShopExpressConfig().getShopId(), productCategory.getCategoryId());
    }

    public void setLoadMoreBegin() {
        binding.childProductList.setupMoreListener(this, 1);
    }

    public void setLoadMoreComplete() {
        binding.childProductList.hideMoreProgress();
        binding.childProductList.removeMoreListener();
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        queryProducts(false, productCategory, position);
    }

    @Override
    public void onRefresh() {
        queryProducts(true, productCategory, position);
    }

    public void updateShopCart(final boolean showAnim) {
        if (updateShopCartRunnable != null)
            updateShopCartHandler.removeCallbacks(updateShopCartRunnable);
        updateShopCartRunnable = new Runnable() {
            public void run() {
                deliveryProductsViewModel.updateShopCart(showAnim, shopSubscribeService.getServiceId(), listener.getShopExpressConfig().getShopId());
            }
        };
        updateShopCartHandler.postDelayed(updateShopCartRunnable, 800);
    }

    @Override
    public void onQuantityChange(StateQuantityView view, Product product, int position, int quantity) {
        if (product.getProductUnits() == null || product.getProductUnits().size() <= 0)
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
            if (shopCartItem.getProductMainId() == product.getMainId()) {
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
            updateShopCart(product.getProductUnits().get(0), quantity);
            updateShopCart(showAnim);

            if (showAnim) {
                view.getLocationOnScreen(startLocation);
                listener.showAddShopCartAnim(startLocation);
            }

        }
        Logger.d("deliveryhome  position " + position + " quantity " + quantity);

    }

    @Override
    public void onEnableStateChange(StateQuantityView view, Product product, int position, boolean isEnable) {
        if (shopCart == null)
            return;
        if (product.getProductUnits() == null || product.getProductUnits().size() <= 0)
            return;
        if (isEnable && shopCart.getShopCartItemBaseUnitNum().get(product.getMainId()) == null) {
            deliveryProductsViewModel.addItemToShopCart(product.getProductUnits().get(0), 1, shopSubscribeService.getServiceId(), listener.getShopExpressConfig().getShopId());
            view.getLocationOnScreen(startLocation);
            listener.showAddShopCartAnim(startLocation);
        }
        if (!isEnable && shopCart.getShopCartItemBaseUnitNum().get(product.getMainId()) != null) {
            deliveryProductsViewModel.removeShopCartItem(product.getProductUnits().get(0), shopSubscribeService.getServiceId(), listener.getShopExpressConfig().getShopId());
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
