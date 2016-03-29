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
     */

    private int WareHouseModuleId;
    private String ModuleName;
    private boolean Subscribed;
    private int DisplayRank;

    public int getWareHouseModuleId() {
        return WareHouseModuleId;
    }

    public void setWareHouseModuleId(int WareHouseModuleId) {
        this.WareHouseModuleId = WareHouseModuleId;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String ModuleName) {
        this.ModuleName = ModuleName;
    }

    public boolean isSubscribed() {
        return Subscribed;
    }

    public void setSubscribed(boolean Subscribed) {
        this.Subscribed = Subscribed;
    }

    public int getDisplayRank() {
        return DisplayRank;
    }

    public void setDisplayRank(int DisplayRank) {
        this.DisplayRank = DisplayRank;
    }

    @Override
    public String toString() {
        return "Module{" +
                "WareHouseModuleId=" + WareHouseModuleId +
                ", ModuleName='" + ModuleName + '\'' +
                ", Subscribed=" + Subscribed +
                ", DisplayRank=" + DisplayRank +
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
        dest.writeInt(this.DisplayRank);
    }

    public Module() {
    }

    protected Module(Parcel in) {
        this.WareHouseModuleId = in.readInt();
        this.ModuleName = in.readString();
        this.Subscribed = in.readByte() != 0;
        this.DisplayRank = in.readInt();
    }

    public static final Parcelable.Creator<Module> CREATOR = new Parcelable.Creator<Module>() {
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
