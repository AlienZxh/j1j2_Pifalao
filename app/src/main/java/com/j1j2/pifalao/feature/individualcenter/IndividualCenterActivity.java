package com.j1j2.pifalao.feature.individualcenter;

import android.databinding.DataBindingUtil;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.service.BackGroundService;
import com.j1j2.pifalao.databinding.ActivityIndividualcenterBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-25.
 */
@RequireBundler
public class IndividualCenterActivity extends BaseActivity implements IndividualCenterFragment.IndividualCenterFragmentListener {

    ActivityIndividualcenterBinding binding;

    @Inject
    UnReadInfoManager unReadInfoManager;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_individualcenter);
    }

    @Override
    protected void initViews() {
        changeFragment(R.id.fragment, Bundler.individualCenterFragment(IndividualCenterFragment.FROM_INDIVIDUALCENTERACTIVITY).create());
    }

    @Override
    protected void onResume() {
        super.onResume();
        BackGroundService.updateUnRead(this);
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {

        } else {
            navigate.navigateToLogin(this, null, true);
        }
    }

    @Override
    public void navigateToOrderManager() {
        navigate.navigateToOrderManager(this, null, false);
    }

    @Override
    public void navigateToQRCode() {
        navigate.navigateToQRCode(this, null, false);
    }

    @Override
    public void navigateToAddressManager() {
        navigate.navigateToAddressManager(this, null, false, false);
    }

    @Override
    public void navigateToWalletManager() {
        navigate.navigateToWalletManager(this, null, false, null);
    }

    @Override
    public void navigateToMessages() {
        navigate.navigateToMessages(this, null, false);
    }

    @Override
    public void navigateToCollects() {
        navigate.navigateToCollects(this, null, false);
    }

    @Override
    public void navigateToAccount() {
        navigate.navigateToAccount(this, null, false);
    }

    @Override
    public void navigateToVipUpdate() {
//        navigate.navigateToVipUpdateStepOne(this, null, false);
        navigate.navigateToVipHome(this, null, false);
    }

    @Override
    public void navigateToSetting() {
        navigate.navigateToSetting(this, null, false);
    }

    @Override
    public void navigateToNormalCoupon() {
        navigate.navigateToCoupons(this, null, false, null, Constant.CouponType.COUPON_NORMAL);
    }
}
