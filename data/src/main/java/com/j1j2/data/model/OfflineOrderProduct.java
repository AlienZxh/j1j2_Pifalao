package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-4-12.
 */
public class OfflineOrderProduct implements Parcelable {

    /**
     * ProductName : [1包]精白沙
     * Spec : 20
     * Unit : 包
     * MemberPrice : 8.3
     * RetailPrice : 8.5
     * Quantity : 1
     * ItemSum : 8.3
     */

    private String ProductName;
    private String Spec;
    private String Unit;
    private double MemberPrice;
    private double RetailPrice;
    private double Quantity;
    private double ItemSum;

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getSpec() {
        return Spec;
    }

    public void setSpec(String Spec) {
        this.Spec = Spec;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(double MemberPrice) {
        this.MemberPrice = MemberPrice;
    }

    public double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(double RetailPrice) {
        this.RetailPrice = RetailPrice;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public double getItemSum() {
        return ItemSum;
    }

    public void setItemSum(double ItemSum) {
        this.ItemSum = ItemSum;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ProductName);
        dest.writeString(this.Spec);
        dest.writeString(this.Unit);
        dest.writeDouble(this.MemberPrice);
        dest.writeDouble(this.RetailPrice);
        dest.writeDouble(this.Quantity);
        dest.writeDouble(this.ItemSum);
    }

    public OfflineOrderProduct() {
    }

    protected OfflineOrderProduct(Parcel in) {
        this.ProductName = in.readString();
        this.Spec = in.readString();
        this.Unit = in.readString();
        this.MemberPrice = in.readDouble();
        this.RetailPrice = in.readDouble();
        this.Quantity = in.readDouble();
        this.ItemSum = in.readDouble();
    }

    public static final Parcelable.Creator<OfflineOrderProduct> CREATOR = new Parcelable.Creator<OfflineOrderProduct>() {
        @Override
        public OfflineOrderProduct createFromParcel(Parcel source) {
            return new OfflineOrderProduct(source);
        }

        @Override
        public OfflineOrderProduct[] newArray(int size) {
            return new OfflineOrderProduct[size];
        }
    };
}
