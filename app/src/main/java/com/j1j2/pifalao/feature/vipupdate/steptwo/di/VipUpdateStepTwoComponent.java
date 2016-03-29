package com.j1j2.pifalao.feature.vipupdate.steptwo.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.vipupdate.steptwo.VipUpdateStepTwoActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-28.
 */
@ActivityScope
@Subcomponent(modules = {VipUpdateStepTwoModule.class})
public interface VipUpdateStepTwoComponent {
    void inject(VipUpdateStepTwoActivity vipUpdateStepTwoActivity);
}
