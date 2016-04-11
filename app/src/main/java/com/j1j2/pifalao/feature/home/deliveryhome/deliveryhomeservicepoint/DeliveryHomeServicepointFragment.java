package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentDeliveryhomeServicepointBinding;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.di.DeliveryServicepointModule;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-30.
 */
@RequireBundler
public class DeliveryHomeServicepointFragment extends BaseFragment {
    FragmentDeliveryhomeServicepointBinding binding;

    @Arg
    ServicePoint servicePoint;

    @Inject
    DeliveryServicepointViewModel deliveryServicepointViewModel;

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deliveryhome_servicepoint, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setDeliveryServicepointViewModel(deliveryServicepointViewModel);
        deliveryServicepointViewModel.queryModule();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(getContext()).getAppComponent().plus(new DeliveryServicepointModule(this, servicePoint)).inject(this);
    }
}
