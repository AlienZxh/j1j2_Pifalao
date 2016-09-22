package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-9-20.
 */

public class LotteryCacluateTime {

    private String OrderTimeStr;
    /// <summary>
    /// 转换成对应的数字
    /// </summary>
    private long TimeConvertData;
    private int UserId;
    private String Mobile;
    private String MobileEncrypt;
    private String StoreName;

    public String getOrderTimeStr() {
        return OrderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        OrderTimeStr = orderTimeStr;
    }

    public long getTimeConvertData() {
        return TimeConvertData;
    }

    public void setTimeConvertData(long timeConvertData) {
        TimeConvertData = timeConvertData;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getMobileEncrypt() {
        return MobileEncrypt;
    }

    public void setMobileEncrypt(String mobileEncrypt) {
        MobileEncrypt = mobileEncrypt;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }
}
