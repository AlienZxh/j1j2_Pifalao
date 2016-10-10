package com.j1j2.pifalao.feature.briberymoneys;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.RedPacket;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.pifalao.databinding.FragmentBriberymoneysBinding;
import com.j1j2.pifalao.feature.participationrecord.ParticipationRecordExchangedAdapter;
import com.j1j2.pifalao.feature.participationrecord.ParticipationRecordExchangedFragment;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-10-7.
 */
@RequireBundler
public class BriberyMonerysFragment extends LazyFragment implements
        SwipeRefreshLayout.OnRefreshListener
        , OnMoreListener {

    public interface BriberyMonerysFragmentListener {
        void navigateToBriberyMoneryOpen(RedPacket redPacket);

        ActivityApi getActivityApi();
    }

    FragmentBriberymoneysBinding binding;

    BriberyMonerysFragmentListener listener;

    BriberyMoneryAdapter adapter;

    @Arg
    int redPacketState;
    int pageIndex = 1;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Override
    protected String getFragmentName() {
        return "BriberyMonerysFragment";
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (BriberyMonerysFragmentListener) activity;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_briberymoneys, container, false);
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
        binding.recordList.setupMoreListener(this, 1);

        ((TextView) binding.recordList.getEmptyView().findViewById(R.id.emptyText)).setText("暂无数据");
        ((TextView) binding.recordList.getEmptyView().findViewById(R.id.retryBtn)).setText("点击刷新");
        binding.recordList.getEmptyView().findViewById(R.id.retryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });


    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        onRefresh();
    }

    public void queryUserParticipageExchangeActivities() {

        listener.getActivityApi().queryUserRedPackets(pageIndex, redPacketState)
                .compose(this.<WebReturn<PagerManager<RedPacket>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<RedPacket>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<RedPacket> redPacketPagerManager) {
                        if (adapter == null || binding.recordList.getAdapter() == null) {
                            binding.recordList.setAdapter(adapter = new BriberyMoneryAdapter(getContext(), redPacketState));
                            adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    if (redPacketState == Constant.RedPacketState.AVAILABILITY)
                                        listener.navigateToBriberyMoneryOpen(adapter.getItem(position));
                                }
                            });
                        }
                        if (pageIndex == 1 && adapter.getCount() > 0)
                            adapter.clear();
                        adapter.addAll(redPacketPagerManager.getList());

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
        pageIndex = 1;
        queryUserParticipageExchangeActivities();
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        queryUserParticipageExchangeActivities();
    }
}
