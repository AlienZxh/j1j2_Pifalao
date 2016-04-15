package com.j1j2.pifalao.feature.offlineorderdetail;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.model.OfflineOrderSimple;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityOfflineOrderdetailBinding;
import com.j1j2.pifalao.databinding.ActivityOfflineOrdersBinding;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-14.
 */
@RequireBundler
public class OfflineOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    ActivityOfflineOrderdetailBinding binding;

    @Arg
    OfflineOrderSimple offlineOrderSimple;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offline_orderdetail);
        binding.setOfflineOrderSimple(offlineOrderSimple);
        binding.setOnClick(this);
    }

    @Override
    protected void initViews() {
        binding.orderProductList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.orderProductList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.colorGrayF0F0F0)
                .margin(AutoUtils.getPercentWidthSize(20), 0).build());
        binding.orderProductList.setAdapter(new OfflineOrderProductDetailAdapter(offlineOrderSimple.getProductDetails()));
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }
}
