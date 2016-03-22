package com.j1j2.pifalao.feature.individualcenter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.User;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentIndividualcenterBinding;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualCenterModule;
import com.j1j2.pifalao.feature.main.MainActivity;
import com.j1j2.pifalao.feature.main.di.MainComponent;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-18.
 */
@RequireBundler
public class IndividualCenterFragment extends BaseFragment implements View.OnClickListener {

    public interface IndividualCenterFragmentListener {
        void navigateToOrderManager();

        void navigateToQRCode();
    }

    private IndividualCenterFragmentListener listener;

    FragmentIndividualcenterBinding binding;

    @Inject
    User user;

    @Inject
    IndividualCenterViewModel individualCenterViewModel;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MainActivity) activity;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_individualcenter, container, false);
        binding.setIndividualCenterViewModel(individualCenterViewModel);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(getActivity()).getUserComponent().plus(new IndividualCenterModule(this)).inject(this);
    }


    @Override
    public void onClick(View v) {
        if (v == binding.orderManager) {
            listener.navigateToOrderManager();
        }
        if (v == binding.qrCode) {
            listener.navigateToQRCode();
        }
    }
}
