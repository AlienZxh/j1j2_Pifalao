package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by alienzxh on 16-12-13.
 */

public class Product implements Parcelable {

    private int MainId;
    private String Name;
    private Integer ParentCategoryId;
    private int SubCategoryId;
    private String NameSpell;
    private String Spec;
    private String Brand;
    private String InvalidStr;
    private Boolean Invalid;
    private Boolean IsPromotion;
    private Boolean IsNew;
    private Boolean IsRecommend;
    private String Introduction;
    private String MainNormalImg;
    private String MainThumImg;
    private String BaseUnit;
    private String TradeUnit;
    private double TradeToBaseFactor;
    private int Ranking;
    private int ShopID;

    private int ModuleId ;
    private int ModuleType ;

    private List<ProductImg> Imgs;

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

    public Integer getParentCategoryId() {
        return ParentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        ParentCategoryId = parentCategoryId;
    }

    public int getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        SubCategoryId = subCategoryId;
    }

    public String getNameSpell() {
        return NameSpell;
    }

    public void setNameSpell(String nameSpell) {
        NameSpell = nameSpell;
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

    public String getInvalidStr() {
        return InvalidStr;
    }

    public void setInvalidStr(String invalidStr) {
        InvalidStr = invalidStr;
    }

    public Boolean getInvalid() {
        return Invalid;
    }

    public void setInvalid(Boolean invalid) {
        Invalid = invalid;
    }

    public Boolean getPromotion() {
        return IsPromotion;
    }

    public void setPromotion(Boolean promotion) {
        IsPromotion = promotion;
    }

    public Boolean getNew() {
        return IsNew;
    }

    public void setNew(Boolean aNew) {
        IsNew = aNew;
    }

    public Boolean getRecommend() {
        return IsRecommend;
    }

    public void setRecommend(Boolean recommend) {
        IsRecommend = recommend;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    public String getMainNormalImg() {
        return MainNormalImg;
    }

    public void setMainNormalImg(String mainNormalImg) {
        MainNormalImg = mainNormalImg;
    }

    public String getMainThumImg() {
        return MainThumImg;
    }

    public void setMainThumImg(String mainThumImg) {
        MainThumImg = mainThumImg;
    }

    public String getBaseUnit() {
        return BaseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        BaseUnit = baseUnit;
    }

    public String getTradeUnit() {
        return TradeUnit;
    }

    public void setTradeUnit(String tradeUnit) {
        TradeUnit = tradeUnit;
    }

    public double getTradeToBaseFactor() {
        return TradeToBaseFactor;
    }

    public void setTradeToBaseFactor(double tradeToBaseFactor) {
        TradeToBaseFactor = tradeToBaseFactor;
    }

    public int getRanking() {
        return Ranking;
    }

    public void setRanking(int ranking) {
        Ranking = ranking;
    }

    public int getShopID() {
        return ShopID;
    }

    public void setShopID(int shopID) {
        ShopID = shopID;
    }

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

    public List<ProductImg> getImgs() {
        return Imgs;
    }

    public void setImgs(List<ProductImg> imgs) {
        Imgs = imgs;
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
        dest.writeInt(this.MainId);
        dest.writeString(this.Name);
        dest.writeValue(this.ParentCategoryId);
        dest.writeInt(this.SubCategoryId);
        dest.writeString(this.NameSpell);
        dest.writeString(this.Spec);
        dest.writeString(this.Brand);
        dest.writeString(this.InvalidStr);
        dest.writeValue(this.Invalid);
        dest.writeValue(this.IsPromotion);
        dest.writeValue(this.IsNew);
        dest.writeValue(this.IsRecommend);
        dest.writeString(this.Introduction);
        dest.writeString(this.MainNormalImg);
        dest.writeString(this.MainThumImg);
        dest.writeString(this.BaseUnit);
        dest.writeString(this.TradeUnit);
        dest.writeDouble(this.TradeToBaseFactor);
        dest.writeInt(this.Ranking);
        dest.writeInt(this.ShopID);
        dest.writeInt(this.ModuleId);
        dest.writeInt(this.ModuleType);
        dest.writeTypedList(this.Imgs);
        dest.writeTypedList(this.ProductUnits);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.MainId = in.readInt();
        this.Name = in.readString();
        this.ParentCategoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.SubCategoryId = in.readInt();
        this.NameSpell = in.readString();
        this.Spec = in.readString();
        this.Brand = in.readString();
        this.InvalidStr = in.readString();
        this.Invalid = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.IsPromotion = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.IsNew = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.IsRecommend = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.Introduction = in.readString();
        this.MainNormalImg = in.readString();
        this.MainThumImg = in.readString();
        this.BaseUnit = in.readString();
        this.TradeUnit = in.readString();
        this.TradeToBaseFactor = in.readDouble();
        this.Ranking = in.readInt();
        this.ShopID = in.readInt();
        this.ModuleId = in.readInt();
        this.ModuleType = in.readInt();
        this.Imgs = in.createTypedArrayList(ProductImg.CREATOR);
        this.ProductUnits = in.createTypedArrayList(ProductUnit.CREATOR);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
