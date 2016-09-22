package com.j1j2.data.model;

import java.util.List;

/**
 * Created by alienzxh on 16-9-20.
 */

public class LotteryCacluteResult {
    private List<LotteryCacluateTime> Times;
    private long A;
    private int B;
    private String LuckTicketNum;

    public List<LotteryCacluateTime> getTimes() {
        return Times;
    }

    public void setTimes(List<LotteryCacluateTime> times) {
        Times = times;
    }

    public long getA() {
        return A;
    }

    public void setA(long a) {
        A = a;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    public String getLuckTicketNum() {
        return LuckTicketNum;
    }

    public void setLuckTicketNum(String luckTicketNum) {
        LuckTicketNum = luckTicketNum;
    }
}
