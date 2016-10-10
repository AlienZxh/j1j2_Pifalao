package com.j1j2.pifalao.feature.prize;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.LotteryParticipationTimes;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.ActivityPrizeBinding;
import com.j1j2.pifalao.feature.freeconvertibility.FreeConvertibilityAdapter;
import com.j1j2.pifalao.feature.prize.di.PrizeModule;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailModule;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
public class PrizeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, PrizeAdapter.PrizeAdapterListener, View.OnClickListener {

    ActivityPrizeBinding binding;

    public static final int PRIZE_PUBLISHED = 0;
    public static final int PRIZE_MINE = 1;

    @Inject
    ActivityApi activityApi;
    @Arg
    int prizeType;

    PrizeAdapter adapter;

    int pageIndex = 1;
    int pageCount = 0;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        if (prizeType == PRIZE_MINE)
            MainAplication.get(this).getUserComponent().plus(new PrizeDetailModule(this)).inject(this);
        if (prizeType == PRIZE_PUBLISHED)
            MainAplication.get(this).getAppComponent().plus(new PrizeModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prize);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);

        if (prizeType == PRIZE_PUBLISHED)
            binding.actionBarTitle.setText("中奖揭晓");
        if (prizeType == PRIZE_MINE)
            binding.actionBarTitle.setText("我的中奖");

        binding.prizeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.prizeList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(0x00ffffff)
                .size(AutoUtils.getPercentHeightSize(20))
                .build());
        binding.prizeList.getRecyclerView().setPadding(0, AutoUtils.getPercentHeightSize(20), 0, AutoUtils.getPercentHeightSize(20));
        binding.prizeList.getRecyclerView().setClipToPadding(false);
        binding.prizeList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.prizeList.setRefreshListener(this);

        //____________________________________________

    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    private void queryRecords(boolean isRefresh) {
        if (prizeType == PRIZE_PUBLISHED)
            queryActivityWinPrizeRecords(isRefresh);
        if (prizeType == PRIZE_MINE)
            queryUserParticipateLotteryActivities(isRefresh);
    }

    private void queryActivityWinPrizeRecords(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            setLoadMoreBegin();
        }
        activityApi.queryActivityWinPrizeRecords(pageIndex)
                .compose(this.<WebReturn<PagerManager<ActivityWinPrize>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<ActivityWinPrize>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<ActivityWinPrize> activityWinPrizePagerManager) {
                        pageCount = activityWinPrizePagerManager.getPageCount();
                        if (pageIndex == 1) {
                            if (adapter == null || binding.prizeList.getAdapter() == null) {
                                binding.prizeList.setAdapter(adapter = new PrizeAdapter(activityWinPrizePagerManager.getList(), prizeType));
                                adapter.setListener(PrizeActivity.this);
                            } else
                                adapter.initData(activityWinPrizePagerManager.getList());
                        } else if (pageIndex <= pageCount) {
                            adapter.addAll(activityWinPrizePagerManager.getList());
                        } else {
                            setLoadMoreComplete();
                        }
                        pageIndex++;
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    private void queryUserParticipateLotteryActivities(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            setLoadMoreBegin();
        }
        activityApi.queryUserParticipateLotteryActivities(pageIndex, ActivityApi.PRIZE_AWARDED)
                .compose(this.<WebReturn<PagerManager<ActivityWinPrize>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<ActivityWinPrize>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<ActivityWinPrize> activityWinPrizePagerManager) {
                        pageCount = activityWinPrizePagerManager.getPageCount();

                        List<ActivityWinPrize> activityWinPrizes = new ArrayList<ActivityWinPrize>();

                        if (!EmptyUtils.isEmpty(activityWinPrizePagerManager.getList()))
                            for (ActivityWinPrize activityWinPrize : activityWinPrizePagerManager.getList()) {
                                if (!activityWinPrizes.contains(activityWinPrize)) {
                                    activityWinPrizes.add(activityWinPrize);
                                }
                            }

                        if (pageIndex == 1) {
                            if (adapter == null || binding.prizeList.getAdapter() == null) {
                                binding.prizeList.setAdapter(adapter = new PrizeAdapter(activityWinPrizes, prizeType));
                                adapter.setListener(PrizeActivity.this);
                            } else
                                adapter.initData(activityWinPrizes);
                        } else if (pageIndex <= pageCount) {
                            adapter.addAll(activityWinPrizes);
                        } else {
                            setLoadMoreComplete();
                        }
                        pageIndex++;
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryParticipationTimesDetails(int lottery) {
        activityApi.queryParticipationTimesDetails(lottery)
                .compose(this.<WebReturn<List<String>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<String>>() {
                    @Override
                    public void onWebReturnSucess(List<String> stringList) {
                        if (messageDialog != null && messageDialog.isShowing())
                            messageDialog.dismiss();
                        messageDialog = new AlertDialog.Builder(PrizeActivity.this)
                                .setCancelable(true)
                                .setTitle("幸运号码")
                                .setItems(stringList.toArray(new String[stringList.size()]), null)
                                .setPositiveButton("知道了", null)
                                .create();
                        messageDialog.show();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void setLoadMoreBegin() {
        binding.prizeList.setupMoreListener(this, 1);
    }

    public void setLoadMoreComplete() {
        binding.prizeList.hideMoreProgress();
        binding.prizeList.removeMoreListener();
    }


    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        queryRecords(false);
    }

    @Override
    public void onRefresh() {
        queryRecords(true);
    }

    @Override
    public void navigateToPrizeOrderTimeline(ActivityWinPrize data) {
        navigate.navigateToPrizeOrderTimelineActivity(this, null, false, data.getOrderId());
    }

    @Override
    public void showCatNumDialog(ActivityWinPrize data) {
        queryParticipationTimesDetails(data.getLotteryId());
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }
}
