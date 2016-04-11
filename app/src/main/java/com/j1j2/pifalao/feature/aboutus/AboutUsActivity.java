package com.j1j2.pifalao.feature.aboutus;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityAboutusBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-6.
 */
@RequireBundler
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    ActivityAboutusBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_aboutus);
        binding.backBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
