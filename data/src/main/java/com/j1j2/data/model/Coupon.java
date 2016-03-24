package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-22.
 */
public class Coupon implements Parcelable {

    /**
     * CouponId : 2084
     * CouponCode : a32684ff-4f65-4fe6-9298-63f2b16de442
     * CouponName : 2元配送券
     * CouponValue : 2
     * Type : 1
     * Constraints :
     * State : 1
     * UserId : 760
     * OrderId :
     * CreateTimeStr : 2015-02-04 11:03:36
     * UsedTimeStr :
     * ExpiryDateStr : 9999-12-31 23:59:59
     * ModuleIdStr :
     */

    private int CouponId;
    private String CouponCode;
    private String CouponName;
    private double CouponValue;
    private int Type;
    private double Constraints;//限制
    private int State;//1:为使用 2：已使用 3：已作废
    private int UserId;
    private int OrderId;
    private String CreateTimeStr;
    private String UsedTimeStr;
    private String ExpiryDateStr;
    private int ModuleIdStr;

    public int getCouponId() {
        return CouponId;
    }

    public void setCouponId(int couponId) {
        CouponId = couponId;
    }

    public String getCouponCode() {
        return CouponCode;
    }

    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }

    public String getCouponName() {
        return CouponName;
    }

    public void setCouponName(String couponName) {
        CouponName = couponName;
    }

    public double getCouponValue() {
        return CouponValue;
    }

    public void setCouponValue(double couponValue) {
        CouponValue = couponValue;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public double getConstraints() {
        return Constraints;
    }

    public void setConstraints(double constraints) {
        Constraints = constraints;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getCreateTimeStr() {
        return CreateTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        CreateTimeStr = createTimeStr;
    }

    public String getUsedTimeStr() {
        return UsedTimeStr;
    }

    public void setUsedTimeStr(String usedTimeStr) {
        UsedTimeStr = usedTimeStr;
    }

    public String getExpiryDateStr() {
        return ExpiryDateStr;
    }

    public void setExpiryDateStr(String expiryDateStr) {
        ExpiryDateStr = expiryDateStr;
    }

    public int getModuleIdStr() {
        return ModuleIdStr;
    }

    public void setModuleIdStr(int moduleIdStr) {
        ModuleIdStr = moduleIdStr;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "CouponId=" + CouponId +
                ", CouponCode='" + CouponCode + '\'' +
                ", CouponName='" + CouponName + '\'' +
                ", CouponValue=" + CouponValue +
                ", Type=" + Type +
                ", Constraints=" + Constraints +
                ", State=" + State +
                ", UserId=" + UserId +
                ", OrderId=" + OrderId +
                ", CreateTimeStr='" + CreateTimeStr + '\'' +
                ", UsedTimeStr='" + UsedTimeStr + '\'' +
                ", ExpiryDateStr='" + ExpiryDateStr + '\'' +
                ", ModuleIdStr=" + ModuleIdStr +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.CouponId);
        dest.writeString(this.CouponCode);
        dest.writeString(this.CouponName);
        dest.writeDouble(this.CouponValue);
        dest.writeInt(this.Type);
        dest.writeDouble(this.Constraints);
        dest.writeInt(this.State);
        dest.writeInt(this.UserId);
        dest.writeInt(this.OrderId);
        dest.writeString(this.CreateTimeStr);
        dest.writeString(this.UsedTimeStr);
        dest.writeString(this.ExpiryDateStr);
        dest.writeInt(this.ModuleIdStr);
    }

    public Coupon() {
    }

    protected Coupon(Parcel in) {
        this.CouponId = in.readInt();
        this.CouponCode = in.readString();
        this.CouponName = in.readString();
        this.CouponValue = in.readDouble();
        this.Type = in.readInt();
        this.Constraints = in.readDouble();
        this.State = in.readInt();
        this.UserId = in.readInt();
        this.OrderId = in.readInt();
        this.CreateTimeStr = in.readString();
        this.UsedTimeStr = in.readString();
        this.ExpiryDateStr = in.readString();
        this.ModuleIdStr = in.readInt();
    }

    public static final Parcelable.Creator<Coupon> CREATOR = new Parcelable.Creator<Coupon>() {
        @Override
        public Coupon createFromParcel(Parcel source) {
            return new Coupon(source);
        }

        @Override
        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };
}
