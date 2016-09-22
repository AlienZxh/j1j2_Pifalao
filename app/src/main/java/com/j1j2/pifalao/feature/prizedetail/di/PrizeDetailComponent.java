package com.j1j2.pifalao.feature.prizedetail.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.calculatedetail.CalculateDetailActivity;
import com.j1j2.pifalao.feature.participationrecord.ParticipationRecordActivity;
import com.j1j2.pifalao.feature.prize.PrizeActivity;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailActivity;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailCompleteFragment;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailRecordFragment;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailSelectFragment;
import com.j1j2.pifalao.feature.prizeordertimeline.PrizeOrderTimelineActivity;
import com.j1j2.pifalao.feature.prizerecord.PrizeRecordActivity;
import com.j1j2.pifalao.feature.showorder.ShowOrderActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-9-8.
 */
@ActivityScope
@Subcomponent(modules = {PrizeDetailModule.class})
public interface PrizeDetailComponent {
    void inject(PrizeDetailActivity prizeDetailActivity);

    void inject(CalculateDetailActivity calculateDetailActivity);

    void inject(PrizeDetailRecordFragment prizeDetailRecordFragment);

    void inject(PrizeDetailSelectFragment prizeDetailSelectFragment);

    void inject(PrizeDetailCompleteFragment prizeDetailCompleteFragment);

    void inject(PrizeActivity prizeActivity);

    void inject(PrizeOrderTimelineActivity prizeOrderTimelineActivity);

    void inject(ShowOrderActivity showOrderActivity);

    void inject(PrizeRecordActivity prizeRecordActivity);

    void inject(ParticipationRecordActivity participationRecordActivity);
}
