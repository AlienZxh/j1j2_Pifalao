package com.j1j2.pifalao.feature.prizedetail;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.ProductImg;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.databinding.FragmentPrizedetailTopBinding;
import com.j1j2.pifalao.feature.productdetail.ProductImgCycleAdapter;

import java.util.List;

/**
 * Created by alienzxh on 16-9-1.
 */
public class PrizeDetailTopFragment extends LazyFragment {

    public interface PrizeDetailTopFragmentListener {
        ActivityProduct getActivityProduct();

        ActivityWinPrize getActivityWinPrize();

        int getActivityType();
    }

    FragmentPrizedetailTopBinding binding;

    PrizeDetailTopFragmentListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (PrizeDetailTopFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "PrizeDetailTopFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prizedetail_top, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        initBanner();
        if (listener.getActivityType() == PrizeDetailActivity.GIFT) {
            binding.tag.setVisibility(View.GONE);
            binding.name.setText(listener.getActivityProduct().getName());
            binding.viewPager.setAdapter(new PrizeImgCycleAdapter(listener.getActivityProduct().getImgList()));
            binding.viewPager.startAutoScroll(2000);
            binding.viewPager.setInterval(2000);
            binding.tab.setViewPager(binding.viewPager);
        } else if (listener.getActivityType() == PrizeDetailActivity.PRIZE_ONGOING) {
            binding.tag.setText("进行中");
            binding.name.setText(listener.getActivityProduct().getName());
            binding.viewPager.setAdapter(new PrizeImgCycleAdapter(listener.getActivityProduct().getImgList()));
            binding.viewPager.startAutoScroll(2000);
            binding.viewPager.setInterval(2000);
            binding.tab.setViewPager(binding.viewPager);
        } else if (listener.getActivityType() == PrizeDetailActivity.PRIZE_COMPLETED) {
            binding.tag.setText("已揭晓");
            binding.name.setText(listener.getActivityWinPrize().getProductInfo().getProductName());
            binding.viewPager.setAdapter(new PrizeImgCycleAdapter(listener.getActivityWinPrize().getProductInfo().getImgs()));
            binding.viewPager.startAutoScroll(2000);
            binding.viewPager.setInterval(2000);
            binding.tab.setViewPager(binding.viewPager);
        }

    }

    public void initBanner() {
        if (listener.getActivityType() == PrizeDetailActivity.GIFT) {
            binding.viewPager.setAdapter(new PrizeImgCycleAdapter(listener.getActivityProduct().getImgList()));
        } else if (listener.getActivityType() == PrizeDetailActivity.PRIZE_ONGOING) {
            binding.viewPager.setAdapter(new PrizeImgCycleAdapter(listener.getActivityProduct().getImgList()));
        } else if (listener.getActivityType() == PrizeDetailActivity.PRIZE_COMPLETED) {
            binding.viewPager.setAdapter(new PrizeImgCycleAdapter(listener.getActivityWinPrize().getProductInfo().getImgs()));
        }

        binding.viewPager.startAutoScroll(2000);
        binding.viewPager.setInterval(2000);
        binding.tab.setViewPager(binding.viewPager);
    }
}
