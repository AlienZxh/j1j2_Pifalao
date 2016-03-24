package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-24.
 */
public class Message implements Parcelable {

    /**
     * MessageId : 4520
     * UserId : 0
     * Message : 您在新安小区服务点，消费结账成功！
     * 本次实收￥20+账户余额￥0.29-应付￥4.82 = 找零￥15+账户存入零钱￥0.47。
     * 目前账户余额￥0.47。
     * 批发佬已为您累计节省了￥22.18哦！
     * SendedState : false
     * type : 1
     * HasRead : false
     * Title : 线下订单消息
     * PushTimeStr : 2016-02-22 15:56:53
     */

    private int MessageId;
    private int UserId;
    private String Message;
    private boolean SendedState;
    private int type;//1:个人   2：系统
    private boolean HasRead;
    private String Title;
    private String PushTimeStr;

    public int getMessageId() {
        return MessageId;
    }

    public void setMessageId(int MessageId) {
        this.MessageId = MessageId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public boolean isSendedState() {
        return SendedState;
    }

    public void setSendedState(boolean SendedState) {
        this.SendedState = SendedState;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isHasRead() {
        return HasRead;
    }

    public void setHasRead(boolean HasRead) {
        this.HasRead = HasRead;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getPushTimeStr() {
        return PushTimeStr;
    }

    public void setPushTimeStr(String PushTimeStr) {
        this.PushTimeStr = PushTimeStr;
    }

    @Override
    public String toString() {
        return "Message{" +
                "MessageId=" + MessageId +
                ", UserId=" + UserId +
                ", Message='" + Message + '\'' +
                ", SendedState=" + SendedState +
                ", type=" + type +
                ", HasRead=" + HasRead +
                ", Title='" + Title + '\'' +
                ", PushTimeStr='" + PushTimeStr + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.MessageId);
        dest.writeInt(this.UserId);
        dest.writeString(this.Message);
        dest.writeByte(SendedState ? (byte) 1 : (byte) 0);
        dest.writeInt(this.type);
        dest.writeByte(HasRead ? (byte) 1 : (byte) 0);
        dest.writeString(this.Title);
        dest.writeString(this.PushTimeStr);
    }

    public Message() {
    }

    protected Message(Parcel in) {
        this.MessageId = in.readInt();
        this.UserId = in.readInt();
        this.Message = in.readString();
        this.SendedState = in.readByte() != 0;
        this.type = in.readInt();
        this.HasRead = in.readByte() != 0;
        this.Title = in.readString();
        this.PushTimeStr = in.readString();
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
