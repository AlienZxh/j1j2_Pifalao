package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 */
public class FindPwdStepOneBody {
    private String Phone;
    private String ValidateCode;//返回的

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getValidateCode() {
        return ValidateCode;
    }

    public void setValidateCode(String validateCode) {
        ValidateCode = validateCode;
    }

    @Override
    public String toString() {
        return "FindPwdStepOneBody{" +
                "Phone='" + Phone + '\'' +
                ", ValidateCode='" + ValidateCode + '\'' +
                '}';
    }
}
