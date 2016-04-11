package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-11.
 */
public class City implements Parcelable {


    /**
     * PCCId : 7
     * PCCName : 株  洲
     * ParentId : 4
     * OpenState : false
     * IsHot : true
     * LastUpdateTime : /Date(1432483200000)/
     * CityType : 1
     * OpenImgPath :
     * ClosedImgPath : /Content/images/city/zhuzhou.jpg
     * ServicePointCount : 0
     * PlatformDescription :
     */

    private int PCCId;
    private String PCCName;
    private int ParentId;
    private boolean OpenState;
    private boolean IsHot;
    private String LastUpdateTime;
    private int CityType;
    private String OpenImgPath;
    private String ClosedImgPath;
    private int ServicePointCount;
    private String PlatformDescription;

    public int getPCCId() {
        return PCCId;
    }

    public void setPCCId(int PCCId) {
        this.PCCId = PCCId;
    }

    public String getPCCName() {
        return PCCName;
    }

    public void setPCCName(String PCCName) {
        this.PCCName = PCCName;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public boolean isOpenState() {
        return OpenState;
    }

    public void setOpenState(boolean openState) {
        OpenState = openState;
    }

    public boolean isHot() {
        return IsHot;
    }

    public void setIsHot(boolean isHot) {
        IsHot = isHot;
    }

    public String getLastUpdateTime() {
        return LastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        LastUpdateTime = lastUpdateTime;
    }

    public int getCityType() {
        return CityType;
    }

    public void setCityType(int cityType) {
        CityType = cityType;
    }

    public String getOpenImgPath() {
        return OpenImgPath;
    }

    public void setOpenImgPath(String openImgPath) {
        OpenImgPath = openImgPath;
    }

    public String getClosedImgPath() {
        return ClosedImgPath;
    }

    public void setClosedImgPath(String closedImgPath) {
        ClosedImgPath = closedImgPath;
    }

    public int getServicePointCount() {
        return ServicePointCount;
    }

    public void setServicePointCount(int servicePointCount) {
        ServicePointCount = servicePointCount;
    }

    public String getPlatformDescription() {
        return PlatformDescription;
    }

    public void setPlatformDescription(String platformDescription) {
        PlatformDescription = platformDescription;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.PCCId);
        dest.writeString(this.PCCName);
        dest.writeInt(this.ParentId);
        dest.writeByte(OpenState ? (byte) 1 : (byte) 0);
        dest.writeByte(IsHot ? (byte) 1 : (byte) 0);
        dest.writeString(this.LastUpdateTime);
        dest.writeInt(this.CityType);
        dest.writeString(this.OpenImgPath);
        dest.writeString(this.ClosedImgPath);
        dest.writeInt(this.ServicePointCount);
        dest.writeString(this.PlatformDescription);
    }

    public City() {
    }

    protected City(Parcel in) {
        this.PCCId = in.readInt();
        this.PCCName = in.readString();
        this.ParentId = in.readInt();
        this.OpenState = in.readByte() != 0;
        this.IsHot = in.readByte() != 0;
        this.LastUpdateTime = in.readString();
        this.CityType = in.readInt();
        this.OpenImgPath = in.readString();
        this.ClosedImgPath = in.readString();
        this.ServicePointCount = in.readInt();
        this.PlatformDescription = in.readString();
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
