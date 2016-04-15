package com.j1j2.pifalao.feature.offlineorders;

import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.OfflineOrderSimple;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.feature.orders.OrdersAdapter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-4-14.
 */
public class OfflineOrdersViewModel {
    private OfflineOrdersActivity offlineOrdersActivity;
    private UserOrderApi userOrderApi;

    private OfflineOrdersAdapter offlineOrdersAdapter;

    private int pageIndex = 1;
    private int pageCount;

    public OfflineOrdersViewModel(OfflineOrdersActivity offlineOrdersActivity, UserOrderApi userOrderApi) {
        this.offlineOrdersActivity = offlineOrdersActivity;
        this.userOrderApi = userOrderApi;
    }

    public void queryOfflineOrders(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            offlineOrdersActivity.setLoadMoreBegin();
        }
        userOrderApi.queryOfflineOrders(pageIndex)
                .compose(offlineOrdersActivity.<WebReturn<PagerManager<OfflineOrderSimple>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<OfflineOrderSimple>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<OfflineOrderSimple> offlineOrderSimplePagerManager) {
                        pageCount = offlineOrderSimplePagerManager.getPageCount();
                        if (pageIndex == 1) {
                            offlineOrdersAdapter = new OfflineOrdersAdapter(offlineOrderSimplePagerManager.getList());
                            offlineOrdersActivity.setAdapter(offlineOrdersAdapter);
                        } else if (pageIndex <= pageCount) {
                            offlineOrdersAdapter.addAll(offlineOrderSimplePagerManager.getList());
                        } else {
                            offlineOrdersActivity.setLoadMoreComplete();
                        }
                        pageIndex++;
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
