package com.j1j2.pifalao.feature.individualcenter;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.j1j2.data.http.api.UserCouponApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.OrderStatistics;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-21.
 */
public class IndividualCenterViewModel {

    private IndividualCenterFragment individualCenterFragment;
    private UserLoginApi userLoginApi;
    private UserCouponApi userCouponApi;
    private UserOrderApi userOrderApi;
    private User user;
    private UserLoginPreference userLoginPreference;
    public ObservableField<User> userObservableField;
    public ObservableInt couponNum = new ObservableInt(0);
    public ObservableField<OrderStatistics> statisticsObservableField = new ObservableField<>();

    public IndividualCenterViewModel(User user, IndividualCenterFragment individualCenterFragment, UserLoginApi userLoginApi, UserCouponApi userCouponApi, UserOrderApi userOrderApi) {
        this.user = user;
        this.individualCenterFragment = individualCenterFragment;
        this.userLoginApi = userLoginApi;
        this.userCouponApi = userCouponApi;
        this.userOrderApi = userOrderApi;
        this.userObservableField = new ObservableField<>();
        userObservableField.set(user);
    }

    public void queryAllCoupons(Module module) {
        userCouponApi.queryUserCoupon(Constant.CouponType.COUPON_NORMAL, "" + (null == module ? "" : module.getWareHouseModuleId()))
                .compose(individualCenterFragment.<WebReturn<List<Coupon>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new WebReturnSubscriber<List<Coupon>>() {
                    @Override
                    public void onWebReturnSucess(List<Coupon> coupons) {
                        int i = 0;
                        for (Coupon coupon : coupons) {
                            if (!coupon.isExpired() && coupon.getState() == 1) {
                                i++;
                            }
                        }
                        couponNum.set(i);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
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

    public void queryOrderStatistics() {
        userOrderApi.queryProductOrderStatistics()
                .compose(individualCenterFragment.<WebReturn<OrderStatistics>>bindToLifecycle())
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


    public IndividualCenterFragment getIndividualCenterFragment() {
        return individualCenterFragment;
    }


}
