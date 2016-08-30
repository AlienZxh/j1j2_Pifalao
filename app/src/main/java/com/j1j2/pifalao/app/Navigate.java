package com.j1j2.pifalao.app;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.baidu.location.BDLocation;
import com.j1j2.data.model.Address;
import com.j1j2.data.model.City;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.OfflineOrderSimple;
import com.j1j2.data.model.OrderProductDetail;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.requestbody.ClientRegisterStepOneBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.feature.products.ProductsActivity;

import java.util.List;

import in.workarounds.bundler.Bundler;

/**
 * Created by alienzxh on 16-3-4.
 */
public class Navigate {


    public Navigate() {
    }

    public void navigateToGuide(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.guideActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.guideActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }


    public void navigateToLocationActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, City city) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.locationActivity(city).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.locationActivity(city).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToCityActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.cityActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.cityActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToServicePointActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ServicePoint servicePoint, BDLocation location) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.servicePointActivity(servicePoint, location).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.servicePointActivity(servicePoint, location).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToServicesActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.servicesActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.servicesActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

//    public void navigateFromServicePointToServicesActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ServicePoint servicePoint) {
//        if (null == options || Build.VERSION.SDK_INT < 16) {
//            context.startActivity(Bundler.servicesActivity(servicePoint).intent(context).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        } else {
//            ActivityCompat.startActivity(context, Bundler.servicesActivity(servicePoint).intent(context).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
//                    options.toBundle());
//        }
//        if (isFinish) {
//            ActivityCompat.finishAfterTransition(context);
//        }
//    }

    public void navigateToProductsActivityFromSort(Activity context, ActivityOptionsCompat options, boolean isFinish, ProductSort productSort, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.productsActivity(module, ProductsActivity.PRODUCTS_TYPE_SORT).productSort(productSort).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.productsActivity(module, ProductsActivity.PRODUCTS_TYPE_SORT).productSort(productSort).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToProductsActivityFromSearch(Activity context, ActivityOptionsCompat options, boolean isFinish, String key, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.productsActivity(module, ProductsActivity.PRODUCTS_TYPE_SEARCH).key(key).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.productsActivity(module, ProductsActivity.PRODUCTS_TYPE_SEARCH).key(key).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToProductDetailActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int mainId) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.productDetailActivity(mainId).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.productDetailActivity(mainId).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToMainActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module, int activityType) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.mainActivity(module, activityType).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.mainActivity(module, activityType).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToSearchActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.searchActivity(module).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.searchActivity(module).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToLogin(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.loginActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.loginActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToShopCart(Activity context, ActivityOptionsCompat options, boolean isFinish, int module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.shopCartActivity(module).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.shopCartActivity(module).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToConfirmOrder(Activity context, ActivityOptionsCompat options, boolean isFinish, int moduleId, List<ShopCartItem> shopCartItems) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.confirmOrderActivity(moduleId, shopCartItems).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.confirmOrderActivity(moduleId, shopCartItems).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToOrderManager(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.orderManagerActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.orderManagerActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToOrders(Activity context, ActivityOptionsCompat options, boolean isFinish, int orderType) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.ordersActivity(orderType).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.ordersActivity(orderType).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToOrderDetail(Activity context, ActivityOptionsCompat options, boolean isFinish, OrderSimple orderSimple, int orderId, int selectPage) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.orderDetailActivity(orderId, selectPage).orderSimple(orderSimple).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.orderDetailActivity(orderId, selectPage).orderSimple(orderSimple).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToQRCode(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.qrCodeActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.qrCodeActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToAddressManager(Activity context, ActivityOptionsCompat options, boolean isFinish, boolean isSelect) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.addressManagerActivity(isSelect).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.addressManagerActivity(isSelect).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToWalletManager(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.walletManagerActivity().module(module).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.walletManagerActivity().module(module).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToCoupons(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module, int couponType) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.couponsActivity(module, couponType).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.couponsActivity(module, couponType).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToMessages(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.messagesActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.messagesActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToCollects(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.collectsActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.collectsActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToAccount(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.accountActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.accountActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToChangePassword(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.changePasswordActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.changePasswordActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToRegisterStepOne(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.registerStepOneActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.registerStepOneActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToRegisterStepTwo(Activity context, ActivityOptionsCompat options, boolean isFinish, ClientRegisterStepOneBody clientRegisterStepOneBody) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.registerStepTwoActivity(clientRegisterStepOneBody).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.registerStepTwoActivity(clientRegisterStepOneBody).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToVipUpdateStepOne(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.vipUpdateStepOneActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.vipUpdateStepOneActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToVipUpdateStepTwo(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.vipUpdateStepTwoActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.vipUpdateStepTwoActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToSetting(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.settingActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.settingActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToIndividualCenter(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.individualCenterActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.individualCenterActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToSuccessResult(Activity context, ActivityOptionsCompat options, boolean isFinish, int activityType) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.successResultActivity(activityType).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.successResultActivity(activityType).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToSuccessResult(Activity context, ActivityOptionsCompat options, boolean isFinish, int activityType, int orderId) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.successResultActivity(activityType).orderId(orderId).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.successResultActivity(activityType).orderId(orderId).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToCatServicePoint(Activity context, ActivityOptionsCompat options, boolean isFinish, int servicePointId) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.catServicePointActivity(servicePointId).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.catServicePointActivity(servicePointId).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToDeliveryHomeActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ServicePoint servicePoint, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.deliveryHomeActivity(servicePoint, module).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.deliveryHomeActivity(servicePoint, module).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToAddAddress(Activity context, ActivityOptionsCompat options, boolean isFinish, int activityType, Address address) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.addAddressActivity(activityType).address(address).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.addAddressActivity(activityType).address(address).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToAddressSelect(Activity context, ActivityOptionsCompat options, boolean isFinish, String city, String district) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.addressSelectActivity(city, district).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.addressSelectActivity(city, district).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToOrderProducts(Activity context, ActivityOptionsCompat options, boolean isFinish, int activityType, int moduleId, List<ShopCartItem> shopCartItems, List<OrderProductDetail> orderProductDetails) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.orderProductsActivity(activityType, moduleId).shopCartItems(shopCartItems).orderProductDetails(orderProductDetails).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.orderProductsActivity(activityType, moduleId).shopCartItems(shopCartItems).orderProductDetails(orderProductDetails).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToCouponSelect(Activity context, ActivityOptionsCompat options, boolean isFinish, int moduleId, List<Coupon> couponList, Coupon selectCoupon) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.couponSelectActivity(moduleId, couponList).selectCoupon(selectCoupon).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.couponSelectActivity(moduleId, couponList).selectCoupon(selectCoupon).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToAboutUs(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.aboutUsActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.aboutUsActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToFeedBack(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.feedBackActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.feedBackActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToMoreModule(Activity context, ActivityOptionsCompat options, boolean isFinish, List<Module> modules) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.moreHomeActivity(modules).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.moreHomeActivity(modules).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToVipHome(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.vipHomeActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.vipHomeActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToOfflineOrder(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.offlineOrdersActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.offlineOrdersActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToOfflineOrderDetail(Activity context, ActivityOptionsCompat options, boolean isFinish, OfflineOrderSimple offlineOrderSimple) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.offlineOrderDetailActivity(offlineOrderSimple).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.offlineOrderDetailActivity(offlineOrderSimple).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }


    public void navigateToUnsubscribeDelivery(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.unsubscribeDeliveryActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.unsubscribeDeliveryActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToUnsubscribeModule(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.unsubscribeModuleActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.unsubscribeModuleActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToUnsubscribeCity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.unsubscribeCityActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.unsubscribeCityActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToFindPSW(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.findPSWActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.findPSWActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToShow(Activity context, ActivityOptionsCompat options, boolean isFinish, int activityType) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.showActivity(activityType).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.showActivity(activityType).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToOrderRate(Activity context, ActivityOptionsCompat options, boolean isFinish, OrderSimple orderSimple) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.orderRateActivity(orderSimple).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.orderRateActivity(orderSimple).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToOnlineOrderPay(Activity context, ActivityOptionsCompat options, boolean isFinish, int orderId, String orderNO, boolean fromrderDetail) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.onlineOrderPayActivity(orderId, orderNO, fromrderDetail).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.onlineOrderPayActivity(orderId, orderNO, fromrderDetail).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToHouseKeeping(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.houseKeepingActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.houseKeepingActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToOfflineModuleHome(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module, ServicePoint servicePoint) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.offlineModuleHomeActivity(module, servicePoint).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.offlineModuleHomeActivity(module, servicePoint).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToBalanceDetail(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.balanceDetailActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.balanceDetailActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToMemberHomeActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.memberHomeActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.memberHomeActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToFreeConvertibilityActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.freeConvertibilityActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.freeConvertibilityActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }
}
