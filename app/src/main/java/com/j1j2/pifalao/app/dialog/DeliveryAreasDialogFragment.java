package com.j1j2.pifalao.app.dialog;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.databinding.ViewDeliveryAreasBinding;
import com.j1j2.pifalao.feature.home.deliveryhome.DeliveryAreasAdapter;

import java.util.Arrays;

/**
 * Created by alienzxh on 16-10-18.
 */

public class DeliveryAreasDialogFragment extends BaseDialogFragment {

    public interface DeliveryAreasDialogFragmentListener {
        String[] getServiceAreas();
    }

    DeliveryAreasDialogFragmentListener listener;
    ViewDeliveryAreasBinding binding;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (DeliveryAreasDialogFragmentListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.view_delivery_areas, null, false);

        binding.areasList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.areasList.setAdapter(new DeliveryAreasAdapter(Arrays.asList(listener.getServiceAreas() != null ? listener.getServiceAreas(): new String[]{})));

        binding.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected AlertDialog.Builder getDialogBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());
        return builder;
    }
}
