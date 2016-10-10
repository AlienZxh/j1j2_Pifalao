package com.j1j2.pifalao.feature.prizedetail;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.User;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
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

        ActivityWinPrize getActivityWinPrize();

        ActivityProduct getActivityProduct();

        User getUser();

        void navigateToPrizeConfirm();

        void backToMemberHome();
    }

    PrizeDetailBottomFragmentListener listener;

    FragmentPrizedetailBottomBinding binding;

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
        if (listener.getActivityWinPrize() != null) {
            binding.text.setVisibility(View.VISIBLE);
            binding.confirmOrder.setText("立即前往");
        } else if (listener.getActivityProduct().getSortType() == Constant.ActivitySortType.EXCHANGE) {
            binding.text.setVisibility(View.GONE);
            binding.confirmOrder.setText("立即兑换");
        } else if (listener.getActivityProduct().getSortType() == Constant.ActivitySortType.LOTTERY) {
            binding.text.setVisibility(View.GONE);
            binding.confirmOrder.setText("立即参与");
        }
        if (listener.getActivityWinPrize() == null)
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
        if (listener.getActivityWinPrize() == null && listener.getActivityProduct().getSortType() == Constant.ActivitySortType.LOTTERY)
            if (listener.getActivityProduct().getStatistics().getMaxUserRemain() <= 0 && listener.getActivityProduct().getConfigs().getMaxUsers() > 0) {
                binding.confirmOrder.setBackgroundColor(0xffdcdcdc);
                binding.confirmOrder.setTextColor(Color.WHITE);
                binding.confirmOrder.setText("参与人数已满");
                binding.confirmOrder.setEnabled(false);
            }
        if (listener.getActivityWinPrize() == null && listener.getActivityProduct().getSortType() == Constant.ActivitySortType.EXCHANGE)
            if (!EmptyUtils.isEmpty(listener.getActivityProduct().getStatistics().getRemain()) && listener.getActivityProduct().getStatistics().getRemain() <= 0) {
                binding.confirmOrder.setBackgroundColor(0xffdcdcdc);
                binding.confirmOrder.setTextColor(Color.WHITE);
                binding.confirmOrder.setText("抢光了");
                binding.confirmOrder.setEnabled(false);
            }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.confirmOrder)
            if (listener.getActivityWinPrize() != null) {
                listener.backToMemberHome();
            } else {
                listener.navigateToPrizeConfirm();
            }

    }


}
