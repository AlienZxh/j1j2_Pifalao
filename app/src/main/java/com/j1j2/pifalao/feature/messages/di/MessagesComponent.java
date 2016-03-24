package com.j1j2.pifalao.feature.messages.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.messages.MessagesActivity;
import com.j1j2.pifalao.feature.messages.MessagesFragment;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-24.
 */
@ActivityScope
@Subcomponent(modules = {MessagesModule.class})
public interface MessagesComponent {
    void inject(MessagesActivity messagesActivity);

    void inject(MessagesFragment messagesFragment);
}
