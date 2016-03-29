package com.j1j2.pifalao.feature.addressmanager;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityAdressmanagerBinding;
import com.j1j2.pifalao.feature.addressmanager.di.AddressManagerModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-23.
 */
@RequireBundler
public class AddressManagerActivity extends BaseActivity implements View.OnClickListener {

    ActivityAdressmanagerBinding binding;

    @Inject
    AddressManagerViewModel addressManagerViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_adressmanager);
        binding.setAddressManagerViewModel(addressManagerViewModel);
        binding.addressList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.addressList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .drawable(R.drawable.item_products_divider)
                .size(AutoUtils.getPercentHeightSize(8))
                .build());
    }

    public void setAddressAdapter(AddressManagerAdapter addressAdapter) {
        binding.addressList.setAdapter(addressAdapter);
    }

    @Override
    protected void initViews() {
        addressManagerViewModel.queryAddress();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new AddressManagerModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }
}
