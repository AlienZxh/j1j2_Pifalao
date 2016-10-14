package com.j1j2.pifalao.feature.individualcenter;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.OrderStatistics;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.trello.rxlifecycle.FragmentEvent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-21.
 */
public class IndividualCenterViewModel {

    private Gson gson;

    private IndividualCenterFragment individualCenterFragment;
    private UserLoginApi userLoginApi;
    private UserOrderApi userOrderApi;
    private User user;

    public ObservableField<User> userObservableField;
    public ObservableField<OrderStatistics> statisticsObservableField = new ObservableField<>();

    public IndividualCenterViewModel(Gson gson, User user, IndividualCenterFragment individualCenterFragment, UserLoginApi userLoginApi,  UserOrderApi userOrderApi) {
        this.gson = gson;
        this.user = user;
        this.individualCenterFragment = individualCenterFragment;
        this.userLoginApi = userLoginApi;

        this.userOrderApi = userOrderApi;
        this.userObservableField = new ObservableField<>();
        userObservableField.set(user);
    }

    public void queryUser(final boolean isUploadImg) {
        userLoginApi.queryUserDetail()
                .compose(individualCenterFragment.<WebReturn<User>>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<User>() {
                    @Override
                    public void onWebReturnSucess(User mUser) {
                        if (isUploadImg)
                            individualCenterFragment.getListener().dismissFragmentProgress();
                        MainAplication.get(individualCenterFragment.getContext()).login(mUser);
                        userObservableField.set(mUser);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        if (isUploadImg)
                            individualCenterFragment.getListener().dismissFragmentProgress();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        individualCenterFragment.getListener().dismissFragmentProgress();
                        individualCenterFragment.toastor.showSingletonToast("连接失败，请重试");
                    }
                });
    }

    public void queryOrderStatistics() {
        userOrderApi.queryProductOrderStatistics()
                .compose(individualCenterFragment.<WebReturn<OrderStatistics>>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<OrderStatistics>() {
                    @Override
                    public void onWebReturnSucess(OrderStatistics orderStatistics) {
                        statisticsObservableField.set(orderStatistics);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }


                });
    }

//    public void upLoadUserImg(String path) {
//        individualCenterFragment.getListener().showFragmentProgress("头像上传中");
//
//        File uploadFile = new File(path);
//
//        userLoginApi.postUserProtrait(  RequestBody.create(MediaType.parse("image/*"), uploadFile))
//                .compose(individualCenterFragment.<WebReturn<String>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new WebReturnSubscriber<String>() {
//                    @Override
//                    public void onWebReturnSucess(String s) {
//                        individualCenterFragment.toastor.showSingletonToast(s);
//                        queryUser();
//                    }
//
//                    @Override
//                    public void onWebReturnFailure(String errorMessage) {
//                        individualCenterFragment.getListener().dismissFragmentProgress();
//                        individualCenterFragment.toastor.showSingletonToast(errorMessage);
//                    }
//
//                    @Override
//                    public void onWebReturnCompleted() {
//
//                    }
//                });
//    }

    public void upLoadUserImg(File uploadFile) {
//       individualCenterFragment.getListener().showFragmentProgress("头像上传中");

        OkHttpUtils.post()
                .url(BuildConfig.IMAGE_URL + "/v5/UserLogin/PostUserProtrait")
                .addFile("imgFile", uploadFile.getName(), uploadFile)
                .build()
                .execute(new Callback<WebReturn<String>>() {
                    @Override
                    public WebReturn<String> parseNetworkResponse(Response response) throws Exception {
                        return gson.fromJson(response.body().string(), new TypeToken<WebReturn<String>>() {
                        }.getType());
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        individualCenterFragment.getListener().dismissFragmentProgress();
                    }

                    @Override
                    public void onResponse(WebReturn<String> response) {

                        if (response.isValue()) {
                            individualCenterFragment.toastor.showSingletonToast("头像更新成功");
                            queryUser(true);
                        } else {
                            individualCenterFragment.toastor.showSingletonToast(response.getErrorMessage());
                            individualCenterFragment.getListener().dismissFragmentProgress();
                        }
                    }
                });
    }


    public IndividualCenterFragment getIndividualCenterFragment() {
        return individualCenterFragment;
    }


}
