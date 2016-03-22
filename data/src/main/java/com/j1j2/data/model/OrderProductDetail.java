package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-22.
 */
public class OrderProductDetail implements Parcelable {

    /**
     * ProductId : 30879
     * OwnCode : null
     * BarCode : 6935284455588
     * ProductName : 卫龙2连包亲嘴豆筋绿色1*12*50
     * RetailPrice : 0.5
     * ProductUnit : 包
     * ThumbImgPath : http://data.j1j2.com/ResourceFiles/OptimizeImgs/2015-06-18/70/93_1_2.jpg
     * MemberPrice : 0.41
     * Quantity : 100
     * AppendQuantity : 0
     * ScalingFactor : 1
     * BaseUnit : 包
     */

    private int ProductId;
    private String OwnCode;
    private String BarCode;
    private String ProductName;
    private double RetailPrice;
    private String ProductUnit;
    private String ThumbImgPath;
    private double MemberPrice;
    private int Quantity;
    private int AppendQuantity;
    private double ScalingFactor;
    private String BaseUnit;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getOwnCode() {
        return OwnCode;
    }

    public void setOwnCode(String ownCode) {
        OwnCode = ownCode;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        RetailPrice = retailPrice;
    }

    public String getProductUnit() {
        return ProductUnit;
    }

    public void setProductUnit(String productUnit) {
        ProductUnit = productUnit;
    }

    public String getThumbImgPath() {
        return ThumbImgPath;
    }

    public void setThumbImgPath(String thumbImgPath) {
        ThumbImgPath = thumbImgPath;
    }

    public double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(double memberPrice) {
        MemberPrice = memberPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getAppendQuantity() {
        return AppendQuantity;
    }

    public void setAppendQuantity(int appendQuantity) {
        AppendQuantity = appendQuantity;
    }

    public double getScalingFactor() {
        return ScalingFactor;
    }

    public void setScalingFactor(double scalingFactor) {
        ScalingFactor = scalingFactor;
    }

    public String getBaseUnit() {
        return BaseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        BaseUnit = baseUnit;
    }

    @Override
    public String toString() {
        return "OrderProductDetail{" +
                "ProductId=" + ProductId +
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
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ProductId);
        dest.writeString(this.OwnCode);
        dest.writeString(this.BarCode);
        dest.writeString(this.ProductName);
        dest.writeDouble(this.RetailPrice);
        dest.writeString(this.ProductUnit);
        dest.writeString(this.ThumbImgPath);
        dest.writeDouble(this.MemberPrice);
        dest.writeInt(this.Quantity);
        dest.writeInt(this.AppendQuantity);
        dest.writeDouble(this.ScalingFactor);
        dest.writeString(this.BaseUnit);
    }

    public OrderProductDetail() {
    }

    protected OrderProductDetail(Parcel in) {
        this.ProductId = in.readInt();
        this.OwnCode = in.readString();
        this.BarCode = in.readString();
        this.ProductName = in.readString();
        this.RetailPrice = in.readDouble();
        this.ProductUnit = in.readString();
        this.ThumbImgPath = in.readString();
        this.MemberPrice = in.readDouble();
        this.Quantity = in.readInt();
        this.AppendQuantity = in.readInt();
        this.ScalingFactor = in.readDouble();
        this.BaseUnit = in.readString();
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
