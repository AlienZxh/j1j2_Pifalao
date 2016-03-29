package com.j1j2.pifalao.feature.vipupdate.steptwo.di;

import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.vipupdate.steptwo.VipUpdateStepTwoActivity;
import com.j1j2.pifalao.feature.vipupdate.steptwo.VipUpdateStepTwoViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-28.
 */
@Module
public class VipUpdateStepTwoModule {

    private VipUpdateStepTwoActivity vipUpdateStepTwoActivity;

    public VipUpdateStepTwoModule(VipUpdateStepTwoActivity vipUpdateStepTwoActivity) {
        this.vipUpdateStepTwoActivity = vipUpdateStepTwoActivity;
    }

    @Provides
    @ActivityScope
    UserVipApi userVipApi(Retrofit retrofit) {
        return retrofit.create(UserVipApi.class);
    }

    @Provides
    @ActivityScope
    VipUpdateStepTwoActivity vipUpdateStepTwoActivity() {
        return vipUpdateStepTwoActivity;
    }

    @Provides
    @ActivityScope
    VipUpdateStepTwoViewModel vipUpdateStepTwoViewModel(VipUpdateStepTwoActivity vipUpdateStepTwoActivity, UserVipApi userVipApi) {
        return new VipUpdateStepTwoViewModel(vipUpdateStepTwoActivity, userVipApi);
    }

}
