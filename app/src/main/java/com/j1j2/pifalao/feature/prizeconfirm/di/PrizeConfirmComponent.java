package com.j1j2.pifalao.feature.prizeconfirm.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.prizeconfirm.PrizeConfirmActivity;
import com.j1j2.pifalao.feature.prizeconfirm.PrizeConfirmLotteryActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-9-8.
 */
@ActivityScope
@Subcomponent(modules = {PrizeConfirmModule.class})
public interface PrizeConfirmComponent {
    void inject(PrizeConfirmActivity confirmActivity);

    void inject(PrizeConfirmLotteryActivity confirmActivity);
}
