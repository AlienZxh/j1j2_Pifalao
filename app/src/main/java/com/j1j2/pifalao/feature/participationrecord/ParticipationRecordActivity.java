package com.j1j2.pifalao.feature.participationrecord;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.j1j2.common.view.scrollablelayout.ScrollableHelper;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.ImgUrl;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.NavigateToMemberHomeEvent;
import com.j1j2.pifalao.app.event.NavigateToPrizeDetailEvent;
import com.j1j2.pifalao.databinding.ActivityParticipationrecordBinding;
import com.j1j2.pifalao.feature.home.memberhome.MemberHomeLuckyFragment;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailActivity;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailComponent;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailModule;
import com.j1j2.pifalao.feature.services.ServicesActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-1.
 */
@RequireBundler
public class ParticipationRecordActivity extends BaseActivity implements View.OnClickListener
        , ParticipationRecordFragment.ParticipationRecordFragmentListener
        , ParticipationRecordExchangedFragment.ParticipationRecordExchangedFragmentListener
        , ParticipationRecordShowFragment.ParticipationRecordShowFragmentListener
        , ParticipationRecordWinFragment.ParticipationRecordWinFragmentListener {
    public static final int RECORD_ONGOING = 1;
    public static final int RECORD_RAFFLED = 2;
    public static final int RECORD_AWARDED = 3;
    public static final int RECORD_EXCHANGED = 4;
    public static final int RECORD_SHOWORDER = 5;

    ActivityParticipationrecordBinding binding;

    @Arg
    int activityType;

    PrizeDetailComponent component;

    @Inject
    ActivityApi activityApi;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        component = MainAplication.get(this).getUserComponent().plus(new PrizeDetailModule(this));
        component.inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_participationrecord);
    }

    @Override
    protected void initViews() {

        binding.backBtn.setOnClickListener(this);

        final List<String> strings = new ArrayList<>();
        strings.add("进行中");
        strings.add("已揭晓");
        strings.add("已中奖");
        strings.add("已兑换");
        strings.add("已晒单");

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(Bundler.participationRecordFragment(ActivityApi.PRIZE_ONGOING).create());
        fragments.add(Bundler.participationRecordFragment(ActivityApi.PRIZE_RAFFLED).create());
        fragments.add(new ParticipationRecordWinFragment());
        fragments.add(new ParticipationRecordExchangedFragment());
        fragments.add(new ParticipationRecordShowFragment());

        binding.viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return strings.get(position);
            }
        });
        binding.tab.setViewPager(binding.viewpager);

        binding.viewpager.setCurrentItem(activityType - 1);
    }

    @Override
    public ActivityApi getActivityApi() {
        return activityApi;
    }


    @Override
    public void showCatNumDialog(int lottery) {
        activityApi.queryParticipationTimesDetails(lottery)
                .compose(this.<WebReturn<List<String>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<String>>() {
                    @Override
                    public void onWebReturnSucess(List<String> stringList) {
                     new AlertDialog.Builder(ParticipationRecordActivity.this)
                                .setCancelable(true)
                                .setTitle("幸运号码")
                                .setItems(stringList.toArray(new String[stringList.size()]), null)
                                .setPositiveButton("知道了", null)
                                .create().show();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    @Override
    public void navigateToPrizeDetail(ActivityWinPrize data) {

        Intent intent = new Intent(this, ServicesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

        EventBus.getDefault().post(new NavigateToPrizeDetailEvent(data.getProductInfo().getProductId()));

//        navigate.navigateToPrizeDetailActivity(this, null, false
//                , data.getProductInfo().getProductId()
//                , null);
    }

    @Override
    public void backToMemberHome() {
        Intent intent = new Intent(this, ServicesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

        EventBus.getDefault().post(new NavigateToMemberHomeEvent());
//        navigate.navigateToMemberHomeActivity(this, null, true);
    }

    @Override
    public void navigateToPrizeOrder(int orderId) {
        navigate.navigateToPrizeOrderTimelineActivity(this, null, false, orderId);
    }

    @Override
    public void navigateToImgsGalleryActivity(View view, List<ImgUrl> urls, int position) {
        navigate.navigateToImgsGalleryActivity(this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, urls, position);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }
}
