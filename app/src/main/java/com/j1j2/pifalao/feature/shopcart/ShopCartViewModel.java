package com.j1j2.pifalao.feature.shopcart;

import android.databinding.ObservableField;

import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-21.
 */
public class ShopCartViewModel {
    private ShopCartApi shopCartApi;
    private ShopCartActivity shopCartActivity;
    private Module module;
    private ShopCartAdapter shopCartAdapter;

    public ObservableField<String> hour = new ObservableField<>();
    public ObservableField<String> minute = new ObservableField<>();
    public ObservableField<String> second = new ObservableField<>();

    public ShopCartViewModel(ShopCartApi shopCartApi, ShopCartActivity shopCartActivity, Module module) {
        this.shopCartApi = shopCartApi;
        this.shopCartActivity = shopCartActivity;
        this.module = module;
        hour.set("00");
        minute.set("00");
        second.set("00");
    }

    public void queryShopCart() {
        shopCartApi.queryShopCart(module.getWareHouseModuleId())
                .compose(shopCartActivity.<WebReturn<List<ShopCartItem>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<ShopCartItem>>() {
                    @Override
                    public void onWebReturnSucess(List<ShopCartItem> shopCartItems) {
                        shopCartAdapter = new ShopCartAdapter(shopCartItems);
                        shopCartActivity.setAdapter(shopCartAdapter);

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public ShopCartActivity getShopCartActivity() {
        return shopCartActivity;
    }
}
