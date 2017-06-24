package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-12-15.
 */

public class ShopExpressConfig implements Parcelable {

    public int ShopId;
    public String ShopNO;
    public String Name;
    public String Mobile;
    public String AddressDetail;
    public double Lat;
    public double Lng;

    public int State;

    /// <summary>
    /// 营业时间
    /// </summary>
    public String ServiceTime;

    /// <summary>
    /// 配送时间
    /// </summary>
    public String DeliveryTime;

    /// <summary>
    /// 根据营业时间 以及 State的值  决定是否正在营业或者打烊
    /// </summary>
    public boolean OpenOrClosed;


    /// <summary>
    /// 服务点图片
    /// </summary>
    public String ShopImg;

    /// <summary>
    /// 服务点介绍
    /// </summary>
    public String Instroduce;

    /// <summary>
    /// 支持服务点自提
    /// </summary>
    public boolean PickUpSelfSupport;


    /// <summary>
    /// 支持送货上门
    /// </summary>
    public boolean HomeDeliverySupport;

    /// <summary>
    /// 服务点的服务配送范围
    /// </summary>
    public String ServiceAreas;

    /// <summary>
    /// 门店快送订单起送的最小值
    /// </summary>
    public double ShopOrderAmountMin;

    /// <summary>
    /// 配送费
    /// </summary>
    public double FreightAmount;

    private String[] ServiceAreasArray;

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getShopNO() {
        return ShopNO;
    }

    public void setShopNO(String shopNO) {
        ShopNO = shopNO;
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

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getServiceTime() {
        return ServiceTime;
    }

    public void setServiceTime(String serviceTime) {
        ServiceTime = serviceTime;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public boolean isOpenOrClosed() {
        return OpenOrClosed;
    }

    public void setOpenOrClosed(boolean openOrClosed) {
        OpenOrClosed = openOrClosed;
    }

    public String getShopImg() {
        return ShopImg;
    }

    public void setShopImg(String shopImg) {
        ShopImg = shopImg;
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

    public String getServiceAreas() {
        return ServiceAreas;
    }

    public void setServiceAreas(String serviceAreas) {
        ServiceAreas = serviceAreas;
    }

    public double getShopOrderAmountMin() {
        return ShopOrderAmountMin;
    }

    public void setShopOrderAmountMin(double shopOrderAmountMin) {
        ShopOrderAmountMin = shopOrderAmountMin;
    }

    public double getFreightAmount() {
        return FreightAmount;
    }

    public void setFreightAmount(double freightAmount) {
        FreightAmount = freightAmount;
    }

    public String[] getServiceAreasArray() {
        return ServiceAreasArray;
    }

    public void setServiceAreasArray(String[] serviceAreasArray) {
        ServiceAreasArray = serviceAreasArray;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ShopId);
        dest.writeString(this.ShopNO);
        dest.writeString(this.Name);
        dest.writeString(this.Mobile);
        dest.writeString(this.AddressDetail);
        dest.writeDouble(this.Lat);
        dest.writeDouble(this.Lng);
        dest.writeInt(this.State);
        dest.writeString(this.ServiceTime);
        dest.writeString(this.DeliveryTime);
        dest.writeByte(this.OpenOrClosed ? (byte) 1 : (byte) 0);
        dest.writeString(this.ShopImg);
        dest.writeString(this.Instroduce);
        dest.writeByte(this.PickUpSelfSupport ? (byte) 1 : (byte) 0);
        dest.writeByte(this.HomeDeliverySupport ? (byte) 1 : (byte) 0);
        dest.writeString(this.ServiceAreas);
        dest.writeDouble(this.ShopOrderAmountMin);
        dest.writeDouble(this.FreightAmount);
        dest.writeStringArray(this.ServiceAreasArray);
    }

    public ShopExpressConfig() {
    }

    protected ShopExpressConfig(Parcel in) {
        this.ShopId = in.readInt();
        this.ShopNO = in.readString();
        this.Name = in.readString();
        this.Mobile = in.readString();
        this.AddressDetail = in.readString();
        this.Lat = in.readDouble();
        this.Lng = in.readDouble();
        this.State = in.readInt();
        this.ServiceTime = in.readString();
        this.DeliveryTime = in.readString();
        this.OpenOrClosed = in.readByte() != 0;
        this.ShopImg = in.readString();
        this.Instroduce = in.readString();
        this.PickUpSelfSupport = in.readByte() != 0;
        this.HomeDeliverySupport = in.readByte() != 0;
        this.ServiceAreas = in.readString();
        this.ShopOrderAmountMin = in.readDouble();
        this.FreightAmount = in.readDouble();
        this.ServiceAreasArray = in.createStringArray();
    }

    public static final Creator<ShopExpressConfig> CREATOR = new Creator<ShopExpressConfig>() {
        @Override
        public ShopExpressConfig createFromParcel(Parcel source) {
            return new ShopExpressConfig(source);
        }

        @Override
        public ShopExpressConfig[] newArray(int size) {
            return new ShopExpressConfig[size];
        }
    };
}
