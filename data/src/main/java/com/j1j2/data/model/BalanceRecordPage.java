package com.j1j2.data.model;

import java.util.List;

/**
 * Created by alienzxh on 16-8-23.
 */
public class BalanceRecordPage {
    private String NextSegementMinTimeStr;
    private List<BalanceRecord> Records;

    public String getNextSegementMinTimeStr() {
        return NextSegementMinTimeStr;
    }

    public void setNextSegementMinTimeStr(String nextSegementMinTimeStr) {
        NextSegementMinTimeStr = nextSegementMinTimeStr;
    }

    public List<BalanceRecord> getRecords() {
        return Records;
    }

    public void setRecords(List<BalanceRecord> records) {
        Records = records;
    }
}
