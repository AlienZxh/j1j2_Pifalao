package com.j1j2.pifalao.feature.briberymoneyopen;

import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

import com.j1j2.common.view.animation.FlipAnimation;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.RedPacket;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.ActivityBriberymoneyOpenBinding;
import com.j1j2.pifalao.feature.prizeconfirm.di.PrizeConfirmModule;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-10-7.
 */
@RequireBundler
public class BriberyMoneyOpenActivity extends BaseActivity implements
        View.OnClickListener
        , Animation.AnimationListener {

    @Arg
    RedPacket redPacket;

    @Inject
    ActivityApi activityApi;

    ActivityBriberymoneyOpenBinding binding;

    FlipAnimation flip;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new PrizeConfirmModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_briberymoney_open);
    }

    @Override
    protected void initViews() {
        binding.close.setOnClickListener(this);
        binding.openBtn.setOnClickListener(this);
        binding.setRedPacket(redPacket);
    }

    void unfoldRedPacket(final View view) {
        activityApi
                .unfoldRedPacket(redPacket.getRecordId(), redPacket.getSaleOrderNO())
                .delay(1200, TimeUnit.MILLISECONDS)
                .compose(this.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        if (flip != null && flip.hasStarted()) {
                            flip.cancel();
                            binding.viewFlipper.clearAnimation();
                        }
                        navigate.navigateToBriberyMoneyResultActivity(BriberyMoneyOpenActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), true, redPacket);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        if (flip != null && flip.hasStarted()) {
                            flip.cancel();
                            binding.viewFlipper.clearAnimation();
                        }
                        toastor.showSingletonToast(errorMessage);
                        finish();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        if (v == binding.close)
            onBackPressed();
        if (v == binding.openBtn) {

            binding.imageView1.setVisibility(View.GONE);
            binding.imageView2.setVisibility(View.VISIBLE);

            float centerX = binding.viewFlipper.getWidth() / 2.0f;
            float centerY = binding.viewFlipper.getHeight() / 2.0f;

            if (flip == null) {
                flip = new FlipAnimation(0, -3600, centerX, centerY, 1f, FlipAnimation.ScaleUpDownEnum.SCALE_NONE);
                flip.setDuration(8000);
                flip.setFillAfter(true);
                flip.setInterpolator(new AccelerateDecelerateInterpolator());
                flip.setDirection(FlipAnimation.ROTATION_Y);
                flip.setAnimationListener(BriberyMoneyOpenActivity.this);
            }
            if (!flip.hasStarted()) {
                binding.viewFlipper.startAnimation(flip);
                unfoldRedPacket(binding.viewFlipper);
            }


        }
    }
}
