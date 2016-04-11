package com.j1j2.pifalao.feature.home.vegetablehome;

import com.j1j2.data.http.api.BannerActivityApi;
import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.BannerActivity;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-4-5.
 */
public class VegetableHomeViewModel {
    private VegetableHomeFragment vegetableHomeFragment;
    private ProductApi productApi;
    private BannerActivityApi bannerActivityApi;

    public VegetableHomeViewModel(VegetableHomeFragment vegetableHomeFragment, ProductApi productApi, BannerActivityApi bannerActivityApi) {
        this.vegetableHomeFragment = vegetableHomeFragment;
        this.productApi = productApi;
        this.bannerActivityApi = bannerActivityApi;
    }

    public void queryBannerActivity(int moduleId) {
        bannerActivityApi.queryBannerActivities(moduleId)
                .compose(vegetableHomeFragment.<WebReturn<List<BannerActivity>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<BannerActivity>>() {
                    @Override
                    public void onWebReturnSucess(List<BannerActivity> bannerActivities) {
                        vegetableHomeFragment.initTopAdv(bannerActivities);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryHotSort(int moduleId) {
        productApi.queryHotSort(moduleId)
                .compose(vegetableHomeFragment.<WebReturn<List<ProductSort>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<ProductSort>>() {
                    @Override
                    public void onWebReturnSucess(List<ProductSort> productSorts) {
                        vegetableHomeFragment.initHortSort(productSorts);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });


    }

    public void queryActivityProducts(int moduleId) {
        productApi.queryActivityProducts(1, 20, moduleId, 1, "", "")
                .compose(vegetableHomeFragment.<WebReturn<PagerManager<ProductSimple>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<ProductSimple>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<ProductSimple> productSorts) {
                        vegetableHomeFragment.initActivityProducts(productSorts.getList());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public VegetableHomeFragment getVegetableHomeFragment() {
        return vegetableHomeFragment;
    }
}
