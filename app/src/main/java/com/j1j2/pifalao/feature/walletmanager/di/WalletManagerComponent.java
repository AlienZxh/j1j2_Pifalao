package com.j1j2.pifalao.feature.walletmanager.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.walletmanager.WalletManagerActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-23.
 */
@ActivityScope
@Subcomponent(modules = {WalletManagerModule.class})
public interface WalletManagerComponent {

    void inject(WalletManagerActivity walletManagerActivity);

}
