package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 */
public class EditorUserRecivingAddressBody {
    private int AddressId;
    private int UserId;
    private String Address;
    private String ReceiverTel;
    private String ReceiverName;
    private boolean IsDefaultAddress;
    private String userIdStr;
    private String loginAccount;
    private double Lat;
    private double Lng;

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

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
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

    @Override
    public String toString() {
        return "EditorUserRecivingAddressBody{" +
                "AddressId=" + AddressId +
                ", UserId=" + UserId +
                ", Address='" + Address + '\'' +
                ", ReceiverTel='" + ReceiverTel + '\'' +
                ", ReceiverName='" + ReceiverName + '\'' +
                ", IsDefaultAddress=" + IsDefaultAddress +
                ", userIdStr='" + userIdStr + '\'' +
                ", loginAccount='" + loginAccount + '\'' +
                ", Lat=" + Lat +
                ", Lng=" + Lng +
                '}';
    }
}
