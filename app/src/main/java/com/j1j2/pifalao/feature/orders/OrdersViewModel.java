package com.j1j2.pifalao.feature.orders;

import android.widget.Toast;

import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.SetOrderReadStateBody;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.OrderCancelEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-22.
 */
public class OrdersViewModel {
    private OrdersActivity ordersActivity;
    private UserOrderApi userOrderApi;


    private OrdersAdapter ordersAdapter;

    private int pageIndex = 1;
    private int pageSize = 20;
    private int pageCount = 0;

    public OrdersViewModel(OrdersActivity ordersActivity, UserOrderApi userOrderApi) {
        this.ordersActivity = ordersActivity;
        this.userOrderApi = userOrderApi;

    }

    public void queryOrders(boolean isRefresh, final int orderType) {
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
                        if (orderType != 0)
                            setOrdersRead(orderSimplePagerManager.getList(), orderType);
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

    public void cancleOrder(int orderId) {
        userOrderApi.cancleOrder(orderId)
                .compose(ordersActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        Toast.makeText(ordersActivity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new OrderCancelEvent());

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(ordersActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void setOrdersRead(List<OrderSimple> ordersRead, int orderType) {
        if (ordersRead == null || ordersRead.size() <= 0)
            return;
        SetOrderReadStateBody setOrderReadStateBody = new SetOrderReadStateBody();
        List<Integer> ids = new ArrayList<>();
        for (OrderSimple orderSimple : ordersRead) {
            ids.add(orderSimple.getOrderId());
        }
        setOrderReadStateBody.setOrderIdList(ids);
        setOrderReadStateBody.setState(orderType);

        userOrderApi.setOrderReadState(setOrderReadStateBody)
                .compose(ordersActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                    }

                    @Override
                    public void onWebReturnCompleted() {
                    }
                });
    }

    public OrdersActivity getOrdersActivity() {
        return ordersActivity;
    }
}
