package com.j1j2.pifalao.feature.briberymoneyresult;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.data.model.RedPacket;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityBriberymoneyResultBinding;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-10-7.
 */
@RequireBundler
public class BriberyMoneyResultActivity extends BaseActivity implements View.OnClickListener {

    ActivityBriberymoneyResultBinding binding;

    @Arg
    RedPacket redPacket;


    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_briberymoney_result);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        binding.amount.setOnClickListener(this);
        binding.record.setOnClickListener(this);
        binding.setRedPacket(redPacket);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.record) {
            startActivity(Bundler.briberyMoneysActivity(Constant.RedPacketState.USED)
                    .intent(this)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
    }
}
