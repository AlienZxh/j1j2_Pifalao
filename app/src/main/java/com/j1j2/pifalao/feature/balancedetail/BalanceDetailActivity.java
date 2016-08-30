package com.j1j2.pifalao.feature.balancedetail;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.http.api.UserFinancialApi;
import com.j1j2.data.model.BalanceRecord;
import com.j1j2.data.model.BalanceRecordPage;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.ActivityBalancedetailBinding;
import com.j1j2.pifalao.feature.balancedetail.di.BalanceDetailModule;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-5-12.
 */
@RequireBundler
public class BalanceDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener {
    ActivityBalancedetailBinding binding;

    @Inject
    UserFinancialApi userFinancialApi;

    BalanceDetailAdapter balanceDetailAdapter;

    String timeStr;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new BalanceDetailModule()).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_balancedetail);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initViews() {

        //_____________________________________________________________________________
        binding.balanceList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.balanceList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .colorResId(R.color.colorGrayEDEDED)
                .showLastDivider()
                .size(1)
                .build());
        binding.balanceList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.balanceList.setRefreshListener(this);
        binding.balanceList.setupMoreListener(this, 1);
        binding.balanceList.setAdapter(balanceDetailAdapter = new BalanceDetailAdapter(new ArrayList<BalanceRecord>()));
        onRefresh();
    }

    public void queryUserCurrentMonthFinancialRecords(String time) {
//        userFinancialApi.queryUserCurrentMonthFinancialRecords()
//                .compose(this.<WebReturn<List<BalanceRecord>>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new WebReturnSubscriber<List<BalanceRecord>>() {
//                    @Override
//                    public void onWebReturnSucess(List<BalanceRecord> balanceRecords) {
//                        BalanceDetailAdapter balanceDetailAdapter = new BalanceDetailAdapter(balanceRecords);
//                        binding.balanceList.setAdapter(balanceDetailAdapter);
//                    }
//
//                    @Override
//                    public void onWebReturnFailure(String errorMessage) {
//
//                    }
//
//                    @Override
//                    public void onWebReturnCompleted() {
//
//                    }
//                });
        userFinancialApi.queryUserFinancalRecords(time)
                .compose(this.<WebReturn<BalanceRecordPage>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<BalanceRecordPage>() {
                    @Override
                    public void onWebReturnSucess(BalanceRecordPage balanceRecordPage) {
                        timeStr = balanceRecordPage.getNextSegementMinTimeStr();
                        if (balanceRecordPage.getRecords().size() <= 0 || balanceRecordPage.getRecords() == null) {
                            binding.balanceList.hideMoreProgress();
                            binding.balanceList.setLoadingMore(false);
                        } else {
                            balanceDetailAdapter.addBalanceRecords(balanceRecordPage.getRecords());
                        }


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
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        queryUserCurrentMonthFinancialRecords(timeStr);
    }

    @Override
    public void onRefresh() {
        balanceDetailAdapter.clear();
        queryUserCurrentMonthFinancialRecords("");
    }
}
