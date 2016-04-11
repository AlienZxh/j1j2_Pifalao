package com.j1j2.pifalao.feature.main;

import android.widget.Toast;

import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserMessageApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.UnReadInfo;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-26.
 */
public class MainActivityViewModel {
    private MainActivity mainActivity;
    private ShopCartApi shopCartApi;
    private UserMessageApi userMessageApi;

    public MainActivityViewModel(MainActivity mainActivity, ShopCartApi shopCartApi, UserMessageApi userMessageApi) {
        this.mainActivity = mainActivity;
        this.shopCartApi = shopCartApi;
        this.userMessageApi = userMessageApi;

    }

    public void queryShopcart(int moduled) {
        shopCartApi.queryShopCart(moduled)
                .compose(mainActivity.<WebReturn<List<ShopCartItem>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<ShopCartItem>>() {
                    @Override
                    public void onWebReturnSucess(List<ShopCartItem> mShopCartItems) {
                        mainActivity.setShopCart(mShopCartItems);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryUserUnReadInfo() {
        userMessageApi.queryUserUnReadInfo()
                .compose(mainActivity.<WebReturn<UnReadInfo>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<UnReadInfo>() {
                    @Override
                    public void onWebReturnSucess(UnReadInfo unReadInfo) {
                        mainActivity.setUnReadInfo(unReadInfo);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }
}
