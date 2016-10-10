package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-9-20.
 */

public class ActivityOrderSimple {

    private String CreateTimeStr;

    /// <summary>
    /// 订单需要花费的积分
    /// </summary>
    private double SpendPoint;

    /// <summary>
    /// 订单支付的金额
    /// </summary>
    private double SpendMoney;
    private int OrderId;
    private String OrderNO;

    /// <summary>
    /// 活动订单类型
    /// 兑换活动订单
    ///ExchangeOrder=1
    /// 抽奖活动订单
    /// LotteryOrder=2
    /// </summary>
    private int ActivityOrderType;

    private boolean DispatchTicketFailed;

    public String getCreateTimeStr() {
        return CreateTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        CreateTimeStr = createTimeStr;
    }

    public double getSpendPoint() {
        return SpendPoint;
    }

    public void setSpendPoint(double spendPoint) {
        SpendPoint = spendPoint;
    }

    public double getSpendMoney() {
        return SpendMoney;
    }

    public void setSpendMoney(double spendMoney) {
        SpendMoney = spendMoney;
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

    public int getActivityOrderType() {
        return ActivityOrderType;
    }

    public void setActivityOrderType(int activityOrderType) {
        ActivityOrderType = activityOrderType;
    }

    public boolean isDispatchTicketFailed() {
        return DispatchTicketFailed;
    }

    public void setDispatchTicketFailed(boolean dispatchTicketFailed) {
        DispatchTicketFailed = dispatchTicketFailed;
    }
}
