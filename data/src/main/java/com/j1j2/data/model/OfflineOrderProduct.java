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
    private String Image;

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getSpec() {
        return Spec;
    }

    public void setSpec(String spec) {
        Spec = spec;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(double memberPrice) {
        MemberPrice = memberPrice;
    }

    public double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        RetailPrice = retailPrice;
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

    public void setItemSum(double itemSum) {
        ItemSum = itemSum;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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
        dest.writeString(this.Image);
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
        this.Image = in.readString();
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
