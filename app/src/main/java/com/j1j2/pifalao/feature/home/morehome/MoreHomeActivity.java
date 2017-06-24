package com.j1j2.pifalao.feature.home.morehome;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityMorehomeBinding;
import com.j1j2.pifalao.feature.home.morehome.di.MoreHomeModule;
import com.j1j2.pifalao.feature.home.viphome.VipHomeActivity;
import com.j1j2.pifalao.feature.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.ParcelListSerializer;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-11.
 */
@RequireBundler
public class MoreHomeActivity extends BaseActivity implements View.OnClickListener, MoreHomeAdapter.OnItemClickListener {

    ActivityMorehomeBinding binding;

    @Arg(serializer = ParcelListSerializer.class)
    List<ShopSubscribeService> shopSubscribeServices;

    @Inject
    UserRelativePreference userRelativePreference;

    MoreHomeAdapter moreHomeAdapter;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_morehome);
        binding.backBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

        moreHomeAdapter = new MoreHomeAdapter(shopSubscribeServices);
        gridLayoutManager = new GridLayoutManager(this, 3);
        binding.moreList.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return moreHomeAdapter.isHeader(position) ? gridLayoutManager.getSpanCount() : 1;
            }
        });

        binding.moreList.setAdapter(moreHomeAdapter);
        moreHomeAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new MoreHomeModule()).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }

    @Override
    public void onItemClickListener(View view, ShopSubscribeService shopSubscribeService, int position) {
        if (shopSubscribeService.isSubscribed()) {
            if (shopSubscribeService.getServiceType() == Constant.ModuleType.DELIVERY) {
                navigate.navigateToDeliveryHomeActivity(this, null, false, userRelativePreference.getSelectedServicePoint(null), shopSubscribeService);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.SHOPSERVICE) {
                if (MainAplication.get(this).isLogin()) {
                    if (MainAplication.get(this).getUserComponent().user().getRoleId() == 10002) {
                        navigate.navigateToMainActivity(this, null, false, shopSubscribeService, MainActivity.STORESTYLE);
                        userRelativePreference.setSelectedModule(shopSubscribeService);
                    } else {
                        navigate.navigateToModulePermissionDeniedActivity(this, null, false, shopSubscribeService);
                    }
                } else {
                    navigate.navigateToLogin(this, null, false);
                }
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.VEGETABLE) {
                navigate.navigateToMainActivity(this, null, false, shopSubscribeService, MainActivity.VEGETABLE);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.VIP) {
                if (MainAplication.get(this).isLogin()) {
                    navigate.navigateToVipHome(this, null, false, VipHomeActivity.VIPHOME);
                    userRelativePreference.setSelectedModule(shopSubscribeService);
                } else {
                    navigate.navigateToLogin(this, null, false);
                }
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.HOUSEKEEPING) {
                navigate.navigateToHouseKeeping(MoreHomeActivity.this, null, false);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.MOBILE
                    ||shopSubscribeService.getServiceType() == Constant.ModuleType.WATERBILL
                    ||shopSubscribeService.getServiceType() == Constant.ModuleType.ELECTRICBILL
                    ||shopSubscribeService.getServiceType() == Constant.ModuleType.GASBILL)
                navigate.navigateToOfflineModuleHome(this, null, false, shopSubscribeService, userRelativePreference.getSelectedServicePoint(null));
            else if (shopSubscribeService.getServiceType() == Constant.ModuleType.MEMBER) {
                navigate.navigateToMemberHomeActivity(this, null, false);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            }else if (shopSubscribeService.getServiceType() == Constant.ModuleType.SPECIALOFFER) {
                navigate.navigateToSpecialOfferActivity(this, null, false);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            }
        } else {
            navigate.navigateToUnsubscribeModule(this, null, false, shopSubscribeService);
        }
    }
}
