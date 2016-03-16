package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 */
public class FindPwdStepTwoBody {

    private String Phone;
    private String SMSCode;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getSMSCode() {
        return SMSCode;
    }

    public void setSMSCode(String SMSCode) {
        this.SMSCode = SMSCode;
    }

    @Override
    public String toString() {
        return "FindPwdStepTwoBody{" +
                "Phone='" + Phone + '\'' +
                ", SMSCode='" + SMSCode + '\'' +
                '}';
    }
}
