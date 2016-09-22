package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-9-7.
 */
public class ActivityProductConfig implements Parcelable {
    private int ProductId;

    /// <summary>
    /// 活动商品的总数，如果有
    /// </summary>
    private Integer Count;

    /// <summary>
    /// 获取该商品需要花费的积分，如果有
    /// </summary>
    private Integer CostExchangePoint;

    /// <summary>
    /// 获取该商品需要花费的金额，如果有
    /// </summary>
    private Double CostExchangeMoney;

    /// <summary>
    /// 该商品活动的次数限制，如果有
    /// </summary>
    private int TimesLimits;

    /// <summary>
    /// 该商品活动限制的类型
    /// </summary>
    /// 1:每天限制　　2：每周限制　　　３：每月限制
    private int TimesLimitsType;

    /// <summary>
    /// 限制次数文本信息
    /// </summary>
    private String TimeLimitsStr;

    /// <summary>
    /// 该商品活动最多允许多少人参加
    /// </summary>
    private int MaxUsers;

    /// <summary>
    /// 该商品活动每次活动获取奖品的人数
    /// </summary>
    private int WinCounts;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public Integer getCostExchangePoint() {
        return CostExchangePoint;
    }

    public void setCostExchangePoint(Integer costExchangePoint) {
        CostExchangePoint = costExchangePoint;
    }

    public Double getCostExchangeMoney() {
        return CostExchangeMoney;
    }

    public void setCostExchangeMoney(Double costExchangeMoney) {
        CostExchangeMoney = costExchangeMoney;
    }

    public int getTimesLimits() {
        return TimesLimits;
    }

    public void setTimesLimits(int timesLimits) {
        TimesLimits = timesLimits;
    }

    public int getTimesLimitsType() {
        return TimesLimitsType;
    }

    public void setTimesLimitsType(int timesLimitsType) {
        TimesLimitsType = timesLimitsType;
    }

    public String getTimeLimitsStr() {
        return TimeLimitsStr;
    }

    public void setTimeLimitsStr(String timeLimitsStr) {
        TimeLimitsStr = timeLimitsStr;
    }

    public int getMaxUsers() {
        return MaxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        MaxUsers = maxUsers;
    }

    public int getWinCounts() {
        return WinCounts;
    }

    public void setWinCounts(int winCounts) {
        WinCounts = winCounts;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ProductId);
        dest.writeValue(this.Count);
        dest.writeValue(this.CostExchangePoint);
        dest.writeValue(this.CostExchangeMoney);
        dest.writeInt(this.TimesLimits);
        dest.writeInt(this.TimesLimitsType);
        dest.writeString(this.TimeLimitsStr);
        dest.writeInt(this.MaxUsers);
        dest.writeInt(this.WinCounts);
    }

    public ActivityProductConfig() {
    }

    protected ActivityProductConfig(Parcel in) {
        this.ProductId = in.readInt();
        this.Count = (Integer) in.readValue(Integer.class.getClassLoader());
        this.CostExchangePoint = (Integer) in.readValue(Integer.class.getClassLoader());
        this.CostExchangeMoney = (Double) in.readValue(Double.class.getClassLoader());
        this.TimesLimits = in.readInt();
        this.TimesLimitsType = in.readInt();
        this.TimeLimitsStr = in.readString();
        this.MaxUsers = in.readInt();
        this.WinCounts = in.readInt();
    }

    public static final Creator<ActivityProductConfig> CREATOR = new Creator<ActivityProductConfig>() {
        @Override
        public ActivityProductConfig createFromParcel(Parcel source) {
            return new ActivityProductConfig(source);
        }

        @Override
        public ActivityProductConfig[] newArray(int size) {
            return new ActivityProductConfig[size];
        }
    };
}
