package com.j1j2.pifalao.feature.home.memberhome.di;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserMessageApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.home.memberhome.MemberHomeActivity;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-8-24.
 */
@Module
public class MemberHomeModule {
    private MemberHomeActivity activity;

    public MemberHomeModule(MemberHomeActivity activity) {
        this.activity = activity;
    }



    @Provides
    @ActivityScope
    ActivityApi activityApi(Retrofit retrofit) {
        return retrofit.create(ActivityApi.class);
    }

    @Provides
    @ActivityScope
    MemberHomeActivity activity() {
        return activity;
    }


}
