package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-14.
 */
public class Module implements Parcelable {

    /**
     * WareHouseModuleId : 23
     * ModuleName : 超市类批发
     * Subscribed : true
     * DisplayRank : 1
     * ModuleType
     * OrderAmountMinLimit
     * ModuleServiceScope
     * Normal
     */

    private int WareHouseModuleId;
    private String ModuleName;
    private boolean Subscribed;
    private int DisplayRank;
    private int ModuleType;
    private double OrderAmountMinLimit;
    private String ModuleServiceScope;
    private boolean Normal;
    private boolean IsHot;


    public int getWareHouseModuleId() {
        return WareHouseModuleId;
    }

    public void setWareHouseModuleId(int wareHouseModuleId) {
        WareHouseModuleId = wareHouseModuleId;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String moduleName) {
        ModuleName = moduleName;
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

    public int getModuleType() {
        return ModuleType;
    }

    public void setModuleType(int moduleType) {
        ModuleType = moduleType;
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
        dest.writeInt(this.WareHouseModuleId);
        dest.writeString(this.ModuleName);
        dest.writeByte(this.Subscribed ? (byte) 1 : (byte) 0);
        dest.writeInt(this.DisplayRank);
        dest.writeInt(this.ModuleType);
        dest.writeDouble(this.OrderAmountMinLimit);
        dest.writeString(this.ModuleServiceScope);
        dest.writeByte(this.Normal ? (byte) 1 : (byte) 0);
        dest.writeByte(this.IsHot ? (byte) 1 : (byte) 0);
    }

    public Module() {
    }

    protected Module(Parcel in) {
        this.WareHouseModuleId = in.readInt();
        this.ModuleName = in.readString();
        this.Subscribed = in.readByte() != 0;
        this.DisplayRank = in.readInt();
        this.ModuleType = in.readInt();
        this.OrderAmountMinLimit = in.readDouble();
        this.ModuleServiceScope = in.readString();
        this.Normal = in.readByte() != 0;
        this.IsHot = in.readByte() != 0;
    }

    public static final Creator<Module> CREATOR = new Creator<Module>() {
        @Override
        public Module createFromParcel(Parcel source) {
            return new Module(source);
        }

        @Override
        public Module[] newArray(int size) {
            return new Module[size];
        }
    };
}
