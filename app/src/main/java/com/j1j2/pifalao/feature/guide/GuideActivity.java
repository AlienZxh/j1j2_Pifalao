package com.j1j2.pifalao.feature.guide;

import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityGuideBinding;
import com.j1j2.pifalao.feature.guide.di.GuideModule;
import com.j1j2.pifalao.feature.home.storestylehome.StoreStyleHomeTopCycleAdapter;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-4.
 */
@RequireBundler
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

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
        //____________________________________________________
        int[] imgId = {R.drawable.guide_img_1,
                R.drawable.guide_img_2,
                R.drawable.guide_img_3,
                R.drawable.guide_img_4};
        GuideAdapter guideAdapter = new GuideAdapter(imgId, 0.58f);
        binding.guideViewPager.setAdapter(guideAdapter);

        binding.tab.setViewPager(binding.guideViewPager);
        binding.tab.setOnPageChangeListener(this);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getAppComponent().plus(new GuideModule(this)).inject(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) {
            binding.guideBtn.setVisibility(View.VISIBLE);
            binding.tab.setVisibility(View.GONE);
        } else {
            binding.tab.setVisibility(View.VISIBLE);
            binding.guideBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void navigateTo(View v) {
        navigate.navigateToCityActivity(GuideActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(v, 0, 0, 0, 0), true);
    }
}
