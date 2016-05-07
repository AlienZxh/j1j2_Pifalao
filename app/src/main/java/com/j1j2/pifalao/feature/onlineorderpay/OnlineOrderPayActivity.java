package com.j1j2.pifalao.feature.onlineorderpay;

import android.databinding.DataBindingUtil;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityOnlineorderpayBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-5-7.
 */
@RequireBundler
public class OnlineOrderPayActivity extends BaseActivity {

    ActivityOnlineorderpayBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onlineorderpay);
    }

    @Override
    protected void initViews() {

    }
}
