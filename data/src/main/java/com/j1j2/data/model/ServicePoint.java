package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-12.
 */
public class ServicePoint implements Parcelable {

    /**
     * ServicePointId : 8
     * Name : 星沙湖南农大服务点
     * Mobile : 123456
     * AddressDetail : 星沙湖南农业大学农大路东湖小区9栋6号
     * Note : 3
     * State : 1
     * ReferCode : 7175
     * PCCId : 4
     * ApllyTime : /Date(1391270400000)/
     * AuditTime :
     * Lat : 28.192382
     * Lng : 113.091893
     * ServiceTime : 09:00-19:00
     * ServicePhone : 0731-83292702
     * OpenOrClosed : false
     * ServicePointImg : /ServicePointImg/ServicePointHeadImg/2015-04-18/9894e54a-a6bc-44c9-8943-13f56ddb0f88.jpg
     * Instroduce :
     * PickUpSelfSupport : true
     * HomeDeliverySupport : true
     */

    private int ServicePointId;
    private String Name;
    private String Mobile;
    private String AddressDetail;
    private String Note;
    private int State;
    private String ReferCode;
    private int PCCId;
    private String ApllyTime;
    private String AuditTime;
    private double Lat;
    private double Lng;
    private String ServiceTime;
    private String ServicePhone;
    private boolean OpenOrClosed;
    private String ServicePointImg;
    private String Instroduce;
    private boolean PickUpSelfSupport;
    private boolean HomeDeliverySupport;

    public void setServicePointId(int ServicePointId) {
        this.ServicePointId = ServicePointId;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public void setAddressDetail(String AddressDetail) {
        this.AddressDetail = AddressDetail;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public void setState(int State) {
        this.State = State;
    }

    public void setReferCode(String ReferCode) {
        this.ReferCode = ReferCode;
    }

    public void setPCCId(int PCCId) {
        this.PCCId = PCCId;
    }

    public void setApllyTime(String ApllyTime) {
        this.ApllyTime = ApllyTime;
    }

    public void setAuditTime(String AuditTime) {
        this.AuditTime = AuditTime;
    }

    public void setLat(double Lat) {
        this.Lat = Lat;
    }

    public void setLng(double Lng) {
        this.Lng = Lng;
    }

    public void setServiceTime(String ServiceTime) {
        this.ServiceTime = ServiceTime;
    }

    public void setServicePhone(String ServicePhone) {
        this.ServicePhone = ServicePhone;
    }

    public void setOpenOrClosed(boolean OpenOrClosed) {
        this.OpenOrClosed = OpenOrClosed;
    }

    public void setServicePointImg(String ServicePointImg) {
        this.ServicePointImg = ServicePointImg;
    }

    public void setInstroduce(String Instroduce) {
        this.Instroduce = Instroduce;
    }

    public void setPickUpSelfSupport(boolean PickUpSelfSupport) {
        this.PickUpSelfSupport = PickUpSelfSupport;
    }

    public void setHomeDeliverySupport(boolean HomeDeliverySupport) {
        this.HomeDeliverySupport = HomeDeliverySupport;
    }

    public int getServicePointId() {
        return ServicePointId;
    }

    public String getName() {
        return Name;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getAddressDetail() {
        return AddressDetail;
    }

    public String getNote() {
        return Note;
    }

    public int getState() {
        return State;
    }

    public String getReferCode() {
        return ReferCode;
    }

    public int getPCCId() {
        return PCCId;
    }

    public String getApllyTime() {
        return ApllyTime;
    }

    public String getAuditTime() {
        return AuditTime;
    }

    public double getLat() {
        return Lat;
    }

    public double getLng() {
        return Lng;
    }

    public String getServiceTime() {
        return ServiceTime;
    }

    public String getServicePhone() {
        return ServicePhone;
    }

    public boolean isOpenOrClosed() {
        return OpenOrClosed;
    }

    public String getServicePointImg() {
        return ServicePointImg;
    }

    public String getInstroduce() {
        return Instroduce;
    }

    public boolean isPickUpSelfSupport() {
        return PickUpSelfSupport;
    }

    public boolean isHomeDeliverySupport() {
        return HomeDeliverySupport;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ServicePointId);
        dest.writeString(this.Name);
        dest.writeString(this.Mobile);
        dest.writeString(this.AddressDetail);
        dest.writeString(this.Note);
        dest.writeInt(this.State);
        dest.writeString(this.ReferCode);
        dest.writeInt(this.PCCId);
        dest.writeString(this.ApllyTime);
        dest.writeString(this.AuditTime);
        dest.writeDouble(this.Lat);
        dest.writeDouble(this.Lng);
        dest.writeString(this.ServiceTime);
        dest.writeString(this.ServicePhone);
        dest.writeByte(OpenOrClosed ? (byte) 1 : (byte) 0);
        dest.writeString(this.ServicePointImg);
        dest.writeString(this.Instroduce);
        dest.writeByte(PickUpSelfSupport ? (byte) 1 : (byte) 0);
        dest.writeByte(HomeDeliverySupport ? (byte) 1 : (byte) 0);
    }

    public ServicePoint() {
    }

    protected ServicePoint(Parcel in) {
        this.ServicePointId = in.readInt();
        this.Name = in.readString();
        this.Mobile = in.readString();
        this.AddressDetail = in.readString();
        this.Note = in.readString();
        this.State = in.readInt();
        this.ReferCode = in.readString();
        this.PCCId = in.readInt();
        this.ApllyTime = in.readString();
        this.AuditTime = in.readString();
        this.Lat = in.readDouble();
        this.Lng = in.readDouble();
        this.ServiceTime = in.readString();
        this.ServicePhone = in.readString();
        this.OpenOrClosed = in.readByte() != 0;
        this.ServicePointImg = in.readString();
        this.Instroduce = in.readString();
        this.PickUpSelfSupport = in.readByte() != 0;
        this.HomeDeliverySupport = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ServicePoint> CREATOR = new Parcelable.Creator<ServicePoint>() {
        public ServicePoint createFromParcel(Parcel source) {
            return new ServicePoint(source);
        }

        public ServicePoint[] newArray(int size) {
            return new ServicePoint[size];
        }
    };
}
