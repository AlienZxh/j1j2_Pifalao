package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-9-7.
 */
public class ActivityProductStatistic implements Parcelable {
    private int ProductId;

    /// <summary>
    /// 浏览次数总计
    /// </summary>
    private int ViewSum;

    /// <summary>
    /// 兑换总计
    /// </summary>
    private int ExchangeSum;


    /// <summary>
    /// 活动商品库存剩余，如果有
    /// </summary>
    private Integer Remain;


    /// <summary>
    /// 该活动商品允许参加获取的剩余人数（次数）
    /// </summary>
    private int MaxUserRemain;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getViewSum() {
        return ViewSum;
    }

    public void setViewSum(int viewSum) {
        ViewSum = viewSum;
    }

    public int getExchangeSum() {
        return ExchangeSum;
    }

    public void setExchangeSum(int exchangeSum) {
        ExchangeSum = exchangeSum;
    }

    public Integer getRemain() {
        return Remain;
    }

    public void setRemain(Integer remain) {
        Remain = remain;
    }

    public int getMaxUserRemain() {
        return MaxUserRemain;
    }

    public void setMaxUserRemain(int maxUserRemain) {
        MaxUserRemain = maxUserRemain;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ProductId);
        dest.writeInt(this.ViewSum);
        dest.writeInt(this.ExchangeSum);
        dest.writeValue(this.Remain);
        dest.writeInt(this.MaxUserRemain);
    }

    public ActivityProductStatistic() {
    }

    protected ActivityProductStatistic(Parcel in) {
        this.ProductId = in.readInt();
        this.ViewSum = in.readInt();
        this.ExchangeSum = in.readInt();
        this.Remain = (Integer) in.readValue(Integer.class.getClassLoader());
        this.MaxUserRemain = in.readInt();
    }

    public static final Creator<ActivityProductStatistic> CREATOR = new Creator<ActivityProductStatistic>() {
        @Override
        public ActivityProductStatistic createFromParcel(Parcel source) {
            return new ActivityProductStatistic(source);
        }

        @Override
        public ActivityProductStatistic[] newArray(int size) {
            return new ActivityProductStatistic[size];
        }
    };
}
