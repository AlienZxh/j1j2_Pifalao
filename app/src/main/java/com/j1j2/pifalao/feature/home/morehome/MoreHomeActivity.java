package com.j1j2.pifalao.feature.home.morehome;

import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.model.Module;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityMorehomeBinding;
import com.j1j2.pifalao.feature.home.morehome.di.MoreHomeModule;
import com.j1j2.pifalao.feature.main.MainActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

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
    List<Module> modules;

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

        moreHomeAdapter = new MoreHomeAdapter(modules);
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
    public void onItemClickListener(View view, Module module, int position) {
        if (module.isSubscribed()) {
            if (module.getModuleType() == Constant.ModuleType.DELIVERY && module.isSubscribed()) {
                navigate.navigateToDeliveryHomeActivity(this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, userRelativePreference.getSelectedServicePoint(null), module);
                userRelativePreference.setSelectedModule(module);
            } else if (module.getModuleType() == Constant.ModuleType.SHOPSERVICE && module.isSubscribed()) {
                navigate.navigateToMainActivity(this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, module, MainActivity.STORESTYLE);
                userRelativePreference.setSelectedModule(module);
            } else if (module.getModuleType() == Constant.ModuleType.VEGETABLE && module.isSubscribed()) {
                navigate.navigateToMainActivity(this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, module, MainActivity.VEGETABLE);
                userRelativePreference.setSelectedModule(module);
            } else if (module.getModuleType() == Constant.ModuleType.VIP) {
                if (MainAplication.get(this).isLogin()) {
                    navigate.navigateToVipHome(this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false);
                    userRelativePreference.setSelectedModule(module);
                } else {
                    navigate.navigateToLogin(this, null, false);
                }
            }
        } else {
            navigate.navigateToUnsubscribeModule(this, null, false);
        }
    }
}
