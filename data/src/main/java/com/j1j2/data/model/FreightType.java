package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-4-7.
 */
public class FreightType {


    /**
     * Id : 5
     * CategoryName : 送货到家中
     * Fixed : 2
     * DeliveryType
     * Limit : 1.0
     * LessLimitFixed : 1.0
     * Rate : 1.0
     * CategoryType : 5
     * FreightState : true
     * DliveryTimeSegement : 13:00-14:00;14:00-15:00;15:00-16:00;16:00-17:00;17:00-18:00;18:00-19:00
     */

    private int Id;
    private int CategoryType;
    private int SysDeliveryType;
    private String CategoryName;
    private double Fixed;
    private double Limit;
    private double LessLimitFixed;
    private double Rate;
    private boolean FreightState;
    private String DliveryTimeSegement;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public double getFixed() {
        return Fixed;
    }

    public void setFixed(double fixed) {
        Fixed = fixed;
    }

    public double getLimit() {
        return Limit;
    }

    public void setLimit(double limit) {
        Limit = limit;
    }

    public double getLessLimitFixed() {
        return LessLimitFixed;
    }

    public void setLessLimitFixed(double lessLimitFixed) {
        LessLimitFixed = lessLimitFixed;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }

    public int getCategoryType() {
        return CategoryType;
    }

    public void setCategoryType(int categoryType) {
        CategoryType = categoryType;
    }

    public boolean isFreightState() {
        return FreightState;
    }

    public void setFreightState(boolean freightState) {
        FreightState = freightState;
    }

    public String getDliveryTimeSegement() {
        return DliveryTimeSegement;
    }

    public void setDliveryTimeSegement(String dliveryTimeSegement) {
        DliveryTimeSegement = dliveryTimeSegement;
    }

    public int getSysDeliveryType() {
        return SysDeliveryType;
    }

    public void setSysDeliveryType(int sysDeliveryType) {
        SysDeliveryType = sysDeliveryType;
    }
}
