package com.j1j2.pifalao.feature.showorders;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.AcceptanceSpeech;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.ActivityShoworderListBinding;
import com.j1j2.pifalao.feature.showorders.di.ShowOrdersModule;
import com.malinskiy.superrecyclerview.OnMoreListener;

import java.util.ArrayList;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-5.
 */

@RequireBundler
public class ShowOrderListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, View.OnClickListener {

    ActivityShoworderListBinding binding;

    @Inject
    ActivityApi activityApi;

    ShowOrderAdapter adapter;
    int pageIndex = 1;
    int pageCount = 0;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getAppComponent().plus(new ShowOrdersModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_showorder_list);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        binding.orderList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.orderList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.orderList.setRefreshListener(this);
        queryAcceptanceSpeechs(true);
    }

    private void queryAcceptanceSpeechs(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            setLoadMoreBegin();
        }
        activityApi.queryAllAcceptanceSpeech(pageIndex)
                .compose(this.<WebReturn<PagerManager<AcceptanceSpeech>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<AcceptanceSpeech>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<AcceptanceSpeech> acceptanceSpeechPagerManager) {
                        pageCount = acceptanceSpeechPagerManager.getPageCount();
                        if (pageIndex == 1) {
                            if (adapter == null || binding.orderList.getAdapter() == null)
                                binding.orderList.setAdapter(adapter = new ShowOrderAdapter(acceptanceSpeechPagerManager.getList()));
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
        binding.orderList.setupMoreListener(this, 1);
    }

    public void setLoadMoreComplete() {
        binding.orderList.hideMoreProgress();
        binding.orderList.removeMoreListener();
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
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }
}
