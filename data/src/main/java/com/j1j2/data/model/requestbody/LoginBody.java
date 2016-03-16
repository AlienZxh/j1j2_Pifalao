package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 * 登录body
 */
public class LoginBody {

    private String LoginAccount;//登陆账号
    private String PassWord;//登陆密码
    private String DeviceToken;//设备终端号
    private int TerminalType;//可空 终端类型 1：android 2：IOS
    private int UserType;//可空  5：为普通会员用户 6：为门店账号 8:暂定为大客户

    public String getLoginAccount() {
        return LoginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        LoginAccount = loginAccount;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    public int getTerminalType() {
        return TerminalType;
    }

    public void setTerminalType(int terminalType) {
        TerminalType = terminalType;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    @Override
    public String toString() {
        return "LoginBody{" +
                "LoginAccount='" + LoginAccount + '\'' +
                ", PassWord='" + PassWord + '\'' +
                ", DeviceToken='" + DeviceToken + '\'' +
                ", TerminalType=" + TerminalType +
                ", UserType=" + UserType +
                '}';
    }
}
