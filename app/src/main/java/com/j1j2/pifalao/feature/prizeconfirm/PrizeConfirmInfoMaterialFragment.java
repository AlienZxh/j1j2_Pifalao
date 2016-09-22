package com.j1j2.pifalao.feature.prizeconfirm;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.Address;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.event.UserAddressSelectEvent;
import com.j1j2.pifalao.databinding.FragmentPrizeconfirmInfoMaterialBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by alienzxh on 16-9-2.
 */
public class PrizeConfirmInfoMaterialFragment extends BaseFragment implements View.OnClickListener {

    public interface PrizeConfirmInfoMaterialFragmentListener {
        void navigateToAddressManager();
    }

    FragmentPrizeconfirmInfoMaterialBinding binding;

    PrizeConfirmInfoMaterialFragmentListener listener;

    Address address;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (PrizeConfirmInfoMaterialFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "PrizeConfirmInfoMaterialFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prizeconfirm_info_material, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.addressBtn.setOnClickListener(this);
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public void onClick(View v) {
        if (v == binding.addressBtn)
            listener.navigateToAddressManager();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onUserAddressSelectEvent(UserAddressSelectEvent event) {
        this.address = event.getAddress();
        binding.addressIcon.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        binding.addressText.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        binding.addressText.setText(event.getAddress().getReceiverName()
                + "　　" + event.getAddress().getReceiverTel()
                + "\n" + event.getAddress().getAddress());
    }
}
