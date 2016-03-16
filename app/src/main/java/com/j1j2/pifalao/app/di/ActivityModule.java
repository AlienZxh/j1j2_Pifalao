package com.j1j2.pifalao.app.di;

import android.app.Activity;

import com.j1j2.pifalao.app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alienzxh on 16-3-5.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity activity() {
        return this.activity;
    }
}
