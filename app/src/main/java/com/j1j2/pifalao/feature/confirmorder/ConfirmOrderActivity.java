package com.j1j2.pifalao.feature.confirmorder;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.j1j2.data.model.Address;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.DeliveryServiceTime;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.Shop;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.dialog.TimeSelectDialogFragment;
import com.j1j2.pifalao.app.event.AddressListChangeEvent;
import com.j1j2.pifalao.app.event.CouponSelectEvent;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;
import com.j1j2.pifalao.app.event.UserAddressSelectEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityConfirmorderBinding;
import com.j1j2.pifalao.feature.confirmorder.di.ConfirmOrderModule;
import com.j1j2.pifalao.feature.orderdetail.OrderDetailActivity;
import com.j1j2.pifalao.feature.orderproducts.OrderProductsActivity;

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
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener,
        TimeSelectDialogFragment.TimeSelectDialogFragmentListener {
    ActivityConfirmorderBinding binding;

    @Arg
    int moduleId;

    @Arg(serializer = ParcelListSerializer.class)
    List<ShopCartItem> shopCartItems;

    public ShopSubscribeService shopSubscribeService;

    @Inject
    ConfirmOrderViewModel confirmOrderViewModel;

    @Inject
    UserRelativePreference userRelativePreference;

    @Inject
    ShopCart shopCart;

    List<String> timeItems;
    int selectIndex = 0;

    public OrderSubmitState orderSubmitState;

    public ObservableList<Coupon> coupons = new ObservableArrayList<>();

    public List<Coupon> allCoupons;

    private TimeSelectDialogFragment timeSelectDialogFragment;


    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new ConfirmOrderModule(this, shopCartItems)).inject(this);
        orderSubmitState = new OrderSubmitState(moduleId, userRelativePreference.getSelectedServicePoint(null));

    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmorder);
        orderSubmitState.OrderPayType.set(Constant.OrderPayType.ONLINEPAYMENT);
        binding.setShopCart(shopCart);
        binding.setConfirmOrderViewModel(confirmOrderViewModel);
        shopSubscribeService = userRelativePreference.getSelectedModule(null);
    }

    @Override
    protected void initViews() {
        //__________________________________________________________
        confirmOrderViewModel.CountDown(moduleId);
        //___________________________________
        binding.orderProductList.setLayoutManager(new GridLayoutManager(this, 4));
        OrderProductAdapter orderProductAdapter = new OrderProductAdapter(shopCartItems);
        binding.orderProductList.setAdapter(orderProductAdapter);
        //_______________________________
        if (orderSubmitState.ServicePointDetail.get() != null)
            confirmOrderViewModel.queryFreightType(moduleId,
                    orderSubmitState.ServicePointDetail.get().getShopId());
    }

    @Override
    public List<String> getTimeItems() {
        return timeItems;
    }

    @Override
    public void setSelectTime(String item) {
        orderSubmitState.PredictSendTime.set(item);
    }

    @Override
    public int getSelectIndex() {
        return selectIndex;
    }

    @Override
    public void setSelectIndex(int index) {
        selectIndex = index;
    }

    public void showTimePicker() {
        if (timeSelectDialogFragment != null)
            timeSelectDialogFragment.show(getSupportFragmentManager(), "TIMESELECTDIALOG");
    }

    public void initTimePicker(int deliveryType, List<DeliveryServiceTime> deliveryServiceTimes) {
        timeItems = new ArrayList<>();
        if (deliveryType == Constant.DeliveryType.PICKBYSELF) {
            timeItems.add(deliveryServiceTimes.get(0).getFullLocalStringTimeSegement().substring(0, 2) + "有空就来拿");
            for (DeliveryServiceTime deliveryServiceTime : deliveryServiceTimes) {
                timeItems.add(deliveryServiceTime.getFullLocalStringTimeSegement());
            }
        } else if (deliveryType == Constant.DeliveryType.HOMEDELIVERY) {
            timeItems.add("立即配送");
            timeItems.add("电话预约");
        }

        orderSubmitState.PredictSendTime.set(timeItems.get(selectIndex));
        timeSelectDialogFragment = new TimeSelectDialogFragment();
    }

    public void initFreightType(List<FreightType> freightTypes) {
        orderSubmitState.FreightTypeDetail.set(freightTypes.get(0));

        if (orderSubmitState.FreightTypeDetail.get().getCategoryType() == Constant.CategoryType.FREE_FREIGHT) {
            orderSubmitState.FreightValue.set(0);
        } else if (orderSubmitState.FreightTypeDetail.get().getCategoryType() == Constant.CategoryType.FIXED_FREIGHT) {
            orderSubmitState.FreightValue.set(orderSubmitState.FreightTypeDetail.get().getFixed());
        } else if (orderSubmitState.FreightTypeDetail.get().getCategoryType() == Constant.CategoryType.VIP_FREIGHT) {
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
        if (coupons.size() > 0)
            coupons.clear();
        for (Coupon coupon : mAllCoupons) {
//            if (coupon.getType() == Constant.CouponType.COUPON_DELIVERY) {
//                if (coupon.getState() == 1 && !coupon.isExpired() && orderSubmitState.FreightValue.get() > 0) {
//                    coupons.add(coupon);
//                }
//            } else
            if (coupon.getType() == Constant.CouponType.COUPON_NORMAL) {
                if (coupon.getState() == 1 && !coupon.isExpired() && shopCart.getAllMemberPrice() >= coupon.getConstraints()) {
                    if (TextUtils.isEmpty(coupon.getModuleIdStr()))
                        coupons.add(coupon);
                    else if (coupon.getModuleIdStr().contains("" + shopSubscribeService.getServiceId()) || coupon.getModuleIdStr().equals("null"))
                        coupons.add(coupon);
                }
            }
        }
        if (coupons.size() <= 0)
            binding.couponText.setTextColor(0xffaaaaaa);
        else
            binding.couponText.setTextColor(0xff333333);

    }

    public void initAddress(Address address) {
        orderSubmitState.AddressDetail.set(address);
    }

    public void setConfirmBtnEnable(boolean isEnable) {
        binding.confirmOrder.setEnabled(isEnable);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.confirmOrder) {
            if (null == orderSubmitState.PredictSendTime.get() || orderSubmitState.PredictSendTime.get().length() <= 0) {
                toastor.showSingletonToast("请选择自提时间");
                return;
            }
            if (orderSubmitState.FreightTypeDetail.get().getSysDeliveryType() == Constant.DeliveryType.HOMEDELIVERY && null == orderSubmitState.AddressDetail.get()) {
                toastor.showSingletonToast("请选择地址");
                return;
            }
            setConfirmBtnEnable(false);
            orderSubmitState.OrderMemo = binding.memo.getText().toString();
            confirmOrderViewModel.commitOrder(orderSubmitState);
        }
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.timPickerBtn)
            showTimePicker();
        if (v == binding.productList) {
            navigate.navigateToOrderProducts(this, null, false, OrderProductsActivity.FROM_CONFIRMORDER, shopCartItems, null);
        }
        if (v == binding.couponBtn) {
            navigate.navigateToCouponSelect(this, null, false, moduleId, coupons, orderSubmitState.Coupon.get());
        }
        if (v == binding.addressLayout) {
            navigate.navigateToAddressManager(this, null, false, true);
        }
        if (v == binding.cashpaybtn)
            orderSubmitState.OrderPayType.set(Constant.OrderPayType.CASHONDELIVERY);
        if (v == binding.onlinepaybtn)
            orderSubmitState.OrderPayType.set(Constant.OrderPayType.ONLINEPAYMENT);

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onAddressListChangeEvent(AddressListChangeEvent event) {
        confirmOrderViewModel.queryDefaultAddress();
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


    public void navigateToOrderDetail(int orderId) {
        navigate.navigateToOrderDetail(this, null, true, orderId, OrderDetailActivity.TIMELINE);
    }

    public void navigateToOnlineOrderPay(int orderId, String orderNO) {
        navigate.navigateToOnlineOrderPay(this, null, true, orderId, orderNO, false, false);
    }
}
