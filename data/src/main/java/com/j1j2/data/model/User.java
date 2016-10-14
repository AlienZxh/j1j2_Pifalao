package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-3-11.
 */
public class User {

    /**
     * UserId : 760
     * LoginAccount : 15200348636
     * StoreName :
     * RoleId : 10003：普通用户  10002：门店用户
     * Point : 122
     * DeliveryTemplateId : 25
     * ServicePointId : 2
     * AdminVerfiy : true
     * UserName : zxh
     * RegisterTimeStr : 2015-04-18 00:00:00
     * ExpireTimeStr : 2017-07-17 00:00:00
     * CurrentServiceTime : 2016-03-15 15:53:25
     * VIPRegisterCode : 6523068014
     * Cost : 155.66
     * UserSaveAmount : 22.18
     * UserLevelId : 5
     * Balance : 0.47
     */

    private int UserId;
    private String LoginAccount;
    private int RoleId;
    private int Point;
    private String UserName;
    private String RegisterTimeStr;
    private String ExpireTimeStr;
    private String CurrentServiceTime;
    private String VIPRegisterCode;
    private double Cost;
    private double UserSaveAmount;
    private int UserLevelId;
    private double Balance;
    private String Portrait;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getLoginAccount() {
        return LoginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        LoginAccount = loginAccount;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int roleId) {
        RoleId = roleId;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getRegisterTimeStr() {
        return RegisterTimeStr;
    }

    public void setRegisterTimeStr(String registerTimeStr) {
        RegisterTimeStr = registerTimeStr;
    }

    public String getExpireTimeStr() {
        return ExpireTimeStr;
    }

    public void setExpireTimeStr(String expireTimeStr) {
        ExpireTimeStr = expireTimeStr;
    }

    public String getCurrentServiceTime() {
        return CurrentServiceTime;
    }

    public void setCurrentServiceTime(String currentServiceTime) {
        CurrentServiceTime = currentServiceTime;
    }

    public String getVIPRegisterCode() {
        return VIPRegisterCode;
    }

    public void setVIPRegisterCode(String VIPRegisterCode) {
        this.VIPRegisterCode = VIPRegisterCode;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public double getUserSaveAmount() {
        return UserSaveAmount;
    }

    public void setUserSaveAmount(double userSaveAmount) {
        UserSaveAmount = userSaveAmount;
    }

    public int getUserLevelId() {
        return UserLevelId;
    }

    public void setUserLevelId(int userLevelId) {
        UserLevelId = userLevelId;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public String getPortrait() {
        return Portrait;
    }

    public void setPortrait(String portrait) {
        Portrait = portrait;
    }
}
