package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 */
public class RestUserPwdBody {
    private int RegisterId;
    private String Phone;
    private String NewPwd;
    private String ConfrimPwd;

    public int getRegisterId() {
        return RegisterId;
    }

    public void setRegisterId(int registerId) {
        RegisterId = registerId;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getNewPwd() {
        return NewPwd;
    }

    public void setNewPwd(String newPwd) {
        NewPwd = newPwd;
    }

    public String getConfrimPwd() {
        return ConfrimPwd;
    }

    public void setConfrimPwd(String confrimPwd) {
        ConfrimPwd = confrimPwd;
    }

    @Override
    public String toString() {
        return "RestUserPwdBody{" +
                "RegisterId=" + RegisterId +
                ", Phone='" + Phone + '\'' +
                ", NewPwd='" + NewPwd + '\'' +
                ", ConfrimPwd='" + ConfrimPwd + '\'' +
                '}';
    }
}
