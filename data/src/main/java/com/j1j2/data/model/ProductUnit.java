package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-16.
 */
public class ProductUnit implements Parcelable {

    private int LimitSalesNumber;
    private int TotalSalesLimit;
    private int Remains;
    private int Views;
    private int Sells;
    private int ProductId;
    private String Unit;
    private int ProductMainId;
    private double RetialPrice;
    private double MemberPrice;
    private int ProductState;
    private String ProductStateStr;
    private String AvgPrimeCost;
    private double LastPrimeCost;
    private String Menmonics;
    private int Factor;
    private boolean IsBaseItem;
    private String IsBaseItemStr;
    private int ProductRank;
    private String MainImg;
    private String BarCode;
    private String Note;

    public int getLimitSalesNumber() {
        return LimitSalesNumber;
    }

    public void setLimitSalesNumber(int limitSalesNumber) {
        LimitSalesNumber = limitSalesNumber;
    }

    public int getTotalSalesLimit() {
        return TotalSalesLimit;
    }

    public void setTotalSalesLimit(int totalSalesLimit) {
        TotalSalesLimit = totalSalesLimit;
    }

    public int getRemains() {
        return Remains;
    }

    public void setRemains(int remains) {
        Remains = remains;
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

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public int getProductMainId() {
        return ProductMainId;
    }

    public void setProductMainId(int productMainId) {
        ProductMainId = productMainId;
    }

    public double getRetialPrice() {
        return RetialPrice;
    }

    public void setRetialPrice(double retialPrice) {
        RetialPrice = retialPrice;
    }

    public double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(double memberPrice) {
        MemberPrice = memberPrice;
    }

    public int getProductState() {
        return ProductState;
    }

    public void setProductState(int productState) {
        ProductState = productState;
    }

    public String getProductStateStr() {
        return ProductStateStr;
    }

    public void setProductStateStr(String productStateStr) {
        ProductStateStr = productStateStr;
    }

    public String getAvgPrimeCost() {
        return AvgPrimeCost;
    }

    public void setAvgPrimeCost(String avgPrimeCost) {
        AvgPrimeCost = avgPrimeCost;
    }

    public double getLastPrimeCost() {
        return LastPrimeCost;
    }

    public void setLastPrimeCost(double lastPrimeCost) {
        LastPrimeCost = lastPrimeCost;
    }

    public String getMenmonics() {
        return Menmonics;
    }

    public void setMenmonics(String menmonics) {
        Menmonics = menmonics;
    }

    public int getFactor() {
        return Factor;
    }

    public void setFactor(int factor) {
        Factor = factor;
    }

    public boolean isBaseItem() {
        return IsBaseItem;
    }

    public void setIsBaseItem(boolean isBaseItem) {
        IsBaseItem = isBaseItem;
    }

    public String getIsBaseItemStr() {
        return IsBaseItemStr;
    }

    public void setIsBaseItemStr(String isBaseItemStr) {
        IsBaseItemStr = isBaseItemStr;
    }

    public int getProductRank() {
        return ProductRank;
    }

    public void setProductRank(int productRank) {
        ProductRank = productRank;
    }

    public String getMainImg() {
        return MainImg;
    }

    public void setMainImg(String mainImg) {
        MainImg = mainImg;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.LimitSalesNumber);
        dest.writeInt(this.TotalSalesLimit);
        dest.writeInt(this.Remains);
        dest.writeInt(this.Views);
        dest.writeInt(this.Sells);
        dest.writeInt(this.ProductId);
        dest.writeString(this.Unit);
        dest.writeInt(this.ProductMainId);
        dest.writeDouble(this.RetialPrice);
        dest.writeDouble(this.MemberPrice);
        dest.writeInt(this.ProductState);
        dest.writeString(this.ProductStateStr);
        dest.writeString(this.AvgPrimeCost);
        dest.writeDouble(this.LastPrimeCost);
        dest.writeString(this.Menmonics);
        dest.writeInt(this.Factor);
        dest.writeByte(IsBaseItem ? (byte) 1 : (byte) 0);
        dest.writeString(this.IsBaseItemStr);
        dest.writeInt(this.ProductRank);
        dest.writeString(this.MainImg);
        dest.writeString(this.BarCode);
        dest.writeString(this.Note);
    }

    public ProductUnit() {
    }

    protected ProductUnit(Parcel in) {
        this.LimitSalesNumber = in.readInt();
        this.TotalSalesLimit = in.readInt();
        this.Remains = in.readInt();
        this.Views = in.readInt();
        this.Sells = in.readInt();
        this.ProductId = in.readInt();
        this.Unit = in.readString();
        this.ProductMainId = in.readInt();
        this.RetialPrice = in.readDouble();
        this.MemberPrice = in.readDouble();
        this.ProductState = in.readInt();
        this.ProductStateStr = in.readString();
        this.AvgPrimeCost = in.readString();
        this.LastPrimeCost = in.readDouble();
        this.Menmonics = in.readString();
        this.Factor = in.readInt();
        this.IsBaseItem = in.readByte() != 0;
        this.IsBaseItemStr = in.readString();
        this.ProductRank = in.readInt();
        this.MainImg = in.readString();
        this.BarCode = in.readString();
        this.Note = in.readString();
    }

    public static final Parcelable.Creator<ProductUnit> CREATOR = new Parcelable.Creator<ProductUnit>() {
        public ProductUnit createFromParcel(Parcel source) {
            return new ProductUnit(source);
        }

        public ProductUnit[] newArray(int size) {
            return new ProductUnit[size];
        }
    };
}
