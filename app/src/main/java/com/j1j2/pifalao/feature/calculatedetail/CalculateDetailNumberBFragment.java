package com.j1j2.pifalao.feature.calculatedetail;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.databinding.FragmentCalculatedetailNumberbBinding;

/**
 * Created by alienzxh on 16-9-6.
 */
public class CalculateDetailNumberBFragment extends LazyFragment {


    public interface CalculateDetailNumberBFragmentListener {
        int getB();
    }

    CalculateDetailNumberBFragmentListener listener;

    FragmentCalculatedetailNumberbBinding binding;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (CalculateDetailNumberBFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "CalculateDetailNumberBFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculatedetail_numberb, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setNumB(listener.getB());
    }
}
