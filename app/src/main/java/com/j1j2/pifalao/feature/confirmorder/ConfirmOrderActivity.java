package com.j1j2.pifalao.feature.confirmorder;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.j1j2.data.model.Address;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.DeliveryServiceTime;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.CouponSelectEvent;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;
import com.j1j2.pifalao.app.event.UserAddressSelectEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityConfirmorderBinding;
import com.j1j2.pifalao.databinding.ViewDeliverytimePickerBinding;
import com.j1j2.pifalao.feature.confirmorder.di.ConfirmOrderModule;
import com.j1j2.pifalao.feature.orderproducts.OrderProductsActivity;
import com.j1j2.pifalao.feature.successresult.SuccessResultActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    int moduleId;

    @Arg(serializer = ParcelListSerializer.class)
    List<ShopCartItem> shopCartItems;

    public Module module;

    @Inject
    ConfirmOrderViewModel confirmOrderViewModel;

    @Inject
    UserRelativePreference userRelativePreference;

    @Inject
    ShopCart shopCart;

    DialogPlus timeDialog;
    ViewDeliverytimePickerBinding dialogBinding;

    public OrderSubmitState orderSubmitState;

    public ObservableList<Coupon> coupons = new ObservableArrayList<>();

    public List<Coupon> allCoupons;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmorder);
        binding.setShopCart(shopCart);
        binding.setConfirmOrderViewModel(confirmOrderViewModel);
        module = userRelativePreference.getSelectedModule(null);
    }

    @Override
    protected void initViews() {
        //___________________________________________________________
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
        //__________________________________________________________
        confirmOrderViewModel.CountDown(moduleId);
        //___________________________________
        binding.orderProductList.setLayoutManager(new GridLayoutManager(this, 4));
        OrderProductAdapter orderProductAdapter = new OrderProductAdapter(shopCartItems);
        binding.orderProductList.setAdapter(orderProductAdapter);
        //_______________________________
        confirmOrderViewModel.queryFreightType(moduleId);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new ConfirmOrderModule(this, shopCartItems)).inject(this);
        orderSubmitState = new OrderSubmitState(moduleId, userRelativePreference.getSelectedServicePoint(null));
    }

    public void showTimePicker() {
        timeDialog.show();
    }

    public void initTimePicker(int deliveryType, List<DeliveryServiceTime> deliveryServiceTimes) {
        List<String> items = new ArrayList<>();
        if (deliveryType == Constant.DeliveryType.PICKBYSELF)
            items.add("明天有空就来拿");
        else if (deliveryType == Constant.DeliveryType.HOMEDELIVERY)
            items.add("货到电话预约");

        for (DeliveryServiceTime deliveryServiceTime : deliveryServiceTimes) {
            items.add(deliveryServiceTime.getFullLocalStringTimeSegement());
        }
        dialogBinding.timePicker.setItems(items);
        dialogBinding.timePicker.setClipToPadding(false);
        orderSubmitState.PredictSendTime.set(items.get(0));
    }

    public void initFreightType(List<FreightType> freightTypes) {
        orderSubmitState.FreightTypeDetail.set(freightTypes.get(0));

        if (orderSubmitState.FreightTypeDetail.get().getCategoryType() == Constant.CategoryType.FREE_FREIGHT) {
            orderSubmitState.FreightValue.set(0);
        } else if (orderSubmitState.FreightTypeDetail.get().getCategoryType() == Constant.CategoryType.FIXED_FREIGHT) {
            orderSubmitState.FreightValue.set(orderSubmitState.FreightTypeDetail.get().getFixed());
        } else {
            orderSubmitState.FreightValue.set(0);
        }

        confirmOrderViewModel.queryDeliveryTime(orderSubmitState);
        confirmOrderViewModel.queryCoupons(Constant.CouponType.COUPON_ALL, moduleId);

        if (orderSubmitState.FreightTypeDetail.get().getSysDeliveryType() == Constant.DeliveryType.HOMEDELIVERY) {
            confirmOrderViewModel.queryDefaultAddress();
        }
    }

    public void initCoupons(List<Coupon> mAllCoupons) {
        if (allCoupons == null) {
            allCoupons = mAllCoupons;
        }
        for (Coupon coupon : mAllCoupons) {
            if (coupon.getType() == Constant.CouponType.COUPON_DELIVERY) {
                if (coupon.getState() == 1 && !coupon.isExpired() && orderSubmitState.FreightValue.get() > 0) {
                    coupons.add(coupon);
                }
            } else if (coupon.getType() == Constant.CouponType.COUPON_NORMAL) {
                if (coupon.getState() == 1 && !coupon.isExpired() && shopCart.getAllMemberPrice() >= coupon.getConstraints()) {
                    coupons.add(coupon);
                }
            }
        }
    }

    public void initAddress(Address address) {
        orderSubmitState.AddressDetail.set(address);
    }

    @Override
    public void onClick(View v) {

        if (v == binding.confirmOrder) {
            if (null == orderSubmitState.PredictSendTime.get()) {
                Toast.makeText(this, "请选择自提时间", Toast.LENGTH_SHORT).show();
                return;
            }
            orderSubmitState.OrderMemo = binding.memo.getText().toString();
            confirmOrderViewModel.commitOrder(orderSubmitState);
        }
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.timPickerBtn)
            showTimePicker();
        if (v == dialogBinding.timeickerCancel)
            timeDialog.dismiss();
        if (v == dialogBinding.timeickerConFirm) {
            orderSubmitState.PredictSendTime.set(dialogBinding.timePicker.getSeletedItem());
            timeDialog.dismiss();
        }
        if (v == binding.productList) {
            navigate.navigateToOrderProducts(this, null, false, OrderProductsActivity.FROM_CONFIRMORDER, moduleId, shopCartItems, null);
        }
        if (v == binding.couponBtn) {
            if (null != coupons && coupons.size() > 0)
                navigate.navigateToCouponSelect(this, null, false, moduleId, coupons, orderSubmitState.Coupon.get());
        }
        if (v == binding.addressLayout) {
            navigate.navigateToAddressManager(this, null, false, true);
        }

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onUserAddressSelectEvent(UserAddressSelectEvent event) {
        orderSubmitState.AddressDetail.set(event.getAddress());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onCouponSelectEvent(CouponSelectEvent event) {
        orderSubmitState.Coupon.set(event.getSelectedCoupon());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onShopCartChangeEvent(ShopCartChangeEvent event) {
        if (allCoupons != null)
            initCoupons(allCoupons);
        else
            confirmOrderViewModel.queryCoupons(Constant.CouponType.COUPON_ALL, moduleId);
    }


    public void clearShopCart() {
        shopCart.clear();
    }

    public void navigateToSuccess(int orderId) {
        navigate.navigateToSuccessResult(this, null, true, SuccessResultActivity.FROM_CONFIRMORDER, orderId);
    }
}
