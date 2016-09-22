package com.j1j2.pifalao.feature.specialoffer;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivitySpecialofferBinding;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.Arrays;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-9-20.
 */
@RequireBundler
public class SpecialOfferActivity extends BaseActivity {

    ActivitySpecialofferBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_specialoffer);
    }

    @Override
    protected void initViews() {
        binding.productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productList.setPadding(AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(20));
        binding.productList.setClipToPadding(false);
        binding.productList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(0x00ffffff)
                .size(AutoUtils.getPercentHeightSize(20))
                .build());
        binding.productList.setAdapter(new SpecialOfferProductAdapter(Arrays.asList("", "", "", "")));
    }
}
