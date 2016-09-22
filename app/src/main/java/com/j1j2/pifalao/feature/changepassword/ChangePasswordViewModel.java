package com.j1j2.pifalao.feature.changepassword;


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
        changePasswordActivity.showProgress("数据提交中");
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
                        changePasswordActivity.dismissProgress();
                        changePasswordActivity.toastor.showSingletonToast(s);
                        changePasswordActivity.finishChange(newPSW);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        changePasswordActivity.dismissProgress();
                        changePasswordActivity.toastor.showSingletonToast(errorMessage);
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
