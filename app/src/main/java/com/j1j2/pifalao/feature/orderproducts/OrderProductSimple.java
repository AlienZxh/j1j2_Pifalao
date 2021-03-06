package com.j1j2.pifalao.feature.orderproducts;

import com.j1j2.data.model.OrderDetail;
import com.j1j2.data.model.ShopCartItem;

/**
 * Created by alienzxh on 16-4-2.
 */
public class OrderProductSimple {
    private int activityType;
    private int ProductId;
    private int ProductMainId;
    private String ProductName;
    private String ProductUnit;
    private double RetailPrice;
    private double MemberPrice;
    private String ThumbImgPath;
    private int Quantity;
    private int Sells;

    public  OrderProductSimple(int activityType, Object data) {
        this.activityType = activityType;
        if (data instanceof ShopCartItem) {
            this.ProductId = ((ShopCartItem) data).getProductId();
            this.ProductMainId = ((ShopCartItem) data).getProductMainId();
            this.ProductName = ((ShopCartItem) data).getProductName();
            this.ProductUnit = ((ShopCartItem) data).getUnit();
            this.RetailPrice = ((ShopCartItem) data).getRetailPrice();
            this.MemberPrice = ((ShopCartItem) data).getMemberPrice();
            this.ThumbImgPath = ((ShopCartItem) data).getThumbImgPath();
            this.Quantity = ((ShopCartItem) data).getQuantity();
            this.Sells = ((ShopCartItem) data).getSells();
        } else if (data instanceof OrderDetail.OrderProductDetail) {
            this.ProductId = ((OrderDetail.OrderProductDetail) data).getProductId();
            this.ProductMainId = ((OrderDetail.OrderProductDetail) data).getProductId();
            this.ProductName = ((OrderDetail.OrderProductDetail) data).getProductName();
            this.ProductUnit = ((OrderDetail.OrderProductDetail) data).getProductUnit();
            this.RetailPrice = ((OrderDetail.OrderProductDetail) data).getRetailPrice();
            this.MemberPrice = ((OrderDetail.OrderProductDetail) data).getMemberPrice();
            this.ThumbImgPath = ((OrderDetail.OrderProductDetail) data).getThumbImgPath();
            this.Quantity = ((OrderDetail.OrderProductDetail) data).getQuantity();
        }
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getProductMainId() {
        return ProductMainId;
    }

    public void setProductMainId(int productMainId) {
        ProductMainId = productMainId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductUnit() {
        return ProductUnit;
    }

    public void setProductUnit(String productUnit) {
        ProductUnit = productUnit;
    }

    public double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        RetailPrice = retailPrice;
    }

    public double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(double memberPrice) {
        MemberPrice = memberPrice;
    }

    public String getThumbImgPath() {
        return ThumbImgPath;
    }

    public void setThumbImgPath(String thumbImgPath) {
        ThumbImgPath = thumbImgPath;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getSells() {
        return Sells;
    }

    public void setSells(int sells) {
        Sells = sells;
    }

}
