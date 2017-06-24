package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by alienzxh on 16-12-13.
 */

public class OrderDetail implements Parcelable {

    private OnlineSaleOrderBaseInfo OrderBaseInfo;
    private OnlineSaleOrderTimesInfo OrderTimesInfo;
    private OnlineSaleOrderPayInfo OrderPayInfo;
    private OnlineSaleOrderRateInfo OrderRateInfo;
    private OnlineSaleOrderReceiverInfo OrderRecevierInfo;
    private OnlineSaleOrderShopsInfo OrderShopInfo;
    private List<OrderProductDetail> OrderProductsInfo;

    public OnlineSaleOrderBaseInfo getOrderBaseInfo() {
        return OrderBaseInfo;
    }

    public void setOrderBaseInfo(OnlineSaleOrderBaseInfo orderBaseInfo) {
        OrderBaseInfo = orderBaseInfo;
    }

    public OnlineSaleOrderTimesInfo getOrderTimesInfo() {
        return OrderTimesInfo;
    }

    public void setOrderTimesInfo(OnlineSaleOrderTimesInfo orderTimesInfo) {
        OrderTimesInfo = orderTimesInfo;
    }

    public OnlineSaleOrderPayInfo getOrderPayInfo() {
        return OrderPayInfo;
    }

    public void setOrderPayInfo(OnlineSaleOrderPayInfo orderPayInfo) {
        OrderPayInfo = orderPayInfo;
    }

    public OnlineSaleOrderRateInfo getOrderRateInfo() {
        return OrderRateInfo;
    }

    public void setOrderRateInfo(OnlineSaleOrderRateInfo orderRateInfo) {
        OrderRateInfo = orderRateInfo;
    }

    public OnlineSaleOrderReceiverInfo getOrderRecevierInfo() {
        return OrderRecevierInfo;
    }

    public void setOrderRecevierInfo(OnlineSaleOrderReceiverInfo orderRecevierInfo) {
        OrderRecevierInfo = orderRecevierInfo;
    }

    public OnlineSaleOrderShopsInfo getOrderShopInfo() {
        return OrderShopInfo;
    }

    public void setOrderShopInfo(OnlineSaleOrderShopsInfo orderShopInfo) {
        OrderShopInfo = orderShopInfo;
    }

    public List<OrderProductDetail> getOrderProductsInfo() {
        return OrderProductsInfo;
    }

    public void setOrderProductsInfo(List<OrderProductDetail> orderProductsInfo) {
        OrderProductsInfo = orderProductsInfo;
    }




    /// <summary>
    /// 线上销售订单的基础信息
    /// </summary>
    public static class OnlineSaleOrderBaseInfo implements Parcelable {
        private String ServiceName;

        private int ShopId;
        private int OrderId;

        /// <summary>
        /// 订单流水号
        /// </summary>
        private String OrderNO;

        private int OrderType; //线上订单类型 对应ServiceType 服务类型


        /// <summary>
        /// 订单的运费金额
        /// </summary>
        private double FreightAmount;

        /// <summary>
        /// 订单商品金额
        /// </summary>
        private double ProductAmount;

        /// <summary>
        /// 订单商品优惠金额
        /// </summary>
        private double ProductDiscount;

        /// <summary>
        /// 优惠券折扣金额
        /// </summary>
        private double CouponDiscount;

        /// <summary>
        /// 订单金额
        /// </summary>
        private double OrderAmount;

        /// <summary>
        /// 订单节省金额
        /// </summary>
        private double SaveAmount;

        private int OrderState;

        /// <summary>
        /// 订单备注
        /// </summary>
        private String OrderMemo;

        public String getServiceName() {
            return ServiceName;
        }

        public void setServiceName(String serviceName) {
            ServiceName = serviceName;
        }

        public int getShopId() {
            return ShopId;
        }

