package com.j1j2.pifalao.feature.productdetail.unit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentProductdetailUnitBinding;

import java.util.List;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.ParcelListSerializer;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-21.
 */
@RequireBundler
public class ProductDetailUnitFragment extends BaseFragment {
    FragmentProductdetailUnitBinding binding;

    @Arg(serializer = ParcelListSerializer.class)
    List<ProductUnit> productUnits;

    SingleSelector singleSelector;

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_productdetail_unit, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        singleSelector = new SingleSelector();
        binding.unitList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.unitList.setAdapter(new ProductDetailUnitAdapter(productUnits, singleSelector));
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }
}
