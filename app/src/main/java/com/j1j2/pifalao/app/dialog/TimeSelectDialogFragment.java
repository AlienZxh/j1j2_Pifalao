package com.j1j2.pifalao.app.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.databinding.ViewDeliverytimePickerBinding;

import java.util.List;

import static com.umeng.socialize.Config.dialog;

/**
 * Created by alienzxh on 16-10-18.
 */

public class TimeSelectDialogFragment extends BaseBottomDialogFragment implements View.OnClickListener {

    public interface TimeSelectDialogFragmentListener {
        List<String> getTimeItems();

        int getSelectIndex();

        void setSelectIndex(int index);

        void setSelectTime(String item);
    }

    TimeSelectDialogFragmentListener listener;
    ViewDeliverytimePickerBinding binding;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (TimeSelectDialogFragmentListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.view_deliverytime_picker, null, false);

        binding.timeickerCancel.setOnClickListener(this);
        binding.timeickerConFirm.setOnClickListener(this);

        binding.timePicker.setItems(listener.getTimeItems());
        binding.timePicker.setClipToPadding(false);
        binding.timePicker.setSeletion(listener.getSelectIndex());

    }

    @Override
    protected AlertDialog.Builder getDialogBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.BottomDialog);
        builder.setView(binding.getRoot());
        return builder;
    }

    @Override
    public void onClick(View v) {
        if (v == binding.timeickerCancel)
            dismiss();
        if (v == binding.timeickerConFirm) {
            listener.setSelectTime(binding.timePicker.getSeletedItem());
            listener.setSelectIndex(binding.timePicker.getSeletedIndex());
            dismiss();
        }
    }

}
