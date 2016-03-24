package com.j1j2.pifalao.feature.register.stepone;

import android.databinding.DataBindingUtil;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityRegistersteponeBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class RegisterStepOneActivity extends BaseActivity {

    ActivityRegistersteponeBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registerstepone);
    }

    @Override
    protected void initViews() {

    }
}
