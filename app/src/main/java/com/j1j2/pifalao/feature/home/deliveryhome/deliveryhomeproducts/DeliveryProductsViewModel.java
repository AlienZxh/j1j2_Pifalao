package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts;

import android.widget.Toast;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.data.model.SecondarySort;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;
import com.j1j2.pifalao.feature.vegetablesort.VegetableSortFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-4-8.
 */
public class DeliveryProductsViewModel {
    private DeliveryHomeProductsFragment deliveryHomeProductsFragment;
    private ProductApi productApi;
    private ShopCartApi shopCartApi;
    private List<ShopCartItem> shopCartItems;

    public DeliveryProductsViewModel(DeliveryHomeProductsFragment deliveryHomeProductsFragment, ProductApi productApi, ShopCartApi shopCartApi) {
        this.deliveryHomeProductsFragment = deliveryHomeProductsFragment;
        this.productApi = productApi;
        this.shopCartApi = shopCartApi;
    }

    public void queryShopcart(int moduled) {
        shopCartApi.queryShopCart(moduled)
                .compose(deliveryHomeProductsFragment.<WebReturn<List<ShopCartItem>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<ShopCartItem>>() {
                    @Override
                    public void onWebReturnSucess(List<ShopCartItem> mShopCartItems) {
                        shopCartItems = mShopCartItems;
                        deliveryHomeProductsFragment.setShopCart(mShopCartItems);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void addItemToShopCart(final ProductUnit unit, final int quantity, int moduleId) {
        shopCartApi.addItemToShopCart(unit.getProductId(), quantity, moduleId)
                .compose(deliveryHomeProductsFragment.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        deliveryHomeProductsFragment.addShopCart(unit, quantity);
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(deliveryHomeProductsFragment.getContext().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void removeShopCartItem(int productId) {
        shopCartApi.removeShopCartItem(productId)
                .compose(deliveryHomeProductsFragment.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String str) {
                        Toast.makeText(deliveryHomeProductsFragment.getContext().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(deliveryHomeProductsFragment.getContext().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void updateShopCart() {
        shopCartApi.updateShopCart(shopCartItems)
                .compose(deliveryHomeProductsFragment.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String str) {
                        Toast.makeText(deliveryHomeProductsFragment.getContext().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(deliveryHomeProductsFragment.getContext().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryProductSort(int moduleId) {
        productApi.queryProductSort("", moduleId)
                .compose(deliveryHomeProductsFragment.<WebReturn<List<SecondarySort>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<SecondarySort>>() {
                    @Override
                    public void onWebReturnSucess(List<SecondarySort> mSecondarySorts) {
                        deliveryHomeProductsFragment.initList(mSecondarySorts.get(0));
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryProductyBySortId(int productSortId) {
        productApi.queryProductyBySortId("" + productSortId, "" + 1, "" + 200, "" + false, 0)
                .compose(deliveryHomeProductsFragment.<WebReturn<PagerManager<ProductSimple>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<ProductSimple>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<ProductSimple> productSimplePagerManager) {
                        deliveryHomeProductsFragment.initProducts(productSimplePagerManager.getList());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public DeliveryHomeProductsFragment getDeliveryHomeProductsFragment() {
        return deliveryHomeProductsFragment;
    }

    public List<ShopCartItem> getShopCartItems() {
        return shopCartItems;
    }
}
