package com.j1j2.pifalao.feature.main;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.View;

import com.j1j2.common.view.smarttablayout.SmartTabLayout;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSort;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.HasComponent;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.MainTab;
import com.j1j2.pifalao.databinding.ActivityMainBinding;
import com.j1j2.pifalao.feature.home.storestylehome.StoreStyleHomeFragment;
import com.j1j2.pifalao.feature.individualcenter.IndividualCenterFragment;
import com.j1j2.pifalao.feature.main.di.MainComponent;
import com.j1j2.pifalao.feature.main.di.MainModule;

import java.util.ArrayList;
import java.util.List;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-16.
 */
@RequireBundler
public class MainActivity extends BaseActivity implements SmartTabLayout.OnTabClickListener, HasComponent<MainComponent>, StoreStyleHomeFragment.StoreStyleHomeFragmentListener, IndividualCenterFragment.IndividualCenterFragmentListener {
    ActivityMainBinding binding;

    @Arg
    Module module;

    private MainComponent mainComponent;

    List<Fragment> fragments;

    MainAdapter mainAdapter;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initViews() {
        fragments = new ArrayList<>();
        fragments.add(Bundler.storeStyleHomeFragment(module).create());
        fragments.add(Bundler.supplierFragment().create());
        fragments.add(new Fragment());
        fragments.add(Bundler.individualCenterFragment().create());

        String[] titles = new String[]{"首页", "供应商", "购物车", "我的"};
        String[] icons = new String[]{getResources().getString(R.string.icon_home), getResources().getString(R.string.icon_supplier), getResources().getString(R.string.icon_shopcart), getResources().getString(R.string.icon_mine)};
        String[] selectedIcons = new String[]{getResources().getString(R.string.icon_home_fill), getResources().getString(R.string.icon_supplier_fill), getResources().getString(R.string.icon_shopcart_fill), getResources().getString(R.string.icon_mine_fill)};
        mainAdapter = new MainAdapter(getSupportFragmentManager(), fragments);
        binding.viewpager.setAdapter(mainAdapter);
        binding.tab.setCustomTabView(new MainTab(this, icons, selectedIcons, titles));
        binding.tab.setViewPager(binding.viewpager);
        binding.tab.setOnTabClickListener(this);

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        mainComponent = MainAplication.get(this).getAppComponent().plus(new MainModule(this, module));
        mainComponent.inject(this);
    }

    @Override
    public void onTabClicked(int position) {
        if (position == 0 || position == 1) {
            binding.viewpager.setCurrentItem(position);
        } else if (position == 2) {
            if (MainAplication.get(this).isLogin()) {
                navigate.navigateToShopCart(this, null, false, module);
            } else {
                navigate.navigateToLogin(this, null, false);
            }
        } else if (position == 3) {
            if (MainAplication.get(this).isLogin()) {
                binding.viewpager.setCurrentItem(4);
            } else {
                navigate.navigateToLogin(this, null, false);
            }
        }
    }

    @Override
    public MainComponent getComponent() {
        return mainComponent;
    }

    @Override
    public void navigateToProductsActivityFromSort(View view, ProductSort productSort, int position) {
        navigate.navigateToProductsActivityFromSort(this, null, false, productSort, module);
    }

    @Override
    public void navigateToSearchActivity(View v) {
        navigate.navigateToSearchActivity(this, null, false, module);
    }

    @Override
    public void navigateToOrderManager() {
        navigate.navigateToOrderManager(this, null, false);
    }

    @Override
    public void navigateToQRCode() {
        navigate.navigateToQRCode(this, null, false);
    }

    @Override
    public void navigateToAddressManager() {
        navigate.navigateToAddressManager(this, null, false);
    }

    @Override
    public void navigateToWalletManager() {
        navigate.navigateToWalletManager(this, null, false, module);
    }

    @Override
    public void navigateToMessages() {
        navigate.navigateToMessages(this, null, false);
    }

    @Override
    public void navigateToCollects() {
        navigate.navigateToCollects(this, null, false);
    }

    @Override
    public void navigateToAccount() {
        navigate.navigateToAccount(this, null, false);
    }
}
