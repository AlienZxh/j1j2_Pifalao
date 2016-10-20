package com.j1j2.pifalao.feature.services.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.app.dialog.QRDialogFragment;
import com.j1j2.pifalao.feature.services.ServicesActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-12.
 */
@ActivityScope
@Subcomponent(modules = {ServicesModule.class})
public interface ServicesComponent {
    void inject(QRDialogFragment qrDialogFragment);

    void inject(ServicesActivity servicesActivity);
}
