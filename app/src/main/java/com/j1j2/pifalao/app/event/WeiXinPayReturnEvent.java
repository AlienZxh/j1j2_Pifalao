package com.j1j2.pifalao.app.event;

import com.tencent.mm.sdk.modelbase.BaseResp;

/**
 * Created by alienzxh on 16-5-13.
 */
public class WeiXinPayReturnEvent {
    private final boolean isSuccess;

    public WeiXinPayReturnEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
