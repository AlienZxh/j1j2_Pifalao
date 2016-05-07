package com.j1j2.pifalao.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.j1j2.pifalao.app.event.NetWorkEvent;
import com.j1j2.common.util.Network;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by alienzxh on 16-4-21.
 */
public class NetWorkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        EventBus.getDefault().postSticky(new NetWorkEvent(Network.isConnected(context), Network.getNetworkType(context)));
    }


}
