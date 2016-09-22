package com.j1j2.pifalao.feature.prizedetail;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.User;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentPrizedetailBottomBinding;
import com.j1j2.pifalao.feature.prizeconfirm.PrizeConfirmActivity;
import com.shizhefei.view.multitype.provider.FragmentData;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-9-2.
 */
@RequireBundler
public class PrizeDetailBottomFragment extends BaseFragment implements View.OnClickListener {

    public interface PrizeDetailBottomFragmentListener {

        ActivityProduct getActivityProduct();

        User getUser();

        void navigateToPrizeConfirm();
    }

    PrizeDetailBottomFragmentListener listener;

    FragmentPrizedetailBottomBinding binding;

    @Arg
    int activityType;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (PrizeDetailBottomFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "PrizeDetailBottomFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prizedetail_bottom, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.confirmOrder.setOnClickListener(this);
        if (activityType == PrizeDetailActivity.GIFT) {
            binding.text.setVisibility(View.GONE);
            binding.confirmOrder.setText("立即兑换");
        } else if (activityType == PrizeDetailActivity.PRIZE_ONGOING) {
            binding.text.setVisibility(View.GONE);
            binding.confirmOrder.setText("立即参与");
        } else if (activityType == PrizeDetailActivity.PRIZE_COMPLETED) {
            binding.text.setVisibility(View.VISIBLE);
            binding.confirmOrder.setText("立即前往");
        }
        if (activityType == PrizeDetailActivity.GIFT || activityType == PrizeDetailActivity.PRIZE_ONGOING)
            if (listener.getActivityProduct().getConfigs().getCostExchangePoint() == null) {

            } else if (listener.getUser().getPoint() < listener.getActivityProduct().getConfigs().getCostExchangePoint()) {
                binding.confirmOrder.setBackgroundColor(0xffdcdcdc);
                binding.confirmOrder.setTextColor(Color.WHITE);
                binding.confirmOrder.setText("积分不足" + "（当前积分" + listener.getUser().getPoint() + "）");
                binding.confirmOrder.setEnabled(false);
            } else if (listener.getActivityProduct().getConfigs().getCount() != null && listener.getActivityProduct().getConfigs().getCount() <= 0) {
                binding.confirmOrder.setBackgroundColor(0xffdcdcdc);
                binding.confirmOrder.setTextColor(Color.WHITE);
                binding.confirmOrder.setText("商品售罄");
                binding.confirmOrder.setEnabled(false);
            }
        if (activityType == PrizeDetailActivity.PRIZE_ONGOING)
            if (listener.getActivityProduct().getStatistics().getMaxUserRemain() <= 0 && listener.getActivityProduct().getConfigs().getMaxUsers() > 0) {
                binding.confirmOrder.setBackgroundColor(0xffdcdcdc);
                binding.confirmOrder.setTextColor(Color.WHITE);
                binding.confirmOrder.setText("参与人数已满");
                binding.confirmOrder.setEnabled(false);
            }


    }

    @Override
    public void onClick(View v) {
        if (v == binding.confirmOrder)
            if (activityType == PrizeDetailActivity.GIFT) {
                listener.navigateToPrizeConfirm();
            } else if (activityType == PrizeDetailActivity.PRIZE_ONGOING) {
                listener.navigateToPrizeConfirm();
            } else if (activityType == PrizeDetailActivity.PRIZE_COMPLETED) {

            }

    }


}
