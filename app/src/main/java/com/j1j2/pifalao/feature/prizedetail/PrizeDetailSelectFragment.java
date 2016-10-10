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

        void showCatNumDialog();

        int getLotteryId();

        int getTimes();
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
                listener.showCatNumDialog();
            }
        });
        binding.setActivityProduct(listener.getActivityProduct());
        binding.setTimes(listener.getTimes());
        int limit = 9999;
        int remain = 9999;
        if (listener.getActivityProduct().getConfigs().getTimesLimits() > 0)
            limit = listener.getActivityProduct().getConfigs().getTimesLimits() - listener.getTimes();
        if (listener.getActivityProduct().getStatistics().getMaxUserRemain() > 0)
            remain = listener.getActivityProduct().getStatistics().getMaxUserRemain();

        binding.quantityview.setMaxQuantity(Math.min(limit, remain));

        binding.quantityview.setOnMaxQuantityListener(new QuantityView.OnMaxQuantityListener() {
            @Override
            public void onMaxQuantity(QuantityView view) {
                listener.showToastor("不能超过购买限制");
            }
        });


    }


    public int getPrizeQuantity() {
        return binding.quantityview.getQuantity();
    }
}
