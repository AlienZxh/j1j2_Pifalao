package com.j1j2.pifalao.app.event;

/**
 * Created by alienzxh on 16-3-29.
 */
public class OrderStateChangeEvent {
    private final int fromOrderState;
    private final int newOrderState;

    public OrderStateChangeEvent(int fromOrderState, int newOrderState) {
        this.fromOrderState = fromOrderState;
        this.newOrderState = newOrderState;
    }

    public int getFromOrderState() {
        return fromOrderState;
    }

    public int getNewOrderState() {
        return newOrderState;
    }
}
