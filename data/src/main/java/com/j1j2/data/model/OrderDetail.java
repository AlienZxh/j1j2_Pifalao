package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by alienzxh on 16-3-22.
 */
public class OrderDetail implements Parcelable {

    /**
     * OrderId : 14281
     * OrderSubmitTimeStr : 2015-09-11 02:22:56
     * OrderSum : 39
     * Freight : -2
     * OrderState : 256
     * OrderEvaluate :
     * AdminReply :
     * ProductDetails : [{"ProductId":30879,"OwnCode":"","BarCode":"6935284455588","ProductName":"卫龙2连包亲嘴豆筋绿色1*12*50","RetailPrice":0.5,"ProductUnit":"包","ThumbImgPath":"http://data.j1j2.com/ResourceFiles/OptimizeImgs/2015-06-18/70/93_1_2.jpg","MemberPrice":0.41,"Quantity":100,"AppendQuantity":0,"ScalingFactor":0,"BaseUnit":""}]
     * DealSubmitTimeStr : 2015-09-11 02:31:41
     * Contacter : zxh
     * ContacterPhone : 15200348636
     * ContacterAddress : 湖南省长沙市雨花区民主路
     * ProductAmout : 41
     * SaveAmount : 9
     * Coupons : []
     * FreightType : 2
     * FreightTypeStr : 服务点自提
     * PredictSendTime : 2015-09-12 有空就来拿
     * ConnectedServicePoint : 星沙星城国际服务点
     * ServicePoint : 1
     * ReceiveAddressLat : 28.235209
     * ReceiveAddressLng : 113.117742
     * CalculateDistance : 0.1
     */

    private int OrderId;
    private String OrderSubmitTimeStr;
    private double OrderSum;
    private double Freight;
    private int OrderState;
    private String OrderEvaluate;
    private String AdminReply;
    private String DealSubmitTimeStr;
    private String Contacter;
    private String ContacterPhone;
    private String ContacterAddress;
    private double ProductAmout;
    private double SaveAmount;
    private int FreightType;
    private String FreightTypeStr;
    private String PredictSendTime;
    private String ConnectedServicePoint;
    private int ServicePoint;
    private double ReceiveAddressLat;
    private double ReceiveAddressLng;
    private double CalculateDistance;
    /**
     * ProductId : 30879
     * OwnCode :
     * BarCode : 6935284455588
     * ProductName : 卫龙2连包亲嘴豆筋绿色1*12*50
     * RetailPrice : 0.5
     * ProductUnit : 包
     * ThumbImgPath : http://data.j1j2.com/ResourceFiles/OptimizeImgs/2015-06-18/70/93_1_2.jpg
     * MemberPrice : 0.41
     * Quantity : 100
     * AppendQuantity : 0
     * ScalingFactor : 0
     * BaseUnit :
     */
    private List<OrderProductDetail> ProductDetails;
    private List<Coupon> Coupons;


    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getOrderSubmitTimeStr() {
        return OrderSubmitTimeStr;
    }

    public void setOrderSubmitTimeStr(String orderSubmitTimeStr) {
        OrderSubmitTimeStr = orderSubmitTimeStr;
    }

    public double getOrderSum() {
        return OrderSum;
    }

    public void setOrderSum(double orderSum) {
        OrderSum = orderSum;
    }

    public double getFreight() {
        return Freight;
    }

    public void setFreight(double freight) {
        Freight = freight;
    }

    public int getOrderState() {
        return OrderState;
    }

    public void setOrderState(int orderState) {
        OrderState = orderState;
    }

    public String getOrderEvaluate() {
        return OrderEvaluate;
    }

    public void setOrderEvaluate(String orderEvaluate) {
        OrderEvaluate = orderEvaluate;
    }

    public String getAdminReply() {
        return AdminReply;
    }

    public void setAdminReply(String adminReply) {
        AdminReply = adminReply;
    }

    public String getDealSubmitTimeStr() {
        return DealSubmitTimeStr;
    }

    public void setDealSubmitTimeStr(String dealSubmitTimeStr) {
        DealSubmitTimeStr = dealSubmitTimeStr;
    }

    public String getContacter() {
        return Contacter;
    }

    public void setContacter(String contacter) {
        Contacter = contacter;
    }

    public String getContacterPhone() {
        return ContacterPhone;
    }

    public void setContacterPhone(String contacterPhone) {
        ContacterPhone = contacterPhone;
    }

    public String getContacterAddress() {
        return ContacterAddress;
    }

    public void setContacterAddress(String contacterAddress) {
        ContacterAddress = contacterAddress;
    }

    public double getProductAmout() {
        return ProductAmout;
    }

    public void setProductAmout(double productAmout) {
        ProductAmout = productAmout;
    }

    public double getSaveAmount() {
        return SaveAmount;
    }

    public void setSaveAmount(double saveAmount) {
        SaveAmount = saveAmount;
    }

    public int getFreightType() {
        return FreightType;
    }

    public void setFreightType(int freightType) {
        FreightType = freightType;
    }

    public String getFreightTypeStr() {
        return FreightTypeStr;
    }

    public void setFreightTypeStr(String freightTypeStr) {
        FreightTypeStr = freightTypeStr;
    }

    public String getPredictSendTime() {
        return PredictSendTime;
    }

