package com.j1j2.pifalao.feature.calculatedetail;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.databinding.FragmentCalculatedetailResultBinding;

/**
 * Created by alienzxh on 16-9-6.
 */
public class CalculateDetailResultFragment extends LazyFragment {

    public interface CalculateDetailResultFragmentListener {
        String getLuckTicketNum();
    }

    CalculateDetailResultFragmentListener listener;

    FragmentCalculatedetailResultBinding binding;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (CalculateDetailResultFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "CalculateDetailResultFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculatedetail_result, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setLuckTicketNum(listener.getLuckTicketNum());
    }
}
