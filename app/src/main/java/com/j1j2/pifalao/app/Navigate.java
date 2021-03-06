package com.j1j2.pifalao.app;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.j1j2.data.model.ActivityProcessStateProductInfo;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.Address;
import com.j1j2.data.model.City;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.ImgUrl;
import com.j1j2.data.model.OrderDetail;
import com.j1j2.data.model.ProductCategory;
import com.j1j2.data.model.Shop;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.data.model.OfflineOrderSimple;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.RedPacket;
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

    public void navigateToServicePointActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, Shop shop) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.servicePointActivity(shop).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.servicePointActivity(shop).intent(context),
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


    public void navigateToProductsActivityFromSort(Activity context, ActivityOptionsCompat options, boolean isFinish, ProductCategory productCategory, ShopSubscribeService shopSubscribeService) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.productsActivity(shopSubscribeService, ProductsActivity.PRODUCTS_TYPE_SORT).productCategory(productCategory).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.productsActivity(shopSubscribeService, ProductsActivity.PRODUCTS_TYPE_SORT).productCategory(productCategory).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToProductsActivityFromSearch(Activity context, ActivityOptionsCompat options, boolean isFinish, String key, ShopSubscribeService shopSubscribeService) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.productsActivity(shopSubscribeService, ProductsActivity.PRODUCTS_TYPE_SEARCH).key(key).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.productsActivity(shopSubscribeService, ProductsActivity.PRODUCTS_TYPE_SEARCH).key(key).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToProductDetailActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int mainId) {
//        if (null == options || Build.VERSION.SDK_INT < 16) {
//            Bundler.productDetailActivity(mainId).start(context);
//            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        } else {
//            ActivityCompat.startActivity(context, Bundler.productDetailActivity(mainId).intent(context),
//                    options.toBundle());
//        }
//        if (isFinish) {
//            ActivityCompat.finishAfterTransition(context);
//        }
    }

    public void navigateToMainActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ShopSubscribeService shopSubscribeService, int activityType) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.mainActivity(shopSubscribeService, activityType).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.mainActivity(shopSubscribeService, activityType).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToSearchActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ShopSubscribeService shopSubscribeService) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.searchActivity(shopSubscribeService).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.searchActivity(shopSubscribeService).intent(context),
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

    public void navigateToOrderDetail(Activity context, ActivityOptionsCompat options, boolean isFinish, int orderId, int selectPage) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.orderDetailActivity(orderId, selectPage).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.orderDetailActivity(orderId, selectPage).intent(context),
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

    public void navigateToWalletManager(Activity context, ActivityOptionsCompat options, boolean isFinish, ShopSubscribeService shopSubscribeService) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.walletManagerActivity().shopSubscribeService(shopSubscribeService).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.walletManagerActivity().shopSubscribeService(shopSubscribeService).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToCoupons(Activity context, ActivityOptionsCompat options, boolean isFinish, ShopSubscribeService shopSubscribeService, int couponType) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.couponsActivity(shopSubscribeService, couponType).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.couponsActivity(shopSubscribeService, couponType).intent(context),
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

    public void navigateToSuccessResult(Activity context, ActivityOptionsCompat options, boolean isFinish, int activityType, int orderId, String orderNO) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.successResultActivity(activityType).orderId(orderId).orderNO(orderNO).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.successResultActivity(activityType).orderId(orderId).orderNO(orderNO).intent(context),
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

    public void navigateToDeliveryHomeActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, Shop shop, ShopSubscribeService shopSubscribeService) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.newDeliveryHomeActivity(shop.getShopId(), shopSubscribeService).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.newDeliveryHomeActivity(shop.getShopId(), shopSubscribeService).intent(context),
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

    public void navigateToOrderProducts(Activity context, ActivityOptionsCompat options, boolean isFinish, int activityType, List<ShopCartItem> shopCartItems, List<OrderDetail.OrderProductDetail> orderProductDetails) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.orderProductsActivity(activityType).shopCartItems(shopCartItems).orderProductDetails(orderProductDetails).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.orderProductsActivity(activityType).shopCartItems(shopCartItems).orderProductDetails(orderProductDetails).intent(context),
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

    public void navigateToMoreModule(Activity context, ActivityOptionsCompat options, boolean isFinish, List<ShopSubscribeService> shopSubscribeServices) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.moreHomeActivity(shopSubscribeServices).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.moreHomeActivity(shopSubscribeServices).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToVipHome(Activity context, ActivityOptionsCompat options, boolean isFinish, int activityType) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.vipHomeActivity(activityType).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.vipHomeActivity(activityType).intent(context),
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


    public void navigateToUnsubscribeModule(Activity context, ActivityOptionsCompat options, boolean isFinish, ShopSubscribeService shopSubscribeService) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.unsubscribeModuleActivity(shopSubscribeService).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.unsubscribeModuleActivity(shopSubscribeService).intent(context),
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

    public void navigateToOrderRate(Activity context, ActivityOptionsCompat options, boolean isFinish, int orderId) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.orderRateActivity(orderId).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.orderRateActivity(orderId).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToOnlineOrderPay(Activity context, ActivityOptionsCompat options, boolean isFinish, int orderId, String orderNO, boolean fromrderDetail, boolean isActivityPay) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.onlineOrderPayActivity(orderId, orderNO, fromrderDetail, isActivityPay).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.onlineOrderPayActivity(orderId, orderNO, fromrderDetail, isActivityPay).intent(context),
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

    public void navigateToOfflineModuleHome(Activity context, ActivityOptionsCompat options, boolean isFinish, ShopSubscribeService shopSubscribeService, Shop shop) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.offlineModuleHomeActivity(shopSubscribeService, shop).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.offlineModuleHomeActivity(shopSubscribeService, shop).intent(context),
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

    public void navigateToFreeConvertibilityActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, List<ActivityProduct> activityProducts) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.freeConvertibilityActivity(activityProducts).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.freeConvertibilityActivity(activityProducts).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToPrizeActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int prizeType) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.prizeActivity(prizeType).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.prizeActivity(prizeType).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToParticipationRecordActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int activityType) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.participationRecordActivity(activityType).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.participationRecordActivity(activityType).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToPrizeDetailActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int activityProductId, ActivityWinPrize activityWinPrize) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.prizeDetailActivity(activityProductId).activityWinPrize(activityWinPrize).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.prizeDetailActivity(activityProductId).activityWinPrize(activityWinPrize).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToPrizeRecordActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int lotteryId) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.prizeRecordActivity(lotteryId).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.prizeRecordActivity(lotteryId).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToPrizeConfirmActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int prizeQuantity, ActivityProduct activityProduct, int prizeType) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.prizeConfirmActivity(activityProduct, prizeQuantity, prizeType).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.prizeConfirmActivity(activityProduct, prizeQuantity, prizeType).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToPrizeConfirmLotteryActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ActivityProcessStateProductInfo activityProduct, int activityProductType, int orderId, String orderNo) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.prizeConfirmLotteryActivity(activityProduct, activityProductType, orderId, orderNo).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.prizeConfirmLotteryActivity(activityProduct, activityProductType, orderId, orderNo).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }


    public void navigateToShowOrderListActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.showOrderListActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.showOrderListActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToShowOrderActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int orderId, ActivityProcessStateProductInfo product, String orderNO) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.showOrderActivity(orderId, product, orderNO).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.showOrderActivity(orderId, product, orderNO).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToCalculateDetailActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int lotteryId) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.calculateDetailActivity(lotteryId).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.calculateDetailActivity(lotteryId).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToPrizeOrderTimelineActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int orderId) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.prizeOrderTimelineActivity(orderId).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.prizeOrderTimelineActivity(orderId).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToSpecialOfferActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.specialOfferActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.specialOfferActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToLotteryRuleActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.lotteryRuleActivity().start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.lotteryRuleActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToImgsGalleryActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, List<ImgUrl> urls, int index) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.imgsGalleryActivity(urls).index(index).start(context);
        } else {
            ActivityCompat.startActivity(context, Bundler.imgsGalleryActivity(urls).index(index).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToModulePermissionDeniedActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ShopSubscribeService shopSubscribeService) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.modulePermissionDeniedActivity(shopSubscribeService).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.modulePermissionDeniedActivity(shopSubscribeService).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToBriberyMoneyRemindActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int count) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.briberyMoneyRemindActivity(count).start(context);
        } else {
            ActivityCompat.startActivity(context, Bundler.briberyMoneyRemindActivity(count).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToBriberyMoneysActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int selectState) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.briberyMoneysActivity(selectState).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.briberyMoneysActivity(selectState).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToBriberyMoneyOpenActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, RedPacket redPacket) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.briberyMoneyOpenActivity(redPacket).start(context);
        } else {
            ActivityCompat.startActivity(context, Bundler.briberyMoneyOpenActivity(redPacket).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToBriberyMoneyResultActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, RedPacket redPacket) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.briberyMoneyResultActivity(redPacket).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.briberyMoneyResultActivity(redPacket).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToValidateAccountActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, String userName, String passWord,
                                                  boolean isAutoLogin) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.validateAccountActivity(userName, passWord, isAutoLogin).start(context);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, Bundler.validateAccountActivity(userName, passWord, isAutoLogin).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }
}
