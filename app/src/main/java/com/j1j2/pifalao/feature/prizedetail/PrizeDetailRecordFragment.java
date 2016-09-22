package com.j1j2.pifalao.feature.prizedetail;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.LotteryParticipationTimes;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.HasComponent;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.FragmentPrizedetailRecordBinding;
import com.j1j2.pifalao.feature.home.memberhome.MemberHomePrizeAdapter;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailComponent;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-1.
 */
public class PrizeDetailRecordFragment extends LazyFragment  {

    public interface PrizeDetailRecordFragmentListener extends HasComponent<PrizeDetailComponent> {
        int getLotteryId();


        void navigateToPrizeRecordActivity();
    }

    PrizeDetailRecordFragmentListener listener;

    FragmentPrizedetailRecordBinding binding;

    PrizeDetailRecordAdapter adapter;

    @Inject
    ActivityApi activityApi;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        listener.getComponent().inject(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (PrizeDetailRecordFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "PrizeDetailRecordFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prizedetail_record, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {

        binding.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                    listener.navigateToPrizeRecordActivity();
            }
        });
        binding.recordList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recordList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getContext())
                .color(0xffd2d2d2)
                .margin(AutoUtils.getPercentWidthSize(20), 0)
                .size(AutoUtils.getPercentWidthSize(1))
                .build());
        queryParticipationTimesDetails();
    }

    private void queryParticipationTimesDetails() {
        activityApi.queryParticipationTimesDetails(listener.getLotteryId(), 1)
                .compose(this.<WebReturn<PagerManager<LotteryParticipationTimes>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<LotteryParticipationTimes>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<LotteryParticipationTimes> lotteryParticipationTimesPagerManager) {
                        binding.recordList.setAdapter(adapter = new PrizeDetailRecordAdapter(lotteryParticipationTimesPagerManager.getList(),5));

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


}
