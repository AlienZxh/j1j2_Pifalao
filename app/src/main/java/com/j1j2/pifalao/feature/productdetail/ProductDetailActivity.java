package com.j1j2.pifalao.feature.productdetail;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductDetail;
import com.j1j2.data.model.ProductImg;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.databinding.ActivityProductdetailBinding;
import com.j1j2.pifalao.feature.main.MainAdapter;
import com.j1j2.pifalao.feature.productdetail.di.ProductDetailComponent;
import com.j1j2.pifalao.feature.productdetail.di.ProductDetailModule;
import com.j1j2.pifalao.feature.productdetail.record.ProductDetailRecordFragment;
import com.j1j2.pifalao.feature.productdetail.unit.ProductDetailUnitFragment;

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
 * Created by alienzxh on 16-3-16.
 */
@RequireBundler
public class ProductDetailActivity extends BaseActivity implements View.OnClickListener, ProductDetailUnitFragment.ProductDetailUnitFragmentListener, ProductDetailRecordFragment.ProductDetailRecordFragmentListener {

    ActivityProductdetailBinding binding;

    @Arg
    int mainId;

    @Inject
    ProductDetailViewModel productDetailViewModel;

    ProductDetailUnitFragment productDetailUnitFragment;

    ShopCart shopCart;

    ProductDetailComponent productDetailComponent;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_productdetail);
        binding.setProductDetailViewModel(productDetailViewModel);
    }

    @Override
    protected void initViews() {
        productDetailViewModel.queryProductDetail(mainId);
        productDetailViewModel.queryProductHasBeenCollected(mainId);
    }

    public void initBanner(List<ProductImg> productImgs) {
        binding.viewPager.setAdapter(new ProductImgCycleAdapter(productImgs));
        binding.viewPager.startAutoScroll(2000);
        binding.viewPager.setInterval(2000);
        binding.tab.setViewPager(binding.viewPager);
    }

    public void initUnitsSelect(ProductDetail productDetail) {
        productDetailUnitFragment = Bundler.productDetailUnitFragment(productDetail.getProductUnits(), productDetail.getBaseUnit(), productDetail.getModuleType()).create();
        changeFragment(R.id.unitFragment, productDetailUnitFragment);
    }

    public void initBottomViewPager(List<ProductImg> productImgs, int productId, ProductDetail productDetail) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Bundler.productDetailImgFragment(productImgs).create());
        fragments.add(Bundler.productDetailParamsFragment(productDetail).create());
        fragments.add(Bundler.productDetailRecordFragment(productId).create());
        ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter(getSupportFragmentManager(), fragments);
        binding.detailViewpager.setAdapter(productDetailAdapter);
        binding.detailTab.setViewPager(binding.detailViewpager);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        productDetailComponent = MainAplication.get(this).getAppComponent().plus(new ProductDetailModule(this));
        productDetailComponent.inject(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        binding.viewPager.stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // start auto scroll when onResume
        binding.viewPager.startAutoScroll();
    }

    public void addShopCart(ProductUnit unit, int Quantity) {
        shopCart.addUnitWitQuantity(unit, Quantity);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            shopCart = MainAplication.get(this).getUserComponent().shopCart();
            binding.setShopCart(shopCart);
        } else {
            if (shopCart != null)
                shopCart.clear();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (MainAplication.get(this).isLogin()) {
            if (v == binding.collectBtn)
                productDetailViewModel.clooectBtnClick(mainId);
            if (v == binding.addBtn)
                if (productDetailViewModel.productDetail.get() != null)
                    productDetailViewModel.addItemToShopCart(productDetailUnitFragment.getSelectUnit(), productDetailUnitFragment.getQuantity(), productDetailViewModel.productDetail.get().getModuleId());
            if (v == binding.shopCartBtn) {
                if (productDetailViewModel.productDetail.get() != null)
                    navigate.navigateToShopCart(this, null, false, productDetailViewModel.productDetail.get().getModuleId());
            }
        } else {
            navigate.navigateToLogin(this, null, false);
        }

    }


    @Override
    public ProductDetailComponent getComponent() {
        return productDetailComponent;
    }

    @Override
    public void setSelectUnit(ProductUnit unit) {

        productDetailViewModel.selectUnit.set(unit);
    }
}
