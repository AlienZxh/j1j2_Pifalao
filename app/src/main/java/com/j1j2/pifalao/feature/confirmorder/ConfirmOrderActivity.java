package com.j1j2.pifalao.feature.confirmorder;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.j1j2.common.view.WheelView;
import com.j1j2.data.model.DeliveryServiceTime;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityConfirmorderBinding;
import com.j1j2.pifalao.databinding.ViewDeliverytimePickerBinding;
import com.j1j2.pifalao.feature.confirmorder.di.ConfirmOrderModule;
import com.j1j2.pifalao.feature.successresult.SuccessResultActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.ParcelListSerializer;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-21.
 */
@RequireBundler
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {
    ActivityConfirmorderBinding binding;

    @Arg
    Module module;

    @Arg(serializer = ParcelListSerializer.class)
    List<ShopCartItem> shopCartItems;

    @Inject
    ConfirmOrderViewModel confirmOrderViewModel;

    @Inject
    UserRelativePreference userRelativePreference;

    @Inject
    ShopCart shopCart;

    ServicePoint servicePoint;

    DialogPlus timeDialog;
    ViewDeliverytimePickerBinding dialogBinding;

    public ObservableField<String> deliveryTime = new ObservableField<>();
    String timeString;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmorder);
        servicePoint = userRelativePreference.getSelectedServicePoint(null);
        binding.setServicepoint(servicePoint);
        binding.setShopCart(shopCart);
        binding.setConfirmOrderViewModel(confirmOrderViewModel);
    }

    @Override
    protected void initViews() {
        dialogBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_deliverytime_picker, null, false);
        timeDialog = DialogPlus.newDialog(this).setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setContentHolder(new ViewHolder(dialogBinding.getRoot()))
                .setContentBackgroundResource(R.color.colorWhite)
                .create();
        dialogBinding.timeickerCancel.setOnClickListener(this);
        dialogBinding.timeickerConFirm.setOnClickListener(this);
        dialogBinding.timePicker.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                timeString = item;
            }
        });
        confirmOrderViewModel.queryDeliveryTime(module.getWareHouseModuleId());
        //___________________________________
        binding.orderProductList.setLayoutManager(new GridLayoutManager(this, 4));
        OrderProductAdapter orderProductAdapter = new OrderProductAdapter(shopCartItems);
        binding.orderProductList.setAdapter(orderProductAdapter);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new ConfirmOrderModule(this, shopCartItems)).inject(this);
    }

    public void showTimePicker() {
        timeDialog.show();
    }

    public void initTimePicker(List<DeliveryServiceTime> deliveryServiceTimes) {
        List<String> items = new ArrayList<>();
        items.add("明天有空就来拿");
        for (DeliveryServiceTime deliveryServiceTime : deliveryServiceTimes) {
            items.add(deliveryServiceTime.getFullLocalStringTimeSegement());
        }
        dialogBinding.timePicker.setItems(items);
        dialogBinding.timePicker.setClipToPadding(false);
        timeString = "明天有空就来拿";
    }

    @Override
    public void onClick(View v) {

        if (v == binding.confirmOrder) {
            if (null == deliveryTime.get()) {
                Toast.makeText(this, "请选择自提时间", Toast.LENGTH_SHORT).show();
                return;
            }
            confirmOrderViewModel.commitOrder(module.getWareHouseModuleId(), servicePoint.getServicePointId(), binding.memo.getText().toString(), deliveryTime.get());
        }
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.timPickerBtn)
            showTimePicker();
        if (v == dialogBinding.timeickerCancel)
            timeDialog.dismiss();
        if (v == dialogBinding.timeickerConFirm) {
            deliveryTime.set(timeString);
            timeDialog.dismiss();
        }
    }

    public void clearShopCart() {
        shopCart.clear();

    }

    public void navigateToSuccess(int orderId) {
        navigate.navigateToSuccessResult(this, null, true, SuccessResultActivity.FROM_CONFIRMORDER, orderId);
    }
}
