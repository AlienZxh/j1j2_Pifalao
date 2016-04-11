package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alienzxh on 16-3-15.
 */
public class ProductSimple implements Parcelable {
    /**
     * ModuleId
     * ModuleType
     * BaseUnit:基本单位
     * MainId : 5377
     * CategoryId : 1555
     * Name : 卡夫125g草莓味鬼脸嘟嘟饼干1*24
     * Spec : 125g
     * Brand : 卡夫
     * Rank : 1
     * MainImg : http://data.j1j2.com/ResourceFiles/ProductImages/20140715/70_70/18208_0.jpg
     * IsPromotion : false
     * IsNew : false
     * IsRecommend : false
     * IsBaseItem : false
     * ProductUnits : [{"LimitSalesNumber":0,"TotalSalesLimit":0,"Remains":0,"Views":578,"Sells":329,"ProductId":28425,"Unit":"包","ProductMainId":5377,"RetialPrice":4.5,"MemberPrice":3.92,"ProductState":1,"ProductStateStr":"正常","AvgPrimeCost":null,"LastPrimeCost":3.55,"Menmonics":null,"Factor":1,"IsBaseItem":true,"IsBaseItemStr":"是","ProductRank":28425,"MainImg":"http://data.j1j2.com/ResourceFiles/ProductImages/20140715/70_70/18208_0.jpg","BarCode":"6901668166203","Note":null},{"LimitSalesNumber":0,"TotalSalesLimit":0,"Remains":0,"Views":208,"Sells":98,"ProductId":28426,"Unit":"件","ProductMainId":5377,"RetialPrice":108,"MemberPrice":94.15,"ProductState":1,"ProductStateStr":"正常","AvgPrimeCost":null,"LastPrimeCost":85.08,"Menmonics":null,"Factor":24,"IsBaseItem":false,"IsBaseItemStr":"否","ProductRank":28426,"MainImg":"http://data.j1j2.com/ResourceFiles/ProductImages/20140715/70_70/18208_0.jpg","BarCode":"6901668866202","Note":null}]
     */

    private int ModuleId;
    private int ModuleType;
    private String BaseUnit;
    private int MainId;
    private int CategoryId;
    private String Name;
    private String Spec;
    private String Brand;
    private int Rank;
    private String MainImg;
    private boolean IsPromotion;
    private boolean IsNew;
    private boolean IsRecommend;
    private boolean IsBaseItem;
    /**
     * LimitSalesNumber : 0
     * TotalSalesLimit : 0
     * Remains : 0
     * Views : 578
     * Sells : 329
     * ProductId : 28425
     * Unit : 包
     * ProductMainId : 5377
     * RetialPrice : 4.5
     * MemberPrice : 3.92
     * ProductState : 1
     * ProductStateStr : 正常
     * AvgPrimeCost : null
     * LastPrimeCost : 3.55
     * Menmonics : null
     * Factor : 1
     * IsBaseItem : true
     * IsBaseItemStr : 是
     * ProductRank : 28425
     * MainImg : http://data.j1j2.com/ResourceFiles/ProductImages/20140715/70_70/18208_0.jpg
     * BarCode : 6901668166203
     * Note : null
     */
    private List<ProductUnit> ProductUnits;


    public int getModuleId() {
        return ModuleId;
    }

    public void setModuleId(int moduleId) {
        ModuleId = moduleId;
    }

    public int getModuleType() {
        return ModuleType;
    }

    public void setModuleType(int moduleType) {
        ModuleType = moduleType;
    }

    public String getBaseUnit() {
        return BaseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        BaseUnit = baseUnit;
    }

    public int getMainId() {
        return MainId;
    }

    public void setMainId(int mainId) {
        MainId = mainId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
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

    public boolean isBaseItem() {
        return IsBaseItem;
    }

    public void setIsBaseItem(boolean isBaseItem) {
        IsBaseItem = isBaseItem;
    }

    public List<ProductUnit> getProductUnits() {
        return ProductUnits;
    }

    public void setProductUnits(List<ProductUnit> productUnits) {
        ProductUnits = productUnits;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ModuleId);
        dest.writeInt(this.ModuleType);
        dest.writeString(this.BaseUnit);
        dest.writeInt(this.MainId);
        dest.writeInt(this.CategoryId);
        dest.writeString(this.Name);
        dest.writeString(this.Spec);
        dest.writeString(this.Brand);
        dest.writeInt(this.Rank);
        dest.writeString(this.MainImg);
        dest.writeByte(IsPromotion ? (byte) 1 : (byte) 0);
        dest.writeByte(IsNew ? (byte) 1 : (byte) 0);
        dest.writeByte(IsRecommend ? (byte) 1 : (byte) 0);
        dest.writeByte(IsBaseItem ? (byte) 1 : (byte) 0);
        dest.writeTypedList(ProductUnits);
    }

    public ProductSimple() {
    }

    protected ProductSimple(Parcel in) {
        this.ModuleId = in.readInt();
        this.ModuleType = in.readInt();
        this.BaseUnit = in.readString();
        this.MainId = in.readInt();
        this.CategoryId = in.readInt();
        this.Name = in.readString();
        this.Spec = in.readString();
        this.Brand = in.readString();
        this.Rank = in.readInt();
        this.MainImg = in.readString();
        this.IsPromotion = in.readByte() != 0;
        this.IsNew = in.readByte() != 0;
        this.IsRecommend = in.readByte() != 0;
        this.IsBaseItem = in.readByte() != 0;
        this.ProductUnits = in.createTypedArrayList(ProductUnit.CREATOR);
    }

    public static final Parcelable.Creator<ProductSimple> CREATOR = new Parcelable.Creator<ProductSimple>() {
        @Override
        public ProductSimple createFromParcel(Parcel source) {
            return new ProductSimple(source);
        }

        @Override
        public ProductSimple[] newArray(int size) {
            return new ProductSimple[size];
        }
    };
}
