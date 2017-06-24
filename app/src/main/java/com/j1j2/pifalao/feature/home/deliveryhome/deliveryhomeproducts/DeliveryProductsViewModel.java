package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.Product;
import com.j1j2.data.model.ProductCategory;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.QueryProductParams;
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
    private int pageSize = 15;
    private int pageCount = 0;

    public DeliveryProductsViewModel(DeliveryHomeProductsFragment deliveryHomeProductsFragment, ProductApi productApi, ShopCartApi shopCartApi) {
        this.deliveryHomeProductsFragment = deliveryHomeProductsFragment;
        this.productApi = productApi;
        this.shopCartApi = shopCartApi;
        deliveryProductsAdapter = new DeliveryProductsAdapter(new ArrayList<Product>());

    }

    public void queryShopcart(int serviceId, int shopId) {
        shopCartApi.queryShopCart(serviceId, shopId)
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

    public void addItemToShopCart(final ProductUnit unit, final int quantity, int serviceId, int shopId) {
        shopCartApi.addItemToShopCart(unit.getProductId(), quantity, serviceId, shopId)
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

    public void removeShopCartItem(final ProductUnit unit, int serviceId, int shopId) {
        shopCartApi.removeShopCartItem(unit.getProductId(), serviceId, shopId)
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

    public void updateShopCart(final boolean showAnim, int serviceId, int shopId) {
        shopCartApi.updateShopCart(shopCartItems, serviceId, shopId)
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

    public void queryDeliveryProductSort(int serviceId, int shopId) {
        productApi.queryProductCategoryTrees(serviceId, shopId)
                .compose(deliveryHomeProductsFragment.<WebReturn<List<ProductCategory>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<ProductCategory>>() {
                    @Override
                    public void onWebReturnSucess(List<ProductCategory> productCategories) {
                        deliveryHomeProductsFragment.initList(productCategories);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public void queryProductyBySortId(boolean isRefresh, int serviceId, int shopId, int categoryId) {
        if (isRefresh) {
            pageIndex = 1;
            deliveryHomeProductsFragment.setLoadMoreBegin();
            deliveryHomeProductsFragment.showLoad();
        }
        QueryProductParams params = new QueryProductParams();
        params.setServiceId(serviceId);
        params.setShopId(shopId);
        params.setPageIndex(pageIndex);
        params.setPageSize(pageSize);
        params.setCategoryId(categoryId);
        params.setQueryDataType(4);
        productApi.queryProducts(params)
                .compose(deliveryHomeProductsFragment.<WebReturn<PagerManager<Product>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<Product>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<Product> productPagerManager) {

                        pageCount = productPagerManager.getPageCount();
                        if (pageIndex == 1) {
                            deliveryProductsAdapter.initData(productPagerManager.getList());
                            deliveryHomeProductsFragment.setAdapter(deliveryProductsAdapter);
                        } else if (pageIndex <= pageCount) {
                            deliveryProductsAdapter.addAll(productPagerManager.getList());
                        } else {
                            deliveryHomeProductsFragment.setLoadMoreComplete();
                        }
                        pageIndex++;
                        deliveryHomeProductsFragment.hideLoad();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void querySellsProducts(boolean isRefresh, int serviceId, int shopId) {
        if (isRefresh) {
            pageIndex = 1;
            deliveryHomeProductsFragment.setLoadMoreBegin();
            deliveryHomeProductsFragment.showLoad();
        }
        QueryProductParams params = new QueryProductParams();
        params.setServiceId(serviceId);
        params.setShopId(shopId);
        params.setPageIndex(pageIndex);
        params.setPageSize(pageSize);
        params.setOrderByType(1);
        params.setQueryDataType(3);
        productApi.queryProducts(params)
                .compose(deliveryHomeProductsFragment.<WebReturn<PagerManager<Product>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<Product>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<Product> productPagerManager) {

                        pageCount = productPagerManager.getPageCount();
                        if (pageIndex == 1) {
                            deliveryProductsAdapter.initData(productPagerManager.getList());
                            deliveryHomeProductsFragment.setAdapter(deliveryProductsAdapter);
                        } else if (pageIndex <= pageCount) {
                            deliveryProductsAdapter.addAll(productPagerManager.getList());
                        } else {
                            deliveryHomeProductsFragment.setLoadMoreComplete();
                        }
                        pageIndex++;
                        deliveryHomeProductsFragment.hideLoad();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    //state:  1: 促销　２：新品
    public void queryActivityProducts(boolean isRefresh, int serviceId, int shopId, int state) {
        if (isRefresh) {
            pageIndex = 1;
            deliveryHomeProductsFragment.setLoadMoreBegin();
            deliveryHomeProductsFragment.showLoad();
        }
        QueryProductParams params = new QueryProductParams();
        params.setServiceId(serviceId);
        params.setShopId(shopId);
        params.setPageIndex(pageIndex);
        params.setPageSize(pageSize);
        params.setQueryDataType(state);
        productApi.queryProducts(params)
                .compose(deliveryHomeProductsFragment.<WebReturn<PagerManager<Product>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<Product>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<Product> productPagerManager) {

                        pageCount = productPagerManager.getPageCount();
                        if (pageIndex == 1) {
                            deliveryProductsAdapter.initData(productPagerManager.getList());
                            deliveryHomeProductsFragment.setAdapter(deliveryProductsAdapter);
                        } else if (pageIndex <= pageCount) {
                            deliveryProductsAdapter.addAll(productPagerManager.getList());
                        } else {
                            deliveryHomeProductsFragment.setLoadMoreComplete();
                        }
                        pageIndex++;
                        deliveryHomeProductsFragment.hideLoad();
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
