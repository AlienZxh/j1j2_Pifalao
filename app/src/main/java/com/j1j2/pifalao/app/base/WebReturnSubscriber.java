package com.j1j2.pifalao.app.base;


import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.event.LoginCookieTimeoutEvent;
import com.j1j2.common.util.Network;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 兴昊 on 2016/1/19.
 */
public abstract class WebReturnSubscriber<E> extends DefaultSubscriber<WebReturn<E>> {


    @Override
    public void onError(Throwable e) {
        super.onError(e);
    }

    public abstract void onWebReturnSucess(E e);

    public abstract void onWebReturnFailure(String errorMessage);

    public abstract void onWebReturnCompleted();

    @Override
    public final void onNext(WebReturn<E> eWebReturn) {
        super.onNext(eWebReturn);
        if (eWebReturn.isValue()) {
            onWebReturnSucess(eWebReturn.getDetail());
        } else {
            onWebReturnFailure(eWebReturn.getErrorMessage());
            if (eWebReturn.getErrorMessage().equals("身份验证信息失效！请重新登陆！")) {
                EventBus.getDefault().postSticky(new LoginCookieTimeoutEvent());
            }
        }
        onWebReturnCompleted();
    }


}
