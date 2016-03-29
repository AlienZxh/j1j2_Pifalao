package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-11.
 */
public class User implements Parcelable {

    /**
     * UserId : 760
     * LoginAccount : 15200348636
     * StoreName :
     * RoleId : 10003
     * Point : 122
     * DeliveryTemplateId : 25
     * ServicePointId : 2
     * AdminVerfiy : true
     * UserName : zxh
     * RegisterTimeStr : 2015-04-18 00:00:00
     * ExpireTimeStr : 2017-07-17 00:00:00
     * CurrentServiceTime : 2016-03-15 15:53:25　　
     * VIPRegisterCode : 6523068014
     * Cost : 155.66
     * UserSaveAmount : 22.18
     * UserLevelId : 5
     * Balance : 0.47
     */

    private int UserId;
    private String LoginAccount;
    private String StoreName;
    private int RoleId;
    private int Point;
    private int DeliveryTemplateId;
    private int ServicePointId;
    private boolean AdminVerfiy;
    private String UserName;
    private String RegisterTimeStr;
    private String ExpireTimeStr;
    private String CurrentServiceTime;
    private String VIPRegisterCode;
    private double Cost;
    private double UserSaveAmount;
    private int UserLevelId;
    private double Balance;

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public void setLoginAccount(String LoginAccount) {
        this.LoginAccount = LoginAccount;
    }

    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    public void setRoleId(int RoleId) {
        this.RoleId = RoleId;
    }

    public void setPoint(int Point) {
        this.Point = Point;
    }

    public void setDeliveryTemplateId(int DeliveryTemplateId) {
        this.DeliveryTemplateId = DeliveryTemplateId;
    }

    public void setServicePointId(int ServicePointId) {
        this.ServicePointId = ServicePointId;
    }

    public void setAdminVerfiy(boolean AdminVerfiy) {
        this.AdminVerfiy = AdminVerfiy;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setRegisterTimeStr(String RegisterTimeStr) {
        this.RegisterTimeStr = RegisterTimeStr;
    }

    public void setExpireTimeStr(String ExpireTimeStr) {
        this.ExpireTimeStr = ExpireTimeStr;
    }

    public void setCurrentServiceTime(String CurrentServiceTime) {
        this.CurrentServiceTime = CurrentServiceTime;
    }

    public void setVIPRegisterCode(String VIPRegisterCode) {
        this.VIPRegisterCode = VIPRegisterCode;
    }

    public void setCost(double Cost) {
        this.Cost = Cost;
    }

    public void setUserSaveAmount(double UserSaveAmount) {
        this.UserSaveAmount = UserSaveAmount;
    }

    public void setUserLevelId(int UserLevelId) {
        this.UserLevelId = UserLevelId;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }

    public int getUserId() {
        return UserId;
    }

    public String getLoginAccount() {
        return LoginAccount;
    }

    public String getStoreName() {
        return StoreName;
    }

    public int getRoleId() {
        return RoleId;
    }

    public int getPoint() {
        return Point;
    }

    public int getDeliveryTemplateId() {
        return DeliveryTemplateId;
    }

    public int getServicePointId() {
        return ServicePointId;
    }

    public boolean isAdminVerfiy() {
        return AdminVerfiy;
    }

    public String getUserName() {
        return UserName;
    }

    public String getRegisterTimeStr() {
        return RegisterTimeStr;
    }

    public String getExpireTimeStr() {
        return ExpireTimeStr;
    }

    public String getCurrentServiceTime() {
        return CurrentServiceTime;
    }

    public String getVIPRegisterCode() {
        return VIPRegisterCode;
    }

    public double getCost() {
        return Cost;
    }

    public double getUserSaveAmount() {
        return UserSaveAmount;
    }

    public int getUserLevelId() {
        return UserLevelId;
    }

    public double getBalance() {
        return Balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserId=" + UserId +
                ", LoginAccount='" + LoginAccount + '\'' +
                ", StoreName='" + StoreName + '\'' +
                ", RoleId=" + RoleId +
                ", Point=" + Point +
                ", DeliveryTemplateId=" + DeliveryTemplateId +
                ", ServicePointId=" + ServicePointId +
                ", AdminVerfiy=" + AdminVerfiy +
                ", UserName='" + UserName + '\'' +
                ", RegisterTimeStr='" + RegisterTimeStr + '\'' +
                ", ExpireTimeStr='" + ExpireTimeStr + '\'' +
                ", CurrentServiceTime='" + CurrentServiceTime + '\'' +
                ", VIPRegisterCode='" + VIPRegisterCode + '\'' +
                ", Cost=" + Cost +
                ", UserSaveAmount=" + UserSaveAmount +
                ", UserLevelId=" + UserLevelId +
                ", Balance=" + Balance +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.UserId);
        dest.writeString(this.LoginAccount);
        dest.writeString(this.StoreName);
        dest.writeInt(this.RoleId);
        dest.writeInt(this.Point);
        dest.writeInt(this.DeliveryTemplateId);
        dest.writeInt(this.ServicePointId);
        dest.writeByte(AdminVerfiy ? (byte) 1 : (byte) 0);
        dest.writeString(this.UserName);
        dest.writeString(this.RegisterTimeStr);
        dest.writeString(this.ExpireTimeStr);
        dest.writeString(this.CurrentServiceTime);
        dest.writeString(this.VIPRegisterCode);
        dest.writeDouble(this.Cost);
        dest.writeDouble(this.UserSaveAmount);
        dest.writeInt(this.UserLevelId);
        dest.writeDouble(this.Balance);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.UserId = in.readInt();
        this.LoginAccount = in.readString();
        this.StoreName = in.readString();
        this.RoleId = in.readInt();
        this.Point = in.readInt();
        this.DeliveryTemplateId = in.readInt();
        this.ServicePointId = in.readInt();
        this.AdminVerfiy = in.readByte() != 0;
        this.UserName = in.readString();
        this.RegisterTimeStr = in.readString();
        this.ExpireTimeStr = in.readString();
        this.CurrentServiceTime = in.readString();
        this.VIPRegisterCode = in.readString();
        this.Cost = in.readDouble();
        this.UserSaveAmount = in.readDouble();
        this.UserLevelId = in.readInt();
        this.Balance = in.readDouble();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
