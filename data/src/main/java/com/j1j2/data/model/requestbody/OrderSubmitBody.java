package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 */
public class OrderSubmitBody {

    private int FreightID;// 选择的运费方式

    private int ShopId;// 用户选择的自提或者关联的服务点
    private String AddressId;// 用户选择的地址的主键编号

    private String CouponCode;
    private double CalculateDistance;// 终端计算的用户与相关服务点见的路径距离
    private double SaveAmount;// 订单此次节省金额
    private String OrderMemo;// 订单其它备注
    private String PredictSendTime;// 订单预计配送时间
    private int ServiceId;//服务模块

    private int OrderPayType;//支付方式

    public int getFreightID() {
        return FreightID;
    }

    public void setFreightID(int freightID) {
        FreightID = freightID;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getAddressId() {
        return AddressId;
    }

    public void setAddressId(String addressId) {
        AddressId = addressId;
    }

    public String getCouponCode() {
        return CouponCode;
    }

    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }

    public double getCalculateDistance() {
        return CalculateDistance;
    }

    public void setCalculateDistance(double calculateDistance) {
        CalculateDistance = calculateDistance;
    }

    public double getSaveAmount() {
        return SaveAmount;
    }

    public void setSaveAmount(double saveAmount) {
        SaveAmount = saveAmount;
    }

    public String getOrderMemo() {
        return OrderMemo;
    }

    public void setOrderMemo(String orderMemo) {
        OrderMemo = orderMemo;
    }

    public String getPredictSendTime() {
        return PredictSendTime;
    }

    public void setPredictSendTime(String predictSendTime) {
        PredictSendTime = predictSendTime;
    }

    public int getServiceId() {
        return ServiceId;
    }

    public void setServiceId(int serviceId) {
        ServiceId = serviceId;
    }

    public int getOrderPayType() {
        return OrderPayType;
    }

    public void setOrderPayType(int orderPayType) {
        OrderPayType = orderPayType;
    }
}
