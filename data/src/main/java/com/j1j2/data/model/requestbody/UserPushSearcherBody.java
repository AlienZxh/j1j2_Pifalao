package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 */
public class UserPushSearcherBody {

    private int MessageType;

    public int getMessageType() {
        return MessageType;
    }

    public void setMessageType(int messageType) {
        MessageType = messageType;
    }

    @Override
    public String toString() {
        return "UserPushSearcherBody{" +
                "MessageType=" + MessageType +
                '}';
    }
}
