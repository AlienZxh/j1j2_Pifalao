package com.j1j2.pifalao.app.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.j1j2.common.util.QRCodeUtils;
import com.j1j2.common.util.Toastor;
import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.HasComponent;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.ViewQrcodeBinding;
import com.j1j2.pifalao.feature.services.di.ServicesComponent;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-10-18.
 */
@RequireBundler
public class QRDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    public interface QRDialogFragmentListener extends HasComponent<ServicesComponent> {

    }

    QRDialogFragmentListener listener;
    ViewQrcodeBinding binding;
    Window window;
    Subscription subscription;

    float rememberScreenBrightness = 0.0f;

    Bitmap appBitmap;
    Bitmap qrBitMap;

    @Inject
    UserVipApi userVipApi;
    @Inject
    Toastor toastor;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (QRDialogFragmentListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener.getComponent().inject(this);

        window = getActivity().getWindow();
        WindowManager.LayoutParams windowLp = window.getAttributes();
        rememberScreenBrightness = windowLp.screenBrightness;

        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        windowLp.screenBrightness = 1.0f;
        window.setAttributes(windowLp);

        appBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.view_qrcode, null, false);
        binding.dialogClose.setOnClickListener(this);
        binding.retryBtn.setOnClickListener(this);
    }

    @Override
    protected AlertDialog.Builder getDialogBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());
        return builder;
    }


    @Override
    public void onStart() {
        super.onStart();
        queryQRCode();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        WindowManager.LayoutParams windowLp = window.getAttributes();
        windowLp.screenBrightness = rememberScreenBrightness;
        window.setAttributes(windowLp);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.dialogClose) {
            dismiss();
        }

        if (v == binding.retryBtn)
            queryQRCode();
    }

    public void queryQRCode() {
        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();
        subscription = Observable
                .interval(0, 60, TimeUnit.SECONDS)
                .compose(this.<Long>bindUntilEvent(FragmentEvent.STOP))
                .flatMap(new Func1<Long, Observable<WebReturn<String>>>() {
                    @Override
                    public Observable<WebReturn<String>> call(Long aLong) {
                        return userVipApi.queryLoginDimensionalCode();
                    }
                })
                .compose(this.<WebReturn<String>>bindUntilEvent(FragmentEvent.STOP))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        toastor.showSingletonToast("特权码已刷新");
                        qrBitMap = QRCodeUtils.createQRCodeWithLogo(s, binding.qrcodeImg.getHeight(), appBitmap);
                        binding.qrcodeImg.setImageBitmap(qrBitMap);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {
                    }
                });

    }


}
