package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.data.model.SecondarySort;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
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

    DeliveryProductsAdapter deliveryProductsAdapter;

    private int pageIndex = 1;
    private int pageSize = 20;
    private int pageCount = 0;

    public DeliveryProductsViewModel(DeliveryHomeProductsFragment deliveryHomeProductsFragment, ProductApi productApi, ShopCartApi shopCartApi) {
        this.deliveryHomeProductsFragment = deliveryHomeProductsFragment;
        this.productApi = productApi;
        this.shopCartApi = shopCartApi;
        deliveryProductsAdapter = new DeliveryProductsAdapter(new ArrayList<ProductSimple>());

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
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                        deliveryHomeProductsFragment.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void removeShopCartItem(final ProductUnit unit) {
        shopCartApi.removeShopCartItem(unit.getProductId())
                .compose(deliveryHomeProductsFragment.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String str) {
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                        deliveryHomeProductsFragment.toastor.showSingletonToast(str);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                        deliveryHomeProductsFragment.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void updateShopCart(final boolean showAnim) {
        shopCartApi.updateShopCart(shopCartItems)
                .compose(deliveryHomeProductsFragment.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String str) {
                        if (!showAnim)
                            deliveryHomeProductsFragment.toastor.showSingletonToast(str);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                        deliveryHomeProductsFragment.toastor.showSingletonToast(errorMessage);
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


    public void queryProductyBySortId(boolean isRefresh, int productSortId) {
        if (isRefresh) {
            pageIndex = 1;
            deliveryHomeProductsFragment.setLoadMoreBegin();
        }
        productApi.queryProductyBySortId("" + productSortId, "" + pageIndex, "" + pageSize, "" + false, Constant.ProductsOrderbyId.PRODUCTS_ORDERBY_DEFAULT)
                .compose(deliveryHomeProductsFragment.<WebReturn<PagerManager<ProductSimple>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<ProductSimple>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<ProductSimple> productSimplePagerManager) {
                        pageCount = productSimplePagerManager.getPageCount();
                        if (pageIndex == 1) {
                            deliveryProductsAdapter.initData(productSimplePagerManager.getList());
                            deliveryHomeProductsFragment.setAdapter(deliveryProductsAdapter);
                        } else if (pageIndex <= pageCount) {
                            deliveryProductsAdapter.addAll(productSimplePagerManager.getList());
                        } else {
                            deliveryHomeProductsFragment.setLoadMoreComplete();
                        }
                        pageIndex++;
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void querySellsProducts(boolean isRefresh, int productSortId) {
        if (isRefresh) {
            pageIndex = 1;
            deliveryHomeProductsFragment.setLoadMoreBegin();
        }
        productApi.queryProductyBySortId("" + productSortId, "" + pageIndex, "" + pageSize, "" + true, Constant.ProductsOrderbyId.PRODUCTS_ORDERBY_SELLS)
                .compose(deliveryHomeProductsFragment.<WebReturn<PagerManager<ProductSimple>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<ProductSimple>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<ProductSimple> productSimplePagerManager) {
                        pageCount = productSimplePagerManager.getPageCount();
                        if (pageIndex == 1) {
                            deliveryProductsAdapter.initData(productSimplePagerManager.getList());
                            deliveryHomeProductsFragment.setAdapter(deliveryProductsAdapter);
                        } else if (pageIndex <= pageCount) {
                            deliveryProductsAdapter.addAll(productSimplePagerManager.getList());
                        } else {
                            deliveryHomeProductsFragment.setLoadMoreComplete();
                        }
                        pageIndex++;
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public void queryActivityProducts(boolean isRefresh, int moduleId) {
        if (isRefresh) {
            pageIndex = 1;
            deliveryHomeProductsFragment.setLoadMoreBegin();
        }
        productApi.queryActivityProducts(pageIndex, pageSize, moduleId, 1, "", "")
                .compose(deliveryHomeProductsFragment.<WebReturn<PagerManager<ProductSimple>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<ProductSimple>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<ProductSimple> productSimplePagerManager) {
                        pageCount = productSimplePagerManager.getPageCount();
                        if (pageIndex == 1) {
                            deliveryProductsAdapter.initData(productSimplePagerManager.getList());
                            deliveryHomeProductsFragment.setAdapter(deliveryProductsAdapter);
                        } else if (pageIndex <= pageCount) {
                            deliveryProductsAdapter.addAll(productSimplePagerManager.getList());
                        } else {
                            deliveryHomeProductsFragment.setLoadMoreComplete();
                        }
                        pageIndex++;
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

    public DeliveryProductsAdapter getDeliveryProductsAdapter() {
        return deliveryProductsAdapter;
    }
}
