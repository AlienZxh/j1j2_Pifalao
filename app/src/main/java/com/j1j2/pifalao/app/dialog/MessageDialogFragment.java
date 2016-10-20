package com.j1j2.pifalao.app.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.j1j2.pifalao.app.dialog.impl.IDialogNegativeButtonClickListener;
import com.j1j2.pifalao.app.dialog.impl.IDialogPositiveButtonClickListener;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;

/**
 * Created by alienzxh on 16-10-16.
 */
@RequireBundler
public class MessageDialogFragment extends BaseDialogFragment implements DialogInterface.OnClickListener {

    @Arg
    @Required(false)
    String fragmentTag;
    @Arg
    @Required(false)
    String title;
    @Arg
    @Required(false)
    String message;
    @Arg
    @Required(false)
    String negativeButtonText;
    @Arg
    @Required(false)
    String positiveButtonText;
    @Arg
    @Required(false)
    boolean cancelable;


    @Override
    protected String generateFragmentTag() {
        if (TextUtils.isEmpty(fragmentTag))
            return super.generateFragmentTag();
        else
            return fragmentTag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundler.inject(this);

        setCancelable(cancelable);
    }

    @Override
    protected AlertDialog.Builder getDialogBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (title != null)
            builder.setTitle(title);
        if (message != null)
            builder.setMessage(message);
        if (negativeButtonText != null)
            builder.setNegativeButton(negativeButtonText, this);
        if (positiveButtonText != null)
            builder.setPositiveButton(positiveButtonText, this);
        return builder;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_NEGATIVE)
            if (getDialogListener(IDialogNegativeButtonClickListener.class) != null)
                getDialogListener(IDialogNegativeButtonClickListener.class).onDialogNegativeClick(generateFragmentTag());
        if (which == DialogInterface.BUTTON_POSITIVE)
            if (getDialogListener(IDialogPositiveButtonClickListener.class) != null)
                getDialogListener(IDialogPositiveButtonClickListener.class).onDialogPositiveClick(generateFragmentTag());
    }
}
