package com.j1j2.pifalao.feature.findpsw.di;

import com.j1j2.data.http.api.FindPwdBackApi;
import com.j1j2.data.http.api.UserMessageApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.findpsw.FindPSWActivity;
import com.j1j2.pifalao.feature.findpsw.FindPSWViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-22.
 */
@Module
public class FindPSWModule {

    private FindPSWActivity findPSWActivity;

    public FindPSWModule(FindPSWActivity findPSWActivity) {
        this.findPSWActivity = findPSWActivity;
    }

    @Provides
    @ActivityScope
    FindPSWActivity findPSWActivity() {
        return findPSWActivity;
    }

    @Provides
    @ActivityScope
    FindPwdBackApi findPwdBackApi(Retrofit retrofit) {
        return retrofit.create(FindPwdBackApi.class);
    }

    @Provides
    @ActivityScope
    FindPSWViewModel findPSWViewModel(FindPSWActivity findPSWActivity, FindPwdBackApi findPwdBackApi) {
        return new FindPSWViewModel(findPSWActivity, findPwdBackApi);
    }
}
