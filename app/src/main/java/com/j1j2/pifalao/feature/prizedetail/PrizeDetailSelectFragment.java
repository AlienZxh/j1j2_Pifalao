package com.j1j2.pifalao.feature.prizedetail;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.util.Toastor;
import com.j1j2.common.view.quantityview.QuantityView;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.LotteryParticipationTimes;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.HasComponent;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.FragmentPrizedetailSelectBinding;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailComponent;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-1.
 */
public class PrizeDetailSelectFragment extends LazyFragment {

    public interface PrizeDetailSelectFragmentListener extends HasComponent<PrizeDetailComponent> {
        ActivityProduct getActivityProduct();

        void showToastor(String msg);

        void showCatNumDialog(List<String> numList);

        int getLotteryId();
    }

    FragmentPrizedetailSelectBinding binding;

    PrizeDetailSelectFragmentListener listener;

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
        listener = (PrizeDetailSelectFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "PrizeDetailSelectFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prizedetail_select, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.catNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showCatNumDialog(numList);
            }
        });
        binding.setActivityProduct(listener.getActivityProduct());
        if (listener.getActivityProduct().getConfigs().getTimesLimits() > 0)
            binding.quantityview.setMaxQuantity(listener.getActivityProduct().getConfigs().getTimesLimits());
        binding.quantityview.setOnMaxQuantityListener(new QuantityView.OnMaxQuantityListener() {
            @Override
            public void onMaxQuantity(QuantityView view) {
                listener.showToastor("不能超过购买限制");
            }
        });
        queryParticipationTimesDetails();
    }


    public void queryParticipationTimesDetails() {
        activityApi.queryParticipationTimesDetails(listener.getLotteryId())
                .compose(this.<WebReturn<List<String>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<String>>() {
                    @Override
                    public void onWebReturnSucess(List<String> stringList) {
                        numList = stringList;
                        binding.setTimes(stringList.size());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }



    public int getPrizeQuantity() {
        return binding.quantityview.getQuantity();
    }
}