        public void setShopId(int shopId) {
            ShopId = shopId;
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

        public double getFreightAmount() {
            return FreightAmount;
        }

        public void setFreightAmount(double freightAmount) {
            FreightAmount = freightAmount;
        }

        public double getProductAmount() {
            return ProductAmount;
        }

        public void setProductAmount(double productAmount) {
            ProductAmount = productAmount;
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

        public double getOrderAmount() {
            return OrderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            OrderAmount = orderAmount;
        }

        public double getSaveAmount() {
            return SaveAmount;
        }

        public void setSaveAmount(double saveAmount) {
            SaveAmount = saveAmount;
        }

        public int getOrderState() {
            return OrderState;
        }

        public void setOrderState(int orderState) {
            OrderState = orderState;
        }

        public String getOrderMemo() {
            return OrderMemo;
        }

        public void setOrderMemo(String orderMemo) {
            OrderMemo = orderMemo;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.ServiceName);
            dest.writeInt(this.ShopId);
            dest.writeInt(this.OrderId);
            dest.writeString(this.OrderNO);
            dest.writeInt(this.OrderType);
            dest.writeDouble(this.FreightAmount);
            dest.writeDouble(this.ProductAmount);
            dest.writeDouble(this.ProductDiscount);
            dest.writeDouble(this.CouponDiscount);
            dest.writeDouble(this.OrderAmount);
            dest.writeDouble(this.SaveAmount);
            dest.writeInt(this.OrderState);
            dest.writeString(this.OrderMemo);
        }

        public OnlineSaleOrderBaseInfo() {
        }

        protected OnlineSaleOrderBaseInfo(Parcel in) {
            this.ServiceName = in.readString();
            this.ShopId = in.readInt();
            this.OrderId = in.readInt();
            this.OrderNO = in.readString();
            this.OrderType = in.readInt();
            this.FreightAmount = in.readDouble();
            this.ProductAmount = in.readDouble();
            this.ProductDiscount = in.readDouble();
            this.CouponDiscount = in.readDouble();
            this.OrderAmount = in.readDouble();
            this.SaveAmount = in.readDouble();
            this.OrderState = in.readInt();
            this.OrderMemo = in.readString();
        }

        public static final Parcelable.Creator<OnlineSaleOrderBaseInfo> CREATOR = new Parcelable.Creator<OnlineSaleOrderBaseInfo>() {
            @Override
            public OnlineSaleOrderBaseInfo createFromParcel(Parcel source) {
                return new OnlineSaleOrderBaseInfo(source);
            }

            @Override
            public OnlineSaleOrderBaseInfo[] newArray(int size) {
                return new OnlineSaleOrderBaseInfo[size];
            }
        };
    }


    /// <summary>
    /// 线上订单的时间相关信息
    /// </summary>
    public static class OnlineSaleOrderTimesInfo implements Parcelable {
        /// <summary>
        /// 订单提交时间
        /// </summary>
        private String OrderSubmitTimeStr;

        /// <summary>
        /// 订单处理时间
        /// </summary>
        private String DealSubmitTimeStr;


        /// <summary>
        /// 订单支付时间
        /// </summary>
        private String PayTimeStr;

        /// <summary>
        /// 预计送货时间
        /// </summary>
        private String PredictSendTime;

        public String getOrderSubmitTimeStr() {
            return OrderSubmitTimeStr;
        }

        public void setOrderSubmitTimeStr(String orderSubmitTimeStr) {
            OrderSubmitTimeStr = orderSubmitTimeStr;
        }

        public String getDealSubmitTimeStr() {
            return DealSubmitTimeStr;
        }

        public void setDealSubmitTimeStr(String dealSubmitTimeStr) {
            DealSubmitTimeStr = dealSubmitTimeStr;
        }

        public String getPayTimeStr() {
            return PayTimeStr;
        }

        public void setPayTimeStr(String payTimeStr) {
            PayTimeStr = payTimeStr;
        }

        public String getPredictSendTime() {
            return PredictSendTime;
        }

        public void setPredictSendTime(String predictSendTime) {
            PredictSendTime = predictSendTime;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.OrderSubmitTimeStr);
            dest.writeString(this.DealSubmitTimeStr);
            dest.writeString(this.PayTimeStr);
            dest.writeString(this.PredictSendTime);
        }

        public OnlineSaleOrderTimesInfo() {
        }

        protected OnlineSaleOrderTimesInfo(Parcel in) {
            this.OrderSubmitTimeStr = in.readString();
            this.DealSubmitTimeStr = in.readString();
            this.PayTimeStr = in.readString();
            this.PredictSendTime = in.readString();
        }

