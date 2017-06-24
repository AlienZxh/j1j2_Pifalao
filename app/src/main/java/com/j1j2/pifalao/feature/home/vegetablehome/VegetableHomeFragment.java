package com.j1j2.pifalao.feature.home.vegetablehome;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.itemdecoration.CustomGridItemDecoration;
import com.j1j2.data.model.BannerActivity;
import com.j1j2.data.model.Product;
import com.j1j2.data.model.ProductCategory;
import com.j1j2.data.model.Shop;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.HasComponent;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.FragmentVegetablehomeBinding;
import com.j1j2.pifalao.feature.home.vegetablehome.di.VegetableHomeModule;
import com.j1j2.pifalao.feature.main.MainActivity;
import com.j1j2.pifalao.feature.main.di.MainComponent;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-5.
 */
@RequireBundler
public class VegetableHomeFragment extends BaseFragment implements View.OnClickListener, VegetableHotSortAdapter.OnHotSortClickListener, VegetableActivityProductAdapter.OnActivityProductClickListener {


    @Override
    protected String getFragmentName() {
        return "VegetableHomeFragment";
    }

    public interface VegetableHomeFragmentListener extends HasComponent<MainComponent> {
        void navigateToProductDetailActivity(View view, Product product, int position);

        void navigateToProductsActivityFromSort(View view, ProductCategory productCategory, int position);

        void navigateToSearchActivity(View v);
    }

    private VegetableHomeFragmentListener listener;

    FragmentVegetablehomeBinding binding;

    @Arg
    public ShopSubscribeService shopSubscribeService;

    @Inject
    VegetableHomeViewModel vegetableHomeViewModel;
    @Inject
    UserRelativePreference userRelativePreference;

    Shop shop;

    public ObservableBoolean isLogin = new ObservableBoolean(true);

    private String timeDialogTag = "TIMEDIALOG";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (MainActivity) activity;
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.viewPager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.viewPager.startAutoScroll();
    }


    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vegetablehome, container, false);
        binding.setVegetableHomeViewModel(vegetableHomeViewModel);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {

        //______________________________________________________
        binding.activityProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.activityProducts.addItemDecoration(new VerticalDividerItemDecoration.Builder(getContext())
                .sizeResId(R.dimen.height_1px)
                .colorResId(R.color.colorDividerE9E9E9)
                .build());
        //_________________________________________
        binding.hotSortList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.hotSortList.addItemDecoration(new CustomGridItemDecoration(AutoUtils.getPercentHeightSize(3), AutoUtils.getPercentHeightSize(3), 2));
        vegetableHomeViewModel.queryBannerActivity(shopSubscribeService.getServiceId());
        vegetableHomeViewModel.queryActivityProducts(shopSubscribeService.getServiceId(), shop.getShopId());
        vegetableHomeViewModel.queryHotCategories(shopSubscribeService.getServiceId(), shop.getShopId());
    }

    public void initTopAdv(List<BannerActivity> bannerActivities) {
        VegetableHomeTopAdapter vegetableHomeTopAdapter = new VegetableHomeTopAdapter(bannerActivities);
        binding.viewPager.setAdapter(vegetableHomeTopAdapter);
        binding.tab.setViewPager(binding.viewPager);
        binding.viewPager.startAutoScroll(2000);
        binding.viewPager.setInterval(2000);
    }

    public void initActivityProducts(List<Product> products) {
        VegetableActivityProductAdapter vegetableActivityProductAdapter = new VegetableActivityProductAdapter(products);
        binding.activityProducts.setAdapter(vegetableActivityProductAdapter);
        vegetableActivityProductAdapter.setOnActivityProductClickListener(this);
    }

    public void initHortSort(List<ProductCategory> productCategories) {
        VegetableHotSortAdapter vegetableHotSortAdapter = new VegetableHotSortAdapter(productCategories);
        binding.hotSortList.setAdapter(vegetableHotSortAdapter);
        vegetableHotSortAdapter.setOnChildSortClickListener(this);
    }

    public void showTimeDialog() {
        showMessageDialogDuplicate(true, timeDialogTag, "提示", "　由于仓库周日休息停配，周五21:00 - 周六21:00期间的全部订单需要周一上午才能送达。", null, "知道了");
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(getContext()).getAppComponent().plus(new VegetableHomeModule(this)).inject(this);
        shop = userRelativePreference.getSelectedServicePoint(null);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            vegetableHomeViewModel.CountDown(shopSubscribeService.getServiceId());
            isLogin.set(true);
        } else {
            isLogin.set(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            getActivity().onBackPressed();
        if (v == binding.searchBtn)
            listener.navigateToSearchActivity(v);
    }

    @Override
    public void onActivityProductClick(View v, Product productSimple, int position) {
        listener.navigateToProductDetailActivity(v, productSimple, position);
    }

    @Override
    public void onHotSortClick(View view, ProductCategory productCategory, int position) {
        listener.navigateToProductsActivityFromSort(view, productCategory, position);
    }
}
