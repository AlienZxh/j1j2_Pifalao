package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-3-15.
 */
public class UserDeliveryTime {

    /**
     * CanOrder : true
     * Year : 2016
     * Month : 3
     * Day : 16
     * TimeSpan : 00:00
     * Reason : null
     * Now : 2016-03-15 15:55:39
     * DeliveryTimeTemplate : {"TemplateId":25,"TemplateName":"门店特殊时间","DeliveryDay":"1,2,3,4,5,6","OrderDealTime":"00:00-00:01"}
     */

    private boolean CanOrder;
    private int Year;
    private int Month;
    private int Day;
    private String TimeSpan;
    private Object Reason;
    private String Now;
    /**
     * TemplateId : 25
     * TemplateName : 门店特殊时间
     * DeliveryDay : 1,2,3,4,5,6
     * OrderDealTime : 00:00-00:01
     */

    private DeliveryTimeTemplateEntity DeliveryTimeTemplate;

    public void setCanOrder(boolean CanOrder) {
        this.CanOrder = CanOrder;
    }

    public void setYear(int Year) {
        this.Year = Year;
    }

    public void setMonth(int Month) {
        this.Month = Month;
    }

    public void setDay(int Day) {
        this.Day = Day;
    }

    public void setTimeSpan(String TimeSpan) {
        this.TimeSpan = TimeSpan;
    }

    public void setReason(Object Reason) {
        this.Reason = Reason;
    }

    public void setNow(String Now) {
        this.Now = Now;
    }

    public void setDeliveryTimeTemplate(DeliveryTimeTemplateEntity DeliveryTimeTemplate) {
        this.DeliveryTimeTemplate = DeliveryTimeTemplate;
    }

    public boolean isCanOrder() {
        return CanOrder;
    }

    public int getYear() {
        return Year;
    }

    public int getMonth() {
        return Month;
    }

    public int getDay() {
        return Day;
    }

    public String getTimeSpan() {
        return TimeSpan;
    }

    public Object getReason() {
        return Reason;
    }

    public String getNow() {
        return Now;
    }

    public DeliveryTimeTemplateEntity getDeliveryTimeTemplate() {
        return DeliveryTimeTemplate;
    }

    public static class DeliveryTimeTemplateEntity {
        private int TemplateId;
        private String TemplateName;
        private String DeliveryDay;
        private String OrderDealTime;

        public void setTemplateId(int TemplateId) {
            this.TemplateId = TemplateId;
        }

        public void setTemplateName(String TemplateName) {
            this.TemplateName = TemplateName;
        }

        public void setDeliveryDay(String DeliveryDay) {
            this.DeliveryDay = DeliveryDay;
        }

        public void setOrderDealTime(String OrderDealTime) {
            this.OrderDealTime = OrderDealTime;
        }

        public int getTemplateId() {
            return TemplateId;
        }

        public String getTemplateName() {
            return TemplateName;
        }

        public String getDeliveryDay() {
            return DeliveryDay;
        }

        public String getOrderDealTime() {
            return OrderDealTime;
        }
    }
}
