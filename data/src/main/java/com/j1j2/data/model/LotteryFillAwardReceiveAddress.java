package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-9-18.
 */
public class LotteryFillAwardReceiveAddress {

    private int OrderId;


    private String Name;


    private String Mobile;


    private String Address;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
