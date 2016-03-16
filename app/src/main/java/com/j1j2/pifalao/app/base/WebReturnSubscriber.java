package com.j1j2.pifalao.app.base;


import com.j1j2.data.model.WebReturn;

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
        }
        onWebReturnCompleted();
    }


}
