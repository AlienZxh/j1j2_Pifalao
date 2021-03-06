package com.j1j2.pifalao.feature.home.vegetablehome;

import android.databinding.ObservableField;

import com.j1j2.data.http.api.BannerActivityApi;
import com.j1j2.data.http.api.CountDownApi;
import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.BannerActivity;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.Product;
import com.j1j2.data.model.ProductCategory;
import com.j1j2.data.model.UserDeliveryTime;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.QueryProductParams;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-4-5.
 */
public class VegetableHomeViewModel {
    private VegetableHomeFragment vegetableHomeFragment;
    private ProductApi productApi;
    private CountDownApi countDownApi;
    private BannerActivityApi bannerActivityApi;

    public ObservableField<String> hour = new ObservableField<>();
    public ObservableField<String> minute = new ObservableField<>();
    public ObservableField<String> second = new ObservableField<>();
    long remian = 0;

    public VegetableHomeViewModel(VegetableHomeFragment vegetableHomeFragment, ProductApi productApi, BannerActivityApi bannerActivityApi, CountDownApi countDownApi) {
        this.vegetableHomeFragment = vegetableHomeFragment;
        this.productApi = productApi;
        this.bannerActivityApi = bannerActivityApi;
        this.countDownApi = countDownApi;
    }

    public void queryBannerActivity(int moduleId) {
        bannerActivityApi.queryBannerActivities(moduleId)
                .compose(vegetableHomeFragment.<WebReturn<List<BannerActivity>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<BannerActivity>>() {
                    @Override
                    public void onWebReturnSucess(List<BannerActivity> bannerActivities) {
                        vegetableHomeFragment.initTopAdv(bannerActivities);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void CountDown(int moduleId) {
        countDownApi.QueryDeliveryCountDownOfModule(moduleId)
                .compose(vegetableHomeFragment.<WebReturn<UserDeliveryTime>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<WebReturn<UserDeliveryTime>>() {
                    @Override
                    public void call(WebReturn<UserDeliveryTime> userDeliveryTimeWebReturn) {
                        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date endDate = simple.parse(userDeliveryTimeWebReturn.getDetail().getYear()
                                    + "-" + userDeliveryTimeWebReturn.getDetail().getMonth()
                                    + "-" + userDeliveryTimeWebReturn.getDetail().getDay()
                                    + " " + userDeliveryTimeWebReturn.getDetail().getTimeSpan() + ":00");
                            Date beginDate = simple.parse(userDeliveryTimeWebReturn.getDetail().getNow());
                            remian = endDate.getTime() - beginDate.getTime();
                        } catch (ParseException e) {

                        }
                        Logger.d("remian " + remian);
                        if (remian > 86400000) {
                            vegetableHomeFragment.showTimeDialog();
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<WebReturn<UserDeliveryTime>, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(WebReturn<UserDeliveryTime> userDeliveryTimeWebReturn) {

                        return Observable.interval(1, TimeUnit.SECONDS);
                    }
                })
                .compose(vegetableHomeFragment.<Long>bindToLifecycle())
                .observeOn(Schedulers.io())
                .subscribe(new DefaultSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        remian -= 1000;
                        initDate(remian);
                    }
                })
        ;

    }

    private void initDate(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60) + days * 24;
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        hour.set(hours < 10 ? ("0" + hours) : ("" + hours));
        minute.set(minutes < 10 ? ("0" + minutes) : ("" + minutes));
        second.set(seconds < 10 ? ("0" + seconds) : ("" + seconds));
    }

    public void queryHotCategories(int serviceId, int shopId) {
        productApi.queryHotCategories(serviceId,shopId)
                .compose(vegetableHomeFragment.<WebReturn<List<ProductCategory>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<ProductCategory>>() {
                    @Override
                    public void onWebReturnSucess(List<ProductCategory> productCategories) {
                        vegetableHomeFragment.initHortSort(productCategories);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });


    }

    public void queryActivityProducts(int serviceId,int shopId) {
        QueryProductParams params = new QueryProductParams();
        params.setServiceId(serviceId);
        params.setShopId(shopId);
        params.setPageIndex(1);
        params.setPageSize(15);
        params.setQueryDataType(1);
        productApi.queryProducts(params)
                .compose(vegetableHomeFragment.<WebReturn<PagerManager<Product>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<Product>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<Product> productPagerManager) {
                        vegetableHomeFragment.initActivityProducts(productPagerManager.getList());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public VegetableHomeFragment getVegetableHomeFragment() {
        return vegetableHomeFragment;
    }
}
