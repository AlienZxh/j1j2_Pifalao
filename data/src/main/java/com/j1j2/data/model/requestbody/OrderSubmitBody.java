package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 */
public class OrderSubmitBody {

    private int AddressId;// 用户选择的地址的主键编号
    private String ProductCoupon;// 选择的商品优惠券的字符串 编码 前台需要传递该参数
    private String FreightCoupon;// 选择的配送费优惠券的 字符串 编码 前台需要传递该参数
    private int FreightType;// 选择的运费方式
    private int ServicePointId;// 用户选择的自提或者关联的服务点
    private double CalculateDistance;// 终端计算的用户与相关服务点见的路径距离
    private double SaveAmount;// 订单此次节省金额
    private String OrderMemo;// 订单其它备注
    private String PredictSendTime;// 订单预计配送时间
    private int ModuleId;//服务模块

    public int getAddressId() {
        return AddressId;
    }

    public void setAddressId(int addressId) {
        AddressId = addressId;
    }

    public String getProductCoupon() {
        return ProductCoupon;
    }

    public void setProductCoupon(String productCoupon) {
        ProductCoupon = productCoupon;
    }

    public String getFreightCoupon() {
        return FreightCoupon;
    }

    public void setFreightCoupon(String freightCoupon) {
        FreightCoupon = freightCoupon;
    }

    public int getFreightType() {
        return FreightType;
    }

    public void setFreightType(int freightType) {
        FreightType = freightType;
    }

    public int getServicePointId() {
        return ServicePointId;
    }

    public void setServicePointId(int servicePointId) {
        ServicePointId = servicePointId;
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

    public int getModuleId() {
        return ModuleId;
    }

    public void setModuleId(int moduleId) {
        ModuleId = moduleId;
    }

    @Override
    public String toString() {
        return "OrderSubmitBody{" +
                "AddressId=" + AddressId +
                ", ProductCoupon='" + ProductCoupon + '\'' +
                ", FreightCoupon='" + FreightCoupon + '\'' +
                ", FreightType=" + FreightType +
                ", ServicePointId=" + ServicePointId +
                ", CalculateDistance=" + CalculateDistance +
                ", SaveAmount=" + SaveAmount +
                ", OrderMemo='" + OrderMemo + '\'' +
                ", PredictSendTime='" + PredictSendTime + '\'' +
                ", ModuleId=" + ModuleId +
                '}';
    }
}
