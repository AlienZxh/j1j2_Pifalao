package com.j1j2.pifalao.feature.home.memberhome;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.databinding.FragmentMemberhomeLuckyBinding;

/**
 * Created by alienzxh on 16-8-24.
 */
public class MemberHomeLuckyFragment extends LazyFragment {
    FragmentMemberhomeLuckyBinding binding;

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
        binding.tab.addTab(binding.tab.newTab().setText("全部"));
        binding.tab.addTab(binding.tab.newTab().setText("免费"));
        binding.tab.addTab(binding.tab.newTab().setText("１元"));
        binding.tab.addTab(binding.tab.newTab().setText("进度"));
    }
}
