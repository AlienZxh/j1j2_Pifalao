package com.j1j2.pifalao.feature.register.steptwo;

import android.databinding.DataBindingUtil;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityRegistersteptwoBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class RegisterStepTwoActivity extends BaseActivity {

    ActivityRegistersteptwoBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registersteptwo);
    }

    @Override
    protected void initViews() {

    }
}
