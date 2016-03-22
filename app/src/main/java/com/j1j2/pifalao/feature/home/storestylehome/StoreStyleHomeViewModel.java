package com.j1j2.pifalao.feature.home.storestylehome;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.SecondarySort;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-15.
 */
public class StoreStyleHomeViewModel {

    private StoreStyleHomeAdapter storeStyleHomeAdapter;

    private ProductApi productApi;

    private Module module;

    private List<SecondarySort> secondarySorts;

    private StoreStyleHomeFragment storeStyleHomeFragment;

    public StoreStyleHomeViewModel(StoreStyleHomeFragment storeStyleHomeFragment, ProductApi productApi, Module module) {
        this.productApi = productApi;
        this.module = module;
        this.storeStyleHomeFragment = storeStyleHomeFragment;
        this.secondarySorts = new ArrayList<>();

    }


    public void queryProductSort() {
        productApi.queryProductSort("", module.getWareHouseModuleId())
                .compose(storeStyleHomeFragment.<WebReturn<List<SecondarySort>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<SecondarySort>>() {
                    @Override
                    public void onWebReturnSucess(List<SecondarySort> secondarySorts) {
                        secondarySorts.addAll(secondarySorts);
                        storeStyleHomeAdapter = new StoreStyleHomeAdapter(secondarySorts);
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
