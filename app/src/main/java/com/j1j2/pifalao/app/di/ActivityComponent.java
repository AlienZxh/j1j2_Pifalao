package com.j1j2.pifalao.app.di;

import android.app.Activity;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.base.BaseActivity;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-5.
 */
@ActivityScope
@Subcomponent( modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();

    void inject(BaseActivity activity);

}
