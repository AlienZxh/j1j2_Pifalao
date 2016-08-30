package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-4-21.
 */
public class SaleOrderStateHistory {
    private int SaleOrderState;
    private int OrderPayType;
    private String ExcutedTimeStr;

    public int getSaleOrderState() {
        return SaleOrderState;
    }

    public void setSaleOrderState(int saleOrderState) {
        SaleOrderState = saleOrderState;
    }

    public String getExcutedTimeStr() {
        return ExcutedTimeStr;
    }

    public void setExcutedTimeStr(String excutedTimeStr) {
        ExcutedTimeStr = excutedTimeStr;
    }

    public int getOrderPayType() {
        return OrderPayType;
    }

    public void setOrderPayType(int orderPayType) {
        OrderPayType = orderPayType;
    }
}
