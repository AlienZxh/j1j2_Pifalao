package com.j1j2.data.model.requestbody;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-10.
 */
public class EditorUserRecivingAddressBody  {
    private int AddressId;//修改必填

    private boolean IsDefaultAddress;
    private String ReceiverTel;
    private String ReceiverName;
    private double Lat;
    private double Lng;

    private String AddressSegementF;
    private String AddressSegementS;
    private String AddressSegementT;
    private String Address;

    public int getAddressId() {
        return AddressId;
    }

    public void setAddressId(int addressId) {
        AddressId = addressId;
    }

    public boolean isDefaultAddress() {
        return IsDefaultAddress;
    }

    public void setIsDefaultAddress(boolean isDefaultAddress) {
        IsDefaultAddress = isDefaultAddress;
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
}
