package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 */
public class ClientRegisterSetpTwoBody {
    private int RegisterId;//  注册Id  访问ClientRegisterStepOne的返回值
    private String Mobile;// 手机号码
    private String RealName;// 用户姓名
    private String ContactAddress;// 联系地址
    private double Lat;//纬度
    private double Lng;//经度

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }

    public int getRegisterId() {
        return RegisterId;
    }

    public void setRegisterId(int registerId) {
        RegisterId = registerId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getContactAddress() {
        return ContactAddress;
    }

    public void setContactAddress(String contactAddress) {
        ContactAddress = contactAddress;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    @Override
    public String toString() {
        return "ClientRegisterSetpTwoBody{" +
                "RegisterId=" + RegisterId +
                ", Mobile='" + Mobile + '\'' +
                ", RealName='" + RealName + '\'' +
                ", ContactAddress='" + ContactAddress + '\'' +
                ", Lat=" + Lat +
                ", Lng=" + Lng +
                '}';
    }
}
