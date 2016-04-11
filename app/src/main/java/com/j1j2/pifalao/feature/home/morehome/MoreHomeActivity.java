package com.j1j2.pifalao.feature.home.morehome;

import android.databinding.DataBindingUtil;

import com.j1j2.data.model.Module;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityMorehomeBinding;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-11.
 */
@RequireBundler
public class MoreHomeActivity extends BaseActivity {

    ActivityMorehomeBinding binding;

    @Arg
    Module module;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_morehome);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }
}
