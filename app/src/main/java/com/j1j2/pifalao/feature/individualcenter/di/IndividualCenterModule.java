package com.j1j2.pifalao.feature.individualcenter.di;

import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.User;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.individualcenter.IndividualCenterFragment;
import com.j1j2.pifalao.feature.individualcenter.IndividualCenterViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-21.
 */
@Module
public class IndividualCenterModule {

    private IndividualCenterFragment individualCenterFragment;

    public IndividualCenterModule(IndividualCenterFragment individualCenterFragment) {
        this.individualCenterFragment = individualCenterFragment;
    }

    @Provides
    @ActivityScope
    UserLoginApi userLoginApi(Retrofit retrofit) {
        return retrofit.create(UserLoginApi.class);
    }

    @Provides
    @ActivityScope
    UserCouponApi userCouponApi(Retrofit retrofit) {
        return retrofit.create(UserCouponApi.class);
    }

    @Provides
    @ActivityScope
    IndividualCenterViewModel individualCenterViewModel(User user, IndividualCenterFragment individualCenterFragment, UserLoginApi userLoginApi, UserCouponApi userCouponApi) {
        return new IndividualCenterViewModel(user, individualCenterFragment, userLoginApi, userCouponApi);
    }

    @Provides
    @ActivityScope
    IndividualCenterFragment individualCenterFragment() {
        return individualCenterFragment;
    }


}
