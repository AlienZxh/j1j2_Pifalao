package com.j1j2.pifalao.app.event;

/**
 * Created by alienzxh on 16-3-28.
 */
public class RegisterSuccessEvent {
    private final boolean isLogin;
    private final String loginAccount;
    private final String passWord;

    public RegisterSuccessEvent(boolean isLogin, String loginAccount, String passWord) {
        this.isLogin = isLogin;
        this.loginAccount = loginAccount;
        this.passWord = passWord;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public String getPassWord() {
        return passWord;
    }
}
