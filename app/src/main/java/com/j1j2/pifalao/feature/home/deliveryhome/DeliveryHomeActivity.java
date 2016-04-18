package com.j1j2.pifalao.feature.home.deliveryhome;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.FreightTypePrefrence;
import com.j1j2.pifalao.databinding.ActivityDeliveryhomeBinding;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.DeliveryHomeProductsFragment;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.DeliveryHomeServicepointFragment;
import com.j1j2.pifalao.feature.home.deliveryhome.di.DeliveryHomeModule;

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
public class DeliveryHomeActivity extends BaseActivity implements View.OnClickListener, DeliveryHomeProductsFragment.DeliveryHomeProductsFragmentListener {

    ActivityDeliveryhomeBinding binding;

    @Arg
    ServicePoint servicePoint;

    @Arg
    public Module module;

    @Inject
    DeliveryHomeViewModel deliveryHomeViewModel;

    @Inject
    FreightTypePrefrence freightTypePrefrence;

    ShopCart shopCart = null;

    DeliveryHomeServicepointFragment deliveryHomeServicepointFragment;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deliveryhome);
        binding.setServicePoint(servicePoint);
        binding.setDeliveryHomeViewModel(deliveryHomeViewModel);
    }

    @Override
    protected void initViews() {
        deliveryHomeServicepointFragment = Bundler.deliveryHomeServicepointFragment(servicePoint).create();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Bundler.deliveryHomeProductsFragment(module).create());
        fragments.add(deliveryHomeServicepointFragment);
        binding.viewpager.setAdapter(new DeliveryHomeTabAdapter(getSupportFragmentManager(), fragments));
        binding.tab.setViewPager(binding.viewpager);
        //________________________________________________________________________________
        deliveryHomeViewModel.queryFreightType(module.getWareHouseModuleId());
    }

    public void initFreightType(FreightType freightType) {
        freightTypePrefrence.setDeliveryFreightType(freightType);
        binding.setFreightType(freightType);
        deliveryHomeServicepointFragment.setFreightType(freightType);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new DeliveryHomeModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }

    @Override
    public void navigateToProductDetail(View view, ProductSimple productSimple, int position) {
        navigate.navigateToProductDetailActivity(this, null, false, productSimple.getMainId());
    }

    @Override
    public void navigateToShopCart(View view, Module module) {
        navigate.navigateToShopCart(this, null, false, module.getWareHouseModuleId());
    }

    @Override
    public void navigateToLogin(View view) {
        navigate.navigateToLogin(this, null, false);
    }
}
