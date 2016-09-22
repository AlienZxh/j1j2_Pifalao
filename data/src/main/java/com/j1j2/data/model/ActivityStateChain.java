package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-9-17.
 */
public class ActivityStateChain {
    /// <summary>
    /// 状态值
    /// </summary>
    private int State;

    /// <summary>
    /// 处理时间
    /// </summary>
    private String ProcessTimeStr;

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getProcessTimeStr() {
        return ProcessTimeStr;
    }

    public void setProcessTimeStr(String processTimeStr) {
        ProcessTimeStr = processTimeStr;
    }
}
