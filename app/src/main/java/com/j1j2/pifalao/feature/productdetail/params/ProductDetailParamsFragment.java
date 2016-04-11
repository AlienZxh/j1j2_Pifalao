package com.j1j2.pifalao.feature.productdetail.params;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ProductDetail;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentProductdetailParamsBinding;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-10.
 */
@RequireBundler
public class ProductDetailParamsFragment extends BaseFragment {
    FragmentProductdetailParamsBinding binding;

    @Arg
    ProductDetail productDetail;

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_productdetail_params, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setProductDetail(productDetail);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }
}
