package com.j1j2.pifalao.feature.guide;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityGuideBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-4.
 */
@RequireBundler
public class GuideActivity extends BaseActivity {

    ActivityGuideBinding binding;


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_guide);
    }

    @Override
    protected void initViews() {


    }

    public void navigateTo(View v) {
        navigate.navigateToCityActivity(GuideActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(v, 0, 0, 0, 0), true);
    }
}
