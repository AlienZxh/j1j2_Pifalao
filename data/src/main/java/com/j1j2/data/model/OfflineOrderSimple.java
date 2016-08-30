package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alienzxh on 16-4-12.
 */
public class OfflineOrderSimple implements Parcelable {


    /**
     * SaleOrderId : 58975
     * OrderNO : 1160535499210837
     * SubmitTimeStr : 2016-02-22 15:17:29
     * ShopName : 新安小区服务点
     * PayType : 1
     * Amount : 8.3
     * OrderDiscount : 0.2
     * PayAmount : 50.0
     * UseBalance : 0.5
     * SaveBalance : 0.2
     * Change : 42.0
     * Point : 8
     * ProductDetails : [{"ProductName":"[1包]精白沙","Spec":"asd","Unit":"包","MemberPrice":8.3,"RetailPrice":8.5,"Quantity":1,"ItemSum":8.3}]
     */

    private int SaleOrderId;
    private String OrderNO;
    private String SubmitTimeStr;
    private String ShopName;
    private int PayType;//1:现金   2:支付宝　　３：微信
    private double Amount;
    private double OrderDiscount;
    private double PayAmount;
    private double UseBalance;
    private double SaveBalance;
    private double Change;
    private int Point;
    /**
     * ProductName : [1包]精白沙
     * Spec : asd
     * Unit : 包
     * MemberPrice : 8.3
     * RetailPrice : 8.5
     * Quantity : 1
     * ItemSum : 8.3
     */

    private List<OfflineOrderProduct> ProductDetails;

    public int getSaleOrderId() {
        return SaleOrderId;
    }

    public void setSaleOrderId(int saleOrderId) {
        SaleOrderId = saleOrderId;
    }

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String orderNO) {
        OrderNO = orderNO;
    }

    public String getSubmitTimeStr() {
        return SubmitTimeStr;
    }

    public void setSubmitTimeStr(String submitTimeStr) {
        SubmitTimeStr = submitTimeStr;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public int getPayType() {
        return PayType;
    }

    public void setPayType(int payType) {
        PayType = payType;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public double getOrderDiscount() {
        return OrderDiscount;
    }

    public void setOrderDiscount(double orderDiscount) {
        OrderDiscount = orderDiscount;
    }

    public double getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(double payAmount) {
        PayAmount = payAmount;
    }

    public double getUseBalance() {
        return UseBalance;
    }

    public void setUseBalance(double useBalance) {
        UseBalance = useBalance;
    }

    public double getSaveBalance() {
        return SaveBalance;
    }

    public void setSaveBalance(double saveBalance) {
        SaveBalance = saveBalance;
    }

    public double getChange() {
        return Change;
    }

    public void setChange(double change) {
        Change = change;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public List<OfflineOrderProduct> getProductDetails() {
        return ProductDetails;
    }

    public void setProductDetails(List<OfflineOrderProduct> productDetails) {
        ProductDetails = productDetails;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.SaleOrderId);
        dest.writeString(this.OrderNO);
        dest.writeString(this.SubmitTimeStr);
        dest.writeString(this.ShopName);
        dest.writeInt(this.PayType);
        dest.writeDouble(this.Amount);
        dest.writeDouble(this.OrderDiscount);
        dest.writeDouble(this.PayAmount);
        dest.writeDouble(this.UseBalance);
        dest.writeDouble(this.SaveBalance);
        dest.writeDouble(this.Change);
        dest.writeInt(this.Point);
        dest.writeTypedList(ProductDetails);
    }

    public OfflineOrderSimple() {
    }

    protected OfflineOrderSimple(Parcel in) {
        this.SaleOrderId = in.readInt();
        this.OrderNO = in.readString();
        this.SubmitTimeStr = in.readString();
        this.ShopName = in.readString();
        this.PayType = in.readInt();
        this.Amount = in.readDouble();
        this.OrderDiscount = in.readDouble();
        this.PayAmount = in.readDouble();
        this.UseBalance = in.readDouble();
        this.SaveBalance = in.readDouble();
        this.Change = in.readDouble();
        this.Point = in.readInt();
        this.ProductDetails = in.createTypedArrayList(OfflineOrderProduct.CREATOR);
    }

    public static final Parcelable.Creator<OfflineOrderSimple> CREATOR = new Parcelable.Creator<OfflineOrderSimple>() {
        @Override
        public OfflineOrderSimple createFromParcel(Parcel source) {
            return new OfflineOrderSimple(source);
        }

        @Override
        public OfflineOrderSimple[] newArray(int size) {
            return new OfflineOrderSimple[size];
        }
    };
}
