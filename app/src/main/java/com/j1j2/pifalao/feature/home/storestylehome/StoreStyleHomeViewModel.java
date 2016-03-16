package com.j1j2.pifalao.feature.home.storestylehome;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.SecondarySort;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.DefaultSubscriber;

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

    private StoreStyleHomeActivity storeStyleHomeActivity;

    public StoreStyleHomeViewModel(StoreStyleHomeActivity storeStyleHomeActivity, ProductApi productApi, Module module) {
        this.productApi = productApi;
        this.module = module;
        this.storeStyleHomeActivity = storeStyleHomeActivity;
        this.secondarySorts = new ArrayList<>();

    }


    public void onCreate() {
        productApi.queryProductSort("", module.getWareHouseModuleId())
                .compose(storeStyleHomeActivity.<WebReturn<List<SecondarySort>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WebReturn<List<SecondarySort>>>() {
                    @Override
                    public void onNext(WebReturn<List<SecondarySort>> listWebReturn) {
                        super.onNext(listWebReturn);
                        secondarySorts.addAll(listWebReturn.getDetail());
                        storeStyleHomeAdapter = new StoreStyleHomeAdapter(secondarySorts);
                        storeStyleHomeActivity.setListAdapter(storeStyleHomeAdapter);
                    }
                });
    }


    public StoreStyleHomeAdapter getStoreStyleHomeAdapter() {
        return storeStyleHomeAdapter;
    }
}
