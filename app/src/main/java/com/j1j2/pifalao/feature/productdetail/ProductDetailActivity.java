package com.j1j2.pifalao.feature.productdetail;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.j1j2.data.model.ProductImg;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityProductdetailBinding;
import com.j1j2.pifalao.feature.main.MainAdapter;
import com.j1j2.pifalao.feature.productdetail.di.ProductDetailModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-16.
 */
@RequireBundler
public class ProductDetailActivity extends BaseActivity {

    ActivityProductdetailBinding binding;

    @Arg
    ProductSimple productSimple;

    @Inject
    ProductDetailViewModel productDetailViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_productdetail);
        binding.setProductDetailViewModel(productDetailViewModel);
    }

    @Override
    protected void initViews() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragment());
        fragments.add(new Fragment());
        fragments.add(new Fragment());
        ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter(getSupportFragmentManager(), fragments);
        binding.detailViewpager.setAdapter(productDetailAdapter);
        binding.detailTab.setViewPager(binding.detailViewpager);
        productDetailViewModel.queryProductDetail();
    }

    public void initBanner(List<ProductImg> productImgs) {
        binding.viewPager.setAdapter(new ProductImgAdapter(productImgs));
        binding.viewPager.startAutoScroll(2000);
        binding.viewPager.setInterval(2000);
        binding.tab.setViewPager(binding.viewPager);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ProductDetailModule(this, productSimple)).inject(this);
    }
}
