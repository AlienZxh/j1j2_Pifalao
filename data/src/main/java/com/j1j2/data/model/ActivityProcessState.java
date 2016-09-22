package com.j1j2.data.model;

import java.util.List;

/**
 * Created by alienzxh on 16-9-17.
 */
public class ActivityProcessState {
    /// <summary>
    /// 活动订单类型
    /// </summary>
    private int ActivityType;

    /// <summary>
    /// 活动商品类型
    /// </summary>
    private int ProductType;

    /// <summary>
    /// 活动订单流水号
    /// </summary>
    private String OrderNO;

    /// <summary>
    /// 活动商品简单详情
    /// </summary>
    private ActivityProcessStateProductInfo ProductInfo;

    /// <summary>
    /// 当前的状态
    /// </summary>
    private int CurrentOrderState;

    /// <summary>
    /// 中奖人领奖信息 可为空
    /// </summary>
    private WinnerAwardInfo WinnerAwardInfo;

    /// <summary>
    /// 晒单内容留言
    /// </summary>
    private AcceptanceSpeech AcceptanceSpeech;

    /// <summary>
    /// 处理时间链 按照状态顺序排序
    /// </summary>
    private List<ActivityStateChain> TimeChains;

    public int getActivityType() {
        return ActivityType;
    }

    public void setActivityType(int activityType) {
        ActivityType = activityType;
    }

    public int getProductType() {
        return ProductType;
    }

    public void setProductType(int productType) {
        ProductType = productType;
    }

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String orderNO) {
        OrderNO = orderNO;
    }

    public ActivityProcessStateProductInfo getProductInfo() {
        return ProductInfo;
    }

    public void setProductInfo(ActivityProcessStateProductInfo productInfo) {
        ProductInfo = productInfo;
    }

    public int getCurrentOrderState() {
        return CurrentOrderState;
    }

    public void setCurrentOrderState(int currentOrderState) {
        CurrentOrderState = currentOrderState;
    }

    public com.j1j2.data.model.WinnerAwardInfo getWinnerAwardInfo() {
        return WinnerAwardInfo;
    }

    public void setWinnerAwardInfo(com.j1j2.data.model.WinnerAwardInfo winnerAwardInfo) {
        WinnerAwardInfo = winnerAwardInfo;
    }

    public com.j1j2.data.model.AcceptanceSpeech getAcceptanceSpeech() {
        return AcceptanceSpeech;
    }

    public void setAcceptanceSpeech(com.j1j2.data.model.AcceptanceSpeech acceptanceSpeech) {
        AcceptanceSpeech = acceptanceSpeech;
    }

    public List<ActivityStateChain> getTimeChains() {
        return TimeChains;
    }

    public void setTimeChains(List<ActivityStateChain> timeChains) {
        TimeChains = timeChains;
    }
}
