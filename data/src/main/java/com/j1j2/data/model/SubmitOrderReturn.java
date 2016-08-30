package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-5-7.
 */
public class SubmitOrderReturn {
    private int OrderId;
    private String OrderNO;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String orderNO) {
        OrderNO = orderNO;
    }
}
