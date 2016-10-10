package com.j1j2.pifalao.feature.home.memberhome;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.databinding.FragmentMemberhomePrizeBinding;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alienzxh on 16-8-24.
 */
public class MemberHomePrizeFragment extends LazyFragment implements View.OnClickListener, MemberHomePrizeAdapter.MemberHomePrizeAdapterListener {

    public interface MemberHomePrizeFragmentListener {
        void navigateToPrize();

        void navigateToPrizeDetail(int activityProductId, ActivityWinPrize activityWinPrize);

        List<ActivityWinPrize> getActivityWinPrizes();
    }

    private MemberHomePrizeFragmentListener listener;

    FragmentMemberhomePrizeBinding binding;

    MemberHomePrizeAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MemberHomePrizeFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "MemberHomePrizeFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memberhome_prize, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.prizeBtn.setOnClickListener(this);
        //__________________________________________________________
        binding.prizeList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.prizeList.addItemDecoration(new VerticalDividerItemDecoration
                .Builder(getContext())
                .color(0xffe0e0e0)
                .margin(AutoUtils.getPercentWidthSize(20))
                .size(AutoUtils.getPercentWidthSize(2))
                .build());
        binding.prizeList.setAdapter(adapter = new MemberHomePrizeAdapter(listener.getActivityWinPrizes()));
        adapter.setListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.prizeBtn) if (listener != null) listener.navigateToPrize();
    }

    @Override
    public void navigateToPrizeDetail(ActivityWinPrize activityWinPrize) {
        listener.navigateToPrizeDetail(activityWinPrize.getProductInfo().getProductId(),activityWinPrize);
    }
}
