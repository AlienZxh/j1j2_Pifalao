package com.j1j2.pifalao.feature.individualcenter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.j1j2.data.model.User;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.event.VipUpdateSuccessEvent;
import com.j1j2.pifalao.databinding.FragmentIndividualcenterBinding;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualCenterModule;
import com.j1j2.pifalao.feature.main.MainActivity;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-18.
 */
@RequireBundler
public class IndividualCenterFragment extends BaseFragment implements View.OnClickListener {
    public static final int FROM_INDIVIDUALCENTERACTIVITY = 0;

    public static final int FROM_MAINACTIVITY = 1;

    public interface IndividualCenterFragmentListener {
        void navigateToOrderManager();

        void navigateToQRCode();

        void navigateToAddressManager();

        void navigateToWalletManager();

        void navigateToMessages();

        void navigateToCollects();

        void navigateToAccount();

        void navigateToVipUpdate();

        void navigateToSetting();
    }

    private IndividualCenterFragmentListener listener;

    FragmentIndividualcenterBinding binding;

    @Inject
    User user;

    @Inject
    IndividualCenterViewModel individualCenterViewModel;

    @Arg
    int fragmentType;

    @Inject
    UnReadInfoManager unReadInfoManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (IndividualCenterFragmentListener) activity;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_individualcenter, container, false);
        binding.setIndividualCenterViewModel(individualCenterViewModel);
        binding.setUnReadInfoManager(unReadInfoManager);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        if (fragmentType != FROM_INDIVIDUALCENTERACTIVITY)
            binding.backBtn.setVisibility(View.GONE);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(getActivity()).getUserComponent().plus(new IndividualCenterModule(this)).inject(this);
    }

    @Subscribe
    public void onVipUpdateSuccessEvent(VipUpdateSuccessEvent event) {
        individualCenterViewModel.refreshUser(user);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.orderManager) {
            listener.navigateToOrderManager();
        }
        if (v == binding.qrCode) {
            listener.navigateToQRCode();
        }
        if (v == binding.adderssManager) {
            listener.navigateToAddressManager();
        }
        if (v == binding.walletManager) {
            listener.navigateToWalletManager();
        }
        if (v == binding.massageManager) {
            listener.navigateToMessages();
        }
        if (v == binding.collectManager) {
            listener.navigateToCollects();
        }
        if (v == binding.accountManager) {
            listener.navigateToAccount();
        }
        if (v == binding.updateVIP) {
            listener.navigateToVipUpdate();
        }
        if (v == binding.setting) {
            listener.navigateToSetting();
        }
        if (v == binding.backBtn)
            getActivity().onBackPressed();
    }
}
