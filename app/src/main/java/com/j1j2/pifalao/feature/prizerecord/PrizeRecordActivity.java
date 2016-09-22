package com.j1j2.pifalao.feature.prizerecord;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.LotteryParticipationTimes;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.ActivityPrizeRecordBinding;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailRecordAdapter;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailModule;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-20.
 */
@RequireBundler
public class PrizeRecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    ActivityPrizeRecordBinding binding;

    @Arg
    int lotteryId;

    @Inject
    ActivityApi activityApi;

    PrizeDetailRecordAdapter adapter;
    int pageIndex = 1;
    int pageCount = 0;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new PrizeDetailModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prize_record);
    }

    @Override
    protected void initViews() {
        binding.recordList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recordList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(0xffd2d2d2)
                .margin(AutoUtils.getPercentWidthSize(20), 0)
                .size(AutoUtils.getPercentWidthSize(1))
                .showLastDivider()
                .build());
        binding.recordList.getRecyclerView().setPadding(0, AutoUtils.getPercentHeightSize(20), 0, AutoUtils.getPercentHeightSize(20));
        binding.recordList.getRecyclerView().setClipToPadding(false);
        binding.recordList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.recordList.setRefreshListener(this);

        //____________________________________________
        onRefresh();
    }

    private void queryParticipationTimesDetails(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            setLoadMoreBegin();
        }
        activityApi.queryParticipationTimesDetails(lotteryId, pageIndex)
                .compose(this.<WebReturn<PagerManager<LotteryParticipationTimes>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<LotteryParticipationTimes>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<LotteryParticipationTimes> lotteryParticipationTimesPagerManager) {
                        pageCount = lotteryParticipationTimesPagerManager.getPageCount();
                        if (pageIndex == 1) {
                            if (adapter == null || binding.recordList.getAdapter() == null)
                                binding.recordList.setAdapter(adapter = new PrizeDetailRecordAdapter(lotteryParticipationTimesPagerManager.getList(), Integer.MAX_VALUE));
                            else
                                adapter.initData(lotteryParticipationTimesPagerManager.getList());
                        } else if (pageIndex <= pageCount) {
                            adapter.addAll(lotteryParticipationTimesPagerManager.getList());
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

    public void setLoadMoreBegin() {
        binding.recordList.setupMoreListener(this, 1);
    }

    public void setLoadMoreComplete() {
        binding.recordList.hideMoreProgress();
        binding.recordList.removeMoreListener();
    }

    @Override
    public void onRefresh() {
        queryParticipationTimesDetails(true);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        queryParticipationTimesDetails(false);
    }
}
