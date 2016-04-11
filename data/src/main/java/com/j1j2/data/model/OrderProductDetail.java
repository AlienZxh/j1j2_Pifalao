package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-22.
 */
public class OrderProductDetail implements Parcelable {


    /**
     * ProductId : 27466
     * ProductMainId : 1993
     * OwnCode : asd
     * BarCode : 6935145301825
     * ProductName : 锐澳橙味伏加鸡尾酒1*24&
     * RetailPrice : 288.1
     * ProductUnit : 件
     * ThumbImgPath : http://data.j1j2.com/ResourceFiles/ProductImages/20140717/70_70/20102_0.jpg
     * MemberPrice : 241.8
     * Quantity : 1
     * AppendQuantity : 0
     * ScalingFactor : 0
     * BaseUnit : string
     * ActualDelivery : 1
     */

    private int ProductId;
    private int ProductMainId;
    private String OwnCode;
    private String BarCode;
    private String ProductName;
    private double RetailPrice;
    private String ProductUnit;
    private String ThumbImgPath;
    private double MemberPrice;
    private int Quantity;
    private int AppendQuantity;
    private int ScalingFactor;
    private String BaseUnit;
    private int ActualDelivery;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int ProductId) {
        this.ProductId = ProductId;
    }

    public int getProductMainId() {
        return ProductMainId;
    }

    public void setProductMainId(int ProductMainId) {
        this.ProductMainId = ProductMainId;
    }

    public String getOwnCode() {
        return OwnCode;
    }

    public void setOwnCode(String OwnCode) {
        this.OwnCode = OwnCode;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String BarCode) {
        this.BarCode = BarCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(double RetailPrice) {
        this.RetailPrice = RetailPrice;
    }

    public String getProductUnit() {
        return ProductUnit;
    }

    public void setProductUnit(String ProductUnit) {
        this.ProductUnit = ProductUnit;
    }

    public String getThumbImgPath() {
        return ThumbImgPath;
    }

    public void setThumbImgPath(String ThumbImgPath) {
        this.ThumbImgPath = ThumbImgPath;
    }

    public double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(double MemberPrice) {
        this.MemberPrice = MemberPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public int getAppendQuantity() {
        return AppendQuantity;
    }

    public void setAppendQuantity(int AppendQuantity) {
        this.AppendQuantity = AppendQuantity;
    }

    public int getScalingFactor() {
        return ScalingFactor;
    }

    public void setScalingFactor(int ScalingFactor) {
        this.ScalingFactor = ScalingFactor;
    }

    public String getBaseUnit() {
        return BaseUnit;
    }

    public void setBaseUnit(String BaseUnit) {
        this.BaseUnit = BaseUnit;
    }

    public int getActualDelivery() {
        return ActualDelivery;
    }

    public void setActualDelivery(int ActualDelivery) {
        this.ActualDelivery = ActualDelivery;
    }

    @Override
    public String toString() {
        return "OrderProductDetail{" +
                "ProductId=" + ProductId +
                ", ProductMainId=" + ProductMainId +
                ", OwnCode='" + OwnCode + '\'' +
                ", BarCode='" + BarCode + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", RetailPrice=" + RetailPrice +
                ", ProductUnit='" + ProductUnit + '\'' +
                ", ThumbImgPath='" + ThumbImgPath + '\'' +
                ", MemberPrice=" + MemberPrice +
                ", Quantity=" + Quantity +
                ", AppendQuantity=" + AppendQuantity +
                ", ScalingFactor=" + ScalingFactor +
                ", BaseUnit='" + BaseUnit + '\'' +
                ", ActualDelivery=" + ActualDelivery +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ProductId);
        dest.writeInt(this.ProductMainId);
        dest.writeString(this.OwnCode);
        dest.writeString(this.BarCode);
        dest.writeString(this.ProductName);
        dest.writeDouble(this.RetailPrice);
        dest.writeString(this.ProductUnit);
        dest.writeString(this.ThumbImgPath);
        dest.writeDouble(this.MemberPrice);
        dest.writeInt(this.Quantity);
        dest.writeInt(this.AppendQuantity);
        dest.writeInt(this.ScalingFactor);
        dest.writeString(this.BaseUnit);
        dest.writeInt(this.ActualDelivery);
    }

    public OrderProductDetail() {
    }

    protected OrderProductDetail(Parcel in) {
        this.ProductId = in.readInt();
        this.ProductMainId = in.readInt();
        this.OwnCode = in.readString();
        this.BarCode = in.readString();
        this.ProductName = in.readString();
        this.RetailPrice = in.readDouble();
        this.ProductUnit = in.readString();
        this.ThumbImgPath = in.readString();
        this.MemberPrice = in.readDouble();
        this.Quantity = in.readInt();
        this.AppendQuantity = in.readInt();
        this.ScalingFactor = in.readInt();
        this.BaseUnit = in.readString();
        this.ActualDelivery = in.readInt();
    }

    public static final Parcelable.Creator<OrderProductDetail> CREATOR = new Parcelable.Creator<OrderProductDetail>() {
        @Override
        public OrderProductDetail createFromParcel(Parcel source) {
            return new OrderProductDetail(source);
        }

        @Override
        public OrderProductDetail[] newArray(int size) {
            return new OrderProductDetail[size];
        }
    };
}
