package com.j1j2.pifalao.feature.messages;

import com.j1j2.data.http.api.UserMessageApi;

/**
 * Created by alienzxh on 16-3-24.
 */
public class MessagesViewModel {

    private MessagesActivity messagesActivity;
    private UserMessageApi userMessageApi;

    public MessagesViewModel(MessagesActivity messagesActivity, UserMessageApi userMessageApi) {
        this.messagesActivity = messagesActivity;
        this.userMessageApi = userMessageApi;
    }


}
