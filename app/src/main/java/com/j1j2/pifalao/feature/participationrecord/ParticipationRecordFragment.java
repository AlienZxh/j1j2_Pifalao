package com.j1j2.pifalao.feature.participationrecord;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.FragmentParticipationrecordBinding;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-1.
 */
@RequireBundler
public class ParticipationRecordFragment extends LazyFragment implements
        SwipeRefreshLayout.OnRefreshListener, OnMoreListener,
        ParticipationRecordAdapter.ParticipationRecordAdapterListener {

    public interface ParticipationRecordFragmentListener {
        ActivityApi getActivityApi();

        void showCatNumDialog(List<String> stringList);

        void navigateToPrizeDetail(ActivityWinPrize data);

        void navigateToPrizeOrder(ActivityWinPrize data);

        void backToMemberHome();
    }

    ParticipationRecordFragmentListener listener;

    FragmentParticipationrecordBinding binding;

    @Arg
    int state;

    ParticipationRecordAdapter adapter;
    int pageIndex = 1;
    int pageCount = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ParticipationRecordFragmentListener) activity;
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Override
    protected String getFragmentName() {
        return "ParticipationRecordFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_participationrecord, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.recordList.getRecyclerView().setClipToPadding(false);
        binding.recordList.getRecyclerView().setPadding(0, AutoUtils.getPercentHeightSize(20), 0, AutoUtils.getPercentHeightSize(20));
        binding.recordList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recordList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getContext())
                .color(0x00ffffff)
                .size(AutoUtils.getPercentHeightSize(20))
                .build());
        binding.recordList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.recordList.setRefreshListener(this);

        binding.recordList.getEmptyView().findViewById(R.id.retryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.backToMemberHome();
            }
        });

        onRefresh();
    }


    public void queryUserParticipateLotteryActivities(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            setLoadMoreBegin();
        }
        listener.getActivityApi().queryUserParticipateLotteryActivities(pageIndex, state)
                .compose(this.<WebReturn<PagerManager<ActivityWinPrize>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<ActivityWinPrize>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<ActivityWinPrize> activityWinPrizePagerManager) {
                        pageCount = activityWinPrizePagerManager.getPageCount();
                        if (pageIndex == 1) {
                            if (adapter == null || binding.recordList.getRecyclerView().getAdapter() == null)
                                binding.recordList.setAdapter(adapter = new ParticipationRecordAdapter(activityWinPrizePagerManager.getList(), ParticipationRecordFragment.this));
                            else
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

    public void queryParticipationTimesDetails(int lottery) {
        listener.getActivityApi().queryParticipationTimesDetails(lottery)
                .compose(this.<WebReturn<List<String>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<String>>() {
                    @Override
                    public void onWebReturnSucess(List<String> stringList) {
                        listener.showCatNumDialog(stringList);
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
        queryUserParticipateLotteryActivities(true);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        queryUserParticipateLotteryActivities(false);
    }

    @Override
    public void showCatNumDialog(ActivityWinPrize data) {
        queryParticipationTimesDetails(data.getLotteryId());
    }

    @Override
    public void navigateToPrizeDetail(ActivityWinPrize data) {
        listener.navigateToPrizeDetail(data);
    }

    @Override
    public void backToMemberHome() {
        listener.backToMemberHome();
    }

    @Override
    public void navigateToPrizeOrder(ActivityWinPrize data) {
        listener.navigateToPrizeOrder(data);
    }
}
