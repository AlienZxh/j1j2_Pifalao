package com.j1j2.pifalao.feature.briberymoneyremind;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityBriberymoneyRemindBinding;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-10-7.
 */
@RequireBundler
public class BriberyMoneyRemindActivity extends BaseActivity implements View.OnClickListener {

    ActivityBriberymoneyRemindBinding binding;

    @Arg
    int count;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_briberymoney_remind);
    }

    @Override
    protected void initViews() {
        binding.close.setOnClickListener(this);
        binding.layout.setOnClickListener(this);
        binding.img.setOnClickListener(this);
        binding.countText.setText("您有" + count + "个红包");
    }

    @Override
    public void onClick(View v) {
        if (v == binding.close || v == binding.layout)
            onBackPressed();
        if (v == binding.img){
            navigate.navigateToBriberyMoneysActivity(this, null, true, Constant.RedPacketState.AVAILABILITY);
        }

    }
}
