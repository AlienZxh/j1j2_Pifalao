package com.j1j2.pifalao.feature.qrcode.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.products.di.ProductsModule;
import com.j1j2.pifalao.feature.qrcode.QrCodeActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-22.
 */
@ActivityScope
@Subcomponent(modules = {QRCodeModule.class})
public interface QRCodeComponent {
    void inject(QrCodeActivity qrCodeActivity);
}
