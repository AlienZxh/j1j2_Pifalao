package com.j1j2.pifalao.app.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import com.j1j2.pifalao.app.dialog.impl.IDialogCancelListener;
import com.j1j2.pifalao.app.dialog.impl.IDialogDismissListener;
import com.trello.rxlifecycle.components.support.RxDialogFragment;

/**
 * Created by alienzxh on 16-10-17.
 */

public abstract class BaseDialogFragment extends RxDialogFragment {


    protected String generateFragmentTag() {
        return getClass().getSimpleName();
    }

    protected abstract AlertDialog.Builder getDialogBuilder();

    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = getDialogBuilder();
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (getDialogListener(IDialogDismissListener.class) != null)
                    getDialogListener(IDialogDismissListener.class).onDismiss(generateFragmentTag());
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (getDialogListener(IDialogCancelListener.class) != null)
                    getDialogListener(IDialogCancelListener.class).onCancel(generateFragmentTag());
            }
        });
        return builder.create();
    }


    /**
     * 为获取接口类型定义的一个辅助方法 简化每次都要强转的麻烦
     *
     * @param listenerInterface
     * @param <T>
     * @return
     */
    protected <T> T getDialogListener(Class<T> listenerInterface) {
        //用targetFragment是否为空来标识是fragment还是activity开启的这个DialogFragment
        final Fragment targetFragment = getTargetFragment();
        if (targetFragment != null && listenerInterface.isAssignableFrom(targetFragment.getClass())) {
            return (T) targetFragment;
        }
        if (getActivity() != null && listenerInterface.isAssignableFrom(getActivity().getClass())) {
            return ((T) getActivity());
        }
        return null;
    }

}
