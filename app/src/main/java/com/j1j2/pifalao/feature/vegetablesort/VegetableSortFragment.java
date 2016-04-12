package com.j1j2.pifalao.feature.vegetablesort;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.SecondarySort;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.HasComponent;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentVegetablesortBinding;
import com.j1j2.pifalao.feature.main.MainActivity;
import com.j1j2.pifalao.feature.main.di.MainComponent;
import com.j1j2.pifalao.feature.vegetablesort.di.VegetableSortModule;

import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-6.
 */
@RequireBundler
public class VegetableSortFragment extends BaseFragment implements VegetableParentSortAdapter.OnSortSelectListener, VegetableChildSortAdapter.OnChildSortClickListener, View.OnClickListener {

    public interface VegetableSortFragmentListener extends HasComponent<MainComponent> {
        void navigateToProductsActivityFromSort(View view, ProductSort productSort, int position);

        void navigateToSearchActivity(View v);
    }

    private VegetableSortFragmentListener listener;

    FragmentVegetablesortBinding binding;

    @Arg
    Module module;

    @Inject
    VegetableSortViewModel vegetableSortViewModel;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MainActivity) activity;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vegetablesort, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.searchBtn.setOnClickListener(this);
        binding.parentSortList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.childSortList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        vegetableSortViewModel.queryProductSort(module.getWareHouseModuleId());
    }

    public void initList(List<SecondarySort> secondarySorts) {
        if (null == secondarySorts || secondarySorts.size() <= 0)
            return;
        SingleSelector singleSelector = new SingleSelector();
        VegetableParentSortAdapter vegetableParentSortAdapter = new VegetableParentSortAdapter(secondarySorts, singleSelector);
        binding.parentSortList.setAdapter(vegetableParentSortAdapter);
        singleSelector.setSelected(0, vegetableParentSortAdapter.getItemId(0), true);
        vegetableParentSortAdapter.setOnSortSelectListener(this);

        initChildList(secondarySorts.get(0));
    }

    public void initChildList(SecondarySort secondarySort) {
        VegetableChildSortAdapter vegetableChildSortAdapter = new VegetableChildSortAdapter(secondarySort);
        binding.childSortList.setAdapter(vegetableChildSortAdapter);
        vegetableChildSortAdapter.setOnChildSortClickListener(this);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(getContext()).getAppComponent().plus(new VegetableSortModule(this)).inject(this);
    }

    @Override
    public void onSortSelectListener(View v, SecondarySort secondarySort, int position) {
        initChildList(secondarySort);
    }

    @Override
    public void onChildSortClick(View view, ProductSort productSort, int position) {
        listener.navigateToProductsActivityFromSort(view, productSort, position);
    }

    @Override
    public void onClick(View v) {
        listener.navigateToSearchActivity(v);
    }
}
