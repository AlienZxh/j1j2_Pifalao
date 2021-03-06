package com.j1j2.pifalao.feature.walletmanager;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.view.View;

import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.data.model.User;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityWalletmanagerBinding;
import com.j1j2.pifalao.feature.home.viphome.VipHomeActivity;
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

    @Inject
    UnReadInfoManager unReadInfoManager;

    @Arg
    @Required(false)
    ShopSubscribeService shopSubscribeService;

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
        binding.setUser(user);
        binding.setUnReadInfoManager(unReadInfoManager);
    }

    @Override
    protected void initViews() {
        walletManagerViewModel.queryAllCoupons(shopSubscribeService);
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
        if (v == binding.pointBtn)
            navigate.navigateToVipHome(this, null, false, VipHomeActivity.VIPHOME);
        if (v == binding.balanceBtn)
            navigate.navigateToBalanceDetail(this, null, false);
        if (v == binding.briberymoneryBtn) {
            navigate.navigateToBriberyMoneysActivity(this, null, true, Constant.RedPacketState.AVAILABILITY);
        }
        if (null != normalCoupons && v == binding.coupon) {
            navigate.navigateToCoupons(this, null, false, shopSubscribeService, Constant.CouponType.COUPON_NORMAL);
        }
        if (null != deliveryCoupons && v == binding.deliverycoupon) {
            navigate.navigateToCoupons(this, null, false, shopSubscribeService, Constant.CouponType.COUPON_DELIVERY);
        }
        if (null != goodsCoupons && v == binding.goodscoupon) {
            navigate.navigateToCoupons(this, null, false, shopSubscribeService, Constant.CouponType.COUPON_GOODS);
        }
    }
}
