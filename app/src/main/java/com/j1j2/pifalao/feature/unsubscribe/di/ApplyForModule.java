package com.j1j2.pifalao.feature.unsubscribe.di;

import android.app.Activity;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.http.api.ApplyForApi;
import com.j1j2.pifalao.app.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-9-25.
 */
@Module
public class ApplyForModule {
    private Activity activity;

    public ApplyForModule(Activity activity) {
        this.activity = activity;
    }


    @Provides
    @ActivityScope
    ApplyForApi applyForApi(Retrofit retrofit) {
        return retrofit.create(ApplyForApi.class);
    }


    @Provides
    @ActivityScope
    Activity activity() {
        return activity;
    }
}
