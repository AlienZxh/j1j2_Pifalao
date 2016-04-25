package com.j1j2.pifalao.feature.orderdetail.orderdetailtimeline;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.SaleOrderStateHistory;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.FragmentOrderdetailTimelineBinding;
import com.j1j2.pifalao.feature.orderdetail.orderdetailtimeline.di.OrderDetailTimeLineModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-4-21.
 */
@RequireBundler
public class OrderDetailTimeLineFragment extends BaseFragment {

    FragmentOrderdetailTimelineBinding binding;

    @Arg
    int orderId;

    @Inject
    UserOrderApi userOrderApi;

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orderdetail_timeline, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.timeLineList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.timeLineList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.colorGrayEDEDED).margin(AutoUtils.getPercentWidthSize(120), 0).build());
        queryOrderStateHistory();
    }

    public void queryOrderStateHistory() {
        userOrderApi.queryOrderStateHistory(orderId)
                .compose(this.<WebReturn<List<SaleOrderStateHistory>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<SaleOrderStateHistory>>() {
                    @Override
                    public void onWebReturnSucess(List<SaleOrderStateHistory> saleOrderStateHistories) {
                        OrderDetailTimeLineAdapter orderDetailTimeLineAdapter = new OrderDetailTimeLineAdapter(saleOrderStateHistories);
                        binding.timeLineList.setAdapter(orderDetailTimeLineAdapter);
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
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(getContext()).getUserComponent().plus(new OrderDetailTimeLineModule()).inject(this);
    }
}
