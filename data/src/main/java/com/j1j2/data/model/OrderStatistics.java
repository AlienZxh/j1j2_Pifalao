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
     */

    private int SubmitOrderCount;
    private int SubmitUnReadCount;
    private int ExcutingOrderCount;
    private int ExcuteingUnReadCount;
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

    public void setSubmitOrderCount(int SubmitOrderCount) {
        this.SubmitOrderCount = SubmitOrderCount;
    }

    public int getSubmitUnReadCount() {
        return SubmitUnReadCount;
    }

    public void setSubmitUnReadCount(int SubmitUnReadCount) {
        this.SubmitUnReadCount = SubmitUnReadCount;
    }

    public int getExcutingOrderCount() {
        return ExcutingOrderCount;
    }

    public void setExcutingOrderCount(int ExcutingOrderCount) {
        this.ExcutingOrderCount = ExcutingOrderCount;
    }

    public int getExcuteingUnReadCount() {
        return ExcuteingUnReadCount;
    }

    public void setExcuteingUnReadCount(int ExcuteingUnReadCount) {
        this.ExcuteingUnReadCount = ExcuteingUnReadCount;
    }

    public int getClientWaitForRecevie() {
        return ClientWaitForRecevie;
    }

    public void setClientWaitForRecevie(int ClientWaitForRecevie) {
        this.ClientWaitForRecevie = ClientWaitForRecevie;
    }

    public int getClientWaitForRecevieUnReadCount() {
        return ClientWaitForRecevieUnReadCount;
    }

    public void setClientWaitForRecevieUnReadCount(int ClientWaitForRecevieUnReadCount) {
        this.ClientWaitForRecevieUnReadCount = ClientWaitForRecevieUnReadCount;
    }

    public int getWaitForRate() {
        return WaitForRate;
    }

    public void setWaitForRate(int WaitForRate) {
        this.WaitForRate = WaitForRate;
    }

    public int getWaitForRateUnReadCount() {
        return WaitForRateUnReadCount;
    }

    public void setWaitForRateUnReadCount(int WaitForRateUnReadCount) {
        this.WaitForRateUnReadCount = WaitForRateUnReadCount;
    }

    public int getInvalid() {
        return Invalid;
    }

    public void setInvalid(int Invalid) {
        this.Invalid = Invalid;
    }

    public int getInvalidUnReadCount() {
        return InvalidUnReadCount;
    }

    public void setInvalidUnReadCount(int InvalidUnReadCount) {
        this.InvalidUnReadCount = InvalidUnReadCount;
    }

    public int getAllStateCount() {
        return AllStateCount;
    }

    public void setAllStateCount(int AllStateCount) {
        this.AllStateCount = AllStateCount;
    }
}
