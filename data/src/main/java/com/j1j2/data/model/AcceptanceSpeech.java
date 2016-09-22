package com.j1j2.data.model;

import java.util.List;

/**
 * Created by alienzxh on 16-9-17.
 */
public class AcceptanceSpeech {
    private int OrderId;
    private String OrderNO;
    private String Message;
    private List<AcceptanceSpeechImg> Imgs;
    private String ShareTimeStr;
    private String UserMobile;
    private String MobileEncrypt;
    private ActivityProcessStateProductInfo ProductInfo;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String OrderNO) {
        this.OrderNO = OrderNO;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<AcceptanceSpeechImg> getImgs() {
        return Imgs;
    }

    public void setImgs(List<AcceptanceSpeechImg> imgs) {
        Imgs = imgs;
    }

    public String getShareTimeStr() {
        return ShareTimeStr;
    }

    public void setShareTimeStr(String shareTimeStr) {
        ShareTimeStr = shareTimeStr;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String userMobile) {
        UserMobile = userMobile;
    }

    public String getMobileEncrypt() {
        return MobileEncrypt;
    }

    public void setMobileEncrypt(String mobileEncrypt) {
        MobileEncrypt = mobileEncrypt;
    }

    public ActivityProcessStateProductInfo getProductInfo() {
        return ProductInfo;
    }

    public void setProductInfo(ActivityProcessStateProductInfo productInfo) {
        ProductInfo = productInfo;
    }
}
