package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-5-13.
 */
public class WeiXinPayResult {

    /**
     * AppId : wxaaf65494c086b0d3
     * PartnerId : 1343037101
     * PrepayId : wx20160513175801c6ab7bd66b0755706354
     * Package : Sign=WXPay
     * NonceStr : 2f55707d4193dc27118a0f19a1985716
     * Timestamp : 1463133482
     * Sign : 0115E00A040D24BCEFC29675A2145CD7
     */

    private String AppId;
    private String PartnerId;
    private String PrepayId;
    private String Package;
    private String NonceStr;
    private String Timestamp;
    private String Sign;

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String AppId) {
        this.AppId = AppId;
    }

    public String getPartnerId() {
        return PartnerId;
    }

    public void setPartnerId(String PartnerId) {
        this.PartnerId = PartnerId;
    }

    public String getPrepayId() {
        return PrepayId;
    }

    public void setPrepayId(String PrepayId) {
        this.PrepayId = PrepayId;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String Package) {
        this.Package = Package;
    }

    public String getNonceStr() {
        return NonceStr;
    }

    public void setNonceStr(String NonceStr) {
        this.NonceStr = NonceStr;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String Timestamp) {
        this.Timestamp = Timestamp;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String Sign) {
        this.Sign = Sign;
    }

    @Override
    public String toString() {
        return "WeiXinPayResult{" +
                "AppId='" + AppId + '\'' +
                ", PartnerId='" + PartnerId + '\'' +
                ", PrepayId='" + PrepayId + '\'' +
                ", Package='" + Package + '\'' +
                ", NonceStr='" + NonceStr + '\'' +
                ", Timestamp='" + Timestamp + '\'' +
                ", Sign='" + Sign + '\'' +
                '}';
    }
}
