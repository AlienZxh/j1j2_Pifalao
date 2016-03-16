package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 */
public class ChangeUserPwdBody {
    private String OldPwd;
    private String NewPwd;

    public String getOldPwd() {
        return OldPwd;
    }

    public void setOldPwd(String oldPwd) {
        OldPwd = oldPwd;
    }

    public String getNewPwd() {
        return NewPwd;
    }

    public void setNewPwd(String newPwd) {
        NewPwd = newPwd;
    }

    @Override
    public String toString() {
        return "ChangeUserPwdBody{" +
                "OldPwd='" + OldPwd + '\'' +
                ", NewPwd='" + NewPwd + '\'' +
                '}';
    }
}
