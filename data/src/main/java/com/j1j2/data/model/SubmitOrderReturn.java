package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-5-7.
 */
public class SubmitOrderReturn {
    private int OrderId;
    private String OrderNo;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }
}
