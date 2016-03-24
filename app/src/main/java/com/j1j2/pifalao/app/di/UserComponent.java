package com.j1j2.pifalao.app.di;

import com.j1j2.pifalao.app.UserScope;
import com.j1j2.pifalao.feature.addressmanager.di.AddressManagerComponent;
import com.j1j2.pifalao.feature.addressmanager.di.AddressManagerModule;
import com.j1j2.pifalao.feature.collects.di.CollectsComponent;
import com.j1j2.pifalao.feature.collects.di.CollectsModule;
import com.j1j2.pifalao.feature.confirmorder.di.ConfirmOrderComponent;
import com.j1j2.pifalao.feature.confirmorder.di.ConfirmOrderModule;
import com.j1j2.pifalao.feature.coupons.di.CouponsComponent;
import com.j1j2.pifalao.feature.coupons.di.CouponsModule;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualCenterComponent;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualCenterModule;
import com.j1j2.pifalao.feature.messages.di.MessagesComponent;
import com.j1j2.pifalao.feature.messages.di.MessagesModule;
import com.j1j2.pifalao.feature.orderdetail.di.OrderDetailComponent;
import com.j1j2.pifalao.feature.orderdetail.di.OrderDetailModule;
import com.j1j2.pifalao.feature.ordermanager.di.OrderManagerComponent;
import com.j1j2.pifalao.feature.ordermanager.di.OrderManagerModule;
import com.j1j2.pifalao.feature.orders.di.OrdersComponent;
import com.j1j2.pifalao.feature.orders.di.OrdersModule;
import com.j1j2.pifalao.feature.qrcode.di.QRCodeComponent;
import com.j1j2.pifalao.feature.qrcode.di.QRCodeModule;
import com.j1j2.pifalao.feature.shopcart.di.ShopCartComponent;
import com.j1j2.pifalao.feature.shopcart.di.ShopCartModule;
import com.j1j2.pifalao.feature.walletmanager.di.WalletManagerComponent;
import com.j1j2.pifalao.feature.walletmanager.di.WalletManagerModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-11.
 */
@UserScope
@Subcomponent(modules = {UserModule.class})
public interface UserComponent {

    IndividualCenterComponent plus(IndividualCenterModule individualCenterModule);

    ShopCartComponent plus(ShopCartModule shopCartModule);

    ConfirmOrderComponent plus(ConfirmOrderModule confirmOrderModule);

    OrderManagerComponent plus(OrderManagerModule orderManagerModule);

    OrdersComponent plus(OrdersModule ordersModule);

    OrderDetailComponent plus(OrderDetailModule orderDetailModule);

    QRCodeComponent plus(QRCodeModule qrCodeModule);

    AddressManagerComponent plus(AddressManagerModule addressManagerModule);

    WalletManagerComponent plus(WalletManagerModule walletManagerModule);

    CouponsComponent plus(CouponsModule couponsModule);

    MessagesComponent plus(MessagesModule messagesModule);

    CollectsComponent plus(CollectsModule collectsModule);
}
