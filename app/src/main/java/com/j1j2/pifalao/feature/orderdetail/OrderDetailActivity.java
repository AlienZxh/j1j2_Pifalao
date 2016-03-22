package com.j1j2.pifalao.feature.orderdetail;

import android.databinding.DataBindingUtil;

import com.j1j2.data.model.OrderSimple;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityOrderdetailBinding;
import com.j1j2.pifalao.feature.orderdetail.di.OrderDetailModule;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-22.
 */
@RequireBundler
public class OrderDetailActivity extends BaseActivity {
    ActivityOrderdetailBinding binding;

    @Arg
    OrderSimple orderSimple;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orderdetail);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new OrderDetailModule()).inject(this);
    }
}
