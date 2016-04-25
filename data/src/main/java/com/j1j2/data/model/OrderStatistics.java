package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-3-22.
 */
public class OrderStatistics {

    /**
     * SubmitOrderCount : 0
     * SubmitUnReadCount : 0
     * ExcutingOrderCount : 0
     * ExcuteingUnReadCount : 0
     * ClientWaitForRecevie : 0
     * ClientWaitForRecevieUnReadCount : 0
     * WaitForRate : 0
     * WaitForRateUnReadCount : 0
     * Invalid : 4
     * InvalidUnReadCount : 0
     * AllStateCount : 8
     * DeliveryOrderCount
     * DeliveryUnReadCount
     */

    private int SubmitOrderCount;
    private int SubmitUnReadCount;
    private int ExcutingOrderCount;
    private int ExcuteingUnReadCount;
    private int DeliveryOrderCount;
    private int DeliveryUnReadCount;
    private int ClientWaitForRecevie;
    private int ClientWaitForRecevieUnReadCount;
    private int WaitForRate;
    private int WaitForRateUnReadCount;
    private int Invalid;
    private int InvalidUnReadCount;
    private int AllStateCount;

    public int getSubmitOrderCount() {
        return SubmitOrderCount;
    }

    public void setSubmitOrderCount(int submitOrderCount) {
        SubmitOrderCount = submitOrderCount;
    }

    public int getSubmitUnReadCount() {
        return SubmitUnReadCount;
    }

    public void setSubmitUnReadCount(int submitUnReadCount) {
        SubmitUnReadCount = submitUnReadCount;
    }

    public int getExcutingOrderCount() {
        return ExcutingOrderCount;
    }

    public void setExcutingOrderCount(int excutingOrderCount) {
        ExcutingOrderCount = excutingOrderCount;
    }

    public int getExcuteingUnReadCount() {
        return ExcuteingUnReadCount;
    }

    public void setExcuteingUnReadCount(int excuteingUnReadCount) {
        ExcuteingUnReadCount = excuteingUnReadCount;
    }

    public int getDeliveryOrderCount() {
        return DeliveryOrderCount;
    }

    public void setDeliveryOrderCount(int deliveryOrderCount) {
        DeliveryOrderCount = deliveryOrderCount;
    }

    public int getDeliveryUnReadCount() {
        return DeliveryUnReadCount;
    }

    public void setDeliveryUnReadCount(int deliveryUnReadCount) {
        DeliveryUnReadCount = deliveryUnReadCount;
    }

    public int getClientWaitForRecevie() {
        return ClientWaitForRecevie;
    }

    public void setClientWaitForRecevie(int clientWaitForRecevie) {
        ClientWaitForRecevie = clientWaitForRecevie;
    }

    public int getClientWaitForRecevieUnReadCount() {
        return ClientWaitForRecevieUnReadCount;
    }

    public void setClientWaitForRecevieUnReadCount(int clientWaitForRecevieUnReadCount) {
        ClientWaitForRecevieUnReadCount = clientWaitForRecevieUnReadCount;
    }

    public int getWaitForRate() {
        return WaitForRate;
    }

    public void setWaitForRate(int waitForRate) {
        WaitForRate = waitForRate;
    }

    public int getWaitForRateUnReadCount() {
        return WaitForRateUnReadCount;
    }

    public void setWaitForRateUnReadCount(int waitForRateUnReadCount) {
        WaitForRateUnReadCount = waitForRateUnReadCount;
    }

    public int getInvalid() {
        return Invalid;
    }

    public void setInvalid(int invalid) {
        Invalid = invalid;
    }

    public int getInvalidUnReadCount() {
        return InvalidUnReadCount;
    }

    public void setInvalidUnReadCount(int invalidUnReadCount) {
        InvalidUnReadCount = invalidUnReadCount;
    }

    public int getAllStateCount() {
        return AllStateCount;
    }

    public void setAllStateCount(int allStateCount) {
        AllStateCount = allStateCount;
    }
}
