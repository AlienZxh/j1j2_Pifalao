package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-9-17.
 */
public class ActivityProcessStateProductInfo implements Parcelable {
    /// <summary>
    /// 商品名称
    /// </summary>
    private String ProductName;

    /// <summary>
    /// 商品图片
    /// </summary>
    private String ThumbImg;

    /// <summary>
    /// 期号，如果有
    /// </summary>
    private String LotteryNum;

    /// <summary>
    /// 商品数量
    /// </summary>
    private int Quantity;

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getThumbImg() {
        return ThumbImg;
    }

    public void setThumbImg(String thumbImg) {
        ThumbImg = thumbImg;
    }

    public String getLotteryNum() {
        return LotteryNum;
    }

    public void setLotteryNum(String lotteryNum) {
        LotteryNum = lotteryNum;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ProductName);
        dest.writeString(this.ThumbImg);
        dest.writeString(this.LotteryNum);
        dest.writeInt(this.Quantity);
    }

    public ActivityProcessStateProductInfo() {
    }

    protected ActivityProcessStateProductInfo(Parcel in) {
        this.ProductName = in.readString();
        this.ThumbImg = in.readString();
        this.LotteryNum = in.readString();
        this.Quantity = in.readInt();
    }

    public static final Parcelable.Creator<ActivityProcessStateProductInfo> CREATOR = new Parcelable.Creator<ActivityProcessStateProductInfo>() {
        @Override
        public ActivityProcessStateProductInfo createFromParcel(Parcel source) {
            return new ActivityProcessStateProductInfo(source);
        }

        @Override
        public ActivityProcessStateProductInfo[] newArray(int size) {
            return new ActivityProcessStateProductInfo[size];
        }
    };
}
