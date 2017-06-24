package com.j1j2.pifalao.feature.vegetablesort;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.ProductCategory;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

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

    public void queryProductSort(int serviceId,int shopId) {
        vegetableSortFragment.showload();
        productApi.queryProductCategoryTrees(serviceId, shopId)
                .compose(vegetableSortFragment.<WebReturn<List<ProductCategory>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<ProductCategory>>() {
                    @Override
                    public void onWebReturnSucess(List<ProductCategory> mSecondarySorts) {
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