        public static final Parcelable.Creator<OnlineSaleOrderTimesInfo> CREATOR = new Parcelable.Creator<OnlineSaleOrderTimesInfo>() {
            @Override
            public OnlineSaleOrderTimesInfo createFromParcel(Parcel source) {
                return new OnlineSaleOrderTimesInfo(source);
            }

            @Override
            public OnlineSaleOrderTimesInfo[] newArray(int size) {
                return new OnlineSaleOrderTimesInfo[size];
            }
        };
    }


    /// <summary>
    /// 线上订单评价信息
    /// </summary>
    public static class OnlineSaleOrderRateInfo implements Parcelable {
        /// <summary>
        /// 订单评价信息
        /// </summary>
        private String OrderEvaluate;

        /// <summary>
        /// 评价回复信息
        /// </summary>
        private String AdminReply;

        public String getOrderEvaluate() {
            return OrderEvaluate;
        }

        public void setOrderEvaluate(String orderEvaluate) {
            OrderEvaluate = orderEvaluate;
        }

        public String getAdminReply() {
            return AdminReply;
        }

        public void setAdminReply(String adminReply) {
            AdminReply = adminReply;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.OrderEvaluate);
            dest.writeString(this.AdminReply);
        }

        public OnlineSaleOrderRateInfo() {
        }

        protected OnlineSaleOrderRateInfo(Parcel in) {
            this.OrderEvaluate = in.readString();
            this.AdminReply = in.readString();
        }

