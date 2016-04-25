package com.j1j2.pifalao.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.j1j2.pifalao.app.event.NetWorkEvent;
import com.litesuits.common.assist.Network;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by alienzxh on 16-4-21.
 */
public class NetWorkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        EventBus.getDefault().postSticky(new NetWorkEvent(Network.isConnected(context), Network.getNetworkType(context)));

//            switch (Network.getNetworkType(context)) {
//                case Net2G:
//                case Net3G:
//                    EventBus.getDefault().postSticky(new NetWorkEvent("", false));
//                    EventBus.getDefault().post(new NetWorkEvent("处于2G/3G网络，建议切换到4G/WIFI网络。", true));
//                    break;
//                default:
//                    EventBus.getDefault().postSticky(new NetWorkEvent("", false));
//                    break;
//            }

    }


}
