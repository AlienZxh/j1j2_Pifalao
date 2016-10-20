package com.j1j2.pifalao.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.j1j2.pifalao.app.dialog.MessageDialogFragmentBundler;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.lang.reflect.Field;

/**
 * Created by alienzxh on 16-8-24.
 */
public class BaseLazyFragment extends RxFragment {
    protected LayoutInflater inflater;
    private View contentView;
    private Context context;
    private ViewGroup container;
    protected DialogFragment messageDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);
        if (contentView == null)
            return super.onCreateView(inflater, container, savedInstanceState);
        return contentView;
    }

    protected void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
        container = null;
        inflater = null;
    }

    public Context getApplicationContext() {
        return context;
    }

//    public void setContentView(int layoutResID) {
//        setContentView((ViewGroup) inflater.inflate(layoutResID, container, false));
//    }

    public void setContentView(View view) {
        contentView = view;
    }

    public View getContentView() {
        return contentView;
    }

    public View findViewById(int id) {
        if (contentView != null)
            return contentView.findViewById(id);
        return null;
    }

    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public void showMessageDialogDuplicate(boolean cancelable, String fragmentTag, String title, String message, String negativeButtonText, String positiveButtonText) {
        messageDialog = MessageDialogFragmentBundler.build().fragmentTag(fragmentTag)
                .cancelable(cancelable)
                .title(title)
                .message(message)
                .negativeButtonText(negativeButtonText)
                .positiveButtonText(positiveButtonText)
                .create();
        messageDialog.show(getChildFragmentManager(), fragmentTag);
    }

    public void showMessageDialogNotDuplicate(boolean cancelable, String fragmentTag, String title, String message, String negativeButtonText, String positiveButtonText) {
        dismissMessageDialog();
        messageDialog = MessageDialogFragmentBundler.build().fragmentTag(fragmentTag)
                .cancelable(cancelable)
                .title(title)
                .message(message)
                .negativeButtonText(negativeButtonText)
                .positiveButtonText(positiveButtonText)
                .create();
        messageDialog.show(getChildFragmentManager(), fragmentTag);
    }

    public void dismissMessageDialog() {
        if (isMessageDialogShowing())
            messageDialog.dismiss();
    }

    public boolean isMessageDialogShowing() {
        if (messageDialog != null && messageDialog.isVisible())
            return true;
        else
            return false;
    }


}
