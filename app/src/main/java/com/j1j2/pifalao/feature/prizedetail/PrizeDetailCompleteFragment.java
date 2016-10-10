package com.j1j2.pifalao.feature.prizedetail;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.LotteryParticipationTimes;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.HasComponent;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.FragmentPrizedetailCompleteBinding;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailComponent;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-2.
 */
public class PrizeDetailCompleteFragment extends BaseFragment implements View.OnClickListener {

    public interface PrizeDetailCompleteFragmentListener extends HasComponent<PrizeDetailComponent> {
        void navigateToCalculateDetail();

        ActivityWinPrize getActivityWinPrize();

        void showCatNumDialog();

        int getLotteryId();

        int getTimes();
    }

    PrizeDetailCompleteFragmentListener listener;

    FragmentPrizedetailCompleteBinding binding;

    @Inject
    ActivityApi activityApi;

    List<String> numList;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        listener.getComponent().inject(this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (PrizeDetailCompleteFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "PrizeDetailCompleteFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prizedetail_complete, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setActivityWinPrize(listener.getActivityWinPrize());
        binding.calculateBtn.setOnClickListener(this);
        binding.catNum.setOnClickListener(this);
        binding.setTimes(listener.getTimes());
    }


    @Override
    public void onClick(View v) {
        if (v == binding.calculateBtn)
            listener.navigateToCalculateDetail();
        if (v == binding.catNum)
            listener.showCatNumDialog();
    }
}
