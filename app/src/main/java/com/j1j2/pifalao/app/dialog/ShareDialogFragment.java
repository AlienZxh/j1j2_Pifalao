package com.j1j2.pifalao.app.dialog;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.databinding.ViewShareboardBinding;

/**
 * Created by alienzxh on 16-10-18.
 */

public class ShareDialogFragment extends BaseBottomDialogFragment implements View.OnClickListener {

    public interface ShareDialogFragmentListener {

        void onWeixinShareBtnClick();

        void onWeixinFriendShareBtnClick();

        void onSinaShareBtnClick();

        void onQQShareBtnClick();

        void onQQZoneShareBtnClick();

        void onCopyShareBtnClick();
    }

    ShareDialogFragmentListener listener;
    ViewShareboardBinding binding;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ShareDialogFragmentListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.view_shareboard, null, false);

        binding.cancelShareBtn.setOnClickListener(this);
        binding.weixinShareBtn.setOnClickListener(this);
        binding.weixinFriendShareBtn.setOnClickListener(this);
        binding.sinaShareBtn.setOnClickListener(this);
        binding.qqShareBtn.setOnClickListener(this);
        binding.qqZoneShareBtn.setOnClickListener(this);
        binding.copyShareBtn.setOnClickListener(this);
    }

    @Override
    protected AlertDialog.Builder getDialogBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.BottomDialog);
        builder.setView(binding.getRoot());
        return builder;
    }

    @Override
    public void onClick(View v) {
        if (v == binding.copyShareBtn)
            listener.onCopyShareBtnClick();
        if (v == binding.qqZoneShareBtn)
            listener.onQQZoneShareBtnClick();
        if (v == binding.qqShareBtn)
            listener.onQQShareBtnClick();
        if (v == binding.sinaShareBtn)
            listener.onSinaShareBtnClick();
        if (v == binding.weixinFriendShareBtn)
            listener.onWeixinFriendShareBtnClick();
        if (v == binding.weixinShareBtn)
            listener.onWeixinShareBtnClick();
        dismiss();
    }
}
