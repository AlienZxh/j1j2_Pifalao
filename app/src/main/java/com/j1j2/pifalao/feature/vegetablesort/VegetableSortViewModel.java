package com.j1j2.pifalao.feature.vegetablesort;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.SecondarySort;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.feature.home.storestylehome.StoreStyleHomeAdapter;
import com.j1j2.pifalao.feature.home.vegetablehome.VegetableHomeFragment;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-4-6.
 */
public class VegetableSortViewModel {

    private VegetableSortFragment vegetableSortFragment;
    private ProductApi productApi;

    public VegetableSortViewModel(VegetableSortFragment vegetableSortFragment, ProductApi productApi) {
        this.vegetableSortFragment = vegetableSortFragment;
        this.productApi = productApi;
    }

    public void queryProductSort(int moduleId) {
        vegetableSortFragment.showload();
        productApi.queryProductSort("", moduleId)
                .compose(vegetableSortFragment.<WebReturn<List<SecondarySort>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<SecondarySort>>() {
                    @Override
                    public void onWebReturnSucess(List<SecondarySort> mSecondarySorts) {
                        vegetableSortFragment.initList(mSecondarySorts);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }
}
