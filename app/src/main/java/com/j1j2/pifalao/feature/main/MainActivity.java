package com.j1j2.pifalao.feature.main;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;

import com.j1j2.data.model.Module;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.HasComponent;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.MainTab;
import com.j1j2.pifalao.databinding.ActivityMainBinding;
import com.j1j2.pifalao.feature.home.storestylehome.StoreStyleHomeFragment;
import com.j1j2.pifalao.feature.individualcenter.IndividualCenterFragment;
import com.j1j2.pifalao.feature.main.di.MainComponent;
import com.j1j2.pifalao.feature.main.di.MainModule;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

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
public class MainActivity extends BaseActivity implements SmartTabLayout.OnTabClickListener, HasComponent<MainComponent> {
    ActivityMainBinding binding;

    @Arg
    Module module;

    private MainComponent mainComponent;

    List<Fragment> fragments;

    @Inject
    StoreStyleHomeFragment storeStyleHomeFragment;

    @Inject
    IndividualCenterFragment individualCenterFragment;

    MainAdapter mainAdapter;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initViews() {
        fragments = new ArrayList<>();
        fragments.add(storeStyleHomeFragment);
        fragments.add(new Fragment());
        fragments.add(new Fragment());
        fragments.add(individualCenterFragment);

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
        mainComponent = MainAplication.get(this).getAppComponent().plus(new MainModule(this, Bundler.storeStyleHomeFragment(module).create(), module));
        mainComponent.inject(this);
    }

    @Override
    public void onTabClicked(int position) {

    }

    @Override
    public MainComponent getComponent() {
        return mainComponent;
    }

    public Navigate getNavigate() {
        return navigate;
    }
}
