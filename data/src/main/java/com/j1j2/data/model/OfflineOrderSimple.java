package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-4-12.
 */
public class OfflineOrderSimple {

    /**
     * SaleOrderId : 58975
     * OrderNO : 1160535499210837
     * SubmitTimeStr : 2016-02-22 15:17:29
     * ShopName : 新安小区服务点
     * PayType : 1
     * Amount : 8.3
     * OrderDiscount : 0.2
     * PayAmount : 50.1
     * UseBalance : 0.5
     * SaveBalance : 0.2
     * Change : 42.0
     * Point : 8
     */

    private int SaleOrderId;
    private String OrderNO;
    private String SubmitTimeStr;
    private String ShopName;
    private int PayType;
    private double Amount;
    private double OrderDiscount;
    private double PayAmount;
    private double UseBalance;
    private double SaveBalance;
    private double Change;
    private int Point;

    public int getSaleOrderId() {
        return SaleOrderId;
    }

    public void setSaleOrderId(int SaleOrderId) {
        this.SaleOrderId = SaleOrderId;
    }

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String OrderNO) {
        this.OrderNO = OrderNO;
    }

    public String getSubmitTimeStr() {
        return SubmitTimeStr;
    }

    public void setSubmitTimeStr(String SubmitTimeStr) {
        this.SubmitTimeStr = SubmitTimeStr;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public int getPayType() {
        return PayType;
    }

    public void setPayType(int PayType) {
        this.PayType = PayType;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public double getOrderDiscount() {
        return OrderDiscount;
    }

    public void setOrderDiscount(double OrderDiscount) {
        this.OrderDiscount = OrderDiscount;
    }

    public double getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(double PayAmount) {
        this.PayAmount = PayAmount;
    }

    public double getUseBalance() {
        return UseBalance;
    }

    public void setUseBalance(double UseBalance) {
        this.UseBalance = UseBalance;
    }

    public double getSaveBalance() {
        return SaveBalance;
    }

    public void setSaveBalance(double SaveBalance) {
        this.SaveBalance = SaveBalance;
    }

    public double getChange() {
        return Change;
    }

    public void setChange(double Change) {
        this.Change = Change;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int Point) {
        this.Point = Point;
    }
}
