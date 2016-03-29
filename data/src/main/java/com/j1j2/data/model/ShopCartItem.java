package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-21.
 */
public class ShopCartItem implements Parcelable {

    /**
     * ProductId : 31054
     * ProductMainId : 5855
     * ModuleId : 23
     * BarCode : 6933117698638
     * ProductName : 万利来730g荔枝罐头1*12
     * RetailPrice : 15
     * ThumbImgPath : http://data.j1j2.com/ResourceFiles/ProductImages/20141208/70_70/22655_0.jpg
     * MemberPrice : 12.98
     * Quantity : 3
     * Unit : 瓶
     * LimitSalesNumber : 0
     * Views : 0
     * Sells : 69
     * ScalingFactor : 1
     * ActivityId : 0
     * Invalid : false
     * ProductState : 1
     */

    private int ProductId;
    private int ProductMainId;
    private int ModuleId;
    private String BarCode;
    private String ProductName;
    private double RetailPrice;
    private String ThumbImgPath;
    private double MemberPrice;
    private int Quantity;
    private String Unit;
    private int LimitSalesNumber;
    private int Views;
    private int Sells;
    private int ScalingFactor;
    private int ActivityId;
    private boolean Invalid;
    private int ProductState;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getProductMainId() {
        return ProductMainId;
    }

    public void setProductMainId(int productMainId) {
        ProductMainId = productMainId;
    }

    public int getModuleId() {
        return ModuleId;
    }

    public void setModuleId(int moduleId) {
        ModuleId = moduleId;
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

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public int getLimitSalesNumber() {
        return LimitSalesNumber;
    }

    public void setLimitSalesNumber(int limitSalesNumber) {
        LimitSalesNumber = limitSalesNumber;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public int getSells() {
        return Sells;
    }

    public void setSells(int sells) {
        Sells = sells;
    }

    public int getScalingFactor() {
        return ScalingFactor;
    }

    public void setScalingFactor(int scalingFactor) {
        ScalingFactor = scalingFactor;
    }

    public int getActivityId() {
        return ActivityId;
    }

    public void setActivityId(int activityId) {
        ActivityId = activityId;
    }

    public boolean isInvalid() {
        return Invalid;
    }

    public void setInvalid(boolean invalid) {
        Invalid = invalid;
    }

    public int getProductState() {
        return ProductState;
    }

    public void setProductState(int productState) {
        ProductState = productState;
    }

    @Override
    public String toString() {
        return "ShopCartItem{" +
                "ProductId=" + ProductId +
                ", ProductMainId=" + ProductMainId +
                ", ModuleId=" + ModuleId +
                ", BarCode='" + BarCode + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", RetailPrice=" + RetailPrice +
                ", ThumbImgPath='" + ThumbImgPath + '\'' +
                ", MemberPrice=" + MemberPrice +
                ", Quantity=" + Quantity +
                ", Unit='" + Unit + '\'' +
                ", LimitSalesNumber=" + LimitSalesNumber +
                ", Views=" + Views +
                ", Sells=" + Sells +
                ", ScalingFactor=" + ScalingFactor +
                ", ActivityId=" + ActivityId +
                ", Invalid=" + Invalid +
                ", ProductState=" + ProductState +
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
        dest.writeInt(this.ModuleId);
        dest.writeString(this.BarCode);
        dest.writeString(this.ProductName);
        dest.writeDouble(this.RetailPrice);
        dest.writeString(this.ThumbImgPath);
        dest.writeDouble(this.MemberPrice);
        dest.writeInt(this.Quantity);
        dest.writeString(this.Unit);
        dest.writeInt(this.LimitSalesNumber);
        dest.writeInt(this.Views);
        dest.writeInt(this.Sells);
        dest.writeInt(this.ScalingFactor);
        dest.writeInt(this.ActivityId);
        dest.writeByte(Invalid ? (byte) 1 : (byte) 0);
        dest.writeInt(this.ProductState);
    }

    public ShopCartItem() {
    }

    protected ShopCartItem(Parcel in) {
        this.ProductId = in.readInt();
        this.ProductMainId = in.readInt();
        this.ModuleId = in.readInt();
        this.BarCode = in.readString();
        this.ProductName = in.readString();
        this.RetailPrice = in.readDouble();
        this.ThumbImgPath = in.readString();
        this.MemberPrice = in.readDouble();
        this.Quantity = in.readInt();
        this.Unit = in.readString();
        this.LimitSalesNumber = in.readInt();
        this.Views = in.readInt();
        this.Sells = in.readInt();
        this.ScalingFactor = in.readInt();
        this.ActivityId = in.readInt();
        this.Invalid = in.readByte() != 0;
        this.ProductState = in.readInt();
    }

    public static final Parcelable.Creator<ShopCartItem> CREATOR = new Parcelable.Creator<ShopCartItem>() {
        @Override
        public ShopCartItem createFromParcel(Parcel source) {
            return new ShopCartItem(source);
        }

        @Override
        public ShopCartItem[] newArray(int size) {
            return new ShopCartItem[size];
        }
    };
}
