package com.j1j2.pifalao.feature.account;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityAccountBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class AccountActivity extends BaseActivity implements View.OnClickListener {

    ActivityAccountBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account);
        binding.setOnClick(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onClick(View v) {
        if (v == binding.changePassword) {
            navigate.navigateToChangePassword(this, null, false);
        }
    }
}
