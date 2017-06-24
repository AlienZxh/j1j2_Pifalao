package com.j1j2.pifalao.feature.orderrate;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.OrderDetail;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ProductSaleOrderRateBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.OrderStateChangeEvent;
import com.j1j2.pifalao.databinding.ActivityOrderrateBinding;
import com.j1j2.pifalao.feature.orderdetail.OrderProductsAdapter;
import com.j1j2.pifalao.feature.orderrate.di.OrderRateModule;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-4-26.
 */
@RequireBundler
public class OrderRateActivity extends BaseActivity implements View.OnClickListener {

    ActivityOrderrateBinding binding;

    @Arg
    int orderId;

    @Inject
    UserOrderApi userOrderApi;

    OrderDetail orderDetail;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orderrate);

    }

    public void orderRate(ProductSaleOrderRateBody orderRateBody) {
        userOrderApi.orderRate(orderRateBody)
                .compose(this.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        toastor.showSingletonToast("订单评价成功");
                        EventBus.getDefault().post(new OrderStateChangeEvent(true, Constant.OrderType.ORDERTYPE_WAITFORRATE, Constant.OrderType.ORDERTYPE_COMPLETE));
                        finish();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryOrderDetail(int orderId) {
        userOrderApi.queryOrderByOrderId("" + orderId)
                .compose(this.<WebReturn<OrderDetail>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<OrderDetail>() {
                    @Override
                    public void onWebReturnSucess(OrderDetail orderDetail) {
                        binding.setOrderDetail(orderDetail);

                        OrderRateProductAdapter orderRateProductAdapter = new OrderRateProductAdapter(orderDetail.getOrderProductsInfo());
                        binding.orderList.setAdapter(orderRateProductAdapter);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });

    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        binding.rateBtn.setOnClickListener(this);
        //______________________________
        binding.orderList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.orderList.addItemDecoration(new VerticalDividerItemDecoration.Builder(this).size(AutoUtils.getPercentWidthSize(10)).colorResId(R.color.colorTransparent).build());
        queryOrderDetail(orderId);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new OrderRateModule()).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.rateBtn) {
            ProductSaleOrderRateBody productSaleOrderRateBody = new ProductSaleOrderRateBody();
            productSaleOrderRateBody.setOrderId(orderDetail.getOrderBaseInfo().getOrderId());
            productSaleOrderRateBody.setComment(binding.comment.getText().toString());
            productSaleOrderRateBody.setDeliverSpeed((byte) binding.deliverSpeed.getRating());
            productSaleOrderRateBody.setFoodTaste((byte) binding.foodTaste.getRating());
            productSaleOrderRateBody.setServiceAttitude((byte) binding.serviceAttitude.getRating());
            orderRate(productSaleOrderRateBody);
        }
    }
}
