package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-3-15.
 */
public class PromtionTime {

    /**
     * BeginStr : 2014-10-16 23:59:59
     * ServerTime : 2016-03-15 13:23:50
     * EndStr : 2014-10-31 23:59:59
     */

    private String BeginStr;
    private String ServerTime;
    private String EndStr;

    public void setBeginStr(String BeginStr) {
        this.BeginStr = BeginStr;
    }

    public void setServerTime(String ServerTime) {
        this.ServerTime = ServerTime;
    }

    public void setEndStr(String EndStr) {
        this.EndStr = EndStr;
    }

    public String getBeginStr() {
        return BeginStr;
    }

    public String getServerTime() {
        return ServerTime;
    }

    public String getEndStr() {
        return EndStr;
    }

    @Override
    public String toString() {
        return "PromtionTime{" +
                "BeginStr='" + BeginStr + '\'' +
                ", ServerTime='" + ServerTime + '\'' +
                ", EndStr='" + EndStr + '\'' +
                '}';
    }
}
