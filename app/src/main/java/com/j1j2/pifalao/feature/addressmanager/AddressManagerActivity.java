package com.j1j2.pifalao.feature.addressmanager;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.model.Address;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.AddressListChangeEvent;
import com.j1j2.pifalao.app.event.UserAddressSelectEvent;
import com.j1j2.pifalao.databinding.ActivityAdressmanagerBinding;
import com.j1j2.pifalao.feature.addaddress.AddAddressActivity;
import com.j1j2.pifalao.feature.addressmanager.di.AddressManagerModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

/**
 * Created by alienzxh on 16-3-23.
 */
@RequireBundler
public class AddressManagerActivity extends BaseActivity implements View.OnClickListener, AddressManagerAdapter.OnAddressClickListener {


    ActivityAdressmanagerBinding binding;

    @Inject
    AddressManagerViewModel addressManagerViewModel;

    @Arg
    boolean isSelect;

    private String deleteDialogTag = "DELETEDIALOG";

    private int deleteAddressId;

    private int deletePosition;

    @Override
    protected void initBinding() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_adressmanager);
        binding.setAddressManagerViewModel(addressManagerViewModel);
        binding.addressList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.addressList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .colorResId(R.color.colorTransparent)
                .size(AutoUtils.getPercentHeightSize(10))
                .build());
        binding.addressList.getRecyclerView().setClipToPadding(false);
        binding.addressList.getRecyclerView().setPadding(0, AutoUtils.getPercentHeightSize(10), 0, AutoUtils.getPercentHeightSize(110));
        binding.addressList.getRecyclerView().setItemAnimator(new LandingAnimator());

    }

    public void setAddressAdapter(AddressManagerAdapter addressAdapter) {
        addressAdapter.setOnAddressClickListener(this);
        binding.addressList.setAdapter(addressAdapter);

    }

    @Override
    protected void initViews() {
        addressManagerViewModel.queryAddress();
        //_____________________________________________________

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new AddressManagerModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.addAddress)
            navigate.navigateToAddAddress(this, null, false, AddAddressActivity.ADD_NEW_ADDRESS, null);
    }

    @Override
    public void onAddressClickListener(View v, Address address, int position) {
        if (isSelect) {
            EventBus.getDefault().post(new UserAddressSelectEvent(address));
            finish();
        }

    }

    @Override
    public void onDefaultBtnClickListener(View v, Address address, int position) {
        addressManagerViewModel.setDefaultAddress(address.getAddressId());
    }

    @Override
    public void onDeleteBtnClickListener(View v, Address address, int position) {
        showDeleteDialog(address.getAddressId(), position);
    }

    @Override
    public void onModitfyBtnClickListener(View v, Address address, int position) {
        navigate.navigateToAddAddress(this, null, false, AddAddressActivity.EDIT_ADDRESS, address);
    }

    public void showDeleteDialog(int addressId, int position) {
        deleteAddressId = addressId;
        deletePosition = position;
        showMessageDialogDuplicate(true, deleteDialogTag, "提示", "确认删除该地址吗？", "取消", "确定");
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onAddressListChangeEvent(AddressListChangeEvent event) {
        addressManagerViewModel.queryAddress();
    }

    @Override
    public void onDialogPositiveClick(String fragmentTag) {
        super.onDialogPositiveClick(fragmentTag);
        if (fragmentTag.equals(deleteDialogTag))
            addressManagerViewModel.deleteAddress(deleteAddressId, deletePosition);
    }
}
