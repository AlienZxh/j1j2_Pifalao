package com.j1j2.pifalao.feature.ordermanager.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualCenterModule;
import com.j1j2.pifalao.feature.ordermanager.OrderManagerActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-22.
 */
@ActivityScope
@Subcomponent(modules = {OrderManagerModule.class})
public interface OrderManagerComponent {
    void inject(OrderManagerActivity orderManagerActivity);
}
