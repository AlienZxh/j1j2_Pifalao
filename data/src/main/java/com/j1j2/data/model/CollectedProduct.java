package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by alienzxh on 16-3-24.
 */
public class CollectedProduct implements Parcelable {

    /**
     * MainId : 5838
     * Name : 伊利190mlQQ星成长益智奶1*6*15&
     * Spec : 190ml
     * Brand : 伊利
     * Rank : 0
     * Invalid : false
     * MainImg : http://data.j1j2.com/ResourceFiles/OptimizeImgs/2015-06-18/240/3124_1_1.jpg
     * IsPromotion : false
     * IsNew : false
     * IsRecommend : false
     * ProductUnits : [{"LimitSalesNumber":0,"TotalSalesLimit":0,"Remains":0,"Views":127,"Sells":28,"ProductId":27575,"Unit":"提","ProductMainId":5838,"RetialPrice":48,"MemberPrice":42.99,"ProductState":1,"ProductStateStr":"正常","AvgPrimeCost":null,"LastPrimeCost":39.8,"Menmonics":null,"Factor":15,"IsBaseItem":true,"IsBaseItemStr":"是","ProductRank":1,"MainImg":"http://data.j1j2.com/ResourceFiles/OptimizeImgs/2015-06-18/70/3124_1_1.jpg","BarCode":"6907992510361","Note":null},{"LimitSalesNumber":0,"TotalSalesLimit":0,"Remains":0,"Views":87,"Sells":44,"ProductId":27574,"Unit":"盒","ProductMainId":5838,"RetialPrice":3.2,"MemberPrice":2.98,"ProductState":2,"ProductStateStr":"冻结","AvgPrimeCost":null,"LastPrimeCost":2.65,"Menmonics":null,"Factor":1,"IsBaseItem":false,"IsBaseItemStr":"否","ProductRank":27574,"MainImg":"","BarCode":"6907992510453","Note":null}]
     */

    private int MainId;
    private String Name;
    private String Spec;
    private String Brand;
    private int Rank;
    private boolean Invalid;
    private String MainImg;
    private boolean IsPromotion;
    private boolean IsNew;
    private boolean IsRecommend;
    /**
     * LimitSalesNumber : 0
     * TotalSalesLimit : 0
     * Remains : 0
     * Views : 127
     * Sells : 28
     * ProductId : 27575
     * Unit : 提
     * ProductMainId : 5838
     * RetialPrice : 48
     * MemberPrice : 42.99
     * ProductState : 1
     * ProductStateStr : 正常
     * AvgPrimeCost : null
     * LastPrimeCost : 39.8
     * Menmonics : null
     * Factor : 15
     * IsBaseItem : true
     * IsBaseItemStr : 是
     * ProductRank : 1
     * MainImg : http://data.j1j2.com/ResourceFiles/OptimizeImgs/2015-06-18/70/3124_1_1.jpg
     * BarCode : 6907992510361
     * Note : null
     */

    private List<ProductUnit> ProductUnits;

    public int getMainId() {
        return MainId;
    }

    public void setMainId(int mainId) {
        MainId = mainId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSpec() {
        return Spec;
    }

    public void setSpec(String spec) {
        Spec = spec;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public boolean isInvalid() {
        return Invalid;
    }

    public void setInvalid(boolean invalid) {
        Invalid = invalid;
    }

    public String getMainImg() {
        return MainImg;
    }

    public void setMainImg(String mainImg) {
        MainImg = mainImg;
    }

    public boolean isPromotion() {
        return IsPromotion;
    }

    public void setIsPromotion(boolean isPromotion) {
        IsPromotion = isPromotion;
    }

    public boolean isNew() {
        return IsNew;
    }

    public void setIsNew(boolean isNew) {
        IsNew = isNew;
    }

    public boolean isRecommend() {
        return IsRecommend;
    }

    public void setIsRecommend(boolean isRecommend) {
        IsRecommend = isRecommend;
    }

    public List<ProductUnit> getProductUnits() {
        return ProductUnits;
    }

    public void setProductUnits(List<ProductUnit> productUnits) {
        ProductUnits = productUnits;
    }

    @Override
    public String toString() {
        return "CollectedProduct{" +
                "MainId=" + MainId +
                ", Name='" + Name + '\'' +
                ", Spec='" + Spec + '\'' +
                ", Brand='" + Brand + '\'' +
                ", Rank=" + Rank +
                ", Invalid=" + Invalid +
                ", MainImg='" + MainImg + '\'' +
                ", IsPromotion=" + IsPromotion +
                ", IsNew=" + IsNew +
                ", IsRecommend=" + IsRecommend +
                ", ProductUnits=" + ProductUnits +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.MainId);
        dest.writeString(this.Name);
        dest.writeString(this.Spec);
        dest.writeString(this.Brand);
        dest.writeInt(this.Rank);
        dest.writeByte(Invalid ? (byte) 1 : (byte) 0);
        dest.writeString(this.MainImg);
        dest.writeByte(IsPromotion ? (byte) 1 : (byte) 0);
        dest.writeByte(IsNew ? (byte) 1 : (byte) 0);
        dest.writeByte(IsRecommend ? (byte) 1 : (byte) 0);
        dest.writeTypedList(ProductUnits);
    }

    public CollectedProduct() {
    }

    protected CollectedProduct(Parcel in) {
        this.MainId = in.readInt();
        this.Name = in.readString();
        this.Spec = in.readString();
        this.Brand = in.readString();
        this.Rank = in.readInt();
        this.Invalid = in.readByte() != 0;
        this.MainImg = in.readString();
        this.IsPromotion = in.readByte() != 0;
        this.IsNew = in.readByte() != 0;
        this.IsRecommend = in.readByte() != 0;
        this.ProductUnits = in.createTypedArrayList(ProductUnit.CREATOR);
    }

    public static final Parcelable.Creator<CollectedProduct> CREATOR = new Parcelable.Creator<CollectedProduct>() {
        @Override
        public CollectedProduct createFromParcel(Parcel source) {
            return new CollectedProduct(source);
        }

        @Override
        public CollectedProduct[] newArray(int size) {
            return new CollectedProduct[size];
        }
    };
}
