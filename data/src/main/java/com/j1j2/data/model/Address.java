package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-23.
 */
public class Address implements Parcelable {

    /**
     * AddressId : 2683
     * UserId : 760
     * Address : 湖南省长沙市雨花区民主路
     * ReceiverTel : 15200348636
     * ReceiverName : zxh
     * IsDefaultAddress : true
     * Lat : 28.235209
     * Lng : 113.117742
     */

    private int AddressId;
    private int UserId;
    private String Address;
    private String ReceiverTel;
    private String ReceiverName;
    private boolean IsDefaultAddress;
    private double Lat;
    private double Lng;
    private String AddressSegementF;
    private String AddressSegementS;
    private String AddressSegementT;

    public int getAddressId() {
        return AddressId;
    }

    public void setAddressId(int addressId) {
        AddressId = addressId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getReceiverTel() {
        return ReceiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        ReceiverTel = receiverTel;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public boolean isDefaultAddress() {
        return IsDefaultAddress;
    }

    public void setIsDefaultAddress(boolean isDefaultAddress) {
        IsDefaultAddress = isDefaultAddress;
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

    public String getAddressSegementF() {
        return AddressSegementF;
    }

    public void setAddressSegementF(String addressSegementF) {
        AddressSegementF = addressSegementF;
    }

    public String getAddressSegementS() {
        return AddressSegementS;
    }

    public void setAddressSegementS(String addressSegementS) {
        AddressSegementS = addressSegementS;
    }

    public String getAddressSegementT() {
        return AddressSegementT;
    }

    public void setAddressSegementT(String addressSegementT) {
        AddressSegementT = addressSegementT;
    }

    @Override
    public String toString() {
        return "Address{" +
                "AddressId=" + AddressId +
                ", UserId=" + UserId +
                ", Address='" + Address + '\'' +
                ", ReceiverTel='" + ReceiverTel + '\'' +
                ", ReceiverName='" + ReceiverName + '\'' +
                ", IsDefaultAddress=" + IsDefaultAddress +
                ", Lat=" + Lat +
                ", Lng=" + Lng +
                ", AddressSegementF='" + AddressSegementF + '\'' +
                ", AddressSegementS='" + AddressSegementS + '\'' +
                ", AddressSegementT='" + AddressSegementT + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.AddressId);
        dest.writeInt(this.UserId);
        dest.writeString(this.Address);
        dest.writeString(this.ReceiverTel);
        dest.writeString(this.ReceiverName);
        dest.writeByte(IsDefaultAddress ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.Lat);
        dest.writeDouble(this.Lng);
        dest.writeString(this.AddressSegementF);
        dest.writeString(this.AddressSegementS);
        dest.writeString(this.AddressSegementT);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.AddressId = in.readInt();
        this.UserId = in.readInt();
        this.Address = in.readString();
        this.ReceiverTel = in.readString();
        this.ReceiverName = in.readString();
        this.IsDefaultAddress = in.readByte() != 0;
        this.Lat = in.readDouble();
        this.Lng = in.readDouble();
        this.AddressSegementF = in.readString();
        this.AddressSegementS = in.readString();
        this.AddressSegementT = in.readString();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
