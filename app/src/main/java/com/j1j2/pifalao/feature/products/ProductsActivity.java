package com.j1j2.pifalao.feature.products;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.Product;
import com.j1j2.data.model.ProductCategory;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.data.model.Shop;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.dialog.AddProductDialogFragment;
import com.j1j2.pifalao.app.dialog.AddProductDialogFragmentBundler;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.sharedpreferences.FreightTypePrefrence;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityProductsBinding;
import com.j1j2.pifalao.feature.products.di.ProductsModule;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler(requireAll = false)
public class ProductsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        OnMoreListener,
        ProductsAdapter.OnProductsClickListener,
        View.OnClickListener, AdapterView.OnItemSelectedListener,
        AddProductDialogFragment.AddProductDialogFragmentListener {

    public static final int PRODUCTS_TYPE_SORT = 1;
    public static final int PRODUCTS_TYPE_SEARCH = 2;

    ActivityProductsBinding binding;

    @Arg
    @Required(true)
    public ShopSubscribeService shopSubscribeService;
    @Arg
    @Required(true)
    int activityType;
    @Arg
    @Required(false)
    ProductCategory productCategory;
    @Arg
    @Required(false)
    String key;

    @Inject
    FreightTypePrefrence freightTypePrefrence;
    @Inject
    ProductsViewModel productsViewModel;
    @Inject
    UserRelativePreference userRelativePreference;

    public ObservableField<FreightType> freightTypeObservableField = new ObservableField<>();

    Shop shop;

    ProductUnit selectedUnit;
    ShopCart shopCart;

    public ObservableBoolean isLogin = new ObservableBoolean();


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products);
        productsViewModel.title.set(activityType == PRODUCTS_TYPE_SORT ? productCategory.getName() : key);
        binding.setProductViewModel(productsViewModel);
        binding.productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .drawable(R.drawable.item_products_divider)
                .size(AutoUtils.getPercentHeightSize(8))
                .build());
        binding.productList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.productList.setRefreshListener(this);
    }

    @Override
    protected void initViews() {
        freightTypeObservableField.set(freightTypePrefrence.getDeliveryFreightType(null));
        queryProducts(true);
        //_____________________________________________________________________________
        List<String> strings = new ArrayList<>();
        strings.add("默认");
        strings.add("销量");
        strings.add("价格");
        strings.add("人气");
        binding.sortSpinner.setAdapter(new ProductFilterAdapter(strings, this));
        binding.sortSpinner.setOnItemSelectedListener(this);
        binding.sortSpinner.setSelection(0);
    }

    public void queryProducts(boolean isRefresh) {
        if (activityType == PRODUCTS_TYPE_SORT)
            productsViewModel.queryProductyBySortId(isRefresh, shop.getShopId());
        else if (activityType == PRODUCTS_TYPE_SEARCH)
            productsViewModel.queryProductyByKey(isRefresh, shop.getShopId());
    }

    public void setProdutsAdapter(ProductsAdapter productsAdapter) {
        if (binding.productList.getAdapter() == null || binding.productList.getAdapter() != productsAdapter) {
            binding.productList.setAdapter(productsAdapter);
            productsAdapter.setOnProductsClickListener(this);
        }
    }

    public void setLoadMoreBegin() {
        binding.productList.setupMoreListener(this, 1);
    }

    public void setLoadMoreComplete() {
        binding.productList.hideMoreProgress();
        binding.productList.removeMoreListener();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ProductsModule(this
                , null == productCategory ? new ProductCategory() : productCategory
                , shopSubscribeService, null == key ? "" : key)).inject(this);
        shop = userRelativePreference.getSelectedServicePoint(null);
    }

    public void addShopCart(ProductUnit unit, int Quantity) {
        shopCart.addUnitWitQuantity(unit, Quantity);
    }


    @Override
    public void onClick(View v) {
        if (v == binding.shopCartView) {
            if (MainAplication.get(this).isLogin())
                navigate.navigateToShopCart(this, null, false, shopSubscribeService.getServiceId());
            else
                navigate.navigateToLogin(this, null, false);
        }

        if (v == binding.backBtn) {

            onBackPressed();
        }
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            shopCart = MainAplication.get(this).getUserComponent().shopCart();
            productsViewModel.getProductAdapter().setShopCart(shopCart);
            binding.setShopCart(shopCart);
            isLogin.set(true);
            productsViewModel.CountDown();
        } else {
            if (shopCart != null)
                shopCart.clear();
            isLogin.set(false);
        }
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        productsViewModel.setOrderBy(position);
        queryProducts(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClickListener(View view, Product product, int position) {
        navigate.navigateToProductDetailActivity(ProductsActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, product.getMainId());
    }

    @Override
    public void onAddIconClickListener(View view, Product product, int position) {
        if (MainAplication.get(this).isLogin()) {
            AddProductDialogFragmentBundler.build().shopSubscribeService(shopSubscribeService).product(product).create().show(getSupportFragmentManager(), "ADDDIALOG");
        } else {
            navigate.navigateToLogin(this, null, false);
        }
    }


    @Override
    public void setSelectedUnit(ProductUnit productUnit) {
        this.selectedUnit = productUnit;
    }

    @Override
    public void onAddBtnClick(int quantity) {
        if (shop != null)
            productsViewModel.addItemToShopCart(selectedUnit, quantity, shop.getShopId());
    }

    @Override
    public void onShopcartBtnClick() {
        if (MainAplication.get(this).isLogin())
            navigate.navigateToShopCart(this, null, false, shopSubscribeService.getServiceId());
        else
            navigate.navigateToLogin(this, null, false);
    }

    public ShopCart getShopCart() {
        return shopCart;
    }
}
