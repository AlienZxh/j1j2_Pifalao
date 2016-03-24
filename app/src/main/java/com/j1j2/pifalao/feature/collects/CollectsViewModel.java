package com.j1j2.pifalao.feature.collects;

import com.j1j2.data.http.api.UserFavoriteApi;
import com.j1j2.data.model.CollectedProduct;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-24.
 */
public class CollectsViewModel {
    private CollectsActivity collectsActivity;
    private UserFavoriteApi userFavoriteApi;

    private int pageIndex = 1;
    private int pageSize = 300;
    private int pageCount = 0;

    private CollectsAdapter collectsAdapter;

    public CollectsViewModel(CollectsActivity collectsActivity, UserFavoriteApi userFavoriteApi) {
        this.collectsActivity = collectsActivity;
        this.userFavoriteApi = userFavoriteApi;
    }

    public void queryCollects() {
        userFavoriteApi.queryUserFavorites("" + pageIndex, "" + pageSize)
                .compose(collectsActivity.<WebReturn<PagerManager<CollectedProduct>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<CollectedProduct>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<CollectedProduct> collectedProductPagerManager) {
                        collectsAdapter = new CollectsAdapter(collectedProductPagerManager.getList());
                        collectsActivity.setAdapter(collectsAdapter);
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
