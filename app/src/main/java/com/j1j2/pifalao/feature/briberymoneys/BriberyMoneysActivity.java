package com.j1j2.pifalao.feature.briberymoneys;

import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.RedPacket;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityBriberymoneysBinding;
import com.j1j2.pifalao.feature.prizeconfirm.di.PrizeConfirmModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-10-7.
 */
@RequireBundler
public class BriberyMoneysActivity extends BaseActivity implements View.OnClickListener
        , BriberyMonerysFragment.BriberyMonerysFragmentListener {

    ActivityBriberymoneysBinding binding;


    @Inject
    ActivityApi activityApi;

    @Arg
    int selectState;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        if (MainAplication.get(this).isLogin())
            MainAplication.get(this).getUserComponent().plus(new PrizeConfirmModule(this)).inject(this);
        else
            navigate.navigateToLogin(this, null, true);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_briberymoneys);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);

        final List<String> strings = new ArrayList<>();
        strings.add("未领取");
        strings.add("已领取");
        strings.add("已失效");

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(Bundler.briberyMonerysFragment(Constant.RedPacketState.AVAILABILITY).create());
        fragments.add(Bundler.briberyMonerysFragment(Constant.RedPacketState.USED).create());
        fragments.add(Bundler.briberyMonerysFragment(Constant.RedPacketState.UNAVAILABILITY).create());


        binding.viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return strings.get(position);
            }
        });
        binding.tab.setViewPager(binding.viewpager);

        binding.viewpager.setCurrentItem(selectState - 1);
    }

    @Override
    public ActivityApi getActivityApi() {
        return activityApi;
    }

    @Override
    public void navigateToBriberyMoneryOpen(RedPacket redPacket) {
        navigate.navigateToBriberyMoneyOpenActivity(this, null, false, redPacket);
    }
    

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }
}
