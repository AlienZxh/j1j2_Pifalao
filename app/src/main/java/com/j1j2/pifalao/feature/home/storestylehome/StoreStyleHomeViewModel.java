package com.j1j2.pifalao.feature.home.storestylehome;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.ProductCategory;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-15.
 */
public class StoreStyleHomeViewModel {

    private StoreStyleHomeAdapter storeStyleHomeAdapter;

    private ProductApi productApi;

    private ShopSubscribeService shopSubscribeService;

    private StoreStyleHomeFragment storeStyleHomeFragment;

    public StoreStyleHomeViewModel(StoreStyleHomeFragment storeStyleHomeFragment, ProductApi productApi, ShopSubscribeService shopSubscribeService) {
        this.productApi = productApi;
        this.shopSubscribeService = shopSubscribeService;
        this.storeStyleHomeFragment = storeStyleHomeFragment;

    }


    public void queryProductSort(int shopId) {
        productApi.queryProductCategoryTrees(shopSubscribeService.getServiceId(), shopId)
                .compose(storeStyleHomeFragment.<WebReturn<List<ProductCategory>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<ProductCategory>>() {
                    @Override
                    public void onWebReturnSucess(List<ProductCategory> categories) {

                        storeStyleHomeAdapter = new StoreStyleHomeAdapter(categories);
                        storeStyleHomeFragment.setListAdapter(storeStyleHomeAdapter);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public StoreStyleHomeAdapter getStoreStyleHomeAdapter() {
        return storeStyleHomeAdapter;
    }
}
