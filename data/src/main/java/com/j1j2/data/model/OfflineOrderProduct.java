package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-4-12.
 */
public class OfflineOrderProduct {

    /**
     * ProductName : [1包]精白沙
     * Spec : 20
     * Unit : 包
     * MemberPrice : 8.3
     * RetailPrice : 8.5
     * Quantity : 1
     * ItemSum : 8.3
     */

    private String ProductName;
    private String Spec;
    private String Unit;
    private double MemberPrice;
    private double RetailPrice;
    private int Quantity;
    private double ItemSum;

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getSpec() {
        return Spec;
    }

    public void setSpec(String Spec) {
        this.Spec = Spec;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(double MemberPrice) {
        this.MemberPrice = MemberPrice;
    }

    public double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(double RetailPrice) {
        this.RetailPrice = RetailPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public double getItemSum() {
        return ItemSum;
    }

    public void setItemSum(double ItemSum) {
        this.ItemSum = ItemSum;
    }
}
