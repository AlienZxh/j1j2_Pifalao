package com.j1j2.pifalao.feature.confirmorder;

import android.databinding.DataBindingUtil;

import com.j1j2.data.model.Module;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityConfirmorderBinding;
import com.j1j2.pifalao.feature.confirmorder.di.ConfirmOrderModule;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-21.
 */
@RequireBundler
public class ConfirmOrderActivity extends BaseActivity {
    ActivityConfirmorderBinding binding;

    @Arg
    Module module;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmorder);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new ConfirmOrderModule()).inject(this);
    }
}