        public static final Parcelable.Creator<OnlineSaleOrderRateInfo> CREATOR = new Parcelable.Creator<OnlineSaleOrderRateInfo>() {
            @Override
            public OnlineSaleOrderRateInfo createFromParcel(Parcel source) {
                return new OnlineSaleOrderRateInfo(source);
            }

            @Override
            public OnlineSaleOrderRateInfo[] newArray(int size) {
                return new OnlineSaleOrderRateInfo[size];
            }
        };
    }


    /// <summary>
    /// 线上订单支付信息
    /// </summary>
    public static class OnlineSaleOrderPayInfo implements Parcelable {
        /// <summary>
        /// 订单的支付方式
        /// 1：货到付款
        /// 2：在线支付
        /// </summary>
        private int OrderPayType;


        /// <summary>
        /// 使用线上支付的类型
        /// 1：余额支付  2：支付宝支付  3：微信支付
        /// </summary>
        private int OnlinePayType;

        public int getOrderPayType() {
            return OrderPayType;
        }

        public void setOrderPayType(int orderPayType) {
            OrderPayType = orderPayType;
        }

        public int getOnlinePayType() {
            return OnlinePayType;
        }

        public void setOnlinePayType(int onlinePayType) {
            OnlinePayType = onlinePayType;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.OrderPayType);
            dest.writeInt(this.OnlinePayType);
        }

        public OnlineSaleOrderPayInfo() {
        }

        protected OnlineSaleOrderPayInfo(Parcel in) {
            this.OrderPayType = in.readInt();
            this.OnlinePayType = in.readInt();
        }

        public static final Parcelable.Creator<OnlineSaleOrderPayInfo> CREATOR = new Parcelable.Creator<OnlineSaleOrderPayInfo>() {
            @Override
            public OnlineSaleOrderPayInfo createFromParcel(Parcel source) {
                return new OnlineSaleOrderPayInfo(source);
            }

            @Override
            public OnlineSaleOrderPayInfo[] newArray(int size) {
                return new OnlineSaleOrderPayInfo[size];
            }
        };
    }


    /// <summary>
    /// 线上订单收货人相关信息
    /// </summary>
    public static class OnlineSaleOrderReceiverInfo implements Parcelable {
        /// <summary>
        /// 收货联系人信息
        /// </summary>
        private String Contacter;

        /// <summary>
        /// 联系电话
        /// </summary>
        private String ContacterPhone;

        /// <summary>
        /// 联系地址
        /// </summary>
        private String ContacterAddress;

        /// <summary>
        /// 收货地址的纬度
        /// </summary>
        private double ReceiveAddressLat;

        /// <summary>
        /// 收货地址的经度
        /// </summary>
        private double ReceiveAddressLng;

        /// <summary>
        /// 配送方式
        /// </summary>
        private int DeliveryType;
        private String DeliveryTypeStr;

        public String getContacter() {
            return Contacter;
        }

        public void setContacter(String contacter) {
            Contacter = contacter;
        }

        public String getContacterPhone() {
            return ContacterPhone;
        }

        public void setContacterPhone(String contacterPhone) {
            ContacterPhone = contacterPhone;
        }

        public String getContacterAddress() {
            return ContacterAddress;
        }

        public void setContacterAddress(String contacterAddress) {
            ContacterAddress = contacterAddress;
        }

        public double getReceiveAddressLat() {
            return ReceiveAddressLat;
        }

        public void setReceiveAddressLat(double receiveAddressLat) {
            ReceiveAddressLat = receiveAddressLat;
        }

        public double getReceiveAddressLng() {
            return ReceiveAddressLng;
        }

        public void setReceiveAddressLng(double receiveAddressLng) {
            ReceiveAddressLng = receiveAddressLng;
        }

        public int getDeliveryType() {
            return DeliveryType;
        }

        public void setDeliveryType(int deliveryType) {
            DeliveryType = deliveryType;
        }

        public String getDeliveryTypeStr() {
            return DeliveryTypeStr;
        }

        public void setDeliveryTypeStr(String deliveryTypeStr) {
            DeliveryTypeStr = deliveryTypeStr;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.Contacter);
            dest.writeString(this.ContacterPhone);
            dest.writeString(this.ContacterAddress);
            dest.writeDouble(this.ReceiveAddressLat);
            dest.writeDouble(this.ReceiveAddressLng);
            dest.writeInt(this.DeliveryType);
            dest.writeString(this.DeliveryTypeStr);
        }

        public OnlineSaleOrderReceiverInfo() {
        }

        protected OnlineSaleOrderReceiverInfo(Parcel in) {
            this.Contacter = in.readString();
            this.ContacterPhone = in.readString();
            this.ContacterAddress = in.readString();
            this.ReceiveAddressLat = in.readDouble();
            this.ReceiveAddressLng = in.readDouble();
            this.DeliveryType = in.readInt();
            this.DeliveryTypeStr = in.readString();
        }

        public static final Parcelable.Creator<OnlineSaleOrderReceiverInfo> CREATOR = new Parcelable.Creator<OnlineSaleOrderReceiverInfo>() {
            @Override
            public OnlineSaleOrderReceiverInfo createFromParcel(Parcel source) {
                return new OnlineSaleOrderReceiverInfo(source);
            }

            @Override
            public OnlineSaleOrderReceiverInfo[] newArray(int size) {
                return new OnlineSaleOrderReceiverInfo[size];
            }
        };
    }


    /// <summary>
    /// 线上订单关联门店信息
    /// </summary>
    public static class OnlineSaleOrderShopsInfo implements Parcelable {
        /// <summary>
        /// 该订单关联的服务点
        /// </summary>
        private String ShopName ;

        /// <summary>
        /// 订单关联服务点的编号
        /// </summary>
        private int ShopId ;

        /// <summary>
        /// 门店联系电话
        /// </summary>
        private String ShopMobile ;

        /// <summary>
        /// 门店营业时间
        /// </summary>
        private String ServiceTime ;

        /// <summary>
        /// 门店地址
        /// </summary>
        private String Address ;


        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String shopName) {
            ShopName = shopName;
        }

        public int getShopId() {
            return ShopId;
        }

        public void setShopId(int shopId) {
            ShopId = shopId;
        }

        public String getShopMobile() {
            return ShopMobile;
        }

        public void setShopMobile(String shopMobile) {
            ShopMobile = shopMobile;
        }

        public String getServiceTime() {
            return ServiceTime;
        }

        public void setServiceTime(String serviceTime) {
            ServiceTime = serviceTime;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.ShopName);
            dest.writeInt(this.ShopId);
            dest.writeString(this.ShopMobile);
            dest.writeString(this.ServiceTime);
            dest.writeString(this.Address);
        }

        public OnlineSaleOrderShopsInfo() {
        }

        protected OnlineSaleOrderShopsInfo(Parcel in) {
            this.ShopName = in.readString();
            this.ShopId = in.readInt();
            this.ShopMobile = in.readString();
            this.ServiceTime = in.readString();
            this.Address = in.readString();
        }

        public static final Creator<OnlineSaleOrderShopsInfo> CREATOR = new Creator<OnlineSaleOrderShopsInfo>() {
            @Override
            public OnlineSaleOrderShopsInfo createFromParcel(Parcel source) {
                return new OnlineSaleOrderShopsInfo(source);
            }

            @Override
            public OnlineSaleOrderShopsInfo[] newArray(int size) {
                return new OnlineSaleOrderShopsInfo[size];
            }
        };
    }


    public static class OrderProductDetail implements Parcelable {


        private int ProductId;
        private String ProductName;
        private double RetailPrice;
        private String ProductUnit;
        private String ThumbImgPath;
        private double MemberPrice;
        private int Quantity;

        public int getProductId() {
            return ProductId;
        }

        public void setProductId(int productId) {
            ProductId = productId;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String productName) {
            ProductName = productName;
        }

        public double getRetailPrice() {
            return RetailPrice;
        }

        public void setRetailPrice(double retailPrice) {
            RetailPrice = retailPrice;
        }

        public String getProductUnit() {
            return ProductUnit;
        }

        public void setProductUnit(String productUnit) {
            ProductUnit = productUnit;
        }

        public String getThumbImgPath() {
            return ThumbImgPath;
        }

        public void setThumbImgPath(String thumbImgPath) {
            ThumbImgPath = thumbImgPath;
        }

        public double getMemberPrice() {
            return MemberPrice;
        }

        public void setMemberPrice(double memberPrice) {
            MemberPrice = memberPrice;
        }

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int quantity) {
            Quantity = quantity;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.ProductId);
            dest.writeString(this.ProductName);
            dest.writeDouble(this.RetailPrice);
            dest.writeString(this.ProductUnit);
            dest.writeString(this.ThumbImgPath);
            dest.writeDouble(this.MemberPrice);
            dest.writeInt(this.Quantity);
        }

        public OrderProductDetail() {
        }

        protected OrderProductDetail(Parcel in) {
            this.ProductId = in.readInt();
            this.ProductName = in.readString();
            this.RetailPrice = in.readDouble();
            this.ProductUnit = in.readString();
            this.ThumbImgPath = in.readString();
            this.MemberPrice = in.readDouble();
            this.Quantity = in.readInt();
        }

        public static final Parcelable.Creator<OrderProductDetail> CREATOR = new Parcelable.Creator<OrderProductDetail>() {
            @Override
            public OrderProductDetail createFromParcel(Parcel source) {
                return new OrderProductDetail(source);
            }

            @Override
            public OrderProductDetail[] newArray(int size) {
                return new OrderProductDetail[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.OrderBaseInfo, flags);
        dest.writeParcelable(this.OrderTimesInfo, flags);
        dest.writeParcelable(this.OrderPayInfo, flags);
        dest.writeParcelable(this.OrderRateInfo, flags);
        dest.writeParcelable(this.OrderRecevierInfo, flags);
        dest.writeParcelable(this.OrderShopInfo, flags);
        dest.writeTypedList(this.OrderProductsInfo);
    }

    public OrderDetail() {
    }

    protected OrderDetail(Parcel in) {
        this.OrderBaseInfo = in.readParcelable(OnlineSaleOrderBaseInfo.class.getClassLoader());
        this.OrderTimesInfo = in.readParcelable(OnlineSaleOrderTimesInfo.class.getClassLoader());
        this.OrderPayInfo = in.readParcelable(OnlineSaleOrderPayInfo.class.getClassLoader());
        this.OrderRateInfo = in.readParcelable(OnlineSaleOrderRateInfo.class.getClassLoader());
        this.OrderRecevierInfo = in.readParcelable(OnlineSaleOrderReceiverInfo.class.getClassLoader());
        this.OrderShopInfo = in.readParcelable(OnlineSaleOrderShopsInfo.class.getClassLoader());
        this.OrderProductsInfo = in.createTypedArrayList(OrderProductDetail.CREATOR);
    }

    public static final Parcelable.Creator<OrderDetail> CREATOR = new Parcelable.Creator<OrderDetail>() {
        @Override
        public OrderDetail createFromParcel(Parcel source) {
            return new OrderDetail(source);
        }

        @Override
        public OrderDetail[] newArray(int size) {
            return new OrderDetail[size];
        }
    };
}
