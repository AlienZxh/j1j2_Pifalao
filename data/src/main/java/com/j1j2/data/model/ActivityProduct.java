package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by alienzxh on 16-9-7.
 */
public class ActivityProduct implements Parcelable {
    private int ProductId;
    private String Name;
    private int LotteryId;
    private int OrderId;
    private String OrderNO;
    /// 活动商品类型
    /// 实物 Material=1,
    /// 话费充值虚拟商品 VirtualPhoneCharge=2
    private int Type;


    private int SortId;

    /// <summary>
    /// 活动商品的有效的截止时间
    /// </summary>
    private String InvalidTime;


    /// 活动商品状态
    /// Available=1,
    ///  Frozen=2
    private int State;

    /// <summary>
    /// 商品成本价
    /// </summary>
    private double CostPrice;

    /// <summary>
    /// 商品市场价
    /// </summary>
    private double RetialPrice;

    /// <summary>
    /// 显示排序值
    /// </summary>
    private int DisplayRank;

    private String Introduce;

    /// <summary>
    /// 活动商品图片集合
    /// </summary>
    private List<ActivityProductImg> ImgList;

    /// <summary>
    /// 活动商品 所属的 父级活动分类
    /// </summary>
    private String ParentSortName;

    /// <summary>
    /// 活动商品所属的子级活动分类
    /// </summary>
    private String SubSortName;

    /// <summary>
    /// 活动分类类型
    /// 1：兑换　　２：抽奖
    /// </summary>
    private int SortType;

    /// <summary>
    /// 活动商品参数配置
    /// </summary>
    private ActivityProductConfig Configs;

    /// <summary>
    /// 活动商品统计数据
    /// </summary>
    private ActivityProductStatistic Statistics;

    /// 兑换时间
    private String ExchangeTimeStr;

    /// 兑换数量
    private int Quantity;

    private int OrderState;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getLotteryId() {
        return LotteryId;
    }

    public void setLotteryId(int lotteryId) {
        LotteryId = lotteryId;
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

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getSortId() {
        return SortId;
    }

    public void setSortId(int sortId) {
        SortId = sortId;
    }

    public String getInvalidTime() {
        return InvalidTime;
    }

    public void setInvalidTime(String invalidTime) {
        InvalidTime = invalidTime;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public double getCostPrice() {
        return CostPrice;
    }

    public void setCostPrice(double costPrice) {
        CostPrice = costPrice;
    }

    public double getRetialPrice() {
        return RetialPrice;
    }

    public void setRetialPrice(double retialPrice) {
        RetialPrice = retialPrice;
    }

    public int getDisplayRank() {
        return DisplayRank;
    }

    public void setDisplayRank(int displayRank) {
        DisplayRank = displayRank;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public List<ActivityProductImg> getImgList() {
        return ImgList;
    }

    public void setImgList(List<ActivityProductImg> imgList) {
        ImgList = imgList;
    }

    public String getParentSortName() {
        return ParentSortName;
    }

    public void setParentSortName(String parentSortName) {
        ParentSortName = parentSortName;
    }

    public String getSubSortName() {
        return SubSortName;
    }

    public void setSubSortName(String subSortName) {
        SubSortName = subSortName;
    }

    public ActivityProductConfig getConfigs() {
        return Configs;
    }

    public void setConfigs(ActivityProductConfig configs) {
        Configs = configs;
    }

    public ActivityProductStatistic getStatistics() {
        return Statistics;
    }

    public void setStatistics(ActivityProductStatistic statistics) {
        Statistics = statistics;
    }

    public String getExchangeTimeStr() {
        return ExchangeTimeStr;
    }

    public void setExchangeTimeStr(String exchangeTimeStr) {
        ExchangeTimeStr = exchangeTimeStr;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getOrderState() {
        return OrderState;
    }

    public void setOrderState(int orderState) {
        OrderState = orderState;
    }


    public int getSortType() {
        return SortType;
    }

    public void setSortType(int sortType) {
        SortType = sortType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ProductId);
        dest.writeString(this.Name);
        dest.writeInt(this.LotteryId);
        dest.writeInt(this.OrderId);
        dest.writeString(this.OrderNO);
        dest.writeInt(this.Type);
        dest.writeInt(this.SortId);
        dest.writeString(this.InvalidTime);
        dest.writeInt(this.State);
        dest.writeDouble(this.CostPrice);
        dest.writeDouble(this.RetialPrice);
        dest.writeInt(this.DisplayRank);
        dest.writeString(this.Introduce);
        dest.writeTypedList(this.ImgList);
        dest.writeString(this.ParentSortName);
        dest.writeString(this.SubSortName);
        dest.writeInt(this.SortType);
        dest.writeParcelable(this.Configs, flags);
        dest.writeParcelable(this.Statistics, flags);
        dest.writeString(this.ExchangeTimeStr);
        dest.writeInt(this.Quantity);
        dest.writeInt(this.OrderState);
    }

    public ActivityProduct() {
    }

    protected ActivityProduct(Parcel in) {
        this.ProductId = in.readInt();
        this.Name = in.readString();
        this.LotteryId = in.readInt();
        this.OrderId = in.readInt();
        this.OrderNO = in.readString();
        this.Type = in.readInt();
        this.SortId = in.readInt();
        this.InvalidTime = in.readString();
        this.State = in.readInt();
        this.CostPrice = in.readDouble();
        this.RetialPrice = in.readDouble();
        this.DisplayRank = in.readInt();
        this.Introduce = in.readString();
        this.ImgList = in.createTypedArrayList(ActivityProductImg.CREATOR);
        this.ParentSortName = in.readString();
        this.SubSortName = in.readString();
        this.SortType = in.readInt();
        this.Configs = in.readParcelable(ActivityProductConfig.class.getClassLoader());
        this.Statistics = in.readParcelable(ActivityProductStatistic.class.getClassLoader());
        this.ExchangeTimeStr = in.readString();
        this.Quantity = in.readInt();
        this.OrderState = in.readInt();
    }

    public static final Creator<ActivityProduct> CREATOR = new Creator<ActivityProduct>() {
        @Override
        public ActivityProduct createFromParcel(Parcel source) {
            return new ActivityProduct(source);
        }

        @Override
        public ActivityProduct[] newArray(int size) {
            return new ActivityProduct[size];
        }
    };
}
