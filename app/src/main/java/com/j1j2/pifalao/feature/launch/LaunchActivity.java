package com.j1j2.pifalao.feature.launch;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.service.LocationService;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.databinding.ActivityLaunchBinding;
import com.j1j2.pifalao.feature.launch.di.LaunchModule;

import javax.inject.Inject;

public class LaunchActivity extends BaseActivity implements Animator.AnimatorListener {


    ActivityLaunchBinding binding;

    @Inject
    UserLoginPreference userLoginPreference;

    @Inject
    LaunchViewModel launchViewModel;

    private boolean isAnimFinish = false;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch);
    }

    @Override
    protected void initViews() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.slogan, "alpha", 0f, 1f);
        animator.setDuration(1500);
        animator.addListener(this);
        animator.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, LocationService.class));
        launchViewModel.initLoginState();
    }

    @SuppressLint("InlinedApi")
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getAppComponent().plus(new LaunchModule(this)).inject(this);
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        isAnimFinish = true;


    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    public boolean isAnimFinish() {
        return isAnimFinish;
    }

    public void navigateToGuide() {
        binding.slogan.setDrawingCacheEnabled(true);
        navigate.navigateToGuide(this, ActivityOptionsCompat.makeThumbnailScaleUpAnimation(binding.slogan, binding.slogan.getDrawingCache(), 0, 0), true);
    }

}
