package com.j1j2.pifalao.feature.walletmanager;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.view.View;

import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.User;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityWalletmanagerBinding;
import com.j1j2.pifalao.feature.coupons.CouponsActivity;
import com.j1j2.pifalao.feature.walletmanager.di.WalletManagerModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;

/**
 * Created by alienzxh on 16-3-23.
 */
@RequireBundler
public class WalletManagerActivity extends BaseActivity implements View.OnClickListener {

    ActivityWalletmanagerBinding binding;

    @Inject
    WalletManagerViewModel walletManagerViewModel;

    @Inject
    User user;

    @Arg
    @Required(false)
    Module module;

    private List<Coupon> normalCoupons = null;
    private List<Coupon> deliveryCoupons = null;
    private List<Coupon> goodsCoupons = null;

    public ObservableInt normalCouponsNum = new ObservableInt();
    public ObservableInt deliveryCouponsNum = new ObservableInt();
    public ObservableInt goodsCouponsNum = new ObservableInt();

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_walletmanager);
        binding.setWalletManagerViewModel(walletManagerViewModel);
    }

    @Override
    protected void initViews() {
        walletManagerViewModel.queryAllCoupons(module);
    }

    public void initCoupons(List<Coupon> couponList) {
        deliveryCoupons = new ArrayList<>();
        normalCoupons = new ArrayList<>();
        goodsCoupons = new ArrayList<>();
        for (Coupon coupon : couponList) {
            if (coupon.getType() == 1) {
                deliveryCoupons.add(coupon);
            } else if (coupon.getType() == 2) {
                normalCoupons.add(coupon);
            } else {
                goodsCoupons.add(coupon);
            }
        }
        normalCouponsNum.set(normalCoupons.size());
        deliveryCouponsNum.set(deliveryCoupons.size());
        goodsCouponsNum.set(goodsCoupons.size());
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new WalletManagerModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (null != normalCoupons && v == binding.coupon) {
            navigate.navigateToCoupons(this, null, false, module, CouponsActivity.COUPON_NORMAL, normalCoupons);
        }
        if (null != deliveryCoupons && v == binding.deliverycoupon) {
            navigate.navigateToCoupons(this, null, false, module, CouponsActivity.COUPON_DELIVERY, deliveryCoupons);
        }
        if (null != goodsCoupons && v == binding.goodscoupon) {
            navigate.navigateToCoupons(this, null, false, module, CouponsActivity.COUPON_GOODS, goodsCoupons);
        }
    }
}
