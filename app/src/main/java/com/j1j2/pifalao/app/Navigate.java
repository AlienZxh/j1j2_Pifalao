package com.j1j2.pifalao.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.j1j2.data.model.City;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.requestbody.ClientRegisterStepOneBody;
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.cityActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToServicePointActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ServicePoint servicePoint) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.servicePointActivity(servicePoint).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.servicePointActivity(servicePoint).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToServicesActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ServicePoint servicePoint) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.servicesActivity(servicePoint).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.servicesActivity(servicePoint).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

//    public void navigateFromServicePointToServicesActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ServicePoint servicePoint) {
//        if (null == options || Build.VERSION.SDK_INT < 16) {
//            context.startActivity(Bundler.servicesActivity(servicePoint).intent(context).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.productsActivity(module, ProductsActivity.PRODUCTS_TYPE_SEARCH).key(key).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToProductDetailActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ProductSimple productSimple, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.productDetailActivity(productSimple, module).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.productDetailActivity(productSimple, module).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToMainActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.mainActivity(module).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.mainActivity(module).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToSearchActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.searchActivity(module).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.loginActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToShopCart(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.shopCartActivity(module).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.shopCartActivity(module).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToConfirmOrder(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module, List<ShopCartItem> shopCartItems) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.confirmOrderActivity(module, shopCartItems).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.confirmOrderActivity(module, shopCartItems).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToOrderManager(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.orderManagerActivity().start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.ordersActivity(orderType).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToOrderDetail(Activity context, ActivityOptionsCompat options, boolean isFinish, OrderSimple orderSimple, int orderId) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.orderDetailActivity(orderId).orderSimple(orderSimple).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.orderDetailActivity(orderId).orderSimple(orderSimple).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToQRCode(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.qrCodeActivity().start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.qrCodeActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToAddressManager(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.addressManagerActivity().start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.addressManagerActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToWalletManager(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.walletManagerActivity().module(module).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.walletManagerActivity().module(module).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToCoupons(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module, int couponType, List<Coupon> couponList) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.couponsActivity(module, couponType, couponList).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.couponsActivity(module, couponType, couponList).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToMessages(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.messagesActivity().start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.catServicePointActivity(servicePointId).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }
}
