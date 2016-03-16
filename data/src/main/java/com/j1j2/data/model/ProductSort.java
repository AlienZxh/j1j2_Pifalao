package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-14.
 */
public class ProductSort implements Parcelable {

    /**
     * SortId : 1557
     * SortName : 膨化/薯片
     * FoodSortRanking : 2
     * Visible : true
     * ParentSortId : 1553
     * ProductCount : 109
     * WebImgPath : /ResourceFiles/ProductSortImg/Web/5.jpg
     * AppImgPath : /ResourceFiles/ProductSortImg/App/5.jpg
     * IsHot : false
     * SortDescription : 零食工坊
     * ModuleId : 0
     */

    private int SortId;
    private String SortName;
    private int FoodSortRanking;
    private boolean Visible;
    private int ParentSortId;
    private int ProductCount;
    private String WebImgPath;
    private String AppImgPath;
    private boolean IsHot;
    private String SortDescription;
    private int ModuleId;


    public void setSortId(int SortId) {
        this.SortId = SortId;
    }

    public void setSortName(String SortName) {
        this.SortName = SortName;
    }

    public void setFoodSortRanking(int FoodSortRanking) {
        this.FoodSortRanking = FoodSortRanking;
    }

    public void setVisible(boolean Visible) {
        this.Visible = Visible;
    }

    public void setParentSortId(int ParentSortId) {
        this.ParentSortId = ParentSortId;
    }

    public void setProductCount(int ProductCount) {
        this.ProductCount = ProductCount;
    }

    public void setWebImgPath(String WebImgPath) {
        this.WebImgPath = WebImgPath;
    }

    public void setAppImgPath(String AppImgPath) {
        this.AppImgPath = AppImgPath;
    }

    public void setIsHot(boolean IsHot) {
        this.IsHot = IsHot;
    }

    public void setSortDescription(String SortDescription) {
        this.SortDescription = SortDescription;
    }

    public void setModuleId(int ModuleId) {
        this.ModuleId = ModuleId;
    }

    public int getSortId() {
        return SortId;
    }

    public String getSortName() {
        return SortName;
    }

    public int getFoodSortRanking() {
        return FoodSortRanking;
    }

    public boolean isVisible() {
        return Visible;
    }

    public int getParentSortId() {
        return ParentSortId;
    }

    public int getProductCount() {
        return ProductCount;
    }

    public String getWebImgPath() {
        return WebImgPath;
    }

    public String getAppImgPath() {
        return AppImgPath;
    }

    public boolean isIsHot() {
        return IsHot;
    }

    public String getSortDescription() {
        return SortDescription;
    }

    public int getModuleId() {
        return ModuleId;
    }

    @Override
    public String toString() {
        return "ProductSort{" +
                "SortId=" + SortId +
                ", SortName='" + SortName + '\'' +
                ", FoodSortRanking=" + FoodSortRanking +
                ", Visible=" + Visible +
                ", ParentSortId=" + ParentSortId +
                ", ProductCount=" + ProductCount +
                ", WebImgPath='" + WebImgPath + '\'' +
                ", AppImgPath='" + AppImgPath + '\'' +
                ", IsHot=" + IsHot +
                ", SortDescription='" + SortDescription + '\'' +
                ", ModuleId=" + ModuleId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.SortId);
        dest.writeString(this.SortName);
        dest.writeInt(this.FoodSortRanking);
        dest.writeByte(Visible ? (byte) 1 : (byte) 0);
        dest.writeInt(this.ParentSortId);
        dest.writeInt(this.ProductCount);
        dest.writeString(this.WebImgPath);
        dest.writeString(this.AppImgPath);
        dest.writeByte(IsHot ? (byte) 1 : (byte) 0);
        dest.writeString(this.SortDescription);
        dest.writeInt(this.ModuleId);
    }

    public ProductSort() {
    }

    protected ProductSort(Parcel in) {
        this.SortId = in.readInt();
        this.SortName = in.readString();
        this.FoodSortRanking = in.readInt();
        this.Visible = in.readByte() != 0;
        this.ParentSortId = in.readInt();
        this.ProductCount = in.readInt();
        this.WebImgPath = in.readString();
        this.AppImgPath = in.readString();
        this.IsHot = in.readByte() != 0;
        this.SortDescription = in.readString();
        this.ModuleId = in.readInt();
    }

    public static final Parcelable.Creator<ProductSort> CREATOR = new Parcelable.Creator<ProductSort>() {
        public ProductSort createFromParcel(Parcel source) {
            return new ProductSort(source);
        }

        public ProductSort[] newArray(int size) {
            return new ProductSort[size];
        }
    };
}
