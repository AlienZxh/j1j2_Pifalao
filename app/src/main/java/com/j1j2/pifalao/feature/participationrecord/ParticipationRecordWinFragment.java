package com.j1j2.pifalao.feature.participationrecord;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.FragmentParticipationrecordWinBinding;
import com.j1j2.pifalao.feature.prize.PrizeActivity;
import com.j1j2.pifalao.feature.prize.PrizeAdapter;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-23.
 */

public class ParticipationRecordWinFragment extends LazyFragment implements
        SwipeRefreshLayout.OnRefreshListener
        , OnMoreListener
        , PrizeAdapter.PrizeAdapterListener {


    public interface ParticipationRecordWinFragmentListener {
        ActivityApi getActivityApi();

        void showCatNumDialog(int lottery);

        void navigateToPrizeOrder(int orderId);

        void backToMemberHome();
    }

    ParticipationRecordWinFragmentListener listener;

    FragmentParticipationrecordWinBinding binding;

    PrizeAdapter adapter;
    int pageIndex = 1;
    int pageCount = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ParticipationRecordWinFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "ParticipationRecordWinFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_participationrecord_win, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.recordList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recordList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getContext())
                .color(0x00ffffff)
                .size(AutoUtils.getPercentHeightSize(20))
                .build());
        binding.recordList.getRecyclerView().setPadding(0, AutoUtils.getPercentHeightSize(20), 0, AutoUtils.getPercentHeightSize(20));
        binding.recordList.getRecyclerView().setClipToPadding(false);
        binding.recordList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.recordList.setRefreshListener(this);

        //____________________________________________
        binding.recordList.getEmptyView().findViewById(R.id.retryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.backToMemberHome();
            }
        });
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        onRefresh();
    }

    private void queryActivityWinPrizeRecords(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            setLoadMoreBegin();
        }
        listener.getActivityApi().queryUserParticipateLotteryActivities(pageIndex, ActivityApi.PRIZE_AWARDED)
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
                            if (adapter == null || binding.recordList.getAdapter() == null) {
                                binding.recordList.setAdapter(adapter = new PrizeAdapter(activityWinPrizes, PrizeActivity.PRIZE_MINE));
                                adapter.setListener(ParticipationRecordWinFragment.this);
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

    public void setLoadMoreBegin() {
        binding.recordList.setupMoreListener(this, 1);
    }

    public void setLoadMoreComplete() {
        binding.recordList.hideMoreProgress();
        binding.recordList.removeMoreListener();
    }

    @Override
    public void onRefresh() {
        queryActivityWinPrizeRecords(true);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        queryActivityWinPrizeRecords(false);
    }

    @Override
    public void navigateToPrizeOrderTimeline(ActivityWinPrize data) {
        listener.navigateToPrizeOrder(data.getOrderId());
    }

    @Override
    public void showCatNumDialog(ActivityWinPrize data) {
        listener.showCatNumDialog(data.getLotteryId());
    }


}
