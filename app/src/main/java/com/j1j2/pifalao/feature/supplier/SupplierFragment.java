package com.j1j2.pifalao.feature.supplier;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentSupplierBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-23.
 */
@RequireBundler
public class SupplierFragment extends BaseFragment{
    FragmentSupplierBinding binding;

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_supplier, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {

    }
}
