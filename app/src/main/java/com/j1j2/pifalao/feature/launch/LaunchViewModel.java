package com.j1j2.pifalao.feature.launch;

import android.os.Environment;

import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.UpdateInfo;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.LoginBody;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.request.RequestCall;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-19.
 */
public class LaunchViewModel {

    private boolean isCheckingUpdate = false;
    private boolean canNavigate = true;

    private UserLoginApi userLoginApi;
    private LaunchActivity launchActivity;
    private UserLoginPreference userLoginPreference;


    public LaunchViewModel(UserLoginApi userLoginApi, LaunchActivity launchActivity, UserLoginPreference userLoginPreference) {
        this.userLoginApi = userLoginApi;
        this.launchActivity = launchActivity;
        this.userLoginPreference = userLoginPreference;
    }


    public void initLoginState() {
        LoginBody loginBody = new LoginBody();
        loginBody.setLoginAccount(userLoginPreference.getUsername(""));
        loginBody.setPassWord(userLoginPreference.getPassWord(""));
        loginBody.setUserType(5);
        loginBody.setTerminalType(1);
        userLoginApi.login(loginBody)
                .delay(1, TimeUnit.SECONDS)
                .compose(launchActivity.<WebReturn<User>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new WebReturnSubscriber<User>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                        while (isCheckingUpdate) {

                        }

                        if (!canNavigate)
                            return;
                        MainAplication.get(launchActivity).loginOut();
                        EventBus.getDefault().postSticky(new LogStateEvent(false));
                        Logger.d("pifalaolaunch loginError " + e.toString());
                        launchActivity.navigateTo();
                    }

                    @Override
                    public void onWebReturnSucess(User user) {

                        Logger.d("pifalaolaunch CanAutoLogin " + launchActivity.getResources().getBoolean(R.bool.can_auto_login));
                        Logger.d("pifalaolaunch IsAutoLogin " + userLoginPreference.getIsAutoLogin(false));

                        if (userLoginPreference.getIsAutoLogin(false) && launchActivity.getResources().getBoolean(R.bool.can_auto_login)) {
                            MainAplication.get(launchActivity).login(user);
                            EventBus.getDefault().postSticky(new LogStateEvent(true));
                            Logger.d("pifalaolaunch loginSucess");
                        } else {
                            MainAplication.get(launchActivity).loginOut();
                            EventBus.getDefault().postSticky(new LogStateEvent(false));
                            Logger.d("pifalaolaunch loginSucess but cant autologin");
                        }
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        MainAplication.get(launchActivity).loginOut();
                        EventBus.getDefault().postSticky(new LogStateEvent(false));
                        Logger.d("pifalaolaunch loginFailure ");
                    }

                    @Override
                    public void onWebReturnCompleted() {

                        while (isCheckingUpdate) {

                        }

                        if (!canNavigate)
                            return;
                        launchActivity.navigateTo();
                    }
                });
    }



    public void onDestory() {
        canNavigate = false;
        isCheckingUpdate = false;
    }

    public boolean isCheckingUpdate() {
        return isCheckingUpdate;
    }

    public void setCheckingUpdate(boolean checkingUpdate) {
        isCheckingUpdate = checkingUpdate;
    }


    public boolean isCanNavigate() {
        return canNavigate;
    }

    public void setCanNavigate(boolean canNavigate) {
        this.canNavigate = canNavigate;
    }
}
