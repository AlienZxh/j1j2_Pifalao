package com.j1j2.pifalao.app.event;

/**
 * Created by alienzxh on 16-9-29.
 */

public class NavigateToPrizeDetailEvent {

    private final int activityProductId;

    public NavigateToPrizeDetailEvent(int activityProductId) {
        this.activityProductId = activityProductId;
    }

    public int getActivityProductId() {
        return activityProductId;
    }
}
