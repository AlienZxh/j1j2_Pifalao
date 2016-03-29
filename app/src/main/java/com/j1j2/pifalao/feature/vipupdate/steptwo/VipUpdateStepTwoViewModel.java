package com.j1j2.pifalao.feature.vipupdate.steptwo;

import android.widget.Toast;

import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.VipUpdateSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-28.
 */
public class VipUpdateStepTwoViewModel {
    private VipUpdateStepTwoActivity vipUpdateStepTwoActivity;
    private UserVipApi userVipApi;

    public VipUpdateStepTwoViewModel(VipUpdateStepTwoActivity vipUpdateStepTwoActivity, UserVipApi userVipApi) {
        this.vipUpdateStepTwoActivity = vipUpdateStepTwoActivity;
        this.userVipApi = userVipApi;
    }

    public void vipUpdate(final String code) {
        userVipApi.renewVip(code)
                .compose(vipUpdateStepTwoActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        Toast.makeText(vipUpdateStepTwoActivity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new VipUpdateSuccessEvent(code));
                        vipUpdateStepTwoActivity.finishUpdate(code);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(vipUpdateStepTwoActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public VipUpdateStepTwoActivity getVipUpdateStepTwoActivity() {
        return vipUpdateStepTwoActivity;
    }
}
