package com.j1j2.pifalao.feature.unsubscribe;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityUnsubscribeModuleBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-16.
 */
@RequireBundler
public class UnsubscribeModuleActivity extends BaseActivity implements View.OnClickListener {

    ActivityUnsubscribeModuleBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_unsubscribe_module);
        binding.backBtn.setOnClickListener(this);
        binding.individualBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.individualBtn)
            navigate.navigateToIndividualCenter(this, null, false);
    }
}
