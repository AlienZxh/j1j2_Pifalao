package com.j1j2.pifalao.feature.individualcenter;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.data.model.ProductSort;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityIndividualcenterBinding;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-25.
 */
@RequireBundler
public class IndividualCenterActivity extends BaseActivity implements IndividualCenterFragment.IndividualCenterFragmentListener {

    ActivityIndividualcenterBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_individualcenter);
    }

    @Override
    protected void initViews() {
        changeFragment(R.id.fragment, Bundler.individualCenterFragment(IndividualCenterFragment.FROM_INDIVIDUALCENTERACTIVITY).create());
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
        navigate.navigateToAddressManager(this, null, false);
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
        navigate.navigateToVipUpdateStepOne(this, null, false);

    }

    @Override
    public void navigateToSetting() {
        navigate.navigateToSetting(this, null, false);
    }
}
