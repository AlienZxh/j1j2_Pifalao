package com.j1j2.pifalao.feature.qrcode;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.j1j2.common.util.QRCodeUtils;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityQrcodeBinding;
import com.j1j2.pifalao.feature.qrcode.di.QRCodeModule;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-22.
 */
@RequireBundler
public class QrCodeActivity extends BaseActivity implements View.OnClickListener {

    ActivityQrcodeBinding binding;

    @Inject
    QrCodeViewModel qrCodeViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qrcode);
        binding.setQrCodeViewModel(qrCodeViewModel);
    }

    @Override
    protected void initViews() {
        initWindows();
        qrCodeViewModel.queryQRCode();
    }

    private void initWindows() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        WindowManager.LayoutParams windowLp = window.getAttributes();
        windowLp.screenBrightness = 1.0f;
        window.setAttributes(windowLp);
    }

    public void initQRCode(final String qrStr) {
        binding.qrcodeImg.post(new Runnable() {
            @Override
            public void run() {
                toastor.showSingletonToast("特权码已刷新");
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                Bitmap qr = QRCodeUtils.createQRCodeWithLogo(qrStr, binding.qrcodeImg.getHeight(), bitmap);
                binding.qrcodeImg.setImageBitmap(qr);
            }
        });
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new QRCodeModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.refresh)
            qrCodeViewModel.queryQRCode();
    }
}
