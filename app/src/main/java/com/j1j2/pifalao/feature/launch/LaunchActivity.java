package com.j1j2.pifalao.feature.launch;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.service.BackGroundService;
import com.j1j2.pifalao.databinding.ActivityLaunchBinding;

public class LaunchActivity extends BaseActivity implements Animator.AnimatorListener {


    ActivityLaunchBinding binding;

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
        startService(new Intent(this, BackGroundService.class));
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
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        binding.slogan.setDrawingCacheEnabled(true);
        navigate.navigateToGuide(this, ActivityOptionsCompat.makeThumbnailScaleUpAnimation(binding.slogan, binding.slogan.getDrawingCache(), 0, 0), true);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

}
