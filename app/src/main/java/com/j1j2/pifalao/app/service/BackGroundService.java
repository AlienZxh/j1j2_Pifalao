package com.j1j2.pifalao.app.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.google.gson.reflect.TypeToken;
import com.j1j2.data.model.UnReadInfo;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.provider.GsonProvider;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import okhttp3.Response;

/**
 * Created by alienzxh on 16-5-6.
 */
public class BackGroundService extends IntentService {

    public final static String UPDATEUNREAD = "updateUnRead";

    private UnReadInfoManager unReadInfoManager = null;
    private RequestCall updateUnReadCall = null;

    public BackGroundService() {
        super("BackGroundService");
    }

    public static void updateUnRead(Context context) {
        Intent intent = new Intent(context, BackGroundService.class);
        intent.putExtra("action", UPDATEUNREAD);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (MainAplication.get(this).isLogin()) {
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
        }
        updateUnReadCall = OkHttpUtils.post().url(BuildConfig.API_URL + "UserMessage/QueryUserUnReadInfo").tag(UPDATEUNREAD).build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getStringExtra("action").equals(UPDATEUNREAD)) {
            if (unReadInfoManager == null) {
                return;
            }
            if (updateUnReadCall.getCall() == null || updateUnReadCall.getCall().isCanceled()) {
                try {
                    Response response = updateUnReadCall.execute();
                    WebReturn<UnReadInfo> unReadInfoWebReturn = GsonProvider.provideGson().fromJson(response.body().string(), new TypeToken<WebReturn<UnReadInfo>>() {
                    }.getType());
                    unReadInfoManager.setUnReadInfo(unReadInfoWebReturn.getDetail());
//                    EventBus.getDefault().post(new BackgroundServiceEvent(intent.getStringExtra("action"), unReadInfoWebReturn.getDetail()));
                    Logger.e("BackGroundService " + unReadInfoWebReturn.toString());
                } catch (Exception exception) {
                    Logger.e("BackGroundService " + exception.getMessage());
                }
            }
        }


    }
}
