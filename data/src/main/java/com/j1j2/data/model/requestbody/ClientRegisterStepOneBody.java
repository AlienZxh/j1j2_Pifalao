package com.j1j2.data.model.requestbody;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-10.
 */
public class ClientRegisterStepOneBody implements Parcelable {

    private String Phone;//用户手机号码
    private String SMSCode;//短信验证码
    private boolean IsAgree;// 是否同意批发佬协议，须赋值true
    private String PassWord;// 密码
    private String ConfrimPwd;// 重复密码
    private String MarketerCode;// 市场营销人员编码，可空
    private int RegisterType;// 首次注册来源客服端，可空

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSMSCode() {
        return SMSCode;
    }

    public void setSMSCode(String SMSCode) {
        this.SMSCode = SMSCode;
    }

    public boolean isAgree() {
        return IsAgree;
    }

    public void setIsAgree(boolean isAgree) {
        IsAgree = isAgree;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getConfrimPwd() {
        return ConfrimPwd;
    }

    public void setConfrimPwd(String confrimPwd) {
        ConfrimPwd = confrimPwd;
    }

    public String getMarketerCode() {
        return MarketerCode;
    }

    public void setMarketerCode(String marketerCode) {
        MarketerCode = marketerCode;
    }

    public int getRegisterType() {
        return RegisterType;
    }

    public void setRegisterType(int registerType) {
        RegisterType = registerType;
    }

    @Override
    public String toString() {
        return "ClientRegisterStepOneBody{" +
                "Phone='" + Phone + '\'' +
                ", SMSCode='" + SMSCode + '\'' +
                ", IsAgree=" + IsAgree +
                ", PassWord='" + PassWord + '\'' +
                ", ConfrimPwd='" + ConfrimPwd + '\'' +
                ", MarketerCode='" + MarketerCode + '\'' +
                ", RegisterType=" + RegisterType +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Phone);
        dest.writeString(this.SMSCode);
        dest.writeByte(IsAgree ? (byte) 1 : (byte) 0);
        dest.writeString(this.PassWord);
        dest.writeString(this.ConfrimPwd);
        dest.writeString(this.MarketerCode);
        dest.writeInt(this.RegisterType);
    }

    public ClientRegisterStepOneBody() {
    }

    protected ClientRegisterStepOneBody(Parcel in) {
        this.Phone = in.readString();
        this.SMSCode = in.readString();
        this.IsAgree = in.readByte() != 0;
        this.PassWord = in.readString();
        this.ConfrimPwd = in.readString();
        this.MarketerCode = in.readString();
        this.RegisterType = in.readInt();
    }

    public static final Parcelable.Creator<ClientRegisterStepOneBody> CREATOR = new Parcelable.Creator<ClientRegisterStepOneBody>() {
        @Override
        public ClientRegisterStepOneBody createFromParcel(Parcel source) {
            return new ClientRegisterStepOneBody(source);
        }

        @Override
        public ClientRegisterStepOneBody[] newArray(int size) {
            return new ClientRegisterStepOneBody[size];
        }
    };
}
