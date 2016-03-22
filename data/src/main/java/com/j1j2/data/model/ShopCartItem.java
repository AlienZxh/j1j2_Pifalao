package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-3-21.
 */
public class ShopCartItem {

    /**
     * ProductId : 31054
     * ProductMainId : 5855
     * ModuleId : 23
     * BarCode : 6933117698638
     * ProductName : 万利来730g荔枝罐头1*12
     * RetailPrice : 15
     * ThumbImgPath : http://data.j1j2.com/ResourceFiles/ProductImages/20141208/70_70/22655_0.jpg
     * MemberPrice : 12.98
     * Quantity : 3
     * Unit : 瓶
     * LimitSalesNumber : 0
     * Views : 0
     * Sells : 69
     * ScalingFactor : 1
     * ActivityId : 0
     * Invalid : false
     * ProductState : 1
     */

    private int ProductId;
    private int ProductMainId;
    private int ModuleId;
    private String BarCode;
    private String ProductName;
    private int RetailPrice;
    private String ThumbImgPath;
    private double MemberPrice;
    private int Quantity;
    private String Unit;
    private int LimitSalesNumber;
    private int Views;
    private int Sells;
    private int ScalingFactor;
    private int ActivityId;
    private boolean Invalid;
    private int ProductState;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int ProductId) {
        this.ProductId = ProductId;
    }

    public int getProductMainId() {
        return ProductMainId;
    }

    public void setProductMainId(int ProductMainId) {
        this.ProductMainId = ProductMainId;
    }

    public int getModuleId() {
        return ModuleId;
    }

    public void setModuleId(int ModuleId) {
        this.ModuleId = ModuleId;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String BarCode) {
        this.BarCode = BarCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public int getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(int RetailPrice) {
        this.RetailPrice = RetailPrice;
    }

    public String getThumbImgPath() {
        return ThumbImgPath;
    }

    public void setThumbImgPath(String ThumbImgPath) {
        this.ThumbImgPath = ThumbImgPath;
    }

    public double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(double MemberPrice) {
        this.MemberPrice = MemberPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public int getLimitSalesNumber() {
        return LimitSalesNumber;
    }

    public void setLimitSalesNumber(int LimitSalesNumber) {
        this.LimitSalesNumber = LimitSalesNumber;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int Views) {
        this.Views = Views;
    }

    public int getSells() {
        return Sells;
    }

    public void setSells(int Sells) {
        this.Sells = Sells;
    }

    public int getScalingFactor() {
        return ScalingFactor;
    }

    public void setScalingFactor(int ScalingFactor) {
        this.ScalingFactor = ScalingFactor;
    }

    public int getActivityId() {
        return ActivityId;
    }

    public void setActivityId(int ActivityId) {
        this.ActivityId = ActivityId;
    }

    public boolean isInvalid() {
        return Invalid;
    }

    public void setInvalid(boolean Invalid) {
        this.Invalid = Invalid;
    }

    public int getProductState() {
        return ProductState;
    }

    public void setProductState(int ProductState) {
        this.ProductState = ProductState;
    }

    @Override
    public String toString() {
        return "ShopCartItem{" +
                "ProductId=" + ProductId +
                ", ProductMainId=" + ProductMainId +
                ", ModuleId=" + ModuleId +
                ", BarCode='" + BarCode + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", RetailPrice=" + RetailPrice +
                ", ThumbImgPath='" + ThumbImgPath + '\'' +
                ", MemberPrice=" + MemberPrice +
                ", Quantity=" + Quantity +
                ", Unit='" + Unit + '\'' +
                ", LimitSalesNumber=" + LimitSalesNumber +
                ", Views=" + Views +
                ", Sells=" + Sells +
                ", ScalingFactor=" + ScalingFactor +
                ", ActivityId=" + ActivityId +
                ", Invalid=" + Invalid +
                ", ProductState=" + ProductState +
                '}';
    }
}
