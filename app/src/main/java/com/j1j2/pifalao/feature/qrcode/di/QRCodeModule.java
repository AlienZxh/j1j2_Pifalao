package com.j1j2.pifalao.feature.qrcode.di;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.qrcode.QrCodeActivity;
import com.j1j2.pifalao.feature.qrcode.QrCodeViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-22.
 */
@Module
public class QRCodeModule {
    private QrCodeActivity qrCodeActivity;

    public QRCodeModule(QrCodeActivity qrCodeActivity) {
        this.qrCodeActivity = qrCodeActivity;
    }

    @Provides
    @ActivityScope
    UserVipApi userVipApi(Retrofit retrofit) {
        return retrofit.create(UserVipApi.class);
    }

    @Provides
    @ActivityScope
    QrCodeActivity qrCodeActivity() {
        return qrCodeActivity;
    }

    @Provides
    @ActivityScope
    QrCodeViewModel qrCodeViewModel(QrCodeActivity qrCodeActivity, UserVipApi userVipApi) {
        return new QrCodeViewModel(qrCodeActivity, userVipApi);
    }
}
