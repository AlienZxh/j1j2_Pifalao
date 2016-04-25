package com.j1j2.pifalao.feature.vipupdate.steptwo;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.data.model.User;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityVipupdatesteptwoBinding;
import com.j1j2.pifalao.feature.successresult.SuccessResultActivity;
import com.j1j2.pifalao.feature.vipupdate.steptwo.di.VipUpdateStepTwoModule;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class VipUpdateStepTwoActivity extends BaseActivity implements View.OnClickListener {

    ActivityVipupdatesteptwoBinding binding;

    @Inject
    VipUpdateStepTwoViewModel vipUpdateStepTwoViewModel;

    @Inject
    User user;


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vipupdatesteptwo);
        binding.setVipUpdateStepTwoViewModel(vipUpdateStepTwoViewModel);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new VipUpdateStepTwoModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.confirm) {
            if (binding.codeEdit.getText().toString().length() <= 0) {
                toastor.showSingletonToast("激活码不能为空");
                return;
            }
            vipUpdateStepTwoViewModel.vipUpdate(binding.codeEdit.getText().toString());
        }
    }

    public void finishUpdate(String code) {
        user.setVIPRegisterCode(code);
        navigate.navigateToSuccessResult(this, null, true, SuccessResultActivity.FROM_UPDATEVIP);
    }
}
