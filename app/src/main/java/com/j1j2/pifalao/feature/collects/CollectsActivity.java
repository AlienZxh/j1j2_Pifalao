package com.j1j2.pifalao.feature.collects;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityCollectsBinding;
import com.j1j2.pifalao.feature.collects.di.CollectsModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class CollectsActivity extends BaseActivity {

    ActivityCollectsBinding binding;

    @Inject
    CollectsViewModel collectsViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collects);
    }

    @Override
    protected void initViews() {
        binding.productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .drawable(R.drawable.item_products_divider)
                .size(AutoUtils.getPercentHeightSize(8))
                .build());
        collectsViewModel.queryCollects();
    }

    public void setAdapter(CollectsAdapter collectsAdapter) {
        binding.productList.setAdapter(collectsAdapter);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new CollectsModule(this)).inject(this);
    }
}
