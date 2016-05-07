package com.j1j2.pifalao.app.event;

/**
 * Created by alienzxh on 16-3-29.
 */
public class OrderStateChangeEvent {

    private final boolean isOnlyOrderDetail;
    private final int fromOrderState;
    private final int newOrderState;

    public OrderStateChangeEvent(boolean isOnlyOrderDetail, int fromOrderState, int newOrderState) {
        this.isOnlyOrderDetail = isOnlyOrderDetail;
        this.fromOrderState = fromOrderState;
        this.newOrderState = newOrderState;
    }

    public boolean isOnlyOrderDetail() {
        return isOnlyOrderDetail;
    }

    public int getFromOrderState() {
        return fromOrderState;
    }

    public int getNewOrderState() {
        return newOrderState;
    }
}
