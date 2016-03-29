package com.j1j2.pifalao.app.event;

import com.baidu.location.BDLocation;

/**
 * Created by alienzxh on 16-3-26.
 */
public class LocationEvent {
    private final BDLocation location;

    public LocationEvent(BDLocation location) {
        this.location = location;
    }

    public BDLocation getLocation() {
        return location;
    }
}
