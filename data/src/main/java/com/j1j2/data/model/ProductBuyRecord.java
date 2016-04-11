package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-4-11.
 */
public class ProductBuyRecord {

    /**
     * UserName : 18627631809
     * PartialHideUserName : *****631809
     * Quantity : 1
     * BoughtTimeStr : 2015-12-16 14:33:40
     * Unit : 包
     */

    private String UserName;
    private String PartialHideUserName;
    private int Quantity;
    private String BoughtTimeStr;
    private String Unit;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPartialHideUserName() {
        return PartialHideUserName;
    }

    public void setPartialHideUserName(String PartialHideUserName) {
        this.PartialHideUserName = PartialHideUserName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }


    public String getBoughtTimeStr() {
        return BoughtTimeStr;
    }

    public void setBoughtTimeStr(String BoughtTimeStr) {
        this.BoughtTimeStr = BoughtTimeStr;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }
}
