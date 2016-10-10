package com.j1j2.pifalao.feature.home.memberhome;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.databinding.FragmentMemberhomeFreeBinding;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailActivity;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alienzxh on 16-8-24.
 */
public class MemberHomeFreeFragment extends LazyFragment implements MemberHomeFreeAdapter.MemberHomeFreeAdapterListener {

    public interface MemberHomeFreeFragmentListener {
        void navigateToPrizeDetail(int activityProductId, ActivityProduct activityProduct);

        List<ActivityProduct> getFreeActivityProducts();
    }

    MemberHomeFreeFragmentListener listener;

    FragmentMemberhomeFreeBinding binding;

    MemberHomeFreeAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MemberHomeFreeFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "MemberHomeFreeFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memberhome_free, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.freeList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.freeList.setAdapter(adapter = new MemberHomeFreeAdapter(listener.getFreeActivityProducts()));
        adapter.setListener(this);
    }

    public void refreshList() {
        adapter.initData(listener.getFreeActivityProducts());
    }


    @Override
    public void navigateToPrizeDetail(ActivityProduct activityProduct) {
        listener.navigateToPrizeDetail(activityProduct.getProductId(), activityProduct);
    }

    @Override
    public void navigateToPrizeConfirm(ActivityProduct activityProduct) {
        listener.navigateToPrizeDetail(activityProduct.getProductId(), activityProduct);
    }
}
