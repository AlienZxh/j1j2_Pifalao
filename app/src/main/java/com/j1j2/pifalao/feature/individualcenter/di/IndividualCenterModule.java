package com.j1j2.pifalao.feature.individualcenter.di;

import com.google.gson.Gson;
import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserOrderApi;
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
    UserOrderApi userOrderApi(Retrofit retrofit) {
        return retrofit.create(UserOrderApi.class);
    }

    @Provides
    @ActivityScope
    IndividualCenterViewModel individualCenterViewModel(Gson gson, User user, IndividualCenterFragment individualCenterFragment, UserLoginApi userLoginApi, UserOrderApi userOrderApi) {
        return new IndividualCenterViewModel(gson, user, individualCenterFragment, userLoginApi, userOrderApi);
    }

    @Provides
    @ActivityScope
    IndividualCenterFragment individualCenterFragment() {
        return individualCenterFragment;
    }


}
