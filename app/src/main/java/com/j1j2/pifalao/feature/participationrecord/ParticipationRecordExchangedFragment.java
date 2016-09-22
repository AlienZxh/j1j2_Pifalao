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
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.FragmentParticipationrecordExchangedBinding;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-21.
 */

public class ParticipationRecordExchangedFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener
        , OnMoreListener
        , ParticipationRecordExchangedAdapter.ParticipationRecordExchangedAdapterListener {

    public interface ParticipationRecordExchangedFragmentListener {
        ActivityApi getActivityApi();

        void navigateToPrizeOrder(ActivityProduct activityProduct);
    }

    ParticipationRecordExchangedFragmentListener listener;

    FragmentParticipationrecordExchangedBinding binding;

    ParticipationRecordExchangedAdapter adapter;
    int pageIndex = 1;
    int pageCount = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ParticipationRecordExchangedFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "ParticipationRecordExchangedFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_participationrecord_exchanged, container, false);
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

        onRefresh();
    }


    public void setLoadMoreBegin() {
        binding.recordList.setupMoreListener(this, 1);
    }

    public void setLoadMoreComplete() {
        binding.recordList.hideMoreProgress();
        binding.recordList.removeMoreListener();
    }


    public void queryUserParticipageExchangeActivities(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            setLoadMoreBegin();
        }
        listener.getActivityApi().queryUserParticipageExchangeActivities(pageIndex)
                .compose(this.<WebReturn<PagerManager<ActivityProduct>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<ActivityProduct>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<ActivityProduct> activityWinPrizePagerManager) {
                        pageCount = activityWinPrizePagerManager.getPageCount();
                        if (pageIndex == 1) {
                            if (adapter == null || binding.recordList.getAdapter() == null)
                                binding.recordList.setAdapter(adapter = new ParticipationRecordExchangedAdapter(activityWinPrizePagerManager.getList(), ParticipationRecordExchangedFragment.this));
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

    @Override
    public void onRefresh() {
        queryUserParticipageExchangeActivities(true);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        queryUserParticipageExchangeActivities(false);
    }

    @Override
    public void navigateToPrizeOrder(ActivityProduct data) {
        listener.navigateToPrizeOrder(data);
    }
}
