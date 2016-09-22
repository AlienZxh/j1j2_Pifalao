package com.j1j2.pifalao.feature.home.memberhome;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.scrollablelayout.ScrollableHelper;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.databinding.FragmentMemberhomeLuckyBinding;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-8-24.
 */
@RequireBundler
public class MemberHomeLuckyFragment extends LazyFragment implements ScrollableHelper.ScrollableContainer, MemberHomeLuckyAdapter.MemberHomeLuckyAdapterListener {


    public interface MemberHomeLuckyFragmentListener {
        void navigateToPrizeDetail(int activityType, ActivityProduct activityProduct);

        List<ActivityProduct> getActivityProducts(String key);
    }

    private MemberHomeLuckyFragmentListener listener;

    FragmentMemberhomeLuckyBinding binding;

    MemberHomeLuckyAdapter adapter;

    @Arg
    String key;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MemberHomeLuckyFragmentListener) activity;
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Override
    protected String getFragmentName() {
        return "MemberHomeLuckyFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memberhome_lucky, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.idStickynavlayoutInnerscrollview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.idStickynavlayoutInnerscrollview.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getContext())
                .color(0x00ffffff)
                .size(AutoUtils.getPercentHeightSize(20))
                .build());
        binding.idStickynavlayoutInnerscrollview.setAdapter(adapter = new MemberHomeLuckyAdapter(listener.getActivityProducts(key)));
        adapter.setListener(this);
    }

    @Override
    public View getScrollableView() {
        return binding.idStickynavlayoutInnerscrollview;
    }

    @Override
    public void navigateToPrizeDetail(ActivityProduct activityProduct) {
        listener.navigateToPrizeDetail(PrizeDetailActivity.PRIZE_ONGOING, activityProduct);
    }


}
