package com.j1j2.pifalao.feature.guide;

import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityGuideBinding;
import com.j1j2.pifalao.feature.guide.di.GuideModule;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-4.
 */
@RequireBundler
public class GuideActivity extends BaseActivity {

    ActivityGuideBinding binding;

    @Inject
    UserRelativePreference userRelativePreference;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_guide);
    }

    @Override
    protected void initViews() {
        userRelativePreference.setIsFirst(false);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getAppComponent().plus(new GuideModule(this)).inject(this);
    }

    public void navigateTo(View v) {
        navigate.navigateToCityActivity(GuideActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(v, 0, 0, 0, 0), true);
    }
}
