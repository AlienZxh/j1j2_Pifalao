package com.j1j2.pifalao.feature.freeconvertibility;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityFreeconvertibilityBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-8-25.
 */
@RequireBundler
public class FreeConvertibilityActivity extends BaseActivity implements View.OnClickListener {

    ActivityFreeconvertibilityBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_freeconvertibility);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
