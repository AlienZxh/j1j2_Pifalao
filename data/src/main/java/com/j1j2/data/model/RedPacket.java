package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-10-8.
 */

public class RedPacket implements Parcelable {
    /// <summary>
    /// 红包ID
    /// </summary>
    private int RecordId;

    /// <summary>
    /// 线下单号
    /// </summary>
    private String SaleOrderNO;

    /// <summary>
    /// 金额
    /// </summary>
    private double Amount;

    /// <summary>
    /// 服务点（门店）
    /// </summary>
    private String Shop;


    private int ShopId;


    /// <summary>
    /// 是否已经领取
    /// </summary>
    private boolean Settle;


    /// <summary>
    /// 中奖时间
    /// </summary>
    private String WinTimeStr;

    /// <summary>
    /// 领奖时间
    /// </summary>
    private String SettleTimeStr;


    /// <summary>
    /// 失效时间
    /// </summary>
    private String EndTimeStr;

    public int getRecordId() {
        return RecordId;
    }

    public void setRecordId(int recordId) {
        RecordId = recordId;
    }

    public String getSaleOrderNO() {
        return SaleOrderNO;
    }

    public void setSaleOrderNO(String saleOrderNO) {
        SaleOrderNO = saleOrderNO;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getShop() {
        return Shop;
    }

    public void setShop(String shop) {
        Shop = shop;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public boolean isSettle() {
        return Settle;
    }

    public void setSettle(boolean settle) {
        Settle = settle;
    }

    public String getWinTimeStr() {
        return WinTimeStr;
    }

    public void setWinTimeStr(String winTimeStr) {
        WinTimeStr = winTimeStr;
    }

    public String getSettleTimeStr() {
        return SettleTimeStr;
    }

    public void setSettleTimeStr(String settleTimeStr) {
        SettleTimeStr = settleTimeStr;
    }

    public String getEndTimeStr() {
        return EndTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        EndTimeStr = endTimeStr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.RecordId);
        dest.writeString(this.SaleOrderNO);
        dest.writeDouble(this.Amount);
        dest.writeString(this.Shop);
        dest.writeInt(this.ShopId);
        dest.writeByte(this.Settle ? (byte) 1 : (byte) 0);
        dest.writeString(this.WinTimeStr);
        dest.writeString(this.SettleTimeStr);
        dest.writeString(this.EndTimeStr);
    }

    public RedPacket() {
    }

    protected RedPacket(Parcel in) {
        this.RecordId = in.readInt();
        this.SaleOrderNO = in.readString();
        this.Amount = in.readDouble();
        this.Shop = in.readString();
        this.ShopId = in.readInt();
        this.Settle = in.readByte() != 0;
        this.WinTimeStr = in.readString();
        this.SettleTimeStr = in.readString();
        this.EndTimeStr = in.readString();
    }

    public static final Parcelable.Creator<RedPacket> CREATOR = new Parcelable.Creator<RedPacket>() {
        @Override
        public RedPacket createFromParcel(Parcel source) {
            return new RedPacket(source);
        }

        @Override
        public RedPacket[] newArray(int size) {
            return new RedPacket[size];
        }
    };
}
