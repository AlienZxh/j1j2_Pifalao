package com.j1j2.pifalao.feature.coupons;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.View;

import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.Module;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityCouponsBinding;
import com.j1j2.pifalao.feature.coupons.di.CouponsModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.ParcelListSerializer;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-23.
 */
@RequireBundler
public class CouponsActivity extends BaseActivity implements View.OnClickListener {


    ActivityCouponsBinding binding;

    @Inject
    CouponsViewModel couponsViewModel;

    @Arg
    Module module;
    @Arg
    int couponType;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coupons);
        binding.backBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        couponsViewModel.queryAllCoupons(couponType, module);
    }

    public void initCoupons(List<Coupon> coupons) {
        CouponsTabAdapter couponsTabAdapter = new CouponsTabAdapter(getSupportFragmentManager(), coupons);
        binding.viewpager.setAdapter(couponsTabAdapter);
        binding.tab.setViewPager(binding.viewpager);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new CouponsModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }
}
