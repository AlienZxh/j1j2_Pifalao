package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-9-27.
 */

public class ApplyForServicePointBody {

    private String ApplyerName;


    private String ApplyPhone;


    private String ApplyAddress;

    private String LoginMobile;

    public String getApplyerName() {
        return ApplyerName;
    }

    public void setApplyerName(String applyerName) {
        ApplyerName = applyerName;
    }

    public String getApplyPhone() {
        return ApplyPhone;
    }

    public void setApplyPhone(String applyPhone) {
        ApplyPhone = applyPhone;
    }

    public String getApplyAddress() {
        return ApplyAddress;
    }

    public void setApplyAddress(String applyAddress) {
        ApplyAddress = applyAddress;
    }

    public String getLoginMobile() {
        return LoginMobile;
    }

    public void setLoginMobile(String loginMobile) {
        LoginMobile = loginMobile;
    }
}
