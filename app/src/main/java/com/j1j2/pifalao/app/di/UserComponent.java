package com.j1j2.pifalao.app.di;

import com.j1j2.data.model.User;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.UserScope;
import com.j1j2.pifalao.feature.account.di.AccountComponent;
import com.j1j2.pifalao.feature.account.di.AccountModule;
import com.j1j2.pifalao.feature.addaddress.di.AddAddressComponent;
import com.j1j2.pifalao.feature.addaddress.di.AddAddressModule;
import com.j1j2.pifalao.feature.addressmanager.di.AddressManagerComponent;
import com.j1j2.pifalao.feature.addressmanager.di.AddressManagerModule;
import com.j1j2.pifalao.feature.balancedetail.BalanceDetailActivity;
import com.j1j2.pifalao.feature.balancedetail.di.BalanceDetailComponent;
import com.j1j2.pifalao.feature.balancedetail.di.BalanceDetailModule;
import com.j1j2.pifalao.feature.catservicepoint.di.CatServicePointComponent;
import com.j1j2.pifalao.feature.catservicepoint.di.CatServicePointModule;
import com.j1j2.pifalao.feature.changepassword.di.ChangePasswordComponent;
import com.j1j2.pifalao.feature.changepassword.di.ChangePasswordModule;
import com.j1j2.pifalao.feature.collects.di.CollectsComponent;
import com.j1j2.pifalao.feature.collects.di.CollectsModule;
import com.j1j2.pifalao.feature.confirmorder.di.ConfirmOrderComponent;
import com.j1j2.pifalao.feature.confirmorder.di.ConfirmOrderModule;
import com.j1j2.pifalao.feature.coupons.di.CouponsComponent;
import com.j1j2.pifalao.feature.coupons.di.CouponsModule;
import com.j1j2.pifalao.feature.couponselect.di.CouponSelectComponent;
import com.j1j2.pifalao.feature.couponselect.di.CouponSelectModule;
import com.j1j2.pifalao.feature.home.viphome.VipHomeActivity;
import com.j1j2.pifalao.feature.home.viphome.di.VipHomeComponent;
import com.j1j2.pifalao.feature.home.viphome.di.VipHomeModule;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualActivityComponent;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualActivityModule;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualCenterComponent;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualCenterModule;
import com.j1j2.pifalao.feature.messages.di.MessagesComponent;
import com.j1j2.pifalao.feature.messages.di.MessagesModule;
import com.j1j2.pifalao.feature.offlineorders.di.OfflineOrdersComponent;
import com.j1j2.pifalao.feature.offlineorders.di.OfflineOrdersModule;
import com.j1j2.pifalao.feature.onlineorderpay.di.OnlineOrderPayComponent;
import com.j1j2.pifalao.feature.onlineorderpay.di.OnlineOrderPayModule;
import com.j1j2.pifalao.feature.orderdetail.di.OrderDetailComponent;
import com.j1j2.pifalao.feature.orderdetail.di.OrderDetailModule;
import com.j1j2.pifalao.feature.orderdetail.orderdetailtimeline.di.OrderDetailTimeLineComponent;
import com.j1j2.pifalao.feature.orderdetail.orderdetailtimeline.di.OrderDetailTimeLineModule;
import com.j1j2.pifalao.feature.ordermanager.di.OrderManagerComponent;
import com.j1j2.pifalao.feature.ordermanager.di.OrderManagerModule;
import com.j1j2.pifalao.feature.orderproducts.di.OrderProductsComponent;
import com.j1j2.pifalao.feature.orderproducts.di.OrderProductsModule;
import com.j1j2.pifalao.feature.orderrate.di.OrderRateComponent;
import com.j1j2.pifalao.feature.orderrate.di.OrderRateModule;
import com.j1j2.pifalao.feature.orders.di.OrdersComponent;
import com.j1j2.pifalao.feature.orders.di.OrdersModule;
import com.j1j2.pifalao.feature.qrcode.di.QRCodeComponent;
import com.j1j2.pifalao.feature.qrcode.di.QRCodeModule;
import com.j1j2.pifalao.feature.register.stepone.di.RegisterStepOneComponent;
import com.j1j2.pifalao.feature.register.stepone.di.RegisterStepOneModule;
import com.j1j2.pifalao.feature.shopcart.di.ShopCartComponent;
import com.j1j2.pifalao.feature.shopcart.di.ShopCartModule;
import com.j1j2.pifalao.feature.vipupdate.steptwo.di.VipUpdateStepTwoComponent;
import com.j1j2.pifalao.feature.vipupdate.steptwo.di.VipUpdateStepTwoModule;
import com.j1j2.pifalao.feature.walletmanager.di.WalletManagerComponent;
import com.j1j2.pifalao.feature.walletmanager.di.WalletManagerModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-11.
 */
@UserScope
@Subcomponent(modules = {UserModule.class})
public interface UserComponent {

    User user();

    ShopCart shopCart();

    UnReadInfoManager unReadInfoManager();

    IndividualActivityComponent plus(IndividualActivityModule individualActivityModule);

    IndividualCenterComponent plus(IndividualCenterModule individualCenterModule);

    ShopCartComponent plus(ShopCartModule shopCartModule);

    ConfirmOrderComponent plus(ConfirmOrderModule confirmOrderModule);

    OrderManagerComponent plus(OrderManagerModule orderManagerModule);

    OrdersComponent plus(OrdersModule ordersModule);

    OrderDetailComponent plus(OrderDetailModule orderDetailModule);

    OrderDetailTimeLineComponent plus(OrderDetailTimeLineModule orderDetailTimeLineModule);

    QRCodeComponent plus(QRCodeModule qrCodeModule);

    AddressManagerComponent plus(AddressManagerModule addressManagerModule);

    WalletManagerComponent plus(WalletManagerModule walletManagerModule);

    CouponsComponent plus(CouponsModule couponsModule);

    MessagesComponent plus(MessagesModule messagesModule);

    CollectsComponent plus(CollectsModule collectsModule);

    AccountComponent plus(AccountModule accountModule);

    ChangePasswordComponent plus(ChangePasswordModule changePasswordModule);

    VipUpdateStepTwoComponent plus(VipUpdateStepTwoModule vipUpdateStepTwoModule);

    CatServicePointComponent plus(CatServicePointModule catServicePointModule);

    AddAddressComponent plus(AddAddressModule addAddressModule);

    OrderProductsComponent plus(OrderProductsModule orderProductsModule);

    CouponSelectComponent plus(CouponSelectModule couponSelectModule);

    VipHomeComponent plus(VipHomeModule vipHomeModule);

    OfflineOrdersComponent plus(OfflineOrdersModule offlineOrdersModule);

    OrderRateComponent plus(OrderRateModule orderRateModule);

    OnlineOrderPayComponent plus(OnlineOrderPayModule onlineOrderPayModule);

    BalanceDetailComponent plus(BalanceDetailModule balanceDetailModule);
}
