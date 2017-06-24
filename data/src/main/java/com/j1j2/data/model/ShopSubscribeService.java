package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-14.
 */
public class ShopSubscribeService implements Parcelable {

    /**
     * ServiceId : 23
     * Name : 超市类批发
     * Subscribed : true
     * DisplayRank : 1
     * ServiceType
     * OrderAmountMinLimit
     * ModuleServiceScope
     * Normal
     */

    private int ServiceId;
    private String Name;
    private boolean Subscribed;
    private int DisplayRank;
    private int ServiceType;
    private double OrderAmountMinLimit;
    private String ModuleServiceScope;
    private boolean Normal;
    private boolean IsHot;


    public int getServiceId() {
        return ServiceId;
    }

    public void setServiceId(int serviceId) {
        ServiceId = serviceId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isSubscribed() {
        return Subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        Subscribed = subscribed;
    }

    public int getDisplayRank() {
        return DisplayRank;
    }

    public void setDisplayRank(int displayRank) {
        DisplayRank = displayRank;
    }

    public int getServiceType() {
        return ServiceType;
    }

    public void setServiceType(int serviceType) {
        ServiceType = serviceType;
    }

    public double getOrderAmountMinLimit() {
        return OrderAmountMinLimit;
    }

    public void setOrderAmountMinLimit(double orderAmountMinLimit) {
        OrderAmountMinLimit = orderAmountMinLimit;
    }

    public String getModuleServiceScope() {
        return ModuleServiceScope;
    }

    public void setModuleServiceScope(String moduleServiceScope) {
        ModuleServiceScope = moduleServiceScope;
    }

    public boolean isNormal() {
        return Normal;
    }

    public void setNormal(boolean normal) {
        Normal = normal;
    }

    public boolean isHot() {
        return IsHot;
    }

    public void setHot(boolean hot) {
        IsHot = hot;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ServiceId);
        dest.writeString(this.Name);
        dest.writeByte(this.Subscribed ? (byte) 1 : (byte) 0);
        dest.writeInt(this.DisplayRank);
        dest.writeInt(this.ServiceType);
        dest.writeDouble(this.OrderAmountMinLimit);
        dest.writeString(this.ModuleServiceScope);
        dest.writeByte(this.Normal ? (byte) 1 : (byte) 0);
        dest.writeByte(this.IsHot ? (byte) 1 : (byte) 0);
    }

    public ShopSubscribeService() {
    }

    protected ShopSubscribeService(Parcel in) {
        this.ServiceId = in.readInt();
        this.Name = in.readString();
        this.Subscribed = in.readByte() != 0;
        this.DisplayRank = in.readInt();
        this.ServiceType = in.readInt();
        this.OrderAmountMinLimit = in.readDouble();
        this.ModuleServiceScope = in.readString();
        this.Normal = in.readByte() != 0;
        this.IsHot = in.readByte() != 0;
    }

    public static final Creator<ShopSubscribeService> CREATOR = new Creator<ShopSubscribeService>() {
        @Override
        public ShopSubscribeService createFromParcel(Parcel source) {
            return new ShopSubscribeService(source);
        }

        @Override
        public ShopSubscribeService[] newArray(int size) {
            return new ShopSubscribeService[size];
        }
    };
}
