package com.j1j2.pifalao.feature.individualcenter.di;

import com.j1j2.data.http.api.UserMessageApi;
import com.j1j2.pifalao.app.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-4-8.
 */
@Module
public class IndividualActivityModule {
    @Provides
    @ActivityScope
    UserMessageApi userMessageApi(Retrofit retrofit) {
        return retrofit.create(UserMessageApi.class);
    }
}
