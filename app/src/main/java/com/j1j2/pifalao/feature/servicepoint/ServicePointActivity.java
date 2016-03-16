package com.j1j2.pifalao.feature.servicepoint;

import android.databinding.DataBindingUtil;

import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityServicepointBinding;
import com.j1j2.pifalao.feature.servicepoint.di.ServicePointModule;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler
public class ServicePointActivity extends BaseActivity {

    ActivityServicepointBinding binding;

    @Arg
    ServicePoint servicePoint;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(ServicePointActivity.this, R.layout.activity_servicepoint);
        binding.setSercivepoint(servicePoint);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ServicePointModule()).inject(this);
    }
}
