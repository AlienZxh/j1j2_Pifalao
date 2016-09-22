package com.j1j2.pifalao.feature.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.j1j2.common.view.bgabadgewidget.AutoBGABadgeFrameLayout;
import com.j1j2.common.view.bgabadgewidget.AutoBGABadgeLinearLayout;
import com.j1j2.common.view.smarttablayout.SmartTabLayout;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.UnReadInfo;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.HasComponent;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.MainTab;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.event.NavigateToHomeEvent;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityMainBinding;
import com.j1j2.pifalao.feature.home.storestylehome.StoreStyleHomeFragment;
import com.j1j2.pifalao.feature.home.vegetablehome.VegetableHomeFragment;
import com.j1j2.pifalao.feature.individualcenter.IndividualCenterFragment;
import com.j1j2.pifalao.feature.main.di.MainComponent;
import com.j1j2.pifalao.feature.main.di.MainModule;
import com.j1j2.pifalao.feature.show.ShowActivity;
import com.j1j2.pifalao.feature.vegetablesort.VegetableSortFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class MainActivity extends BaseActivity implements SmartTabLayout.OnTabClickListener, HasComponent<MainComponent>, StoreStyleHomeFragment.StoreStyleHomeFragmentListener, IndividualCenterFragment.IndividualCenterFragmentListener, VegetableHomeFragment.VegetableHomeFragmentListener, View.OnClickListener, VegetableSortFragment.VegetableSortFragmentListener {

    public final static int STORESTYLE = 0;
    public final static int VEGETABLE = 1;

    ActivityMainBinding binding;

    @Arg
    Module module;
    @Arg
    int activityTpe;

    private MainComponent mainComponent;

    List<Fragment> fragments;

    MainAdapter mainAdapter;

    @Inject
    UserRelativePreference userRelativePreference;
    @Inject
    MainActivityViewModel mainActivityViewModel;

    ShopCart shopCart = null;
    UnReadInfoManager unReadInfoManager = null;
    AutoBGABadgeLinearLayout shopCartBadgeView;
    AutoBGABadgeFrameLayout individualcenterBadgeView;


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initViews() {
        switch (activityTpe) {
            case STORESTYLE:
            default:
                initStore();
                break;
            case VEGETABLE:
                initVegetable();
                break;
        }

        shopCartBadgeView = (AutoBGABadgeLinearLayout) binding.tab.getTabAt(2);
        individualcenterBadgeView = (AutoBGABadgeFrameLayout) binding.tab.getTabAt(3).findViewById(R.id.badgeFrame);
    }

    private void initStore() {
        fragments = new ArrayList<>();
        fragments.add(Bundler.storeStyleHomeFragment(module).create());
        fragments.add(Bundler.supplierFragment(userRelativePreference.getSelectedCity(null)).create());
        fragments.add(Bundler.emptyFragment().create());
        fragments.add(Bundler.individualCenterFragment(IndividualCenterFragment.FROM_MAINACTIVITY).create());
        String[] titles = new String[]{"首页", "供应商", "购物车", "我的"};
        String[] icons = new String[]{getResources().getString(R.string.icon_home), getResources().getString(R.string.icon_supplier), getResources().getString(R.string.icon_shopcart), getResources().getString(R.string.icon_mine)};
        String[] selectedIcons = new String[]{getResources().getString(R.string.icon_home_fill), getResources().getString(R.string.icon_supplier_fill), getResources().getString(R.string.icon_shopcart_fill), getResources().getString(R.string.icon_mine_fill)};
        mainAdapter = new MainAdapter(getSupportFragmentManager(), fragments);
        binding.viewpager.setAdapter(mainAdapter);
        binding.tab.setCustomTabView(new MainTab(this, icons, selectedIcons, titles));
        binding.tab.setViewPager(binding.viewpager);
        binding.tab.setOnTabClickListener(this);
    }

    private void initVegetable() {
        fragments = new ArrayList<>();
        fragments.add(Bundler.vegetableHomeFragment(module).create());
        fragments.add(Bundler.vegetableSortFragment(module).create());
        fragments.add(Bundler.emptyFragment().create());
        fragments.add(Bundler.individualCenterFragment(IndividualCenterFragment.FROM_MAINACTIVITY).create());
        String[] titles = new String[]{"首页", "分类", "购物车", "我的"};
        String[] icons = new String[]{getResources().getString(R.string.icon_home), getResources().getString(R.string.icon_sort), getResources().getString(R.string.icon_shopcart), getResources().getString(R.string.icon_mine)};
        String[] selectedIcons = new String[]{getResources().getString(R.string.icon_home_fill), getResources().getString(R.string.icon_sort_fill), getResources().getString(R.string.icon_shopcart_fill), getResources().getString(R.string.icon_mine_fill)};
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
                navigate.navigateToShopCart(this, null, false, module.getWareHouseModuleId());
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
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MainAplication.get(this).isLogin()) {
            mainActivityViewModel.queryUserUnReadInfo();
        }
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onShopCartChangeEvent(ShopCartChangeEvent event) {
        if (MainAplication.get(this).isLogin()) {
            mainActivityViewModel.queryShopcart(module.getWareHouseModuleId());
            mainActivityViewModel.queryUserUnReadInfo();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            shopCart = MainAplication.get(this).getUserComponent().shopCart();
            mainActivityViewModel.queryShopcart(module.getWareHouseModuleId());
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
            mainActivityViewModel.queryUserUnReadInfo();
        } else {
            if (binding.viewpager.getCurrentItem() != 0)
                binding.viewpager.setCurrentItem(0);
            shopCartBadgeView.hiddenBadge();
            individualcenterBadgeView.hiddenBadge();
        }
    }


    @Subscribe
    public void onNavigateToHomeEvent(NavigateToHomeEvent event) {
        if (binding.viewpager.getCurrentItem() != 0)
            binding.viewpager.setCurrentItem(0);
    }

    public void setUnReadInfo(UnReadInfo unReadInfo) {
        if (null == unReadInfo)
            return;
        unReadInfoManager.setUnReadInfo(unReadInfo);
        showUnReadCircle();
    }

    public void showUnReadCircle() {
        if (unReadInfoManager.isHasUnRead())
            individualcenterBadgeView.showCirclePointBadge();
        else
            individualcenterBadgeView.hiddenBadge();
    }

    public void setShopCart(List<ShopCartItem> shopCartItems) {
        if (null == shopCart)
            return;
        shopCart.setShopCartItemList(shopCartItems);
        showNum();
    }

    public void showNum() {
        if (shopCart == null)
            return;
        if (shopCart.getAllUnitNum() > 0)
            shopCartBadgeView.showTextBadge("" + shopCart.getAllUnitNum());
        else
            shopCartBadgeView.hiddenBadge();
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
        navigate.navigateToAddressManager(this, null, false, false);
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

    @Override
    public void navigateToVipUpdate() {
//        navigate.navigateToVipUpdateStepOne(this, null, false);
        navigate.navigateToVipHome(this, null, false);
    }

    @Override
    public void navigateToSetting() {
        navigate.navigateToSetting(this, null, false);
    }

    @Override
    public void navigateToProductDetailActivity(View view, ProductSimple productSimple, int position) {
        navigate.navigateToProductDetailActivity(this, null, false, productSimple.getMainId());
    }


    @Override
    public void navigateToShowStore() {
        navigate.navigateToShow(this, null, false, ShowActivity.STORE);
    }

    @Override
    public void navigateToOrders(int orderType) {
        navigate.navigateToOrders(this, null, false,orderType);
    }

    @Override
    public void navigateToParticipationRecord(int activityType) {
        navigate.navigateToParticipationRecordActivity(this,null,false,activityType);
    }
}
