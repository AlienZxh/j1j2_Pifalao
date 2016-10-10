package com.j1j2.pifalao.feature.freeconvertibility;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.model.ActivityProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityFreeconvertibilityBinding;
import com.j1j2.pifalao.feature.home.memberhome.MemberHomeLuckyAdapter;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.Arrays;
import java.util.List;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.ParcelListSerializer;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-8-25.
 */
@RequireBundler
public class FreeConvertibilityActivity extends BaseActivity implements View.OnClickListener, FreeConvertibilityAdapter.FreeConvertibilityAdapterListener {

    ActivityFreeconvertibilityBinding binding;

    FreeConvertibilityAdapter adapter;

    @Arg(serializer = ParcelListSerializer.class)
    List<ActivityProduct> activityProductList;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_freeconvertibility);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);

        binding.freeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.freeList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(0x00ffffff)
                .size(AutoUtils.getPercentHeightSize(20))
                .build());
        binding.freeList.setAdapter(adapter = new FreeConvertibilityAdapter(activityProductList));
        adapter.setListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }

    @Override
    public void navigateToPrizeDetail(ActivityProduct activityProduct) {
        if (MainAplication.get(this).isLogin())
            navigate.navigateToPrizeDetailActivity(this, null, false, activityProduct.getProductId(), null);
        else
            navigate.navigateToLogin(this, null, false);
    }
}
