package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

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
    //________________________________________
    private transient double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    //______________________________________

    public int getServicePointId() {
        return ServicePointId;
    }

    public void setServicePointId(int servicePointId) {
        ServicePointId = servicePointId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAddressDetail() {
        return AddressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        AddressDetail = addressDetail;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getReferCode() {
        return ReferCode;
    }

    public void setReferCode(String referCode) {
        ReferCode = referCode;
    }

    public int getPCCId() {
        return PCCId;
    }

    public void setPCCId(int PCCId) {
        this.PCCId = PCCId;
    }

    public String getApllyTime() {
        return ApllyTime;
    }

    public void setApllyTime(String apllyTime) {
        ApllyTime = apllyTime;
    }

    public String getAuditTime() {
        return AuditTime;
    }

    public void setAuditTime(String auditTime) {
        AuditTime = auditTime;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }

    public String getServiceTime() {
        return ServiceTime;
    }

    public void setServiceTime(String serviceTime) {
        ServiceTime = serviceTime;
    }

    public String getServicePhone() {
        return ServicePhone;
    }

    public void setServicePhone(String servicePhone) {
        ServicePhone = servicePhone;
    }

    public boolean isOpenOrClosed() {
        return OpenOrClosed;
    }

    public void setOpenOrClosed(boolean openOrClosed) {
        OpenOrClosed = openOrClosed;
    }

    public String getServicePointImg() {
        return ServicePointImg;
    }

    public void setServicePointImg(String servicePointImg) {
        ServicePointImg = servicePointImg;
    }

    public String getInstroduce() {
        return Instroduce;
    }

    public void setInstroduce(String instroduce) {
        Instroduce = instroduce;
    }

    public boolean isPickUpSelfSupport() {
        return PickUpSelfSupport;
    }

    public void setPickUpSelfSupport(boolean pickUpSelfSupport) {
        PickUpSelfSupport = pickUpSelfSupport;
    }

    public boolean isHomeDeliverySupport() {
        return HomeDeliverySupport;
    }

    public void setHomeDeliverySupport(boolean homeDeliverySupport) {
        HomeDeliverySupport = homeDeliverySupport;
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
        dest.writeDouble(this.distance);
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
        this.distance = in.readDouble();
    }

    public static final Parcelable.Creator<ServicePoint> CREATOR = new Parcelable.Creator<ServicePoint>() {
        @Override
        public ServicePoint createFromParcel(Parcel source) {
            return new ServicePoint(source);
        }

        @Override
        public ServicePoint[] newArray(int size) {
            return new ServicePoint[size];
        }
    };
}
