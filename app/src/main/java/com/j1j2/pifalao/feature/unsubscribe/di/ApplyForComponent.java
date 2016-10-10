package com.j1j2.pifalao.feature.unsubscribe.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.unsubscribe.ModulePermissionDeniedActivity;
import com.j1j2.pifalao.feature.unsubscribe.UnsubscribeModuleActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-9-25.
 */
@ActivityScope
@Subcomponent(modules = {ApplyForModule.class})
public interface ApplyForComponent {
    void inject(UnsubscribeModuleActivity unsubscribeDeliveryActivity);

    void inject(ModulePermissionDeniedActivity modulePermissionDeniedActivity);
}
