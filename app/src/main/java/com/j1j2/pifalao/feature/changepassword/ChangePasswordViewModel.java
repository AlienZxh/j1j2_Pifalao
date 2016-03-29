package com.j1j2.pifalao.feature.changepassword;

import android.widget.Toast;

import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ChangeUserPwdBody;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-28.
 */
public class ChangePasswordViewModel {

    private ChangePasswordActivity changePasswordActivity;
    private UserVipApi userVipApi;

    public ChangePasswordViewModel(ChangePasswordActivity changePasswordActivity, UserVipApi userVipApi) {
        this.changePasswordActivity = changePasswordActivity;
        this.userVipApi = userVipApi;
    }

    public void changePassword(String oldPSW, final String newPSW) {
        ChangeUserPwdBody changeUserPwdBody = new ChangeUserPwdBody();
        changeUserPwdBody.setNewPwd(newPSW);
        changeUserPwdBody.setOldPwd(oldPSW);
        userVipApi.changeUserPwd(changeUserPwdBody)
                .compose(changePasswordActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        Toast.makeText(changePasswordActivity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        changePasswordActivity.finishChange(newPSW);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(changePasswordActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public ChangePasswordActivity getChangePasswordActivity() {
        return changePasswordActivity;
    }
}
