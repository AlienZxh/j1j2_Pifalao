package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-5-7.
 */
public class OrderOnlinePayBody {
    private int OrderId;
    private String OrderNO;
    private boolean UseAliPay;
    private boolean UseWeiXinPay;
    private boolean UseBalancePay;

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

    public boolean isUseAliPay() {
        return UseAliPay;
    }

    public void setUseAliPay(boolean useAliPay) {
        UseAliPay = useAliPay;
    }

    public boolean isUseWeiXinPay() {
        return UseWeiXinPay;
    }

    public void setUseWeiXinPay(boolean useWeiXinPay) {
        UseWeiXinPay = useWeiXinPay;
    }

    public boolean isUseBalancePay() {
        return UseBalancePay;
    }

    public void setUseBalancePay(boolean useBalancePay) {
        UseBalancePay = useBalancePay;
    }
}
