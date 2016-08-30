package com.j1j2.pifalao.feature.home.memberhome;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.databinding.FragmentMemberhomeBannerBinding;

/**
 * Created by alienzxh on 16-8-24.
 */
public class MemberHomeBannerFragment extends LazyFragment implements View.OnClickListener {

    public interface MemberHomeBannerFragmentListener {
        void navigateToFree();
    }

    MemberHomeBannerFragmentListener listener;

    FragmentMemberhomeBannerBinding binding;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MemberHomeActivity) activity;
    }

    @Override
    protected String getFragmentName() {
        return "MemberHomeBannerFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memberhome_banner, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.free.setOnClickListener(this);
        binding.luckyorder.setOnClickListener(this);
        binding.prize.setOnClickListener(this);
        binding.pbalance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.free) {
            listener.navigateToFree();
        }
        if (v == binding.luckyorder) {

        }
        if (v == binding.prize) {

        }
        if (v == binding.pbalance) {

        }
    }
}
