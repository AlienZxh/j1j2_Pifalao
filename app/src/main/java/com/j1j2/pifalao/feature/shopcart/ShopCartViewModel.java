package com.j1j2.pifalao.feature.shopcart;

import android.databinding.ObservableField;
import android.widget.Toast;

import com.j1j2.data.http.api.CountDownApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.UserDeliveryTime;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-21.
 */
public class ShopCartViewModel {
    private ShopCartApi shopCartApi;
    private CountDownApi countDownApi;
    private ShopCartActivity shopCartActivity;
    private int moduleId;
    private ShopCartAdapter shopCartAdapter;
    private ShopCart shopCart;

    public ObservableField<String> hour = new ObservableField<>();
    public ObservableField<String> minute = new ObservableField<>();
    public ObservableField<String> second = new ObservableField<>();

    long remian = 0;

    private List<ShopCartItem> shopCartItems;

    public ShopCartViewModel(ShopCartApi shopCartApi, ShopCartActivity shopCartActivity, int moduleId, CountDownApi countDownApi, ShopCart shopCart) {
        this.shopCartApi = shopCartApi;
        this.countDownApi = countDownApi;
        this.shopCartActivity = shopCartActivity;
        this.moduleId = moduleId;
        this.shopCart = shopCart;
    }

    public void queryShopCart(final int moduleType) {
        shopCartApi.queryShopCart(moduleId)
                .compose(shopCartActivity.<WebReturn<List<ShopCartItem>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<ShopCartItem>>() {
                    @Override
                    public void onWebReturnSucess(List<ShopCartItem> mShopCartItems) {
                        shopCartItems = mShopCartItems;
                        shopCartAdapter = new ShopCartAdapter(shopCartItems, moduleType);
                        shopCartActivity.setAdapter(shopCartAdapter);
                        shopCart.setShopCartItemList(shopCartItems);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void removeShopCartItem(int productId) {
        shopCartApi.removeShopCartItem(productId)
                .compose(shopCartActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String str) {
                        Toast.makeText(shopCartActivity, str, Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(shopCartActivity, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void updateShopCart() {
        shopCartApi.updateShopCart(shopCartItems)
                .compose(shopCartActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String str) {
                        Toast.makeText(shopCartActivity, str, Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(shopCartActivity, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void CountDown() {
        countDownApi.QueryDeliveryCountDownOfModule(moduleId)
                .compose(shopCartActivity.<WebReturn<UserDeliveryTime>>bindToLifecycle())
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
                .compose(shopCartActivity.<Long>bindToLifecycle())
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

    private void initDate(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60) + days * 24;
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        hour.set(hours < 10 ? ("0" + hours) : ("" + hours));
        minute.set(minutes < 10 ? ("0" + minutes) : ("" + minutes));
        second.set(seconds < 10 ? ("0" + seconds) : ("" + seconds));
    }

    public ShopCartActivity getShopCartActivity() {
        return shopCartActivity;
    }

    public ShopCart getShopCart() {
        return shopCart;
    }

    public List<ShopCartItem> getShopCartItems() {
        return shopCartItems;
    }
}
