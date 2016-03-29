package com.j1j2.data.model.requestbody;

import java.util.List;

/**
 * Created by alienzxh on 16-3-10.
 */
public class SetOrderReadStateBody {

    private List<Integer> orderIdList;
    private int state;

    public List<Integer> getOrderIdList() {
        return orderIdList;
    }

    public void setOrderIdList(List<Integer> orderIdList) {
        this.orderIdList = orderIdList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SetOrderReadStateBody{" +
                "orderIdList=" + orderIdList +
                ", state=" + state +
                '}';
    }
}
