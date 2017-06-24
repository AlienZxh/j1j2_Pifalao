package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by alienzxh on 16-3-22.
 */
public class OrderSimple implements Parcelable {

    private String ServiceName;
    private int OrderId;
    private String OrderNO;
    private int OrderType; //线上订单类型 对应ServiceType 服务类型
    private int ShopId; //门店(服务点)ID

    private String OrderSubmitTimeStr;
    private double OrderAmount;
    private double ProductAmount;
    private double FreightAmount;
    private double ProductDiscount;
    private double CouponDiscount;
    private int OrderState;
    private int ProductCount; //商品数量
    private List<OrderSimpleProductDetail> ProductDetails;

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String orderNO) {
        OrderNO = orderNO;
    }

    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int orderType) {
        OrderType = orderType;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getOrderSubmitTimeStr() {
        return OrderSubmitTimeStr;
    }

    public void setOrderSubmitTimeStr(String orderSubmitTimeStr) {
        OrderSubmitTimeStr = orderSubmitTimeStr;
    }

    public double getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        OrderAmount = orderAmount;
    }

    public double getProductAmount() {
        return ProductAmount;
    }

    public void setProductAmount(double productAmount) {
        ProductAmount = productAmount;
    }

    public double getFreightAmount() {
        return FreightAmount;
    }

    public void setFreightAmount(double freightAmount) {
        FreightAmount = freightAmount;
    }

    public double getProductDiscount() {
        return ProductDiscount;
    }

    public void setProductDiscount(double productDiscount) {
        ProductDiscount = productDiscount;
    }

    public double getCouponDiscount() {
        return CouponDiscount;
    }

    public void setCouponDiscount(double couponDiscount) {
        CouponDiscount = couponDiscount;
    }

    public int getOrderState() {
        return OrderState;
    }

    public void setOrderState(int orderState) {
        OrderState = orderState;
    }

    public int getProductCount() {
        return ProductCount;
    }

    public void setProductCount(int productCount) {
        ProductCount = productCount;
    }

    public List<OrderSimpleProductDetail> getProductDetails() {
        return ProductDetails;
    }

    public void setProductDetails(List<OrderSimpleProductDetail> productDetails) {
        ProductDetails = productDetails;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ServiceName);
        dest.writeInt(this.OrderId);
        dest.writeString(this.OrderNO);
        dest.writeInt(this.OrderType);
        dest.writeInt(this.ShopId);
        dest.writeString(this.OrderSubmitTimeStr);
        dest.writeDouble(this.OrderAmount);
        dest.writeDouble(this.ProductAmount);
        dest.writeDouble(this.FreightAmount);
        dest.writeDouble(this.ProductDiscount);
        dest.writeDouble(this.CouponDiscount);
        dest.writeInt(this.OrderState);
        dest.writeInt(this.ProductCount);
        dest.writeTypedList(this.ProductDetails);
    }

    public OrderSimple() {
    }

    protected OrderSimple(Parcel in) {
        this.ServiceName = in.readString();
        this.OrderId = in.readInt();
        this.OrderNO = in.readString();
        this.OrderType = in.readInt();
        this.ShopId = in.readInt();
        this.OrderSubmitTimeStr = in.readString();
        this.OrderAmount = in.readDouble();
        this.ProductAmount = in.readDouble();
        this.FreightAmount = in.readDouble();
        this.ProductDiscount = in.readDouble();
        this.CouponDiscount = in.readDouble();
        this.OrderState = in.readInt();
        this.ProductCount = in.readInt();
        this.ProductDetails = in.createTypedArrayList(OrderSimpleProductDetail.CREATOR);
    }

    public static final Creator<OrderSimple> CREATOR = new Creator<OrderSimple>() {
        @Override
        public OrderSimple createFromParcel(Parcel source) {
            return new OrderSimple(source);
        }

        @Override
        public OrderSimple[] newArray(int size) {
            return new OrderSimple[size];
        }
    };

    /**
     * Created by alienzxh on 16-12-13.
     */

    public static class OrderSimpleProductDetail implements Parcelable {
        private String ImageThumb;
        private String ProductName;

        public String getImageThumb() {
            return ImageThumb;
        }

        public void setImageThumb(String imageThumb) {
            ImageThumb = imageThumb;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String productName) {
            ProductName = productName;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.ImageThumb);
            dest.writeString(this.ProductName);
        }

        public OrderSimpleProductDetail() {
        }

        protected OrderSimpleProductDetail(Parcel in) {
            this.ImageThumb = in.readString();
            this.ProductName = in.readString();
        }

        public static final Creator<OrderSimpleProductDetail> CREATOR = new Creator<OrderSimpleProductDetail>() {
            @Override
            public OrderSimpleProductDetail createFromParcel(Parcel source) {
                return new OrderSimpleProductDetail(source);
            }

            @Override
            public OrderSimpleProductDetail[] newArray(int size) {
                return new OrderSimpleProductDetail[size];
            }
        };
    }
}
