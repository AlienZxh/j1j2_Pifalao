package com.j1j2.pifalao.feature.prizeconfirm;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.util.ValidateUtils;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentPrizeconfirmInfoPhoneBinding;

/**
 * Created by alienzxh on 16-9-2.
 */
public class PrizeConfirmInfoPhoneFragment extends BaseFragment implements TextWatcher {

    FragmentPrizeconfirmInfoPhoneBinding binding;

    @Override
    protected String getFragmentName() {
        return "PrizeConfirmInfoPhoneFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prizeconfirm_info_phone, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.phoneText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (ValidateUtils.isMobileNO(s.toString())) {
            binding.phoneIcon.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            binding.phoneText.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        } else {
            binding.phoneIcon.setTextColor(getActivity().getResources().getColor(R.color.colorRed));
            binding.phoneText.setTextColor(getActivity().getResources().getColor(R.color.colorRed));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public String getPhoneNO() {
        return binding.phoneText.getText().toString();
    }
}
