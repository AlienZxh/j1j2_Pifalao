package com.j1j2.pifalao.feature.productdetail;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.Toast;

import com.j1j2.data.model.Module;
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
import com.j1j2.pifalao.feature.productdetail.di.ProductDetailModule;
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
public class ProductDetailActivity extends BaseActivity implements View.OnClickListener, ProductDetailUnitFragment.ProductDetailUnitFragmentListener {

    ActivityProductdetailBinding binding;

    @Arg
    ProductSimple productSimple;
    @Arg
    Module module;

    @Inject
    ProductDetailViewModel productDetailViewModel;

    ProductDetailUnitFragment productDetailUnitFragment;

    ShopCart shopCart;


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_productdetail);
        binding.setProductDetailViewModel(productDetailViewModel);
    }

    @Override
    protected void initViews() {
        productDetailUnitFragment = Bundler.productDetailUnitFragment(productSimple.getProductUnits(), productSimple.getBaseUnit()).create();
        changeFragment(R.id.unitFragment, productDetailUnitFragment);
        productDetailViewModel.queryProductDetail();
        productDetailViewModel.queryProductHasBeenCollected();
    }

    public void initBanner(List<ProductImg> productImgs) {
        binding.viewPager.setAdapter(new ProductImgAdapter(productImgs));
        binding.viewPager.startAutoScroll(2000);
        binding.viewPager.setInterval(2000);
        binding.tab.setViewPager(binding.viewPager);
    }

    public void initBottomViewPager(List<ProductImg> productImgs) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Bundler.productDetailImgFragment(productImgs).create());
        fragments.add(new Fragment());
        fragments.add(new Fragment());
        ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter(getSupportFragmentManager(), fragments);
        binding.detailViewpager.setAdapter(productDetailAdapter);
        binding.detailTab.setViewPager(binding.detailViewpager);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ProductDetailModule(this, productSimple)).inject(this);
    }

    public void addShopCart(ProductUnit unit, int Quantity) {
        shopCart.addUnitWitQuantity(unit, Quantity);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            shopCart = MainAplication.get(this).getUserComponent().shopCart();
            binding.setShopCart(shopCart);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.collectBtn)
            productDetailViewModel.clooectBtnClick();
        if (v == binding.addBtn)
            productDetailViewModel.addItemToShopCart(productDetailUnitFragment.getSelectUnit(), productDetailUnitFragment.getQuantity(), module.getWareHouseModuleId());
        if (v == binding.shopCartBtn)
            navigate.navigateToShopCart(this, null, false, module);
    }

    @Override
    public void setSelectUnit(ProductUnit unit) {

        productDetailViewModel.selectUnit.set(unit);
    }
}
