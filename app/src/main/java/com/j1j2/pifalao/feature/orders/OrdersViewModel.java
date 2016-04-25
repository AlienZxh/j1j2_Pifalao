package com.j1j2.pifalao.feature.orders;


import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.SetOrderReadStateBody;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.OrderStateChangeEvent;

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
            ordersActivity.setLoadMoreBegin();
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
                        } else if (pageIndex <= pageCount) {
                            ordersAdapter.addAll(orderSimplePagerManager.getList());
                        } else {
                            ordersActivity.setLoadMoreComplete();
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
                        ordersActivity.toastor.showSingletonToast(s);
                        EventBus.getDefault().post(new OrderStateChangeEvent(Constant.OrderType.ORDERTYPE_SUBMIT, Constant.OrderType.ORDERTYPE_INVALID));

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        ordersActivity.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void receiveOrder(int orderId) {
        userOrderApi.confrimReceive(orderId)
                .compose(ordersActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        ordersActivity.toastor.showSingletonToast(s);
                        EventBus.getDefault().post(new OrderStateChangeEvent(Constant.OrderType.ORDERTYPE_CLIENTWAITFORRECEVIE, Constant.OrderType.ORDERTYPE_WAITFORRATE));

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        ordersActivity.toastor.showSingletonToast(errorMessage);
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
