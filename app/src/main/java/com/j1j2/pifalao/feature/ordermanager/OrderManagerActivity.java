package com.j1j2.pifalao.feature.ordermanager;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityOrdermanagerBinding;
import com.j1j2.pifalao.feature.ordermanager.di.OrderManagerModule;
import com.j1j2.pifalao.feature.orders.OrdersActivity;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-22.
 */
@RequireBundler
public class OrderManagerActivity extends BaseActivity implements View.OnClickListener {

    ActivityOrdermanagerBinding binding;

    @Inject
    OrderManagerViewModel orderManagerViewModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ordermanager);
        binding.setOrderManagerViewModel(orderManagerViewModel);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onClick(View v) {
        if (v == binding.SubmitOrder) {
            navigate.navigateToOrders(this, null, false, OrdersActivity.ORDERTYPE_SUBMIT);
        } else if (v == binding.ExcutingOrder) {
            navigate.navigateToOrders(this, null, false, OrdersActivity.ORDERTYPE_EXECUTING);
        } else if (v == binding.ClientWaitForRecevieOrder) {
            navigate.navigateToOrders(this, null, false, OrdersActivity.ORDERTYPE_CLIENTWAITFORRECEVIE);
        } else if (v == binding.WaitForRateOrder) {
            navigate.navigateToOrders(this, null, false, OrdersActivity.ORDERTYPE_WAITFORRATE);
        } else if (v == binding.InvalidOrder) {
            navigate.navigateToOrders(this, null, false, OrdersActivity.ORDERTYPE_INVALID);
        } else if (v == binding.allOrder) {
            navigate.navigateToOrders(this, null, false, OrdersActivity.ORDERTYPE_ALL);
        }
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new OrderManagerModule(this)).inject(this);
    }
}
