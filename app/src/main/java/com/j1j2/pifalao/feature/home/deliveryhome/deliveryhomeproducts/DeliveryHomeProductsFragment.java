package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.j1j2.common.view.quantityview.StateQuantityView;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
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
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-30.
 */
@RequireBundler
public class DeliveryHomeProductsFragment extends BaseFragment implements DeliverySortAdapter.OnSortClickListener, DeliveryProductsAdapter.OnProductClickListener, View.OnClickListener {

    public interface DeliveryHomeProductsFragmentListener {
        void navigateToProductDetail(View view, ProductSimple productSimple, int position);

        void navigateToShopCart(View view, Module module);

        void navigateToLogin(View view);
    }

    private DeliveryHomeProductsFragmentListener listener;

    FragmentDeliveryhomeProductsBinding binding;

    @Arg
    Module module;

    SingleSelector singleSelector = new SingleSelector();

    @Inject
    DeliveryProductsViewModel deliveryProductsViewModel;

    DeliveryProductsAdapter deliveryProductsAdapter;

    ShopCart shopCart;

    boolean isOnBackGround = false;

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
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deliveryhome_products, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setDeliveryProductsViewModel(deliveryProductsViewModel);
        binding.parentSortList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.childSortList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.childSortList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.colorGrayF0F0F0).sizeResId(R.dimen.height_1px).build());
        deliveryProductsAdapter = new DeliveryProductsAdapter(new ArrayList<ProductSimple>());
        binding.childSortList.setAdapter(deliveryProductsAdapter);
        deliveryProductsAdapter.setOnProductClickListener(this);
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
        deliveryProductsViewModel.querySellsProducts(secondarySort.getParentProductSort().getSortId());
    }

    public void initProducts(List<ProductSimple> productSimples) {
        deliveryProductsAdapter.initData(productSimples);
    }

    public void setShopCart(List<ShopCartItem> shopCartItems) {
        if (null == shopCart)
            return;
        shopCart.setShopCartItemList(shopCartItems);
    }

    public void addShopCart(ProductUnit unit, int Quantity) {
        shopCart.addUnitWitQuantity(unit, Quantity);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            shopCart = MainAplication.get(getContext()).getUserComponent().shopCart();
            binding.setShopCart(shopCart);
            deliveryProductsAdapter.setShopCart(shopCart);
            deliveryProductsViewModel.queryShopcart(module.getWareHouseModuleId());
        } else {
            if (shopCart != null)
                shopCart.clear();
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onShopCartChangeEvent(ShopCartChangeEvent event) {
        deliveryProductsViewModel.queryShopcart(module.getWareHouseModuleId());
    }

    @Override
    public void onClick(View v) {
        if (v == binding.shopCartView) {
            if (MainAplication.get(getContext()).isLogin())
                listener.navigateToShopCart(v, module);
            else
                listener.navigateToLogin(v);
        }
    }

    @Override
    public void onSortClick(View view, ProductSort parentSort, ProductSort childSort, int position) {

        if (position == 0) {
            deliveryProductsViewModel.querySellsProducts(parentSort.getSortId());
        } else if (position == 1) {
            deliveryProductsViewModel.queryActivityProducts(module.getWareHouseModuleId());
        } else
            deliveryProductsViewModel.queryProductyBySortId(childSort.getSortId());
    }

    @Override
    public void onProductClick(View view, ProductSimple productSimple, int position) {
        listener.navigateToProductDetail(view, productSimple, position);
    }

    @Override
    public void onQuantityChange(StateQuantityView view, ProductSimple productSimple, int position, int quantity) {
        if (null == deliveryProductsViewModel.getShopCartItems() || deliveryProductsViewModel.getShopCartItems().size() <= 0)
            return;
        if (quantity == deliveryProductsViewModel.getShopCartItems().get(position).getQuantity())
            return;
        if (isOnBackGround)
            return;
        deliveryProductsViewModel.getShopCartItems().get(position).setQuantity(quantity);
        deliveryProductsViewModel.updateShopCart();
    }

    @Override
    public void onEnableStateChange(StateQuantityView view, ProductSimple productSimple, int position, boolean isEnable) {
        if (shopCart == null)
            return;
        if (isEnable && shopCart.getShopCartItemBaseUnitNum().get(productSimple.getMainId()) == null)
            deliveryProductsViewModel.addItemToShopCart(productSimple.getProductUnits().get(0), 1, module.getWareHouseModuleId());
        if (!isEnable && shopCart.getShopCartItemBaseUnitNum().get(productSimple.getMainId()) != null)
            deliveryProductsViewModel.removeShopCartItem(productSimple.getProductUnits().get(0).getProductId());
    }

    @Override
    public boolean canEnable(StateQuantityView view) {
        if (!MainAplication.get(getContext()).isLogin())
            listener.navigateToLogin(view);
        return MainAplication.get(getContext()).isLogin();
    }
}
