package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by alienzxh on 16-9-7.
 */
public class ActivityWinPrize implements Parcelable {

    private int OrderId;

    /// <summary>
    /// 中奖活动期号编号ID
    /// </summary>
    private int LotteryId;


    /// <summary>
    /// 中奖活动期号
    /// </summary>
    private String LotteryNum;

    /// <summary>
    /// 活动商品名称
    /// </summary>
    private String ProductName;

    /// <summary>
    /// 获奖时间
    /// </summary>
    private String WinTime;

    /// <summary>
    /// 活动抽奖总须人数
    /// </summary>
    private int MaxUser;

    ///// <summary>
    ///// 本次活动当前用户参与次数
    ///// </summary>
    private int CurrentUserParticipationTimes;

    /// <summary>
    /// 当前活动 剩余可参加人数
    /// </summary>
    private int MaxUserRemain;

    /// <summary>
    /// 活动订单状态
    /// </summary>
    private int OrderState;

    /// <summary>
    /// 获奖商品信息
    /// </summary>
    private ActivityWinProductHistoryInfo ProductInfo;

    /// <summary>
    /// 获奖者信息
    /// </summary>
    private ActivityParticipator Winner;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getLotteryId() {
        return LotteryId;
    }

    public void setLotteryId(int lotteryId) {
        LotteryId = lotteryId;
    }

    public String getLotteryNum() {
        return LotteryNum;
    }

    public void setLotteryNum(String lotteryNum) {
        LotteryNum = lotteryNum;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getWinTime() {
        return WinTime;
    }

    public void setWinTime(String winTime) {
        WinTime = winTime;
    }

    public int getMaxUser() {
        return MaxUser;
    }

    public void setMaxUser(int maxUser) {
        MaxUser = maxUser;
    }

    public int getCurrentUserParticipationTimes() {
        return CurrentUserParticipationTimes;
    }

    public void setCurrentUserParticipationTimes(int currentUserParticipationTimes) {
        CurrentUserParticipationTimes = currentUserParticipationTimes;
    }

    public int getMaxUserRemain() {
        return MaxUserRemain;
    }

    public void setMaxUserRemain(int maxUserRemain) {
        MaxUserRemain = maxUserRemain;
    }

    public int getOrderState() {
        return OrderState;
    }

    public void setOrderState(int orderState) {
        OrderState = orderState;
    }

    public ActivityWinProductHistoryInfo getProductInfo() {
        return ProductInfo;
    }

    public void setProductInfo(ActivityWinProductHistoryInfo productInfo) {
        ProductInfo = productInfo;
    }

    public ActivityParticipator getWinner() {
        return Winner;
    }

    public void setWinner(ActivityParticipator winner) {
        Winner = winner;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.OrderId);
        dest.writeInt(this.LotteryId);
        dest.writeString(this.LotteryNum);
        dest.writeString(this.ProductName);
        dest.writeString(this.WinTime);
        dest.writeInt(this.MaxUser);
        dest.writeInt(this.CurrentUserParticipationTimes);
        dest.writeInt(this.MaxUserRemain);
        dest.writeInt(this.OrderState);
        dest.writeParcelable(this.ProductInfo, flags);
        dest.writeParcelable(this.Winner, flags);
    }

    public ActivityWinPrize() {
    }

    protected ActivityWinPrize(Parcel in) {
        this.OrderId = in.readInt();
        this.LotteryId = in.readInt();
        this.LotteryNum = in.readString();
        this.ProductName = in.readString();
        this.WinTime = in.readString();
        this.MaxUser = in.readInt();
        this.CurrentUserParticipationTimes = in.readInt();
        this.MaxUserRemain = in.readInt();
        this.OrderState = in.readInt();
        this.ProductInfo = in.readParcelable(ActivityWinProductHistoryInfo.class.getClassLoader());
        this.Winner = in.readParcelable(ActivityParticipator.class.getClassLoader());
    }

    public static final Creator<ActivityWinPrize> CREATOR = new Creator<ActivityWinPrize>() {
        @Override
        public ActivityWinPrize createFromParcel(Parcel source) {
            return new ActivityWinPrize(source);
        }

        @Override
        public ActivityWinPrize[] newArray(int size) {
            return new ActivityWinPrize[size];
        }
    };
}