    public void setPredictSendTime(String predictSendTime) {
        PredictSendTime = predictSendTime;
    }

    public String getConnectedServicePoint() {
        return ConnectedServicePoint;
    }

    public void setConnectedServicePoint(String connectedServicePoint) {
        ConnectedServicePoint = connectedServicePoint;
    }

    public int getServicePoint() {
        return ServicePoint;
    }

    public void setServicePoint(int servicePoint) {
        ServicePoint = servicePoint;
    }

    public double getReceiveAddressLat() {
        return ReceiveAddressLat;
    }

    public void setReceiveAddressLat(double receiveAddressLat) {
        ReceiveAddressLat = receiveAddressLat;
    }

    public double getReceiveAddressLng() {
        return ReceiveAddressLng;
    }

    public void setReceiveAddressLng(double receiveAddressLng) {
        ReceiveAddressLng = receiveAddressLng;
    }

    public double getCalculateDistance() {
        return CalculateDistance;
    }

    public void setCalculateDistance(double calculateDistance) {
        CalculateDistance = calculateDistance;
    }

    public List<OrderProductDetail> getProductDetails() {
        return ProductDetails;
    }

    public void setProductDetails(List<OrderProductDetail> productDetails) {
        ProductDetails = productDetails;
    }

    public List<Coupon> getCoupons() {
        return Coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        Coupons = coupons;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "OrderId=" + OrderId +
                ", OrderSubmitTimeStr='" + OrderSubmitTimeStr + '\'' +
                ", OrderSum=" + OrderSum +
                ", Freight=" + Freight +
                ", OrderState=" + OrderState +
                ", OrderEvaluate='" + OrderEvaluate + '\'' +
                ", AdminReply='" + AdminReply + '\'' +
                ", DealSubmitTimeStr='" + DealSubmitTimeStr + '\'' +
                ", Contacter='" + Contacter + '\'' +
                ", ContacterPhone='" + ContacterPhone + '\'' +
                ", ContacterAddress='" + ContacterAddress + '\'' +
                ", ProductAmout=" + ProductAmout +
                ", SaveAmount=" + SaveAmount +
                ", FreightType=" + FreightType +
                ", FreightTypeStr='" + FreightTypeStr + '\'' +
                ", PredictSendTime='" + PredictSendTime + '\'' +
                ", ConnectedServicePoint='" + ConnectedServicePoint + '\'' +
                ", ServicePoint=" + ServicePoint +
                ", ReceiveAddressLat=" + ReceiveAddressLat +
                ", ReceiveAddressLng=" + ReceiveAddressLng +
                ", CalculateDistance=" + CalculateDistance +
                ", ProductDetails=" + ProductDetails +
                ", Coupons=" + Coupons +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.OrderId);
        dest.writeString(this.OrderSubmitTimeStr);
        dest.writeDouble(this.OrderSum);
        dest.writeDouble(this.Freight);
        dest.writeInt(this.OrderState);
        dest.writeString(this.OrderEvaluate);
        dest.writeString(this.AdminReply);
        dest.writeString(this.DealSubmitTimeStr);
        dest.writeString(this.Contacter);
        dest.writeString(this.ContacterPhone);
        dest.writeString(this.ContacterAddress);
        dest.writeDouble(this.ProductAmout);
        dest.writeDouble(this.SaveAmount);
        dest.writeInt(this.FreightType);
        dest.writeString(this.FreightTypeStr);
        dest.writeString(this.PredictSendTime);
        dest.writeString(this.ConnectedServicePoint);
        dest.writeInt(this.ServicePoint);
        dest.writeDouble(this.ReceiveAddressLat);
        dest.writeDouble(this.ReceiveAddressLng);
        dest.writeDouble(this.CalculateDistance);
        dest.writeTypedList(ProductDetails);
        dest.writeTypedList(Coupons);
    }

    public OrderDetail() {
    }

    protected OrderDetail(Parcel in) {
        this.OrderId = in.readInt();
        this.OrderSubmitTimeStr = in.readString();
        this.OrderSum = in.readDouble();
        this.Freight = in.readDouble();
        this.OrderState = in.readInt();
        this.OrderEvaluate = in.readString();
        this.AdminReply = in.readString();
        this.DealSubmitTimeStr = in.readString();
        this.Contacter = in.readString();
        this.ContacterPhone = in.readString();
        this.ContacterAddress = in.readString();
        this.ProductAmout = in.readDouble();
        this.SaveAmount = in.readDouble();
        this.FreightType = in.readInt();
        this.FreightTypeStr = in.readString();
        this.PredictSendTime = in.readString();
        this.ConnectedServicePoint = in.readString();
        this.ServicePoint = in.readInt();
        this.ReceiveAddressLat = in.readDouble();
        this.ReceiveAddressLng = in.readDouble();
        this.CalculateDistance = in.readDouble();
        this.ProductDetails = in.createTypedArrayList(OrderProductDetail.CREATOR);
        this.Coupons = in.createTypedArrayList(Coupon.CREATOR);
    }

    public static final Parcelable.Creator<OrderDetail> CREATOR = new Parcelable.Creator<OrderDetail>() {
        @Override
        public OrderDetail createFromParcel(Parcel source) {
            return new OrderDetail(source);
        }

        @Override
        public OrderDetail[] newArray(int size) {
            return new OrderDetail[size];
        }
    };
}