package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-3-23.
 */
public class Address {

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

    public int getAddressId() {
        return AddressId;
    }

    public void setAddressId(int AddressId) {
        this.AddressId = AddressId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getReceiverTel() {
        return ReceiverTel;
    }

    public void setReceiverTel(String ReceiverTel) {
        this.ReceiverTel = ReceiverTel;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String ReceiverName) {
        this.ReceiverName = ReceiverName;
    }

    public boolean isIsDefaultAddress() {
        return IsDefaultAddress;
    }

    public void setIsDefaultAddress(boolean IsDefaultAddress) {
        this.IsDefaultAddress = IsDefaultAddress;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double Lat) {
        this.Lat = Lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double Lng) {
        this.Lng = Lng;
    }
}
