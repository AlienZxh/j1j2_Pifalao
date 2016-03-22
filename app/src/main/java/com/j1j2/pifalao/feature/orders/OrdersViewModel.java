package com.j1j2.pifalao.feature.orders;

import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-22.
 */
public class OrdersViewModel {
    private OrdersActivity ordersActivity;
    private UserOrderApi userOrderApi;
    private int orderType;

    private OrdersAdapter ordersAdapter;

    private int pageIndex = 1;
    private int pageSize = 20;
    private int pageCount = 0;

    public OrdersViewModel(OrdersActivity ordersActivity, UserOrderApi userOrderApi, int orderType) {
        this.ordersActivity = ordersActivity;
        this.userOrderApi = userOrderApi;
        this.orderType = orderType;
    }

    public void queryOrders(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            ordersActivity.setLoadMoreEnable(true);
        }
        userOrderApi.queryOrders("" + pageIndex, "" + pageSize, "" + orderType)
                .compose(ordersActivity.<WebReturn<PagerManager<OrderSimple>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<OrderSimple>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<OrderSimple> orderSimplePagerManager) {
                        pageCount = orderSimplePagerManager.getPageCount();
                        if (pageIndex == 1) {
                            ordersAdapter = new OrdersAdapter(orderSimplePagerManager.getList());
                            ordersActivity.setOrdersAdapter(ordersAdapter);
//                            ordersAdapter.notifyItemRangeChanged(0, orderSimplePagerManager.getList().size());
                            if (pageIndex == pageCount) {
                                ordersActivity.setLoadMoreFinish();
                                ordersActivity.setLoadMoreEnable(false);
                            }
                        } else {
                            if (pageIndex < pageCount) {
                                ordersAdapter.addAll(orderSimplePagerManager.getList());
                                ordersActivity.setLoadMoreFinish();
                            } else if (pageIndex == pageCount) {
                                ordersAdapter.addAll(orderSimplePagerManager.getList());
                                ordersActivity.setLoadMoreFinish();
                                ordersActivity.setLoadMoreEnable(false);
                            } else {
                                ordersActivity.setLoadMoreFinish();
                                ordersActivity.setLoadMoreEnable(false);
                            }
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
