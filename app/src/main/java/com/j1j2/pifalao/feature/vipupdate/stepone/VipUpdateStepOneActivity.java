package com.j1j2.pifalao.feature.vipupdate.stepone;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.FinishLocationActivityEvent;
import com.j1j2.pifalao.app.event.VipUpdateSuccessEvent;
import com.j1j2.pifalao.databinding.ActivityRegistersteptwoBinding;
import com.j1j2.pifalao.databinding.ActivityVipupdatesteponeBinding;

import org.greenrobot.eventbus.Subscribe;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class VipUpdateStepOneActivity extends BaseActivity implements View.OnClickListener {

    ActivityVipupdatesteponeBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vipupdatestepone);
        binding.setVipUpdateStepOneActivity(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.updateVIPBtn) {
            navigate.navigateToVipUpdateStepTwo(this, null, false);
        }
    }

    @Subscribe
    public void onVipUpdateSuccessEvent(VipUpdateSuccessEvent event) {
        finish();
    }
}
