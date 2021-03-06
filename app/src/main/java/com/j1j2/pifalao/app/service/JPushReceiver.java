package com.j1j2.pifalao.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.j1j2.common.util.AppUtil;
import com.j1j2.data.model.JPushExtra;
import com.j1j2.pifalao.app.event.OrderStateChangeEvent;
import com.j1j2.pifalao.app.provider.GsonProvider;
import com.j1j2.pifalao.feature.launch.LaunchActivity;
import com.j1j2.pifalao.feature.messages.MessagesActivity;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;


public class JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {


        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            BackGroundService.updateUnRead(context);
            if (bundle != null) {
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                if (extras != null) {
                    JPushExtra jPushExtra = GsonProvider.provideGson().fromJson(extras, JPushExtra.class);
                    EventBus.getDefault().post(new OrderStateChangeEvent(true, 0, jPushExtra.getOrderState()));
                }
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {

//            Intent i = new Intent(context, MessagesActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
                .getAction())) {

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
                .getAction())) {

        } else {

        }
    }

}
