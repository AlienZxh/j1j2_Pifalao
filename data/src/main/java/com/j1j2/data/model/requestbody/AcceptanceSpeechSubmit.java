package com.j1j2.data.model.requestbody;

import com.j1j2.data.model.AcceptanceSpeechImg;

import java.util.List;

/**
 * Created by alienzxh on 16-9-20.
 */

public class AcceptanceSpeechSubmit {
    private int OrderId;
    private String Msg;
    private List<AcceptanceSpeechImg> Imgs;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public List<AcceptanceSpeechImg> getImgs() {
        return Imgs;
    }

    public void setImgs(List<AcceptanceSpeechImg> imgs) {
        Imgs = imgs;
    }
}
