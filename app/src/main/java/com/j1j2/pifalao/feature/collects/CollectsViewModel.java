package com.j1j2.pifalao.feature.collects;

import com.j1j2.data.http.api.UserFavoriteApi;
import com.j1j2.data.model.CollectedProduct;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.RemoveUserFavoritesBody;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.ArrayList;
import java.util.List;

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


    public CollectsViewModel(CollectsActivity collectsActivity, UserFavoriteApi userFavoriteApi) {
        this.collectsActivity = collectsActivity;
        this.userFavoriteApi = userFavoriteApi;
    }

    public void queryCollects() {
//        userFavoriteApi.queryUserFavorites("" + pageIndex, "" + pageSize)
//                .compose(collectsActivity.<WebReturn<PagerManager<CollectedProduct>>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new WebReturnSubscriber<PagerManager<CollectedProduct>>() {
//                    @Override
//                    public void onWebReturnSucess(PagerManager<CollectedProduct> collectedProductPagerManager) {
//
//                        collectsActivity.setAdapter(collectedProductPagerManager.getList());
//                    }
//
//                    @Override
//                    public void onWebReturnFailure(String errorMessage) {
//
//                    }
//
//                    @Override
//                    public void onWebReturnCompleted() {
//
//                    }
//                });
        collectsActivity.setAdapter(new ArrayList<CollectedProduct>());
    }

    public void removeItemFromUserFavorites(List<Integer> mainIds) {
        RemoveUserFavoritesBody removeUserFavoritesBody = new RemoveUserFavoritesBody();
        removeUserFavoritesBody.setMainIdList(mainIds);
        userFavoriteApi.removeItemFromUserFavorites(removeUserFavoritesBody)
                .compose(collectsActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {

                        collectsActivity.toastor.showSingletonToast(s);
                        queryCollects();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        collectsActivity.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });

    }

    public CollectsActivity getCollectsActivity() {
        return collectsActivity;
    }
}
