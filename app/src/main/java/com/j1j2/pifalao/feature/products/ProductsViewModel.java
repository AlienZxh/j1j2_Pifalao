package com.j1j2.pifalao.feature.products;

import android.databinding.ObservableField;

import com.j1j2.data.http.api.CountDownApi;
import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.SystemAssistApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.PromtionTime;
import com.j1j2.data.model.UserDeliveryTime;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-15.
 */
public class ProductsViewModel {

    private ProductsActivity productsActivity;
    private ProductSort productSort;
    private ProductsAdapter productAdapter;
    private com.j1j2.data.model.Module module;
    private ProductApi productApi;
    private CountDownApi countDownApi;
    private PagerManager<ProductSimple> productSimples;

    private int pageIndex = 1;
    private final int pageSize = 20;
    private int pageCount = 0;
    private int orderBy = 0;

    public ObservableField<String> hour = new ObservableField<>();
    public ObservableField<String> minute = new ObservableField<>();
    public ObservableField<String> second = new ObservableField<>();

    long remian = 0;

    public ProductsViewModel(ProductsActivity productsActivity, ProductSort productSort, Module module, ProductApi productApi, CountDownApi countDownApi) {
        this.productsActivity = productsActivity;
        this.productSort = productSort;
        this.module = module;
        this.productApi = productApi;
        this.countDownApi = countDownApi;
        hour.set("00");
        minute.set("00");
        second.set("00");
    }

    public void queryProductyBySortId(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            productsActivity.setLoadMoreEnable(true);
        }
        productApi.queryProductyBySortId("" + productSort.getSortId(), "" + pageIndex, "" + pageSize, "" + (productSort.getParentSortId() <= 0), orderBy)
                .compose(productsActivity.<WebReturn<PagerManager<ProductSimple>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WebReturn<PagerManager<ProductSimple>>>() {
                    @Override
                    public void onNext(WebReturn<PagerManager<ProductSimple>> pagerManagerWebReturn) {
                        if (pagerManagerWebReturn.isValue()) {
                            if (pageIndex == 1) {
                                pageCount = pagerManagerWebReturn.getDetail().getPageCount();
                                productAdapter = new ProductsAdapter(pagerManagerWebReturn.getDetail().getList());
                                productsActivity.setProdutsAdapter(productAdapter);
                                productAdapter.notifyItemRangeChanged(0, pagerManagerWebReturn.getDetail().getList().size());
                            } else {
                                if (pageIndex <= pageCount)
                                    productAdapter.addAll(pagerManagerWebReturn.getDetail().getList());
                                else {
                                    productsActivity.setLoadMoreEnable(false);
                                }
                                productsActivity.setLoadMoreFinish();
                            }
                            pageIndex++;
                        }
                    }
                });
    }

    public void CountDown() {

        countDownApi.queryUserDeliveryTime()
                .compose(productsActivity.<WebReturn<UserDeliveryTime>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<WebReturn<UserDeliveryTime>, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(WebReturn<UserDeliveryTime> userDeliveryTimeWebReturn) {

                        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date endDate = simple.parse(userDeliveryTimeWebReturn.getDetail().getYear()
                                    + "-" + userDeliveryTimeWebReturn.getDetail().getMonth()
                                    + "-" + userDeliveryTimeWebReturn.getDetail().getDay()
                                    + " " + userDeliveryTimeWebReturn.getDetail().getTimeSpan());
                            Date beginDate = simple.parse(userDeliveryTimeWebReturn.getDetail().getNow());
                            remian = endDate.getTime() - beginDate.getTime();
                        } catch (ParseException e) {

                        }
                        return Observable.interval(1, TimeUnit.SECONDS);
                    }
                }).subscribe(new DefaultSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                super.onNext(aLong);
                initDate(remian - aLong);
            }
        });

    }

    private void initDate(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        hour.set("" + hours);
        minute.set("" + minutes);
        second.set("" + seconds);
    }

    public ProductSort getProductSort() {
        return productSort;
    }
}
