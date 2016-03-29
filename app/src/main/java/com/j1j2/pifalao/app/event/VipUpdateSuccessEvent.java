package com.j1j2.pifalao.app.event;

/**
 * Created by alienzxh on 16-3-28.
 */
public class VipUpdateSuccessEvent {
    private final String code;

    public VipUpdateSuccessEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
