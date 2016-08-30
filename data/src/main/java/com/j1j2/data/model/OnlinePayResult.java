package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-5-8.
 */
public class OnlinePayResult {
    private int OrderId;
    private String OrderNO;
    private boolean UseAliPay;
    private boolean UseWeiXinPay;
    private boolean UseBalance;
    private String AliPayResult;
    private WeiXinPayResult WeiXinPayResult;

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

    public boolean isUseBalance() {
        return UseBalance;
    }

    public void setUseBalance(boolean useBalance) {
        UseBalance = useBalance;
    }

    public String getAliPayResult() {
        return AliPayResult;
    }

    public void setAliPayResult(String aliPayResult) {
        AliPayResult = aliPayResult;
    }

    public com.j1j2.data.model.WeiXinPayResult getWeiXinPayResult() {
        return WeiXinPayResult;
    }

    public void setWeiXinPayResult(com.j1j2.data.model.WeiXinPayResult weiXinPayResult) {
        WeiXinPayResult = weiXinPayResult;
    }

    @Override
    public String toString() {
        return "OnlinePayResult{" +
                "OrderId=" + OrderId +
                ", OrderNO='" + OrderNO + '\'' +
                ", UseAliPay=" + UseAliPay +
                ", UseWeiXinPay=" + UseWeiXinPay +
                ", UseBalance=" + UseBalance +
                ", AliPayResult='" + AliPayResult + '\'' +
                ", WeiXinPayResult=" + WeiXinPayResult +
                '}';
    }
}
