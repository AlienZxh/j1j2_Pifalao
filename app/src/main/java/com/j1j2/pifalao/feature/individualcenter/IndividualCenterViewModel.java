package com.j1j2.pifalao.feature.individualcenter;

import android.databinding.ObservableField;
import android.widget.Toast;

import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-21.
 */
public class IndividualCenterViewModel {

    private IndividualCenterFragment individualCenterFragment;
    private UserLoginApi userLoginApi;
    private User user;
    private UserLoginPreference userLoginPreference;
    public ObservableField<User> userObservableField;

    public IndividualCenterViewModel(User user, IndividualCenterFragment individualCenterFragment, UserLoginApi userLoginApi) {
        this.user = user;
        this.individualCenterFragment = individualCenterFragment;
        this.userLoginApi = userLoginApi;
        this.userObservableField = new ObservableField<>();
        userObservableField.set(user);
    }

    public void queryUser() {
        userLoginApi.queryUserDetail()
                .compose(individualCenterFragment.<WebReturn<User>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<User>() {
                    @Override
                    public void onWebReturnSucess(User mUser) {
                        MainAplication.get(individualCenterFragment.getContext()).login(mUser);
                        userObservableField.set(mUser);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public IndividualCenterFragment getIndividualCenterFragment() {
        return individualCenterFragment;
    }


}
