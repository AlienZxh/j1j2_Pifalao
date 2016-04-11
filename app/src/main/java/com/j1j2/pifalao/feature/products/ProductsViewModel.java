package com.j1j2.pifalao.feature.products;

import android.databinding.ObservableField;
import android.widget.Toast;

import com.j1j2.data.http.api.CountDownApi;
import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.data.model.UserDeliveryTime;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import rx.Observable;
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
    private ShopCartApi shopCartApi;
    private CountDownApi countDownApi;
    private PagerManager<ProductSimple> productSimples;


    private String key;

    private int orderBy = 0;//2:按价格  1:销量   3:人气  0:默认

    private int pageIndex = 1;
    private final int pageSize = 20;
    private int pageCount = 0;

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> hour = new ObservableField<>();
    public ObservableField<String> minute = new ObservableField<>();
    public ObservableField<String> second = new ObservableField<>();

    long remian = 0;

    public ProductsViewModel(ProductsActivity productsActivity, ProductSort productSort, Module module, ProductApi productApi, CountDownApi countDownApi, String key, ShopCartApi shopCartApi) {
        this.productsActivity = productsActivity;
        this.productSort = productSort;
        this.module = module;
        this.productApi = productApi;
        this.countDownApi = countDownApi;
        this.key = key;
        this.shopCartApi = shopCartApi;
        productAdapter = new ProductsAdapter(new ArrayList<ProductSimple>(), module.getModuleType());
    }


    public void queryProductyByKey(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            productsActivity.setLoadMoreBegin();
        }
        productApi.puzzyQueryProduct(module.getWareHouseModuleId(), "" + pageIndex, "" + pageSize, key)
                .compose(productsActivity.<WebReturn<PagerManager<ProductSimple>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<ProductSimple>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<ProductSimple> productSimplePagerManager) {
                        pageCount = productSimplePagerManager.getPageCount();
                        if (pageIndex == 1) {
                            productAdapter.initData(productSimplePagerManager.getList());
                            productsActivity.setProdutsAdapter(productAdapter);
                        } else if (pageIndex <= pageCount) {
                            productAdapter.addAll(productSimplePagerManager.getList());
                        } else {
                            productsActivity.setLoadMoreComplete();
                        }
                        pageIndex++;
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public void queryProductyBySortId(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            productsActivity.setLoadMoreBegin();
        }
        productApi.queryProductyBySortId("" + productSort.getSortId(), "" + pageIndex, "" + pageSize, "" + (productSort.getParentSortId() <= 0), orderBy)
                .compose(productsActivity.<WebReturn<PagerManager<ProductSimple>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<ProductSimple>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<ProductSimple> productSimplePagerManager) {
                        pageCount = productSimplePagerManager.getPageCount();
                        if (pageIndex == 1) {
                            productAdapter.initData(productSimplePagerManager.getList());
                            productsActivity.setProdutsAdapter(productAdapter);
                        } else if (pageIndex <= pageCount) {
                            productAdapter.addAll(productSimplePagerManager.getList());
                        } else {
                            productsActivity.setLoadMoreComplete();
                        }
                        pageIndex++;
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void CountDown() {
        countDownApi.QueryDeliveryCountDownOfModule(module.getWareHouseModuleId())
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
                                    + " " + userDeliveryTimeWebReturn.getDetail().getTimeSpan() + ":00");
                            Date beginDate = simple.parse(userDeliveryTimeWebReturn.getDetail().getNow());
                            remian = endDate.getTime() - beginDate.getTime();
                        } catch (ParseException e) {

                        }
                        return Observable.interval(1, TimeUnit.SECONDS);
                    }
                })
                .compose(productsActivity.<Long>bindToLifecycle())
                .observeOn(Schedulers.io())
                .subscribe(new DefaultSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        remian -= 1000;
                        initDate(remian);
                    }
                });

    }

    public void addItemToShopCart(final ProductUnit unit, final int quantity) {
        shopCartApi.addItemToShopCart(unit.getProductId(), quantity, module.getWareHouseModuleId())
                .compose(productsActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        productsActivity.addShopCart(unit, quantity);
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(productsActivity, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
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

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public ProductSort getProductSort() {
        return productSort;
    }

    public ProductsActivity getProductsActivity() {
        return productsActivity;
    }

    public ProductsAdapter getProductAdapter() {
        return productAdapter;
    }
}
