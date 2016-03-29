package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-3-27.
 */
public class DeliveryServiceTime {

    /**
     * FullStringTimeSegement : 2016-03-28 12:00-13:00
     * FullLocalStringTimeSegement : 明天(03-28) 12:00-13:00
     */

    private String FullStringTimeSegement;
    private String FullLocalStringTimeSegement;

    public String getFullStringTimeSegement() {
        return FullStringTimeSegement;
    }

    public void setFullStringTimeSegement(String FullStringTimeSegement) {
        this.FullStringTimeSegement = FullStringTimeSegement;
    }

    public String getFullLocalStringTimeSegement() {
        return FullLocalStringTimeSegement;
    }

    public void setFullLocalStringTimeSegement(String FullLocalStringTimeSegement) {
        this.FullLocalStringTimeSegement = FullLocalStringTimeSegement;
    }
}
