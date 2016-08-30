package com.j1j2.pifalao.feature.coupons;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.Coupon;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentCouponsBinding;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.ParcelListSerializer;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-23.
 */
@RequireBundler
public class CouponsFragment extends BaseFragment {

    @Override
    protected String getFragmentName() {
        return "CouponsFragment";
    }

    FragmentCouponsBinding binding;

    @Arg(serializer = ParcelListSerializer.class)
    List<Coupon> coupons;

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coupons, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.couponList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.couponList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getContext())
                .colorResId(R.color.colorTransparent)
                .size(AutoUtils.getPercentHeightSize(10))
                .showLastDivider()
                .build());
        binding.couponList.getRecyclerView().setClipToPadding(false);
        binding.couponList.getRecyclerView().setClipChildren(false);
        binding.couponList.getRecyclerView().setPadding(0, AutoUtils.getPercentHeightSize(10), 0, 0);
        binding.couponList.setAdapter(new CouponsAdapter(coupons));
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }
}
