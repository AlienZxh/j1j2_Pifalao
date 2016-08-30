package com.j1j2.pifalao.feature.empty;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentEmptyBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-26.
 */
@RequireBundler
public class EmptyFragment extends BaseFragment {

    @Override
    protected String getFragmentName() {
        return "EmptyFragment";
    }


    FragmentEmptyBinding binding;

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_empty, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {

    }
}
