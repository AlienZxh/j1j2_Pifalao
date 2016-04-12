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
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductSort;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.HasComponent;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.event.LogStateEvent;
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

    public interface VegetableHomeFragmentListener extends HasComponent<MainComponent> {
        void navigateToProductDetailActivity(View view, ProductSimple productSimple, int position);

        void navigateToProductsActivityFromSort(View view, ProductSort productSort, int position);

        void navigateToSearchActivity(View v);
    }

    private VegetableHomeFragmentListener listener;

    FragmentVegetablehomeBinding binding;

    @Arg
    Module module;

    @Inject
    VegetableHomeViewModel vegetableHomeViewModel;

    public ObservableBoolean isLogin = new ObservableBoolean(true);

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
        vegetableHomeViewModel.CountDown(module.getWareHouseModuleId());
        //______________________________________________________
        binding.activityProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.activityProducts.addItemDecoration(new VerticalDividerItemDecoration.Builder(getContext())
                .sizeResId(R.dimen.height_1px)
                .colorResId(R.color.colorDividerE9E9E9)
                .build());
        //_________________________________________
        binding.hotSortList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.hotSortList.addItemDecoration(new CustomGridItemDecoration(AutoUtils.getPercentHeightSize(3), AutoUtils.getPercentHeightSize(3), 2));
        vegetableHomeViewModel.queryBannerActivity(module.getWareHouseModuleId());
        vegetableHomeViewModel.queryActivityProducts(module.getWareHouseModuleId());
        vegetableHomeViewModel.queryHotSort(module.getWareHouseModuleId());
    }

    public void initTopAdv(List<BannerActivity> bannerActivities) {
        VegetableHomeTopAdapter vegetableHomeTopAdapter = new VegetableHomeTopAdapter(bannerActivities);
        binding.viewPager.setAdapter(vegetableHomeTopAdapter);
        binding.tab.setViewPager(binding.viewPager);
        binding.viewPager.startAutoScroll(2000);
        binding.viewPager.setInterval(2000);
        binding.viewPager.startAutoScroll();
    }

    public void initActivityProducts(List<ProductSimple> productSimples) {
        VegetableActivityProductAdapter vegetableActivityProductAdapter = new VegetableActivityProductAdapter(productSimples);
        binding.activityProducts.setAdapter(vegetableActivityProductAdapter);
        vegetableActivityProductAdapter.setOnActivityProductClickListener(this);
    }

    public void initHortSort(List<ProductSort> productSorts) {
        VegetableHotSortAdapter vegetableHotSortAdapter = new VegetableHotSortAdapter(productSorts);
        binding.hotSortList.setAdapter(vegetableHotSortAdapter);
        vegetableHotSortAdapter.setOnChildSortClickListener(this);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(getContext()).getAppComponent().plus(new VegetableHomeModule(this)).inject(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
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
    public void onActivityProductClick(View v, ProductSimple productSimple, int position) {
        listener.navigateToProductDetailActivity(v, productSimple, position);
    }

    @Override
    public void onHotSortClick(View view, ProductSort productSort, int position) {
        listener.navigateToProductsActivityFromSort(view, productSort, position);
    }
}
