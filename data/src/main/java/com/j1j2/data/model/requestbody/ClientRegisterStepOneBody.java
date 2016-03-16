package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 */
public class ClientRegisterStepOneBody {

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
}
