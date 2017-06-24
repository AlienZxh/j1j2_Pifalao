package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alienzxh on 16-12-13.
 */

public class ProductCategory implements Parcelable {

    private int CategoryId;

    private String Name;

    private int Ranking;

    private boolean Visible;

    private int ParentId;

    /// <summary>
    /// 该分类下的商品总数,如果是父级分类，
    /// 则是所有子级分类下的商品总数
    /// </summary>
    private int ProductCount;

    private String WebImgPath;

    private String AppImgPath;


    /// <summary>
    /// 是否热门分类
    /// </summary>
    private boolean IsHot;

    /// <summary>
    /// 分类描述
    /// </summary>
    private String SortDescription;


    private List<ProductCategory> ChildCategories;

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

    public int getRanking() {
        return Ranking;
    }

    public void setRanking(int ranking) {
        Ranking = ranking;
    }

    public boolean isVisible() {
        return Visible;
    }

    public void setVisible(boolean visible) {
        Visible = visible;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public int getProductCount() {
        return ProductCount;
    }

    public void setProductCount(int productCount) {
        ProductCount = productCount;
    }

    public String getWebImgPath() {
        return WebImgPath;
    }

    public void setWebImgPath(String webImgPath) {
        WebImgPath = webImgPath;
    }

    public String getAppImgPath() {
        return AppImgPath;
    }

    public void setAppImgPath(String appImgPath) {
        AppImgPath = appImgPath;
    }

    public boolean isHot() {
        return IsHot;
    }

    public void setHot(boolean hot) {
        IsHot = hot;
    }

    public String getSortDescription() {
        return SortDescription;
    }

    public void setSortDescription(String sortDescription) {
        SortDescription = sortDescription;
    }

    public List<ProductCategory> getChildCategories() {
        return ChildCategories;
    }

    public void setChildCategories(List<ProductCategory> childCategories) {
        ChildCategories = childCategories;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.CategoryId);
        dest.writeString(this.Name);
        dest.writeInt(this.Ranking);
        dest.writeByte(this.Visible ? (byte) 1 : (byte) 0);
        dest.writeInt(this.ParentId);
        dest.writeInt(this.ProductCount);
        dest.writeString(this.WebImgPath);
        dest.writeString(this.AppImgPath);
        dest.writeByte(this.IsHot ? (byte) 1 : (byte) 0);
        dest.writeString(this.SortDescription);
        dest.writeList(this.ChildCategories);
    }

    public ProductCategory() {
    }

    protected ProductCategory(Parcel in) {
        this.CategoryId = in.readInt();
        this.Name = in.readString();
        this.Ranking = in.readInt();
        this.Visible = in.readByte() != 0;
        this.ParentId = in.readInt();
        this.ProductCount = in.readInt();
        this.WebImgPath = in.readString();
        this.AppImgPath = in.readString();
        this.IsHot = in.readByte() != 0;
        this.SortDescription = in.readString();
        this.ChildCategories = new ArrayList<ProductCategory>();
        in.readList(this.ChildCategories, ProductCategory.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProductCategory> CREATOR = new Parcelable.Creator<ProductCategory>() {
        @Override
        public ProductCategory createFromParcel(Parcel source) {
            return new ProductCategory(source);
        }

        @Override
        public ProductCategory[] newArray(int size) {
            return new ProductCategory[size];
        }
    };
}
