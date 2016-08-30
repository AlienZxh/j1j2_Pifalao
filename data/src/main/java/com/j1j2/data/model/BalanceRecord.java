package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-5-12.
 */
public class BalanceRecord {


    /**
     * SourceType : 2
     * OrderNO : 1161336327000854
     * CreateTimeStr : 2016-05-12 17:34:35
     * Amount : 0.01
     * RecordType : 1
     */

    private int SourceType;// 1：线下 2：线上
    private String OrderNO;
    private String CreateTimeStr;
    private double Amount;
    private int RecordType;// 1:余额支付 2:支付宝支付 3:微信支付 4:Online退款 5:线下使用存零（-） 6:增加存零（+） 7:现金充值 -1:其它



    public int getSourceType() {
        return SourceType;
    }

    public void setSourceType(int SourceType) {
        this.SourceType = SourceType;
    }

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String OrderNO) {
        this.OrderNO = OrderNO;
    }

    public String getCreateTimeStr() {
        return CreateTimeStr;
    }

    public void setCreateTimeStr(String CreateTimeStr) {
        this.CreateTimeStr = CreateTimeStr;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public int getRecordType() {
        return RecordType;
    }

    public void setRecordType(int RecordType) {
        this.RecordType = RecordType;
    }
}
