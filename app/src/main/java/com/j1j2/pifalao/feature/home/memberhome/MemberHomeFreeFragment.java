package com.j1j2.pifalao.feature.home.memberhome;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.databinding.FragmentMemberhomeFreeBinding;

/**
 * Created by alienzxh on 16-8-24.
 */
public class MemberHomeFreeFragment extends LazyFragment {

    FragmentMemberhomeFreeBinding binding;


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

    }
}
