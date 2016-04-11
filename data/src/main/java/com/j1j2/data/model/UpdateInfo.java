package com.j1j2.data.model;

/**
 * Created by 兴昊 on 2016/1/19.
 */
public class UpdateInfo {

    /**
     * VersionCode : 25
     * VersionName: 2.0.3
     * SoftUrl : www
     * SoftName : 批发佬
     * IsCompulsory : true
     */

    private int VersionCode;
    private String VersionName;
    private String SoftUrl;
    private String SoftName;
    private boolean IsCompulsory;

    public int getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(int versionCode) {
        VersionCode = versionCode;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String versionName) {
        VersionName = versionName;
    }

    public String getSoftUrl() {
        return SoftUrl;
    }

    public void setSoftUrl(String softUrl) {
        SoftUrl = softUrl;
    }

    public String getSoftName() {
        return SoftName;
    }

    public void setSoftName(String softName) {
        SoftName = softName;
    }

    public boolean isCompulsory() {
        return IsCompulsory;
    }

    public void setCompulsory(boolean compulsory) {
        IsCompulsory = compulsory;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "VersionCode='" + VersionCode + '\'' +
                ", VersionName='" + VersionName + '\'' +
                ", SoftUrl='" + SoftUrl + '\'' +
                ", SoftName='" + SoftName + '\'' +
                ", IsCompulsory=" + IsCompulsory +
                '}';
    }
}
