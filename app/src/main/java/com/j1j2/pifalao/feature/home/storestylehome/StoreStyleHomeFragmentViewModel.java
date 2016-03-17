package com.j1j2.pifalao.feature.home.storestylehome;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.SecondarySort;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.DefaultSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-16.
 */
public class StoreStyleHomeFragmentViewModel {

    private StoreStyleHomeAdapter storeStyleHomeAdapter;

    private ProductApi productApi;

    private Module module;

    private List<SecondarySort> secondarySorts;

    private StoreStyleHomeFragment storeStyleHomeFragment;

    public StoreStyleHomeFragmentViewModel(StoreStyleHomeFragment storeStyleHomeFragment, ProductApi productApi, Module module) {
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
                .subscribe(new DefaultSubscriber<WebReturn<List<SecondarySort>>>() {
                    @Override
                    public void onNext(WebReturn<List<SecondarySort>> listWebReturn) {
                        super.onNext(listWebReturn);
                        secondarySorts.clear();
                        secondarySorts.addAll(listWebReturn.getDetail());
                        storeStyleHomeAdapter = new StoreStyleHomeAdapter(secondarySorts);
                        storeStyleHomeFragment.setListAdapter(storeStyleHomeAdapter);
                    }
                });
    }


    public StoreStyleHomeAdapter getStoreStyleHomeAdapter() {
        return storeStyleHomeAdapter;
    }
}
