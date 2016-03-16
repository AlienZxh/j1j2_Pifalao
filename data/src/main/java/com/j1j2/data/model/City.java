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

    public void setPCCId(int PCCId) {
        this.PCCId = PCCId;
    }

    public void setPCCName(String PCCName) {
        this.PCCName = PCCName;
    }

    public void setParentId(int ParentId) {
        this.ParentId = ParentId;
    }

    public void setOpenState(boolean OpenState) {
        this.OpenState = OpenState;
    }

    public void setIsHot(boolean IsHot) {
        this.IsHot = IsHot;
    }

    public void setLastUpdateTime(String LastUpdateTime) {
        this.LastUpdateTime = LastUpdateTime;
    }

    public void setCityType(int CityType) {
        this.CityType = CityType;
    }

    public void setOpenImgPath(String OpenImgPath) {
        this.OpenImgPath = OpenImgPath;
    }

    public void setClosedImgPath(String ClosedImgPath) {
        this.ClosedImgPath = ClosedImgPath;
    }

    public void setServicePointCount(int ServicePointCount) {
        this.ServicePointCount = ServicePointCount;
    }

    public void setPlatformDescription(String PlatformDescription) {
        this.PlatformDescription = PlatformDescription;
    }

    public int getPCCId() {
        return PCCId;
    }

    public String getPCCName() {
        return PCCName;
    }

    public int getParentId() {
        return ParentId;
    }

    public boolean isOpenState() {
        return OpenState;
    }

    public boolean isIsHot() {
        return IsHot;
    }

    public String getLastUpdateTime() {
        return LastUpdateTime;
    }

    public int getCityType() {
        return CityType;
    }

    public String getOpenImgPath() {
        return OpenImgPath;
    }

    public String getClosedImgPath() {
        return ClosedImgPath;
    }

    public int getServicePointCount() {
        return ServicePointCount;
    }

    public String getPlatformDescription() {
        return PlatformDescription;
    }

    @Override
    public String toString() {
        return "City{" +
                "PCCId=" + PCCId +
                ", PCCName='" + PCCName + '\'' +
                ", ParentId=" + ParentId +
                ", OpenState=" + OpenState +
                ", IsHot=" + IsHot +
                ", LastUpdateTime='" + LastUpdateTime + '\'' +
                ", CityType=" + CityType +
                ", OpenImgPath='" + OpenImgPath + '\'' +
                ", ClosedImgPath='" + ClosedImgPath + '\'' +
                ", ServicePointCount=" + ServicePointCount +
                ", PlatformDescription='" + PlatformDescription + '\'' +
                '}';
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
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
