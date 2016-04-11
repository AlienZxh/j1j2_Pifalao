package com.j1j2.pifalao.feature.account;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.databinding.ActivityAccountBinding;
import com.j1j2.pifalao.feature.account.di.AccountModule;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class AccountActivity extends BaseActivity implements View.OnClickListener {

    ActivityAccountBinding binding;

    @Inject
    ShopCart shopCart;

    @Inject
    UnReadInfoManager unReadInfoManager;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account);
        binding.setOnClick(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new AccountModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.changePassword) {
            navigate.navigateToChangePassword(this, null, false);
        }
        if (v == binding.backBtn) {
            onBackPressed();
        }
        if (v == binding.logout) {
            MainAplication.get(this).loginOut();
            shopCart.clear();
            unReadInfoManager.clear();
            EventBus.getDefault().postSticky(new LogStateEvent(false));
            finish();
        }
    }
}
