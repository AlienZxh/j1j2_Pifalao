package com.j1j2.pifalao.app.event;

import com.litesuits.common.assist.Network;

/**
 * Created by alienzxh on 16-4-21.
 */
public class NetWorkEvent {

    private final boolean isConnected;
    private final Network.NetWorkType netWorkType;

    public NetWorkEvent(boolean isConnected, Network.NetWorkType netWorkType) {
        this.isConnected = isConnected;
        this.netWorkType = netWorkType;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public Network.NetWorkType getNetWorkType() {
        return netWorkType;
    }
}
