package com.j1j2.pifalao.app.event;

/**
 * Created by alienzxh on 16-3-26.
 */
public class LogStateEvent {

    private final boolean isLogin;

    public LogStateEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
