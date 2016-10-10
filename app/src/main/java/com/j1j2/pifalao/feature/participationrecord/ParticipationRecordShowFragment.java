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
import com.j1j2.data.model.AcceptanceSpeech;
import com.j1j2.data.model.ImgUrl;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.FragmentParticipationrecordShowBinding;
import com.j1j2.pifalao.feature.showorders.ShowOrderAdapter;
import com.malinskiy.superrecyclerview.OnMoreListener;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-21.
 */
public class ParticipationRecordShowFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener
        , OnMoreListener
        , ShowOrderAdapter.ShowOrderAdapterListener {

    public interface ParticipationRecordShowFragmentListener {
        ActivityApi getActivityApi();
        void navigateToImgsGalleryActivity(View view,List<ImgUrl> urls, int position);
        void backToMemberHome();
    }

    ParticipationRecordShowFragmentListener listener;

    FragmentParticipationrecordShowBinding binding;

    ShowOrderAdapter adapter;
    int pageIndex = 1;
    int pageCount = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ParticipationRecordShowFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "ParticipationRecordShowFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_participationrecord_show, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.recordList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recordList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.recordList.setRefreshListener(this);
        queryAcceptanceSpeechs(true);
        binding.recordList.getEmptyView().findViewById(R.id.retryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.backToMemberHome();
            }
        });
    }

    private void queryAcceptanceSpeechs(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            setLoadMoreBegin();
        }
        listener.getActivityApi().queryAllAcceptanceSpeech(pageIndex)
                .compose(this.<WebReturn<PagerManager<AcceptanceSpeech>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<AcceptanceSpeech>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<AcceptanceSpeech> acceptanceSpeechPagerManager) {
                        pageCount = acceptanceSpeechPagerManager.getPageCount();
                        if (pageIndex == 1) {
                            if (adapter == null || binding.recordList.getAdapter() == null)
                                binding.recordList.setAdapter(adapter = new ShowOrderAdapter(acceptanceSpeechPagerManager.getList(),ParticipationRecordShowFragment.this));
                            else
                                adapter.initData(acceptanceSpeechPagerManager.getList());
                        } else if (pageIndex <= pageCount) {
                            adapter.addAll(acceptanceSpeechPagerManager.getList());
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
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        queryAcceptanceSpeechs(false);
    }

    @Override
    public void onRefresh() {
        queryAcceptanceSpeechs(true);
    }

    @Override
    public void navigateToImgsGalleryActivity(View view,List<ImgUrl> urls, int position) {
        listener.navigateToImgsGalleryActivity(view,urls,position);
    }
}
