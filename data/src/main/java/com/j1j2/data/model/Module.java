package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-14.
 */
public class Module implements Parcelable {


    /**
     * WareHouseModuleId : 23
     * ModuleName : 进货佬批发网
     * Subscribed : true
     */

    private int WareHouseModuleId;
    private String ModuleName;
    private boolean Subscribed;

    public void setWareHouseModuleId(int WareHouseModuleId) {
        this.WareHouseModuleId = WareHouseModuleId;
    }

    public void setModuleName(String ModuleName) {
        this.ModuleName = ModuleName;
    }

    public void setSubscribed(boolean Subscribed) {
        this.Subscribed = Subscribed;
    }

    public int getWareHouseModuleId() {
        return WareHouseModuleId;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public boolean isSubscribed() {
        return Subscribed;
    }

    @Override
    public String toString() {
        return "Module{" +
                "WareHouseModuleId=" + WareHouseModuleId +
                ", ModuleName='" + ModuleName + '\'' +
                ", Subscribed=" + Subscribed +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.WareHouseModuleId);
        dest.writeString(this.ModuleName);
        dest.writeByte(Subscribed ? (byte) 1 : (byte) 0);
    }

    public Module() {
    }

    protected Module(Parcel in) {
        this.WareHouseModuleId = in.readInt();
        this.ModuleName = in.readString();
        this.Subscribed = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Module> CREATOR = new Parcelable.Creator<Module>() {
        public Module createFromParcel(Parcel source) {
            return new Module(source);
        }

        public Module[] newArray(int size) {
            return new Module[size];
        }
    };
}
