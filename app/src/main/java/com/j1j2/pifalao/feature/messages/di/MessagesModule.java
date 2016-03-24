package com.j1j2.pifalao.feature.messages.di;

import com.j1j2.data.http.api.UserMessageApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.messages.MessagesActivity;
import com.j1j2.pifalao.feature.messages.MessagesViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-24.
 */
@Module
public class MessagesModule {

    private MessagesActivity messagesActivity;

    public MessagesModule(MessagesActivity messagesActivity) {
        this.messagesActivity = messagesActivity;
    }

    @Provides
    @ActivityScope
    UserMessageApi userMessageApi(Retrofit retrofit) {
        return retrofit.create(UserMessageApi.class);
    }

    @Provides
    @ActivityScope
    MessagesActivity messagesActivity() {
        return messagesActivity;
    }

    @Provides
    @ActivityScope
    MessagesViewModel messagesViewModel(MessagesActivity messagesActivity, UserMessageApi userMessageApi) {
        return new MessagesViewModel(messagesActivity, userMessageApi);
    }
}
