package com.j1j2.pifalao.feature.productdetail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserFavoriteApi;
import com.j1j2.data.model.Product;
import com.j1j2.data.model.ProductImg;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.RemoveUserFavoritesBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-16.
 */
public class ProductDetailViewModel {

    private ProductDetailActivity productDetailActivity;
    private ProductApi productApi;
    private ShopCartApi shopCartApi;
    private UserFavoriteApi userFavoriteApi;


    public ObservableField<Product> productDetail = new ObservableField<>();
    public ObservableBoolean isCollect = new ObservableBoolean(false);

    public ObservableField<ProductUnit> selectUnit = new ObservableField<>();

    public int[] array = {R.drawable.view_productdetail_show_servicepoint_img1,
            R.drawable.view_productdetail_show_servicepoint_img2,
            R.drawable.view_productdetail_show_storehouse_img1,
            R.drawable.view_productdetail_show_storehouse_img2};


    public ProductDetailViewModel(ProductDetailActivity productDetailActivity, ProductApi productApi, ShopCartApi shopCartApi, UserFavoriteApi userFavoriteApi) {
        this.productDetailActivity = productDetailActivity;
        this.productApi = productApi;
        this.shopCartApi = shopCartApi;
        this.userFavoriteApi = userFavoriteApi;

    }

    public void updateProductViews(int productId) {
        productApi.updateProductViews(productId)
                .compose(productDetailActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryProductDetail(int mainId) {
        productApi.queryProductDetails(mainId)
                .compose(productDetailActivity.<WebReturn<Product>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<Product>() {
                    @Override
                    public void onWebReturnSucess(Product mProductDetail) {
                        selectUnit.set(mProductDetail.getProductUnits().get(0));
                        productDetail.set(mProductDetail);
                        List<ProductImg> sizeProductImgs = new ArrayList<ProductImg>();
                        for (ProductImg productImg : productDetail.get().getImgs()) {
                            if (productImg.getSize() == 3) {
                                sizeProductImgs.add(productImg);
                            }
                            if (sizeProductImgs.size() == 4)
                                break;
                        }
                        productDetailActivity.moduleType = mProductDetail.getModuleType();
                        productDetailActivity.initShareContent(mProductDetail);
                        productDetailActivity.initBanner(sizeProductImgs);
                        productDetailActivity.initPrice(mProductDetail);
                        productDetailActivity.initUnitsSelect(mProductDetail);
                        productDetailActivity.initBottomViewPager(sizeProductImgs, mProductDetail.getProductUnits().get(0).getProductId(), mProductDetail);
                        if (mProductDetail.getProductUnits() != null && mProductDetail.getProductUnits().size() > 0)
                            updateProductViews(mProductDetail.getProductUnits().get(0).getProductId());
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
                .compose(productDetailActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        productDetailActivity.toastor.showSingletonToast(s);
                        productDetailActivity.addShopCart(unit, quantity);
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        productDetailActivity.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void clooectBtnClick(int mainId) {
        if (isCollect.get()) {
            removeItemFromUserFavorites(mainId);
        } else {
            addProductToFavorites(mainId);
        }
    }

    public void addProductToFavorites(int mainId) {
        userFavoriteApi.addProductToFavorites(mainId)
                .compose(productDetailActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        productDetailActivity.toastor.showSingletonToast(s);
                        isCollect.set(true);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        productDetailActivity.toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public void removeItemFromUserFavorites(int mainId) {
        List<Integer> integers = new ArrayList<>();
        integers.add(mainId);
        RemoveUserFavoritesBody removeUserFavoritesBody = new RemoveUserFavoritesBody();
        removeUserFavoritesBody.setMainIdList(integers);
        userFavoriteApi.removeItemFromUserFavorites(removeUserFavoritesBody)
                .compose(productDetailActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        productDetailActivity.toastor.showSingletonToast(s);
                        isCollect.set(false);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        productDetailActivity.toastor.showSingletonToast(errorMessage);
                        isCollect.set(true);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryProductHasBeenCollected(int mainId) {
        userFavoriteApi.queryProductHasBeenCollected(mainId)
                .compose(productDetailActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        isCollect.set(true);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        isCollect.set(false);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }


    public ProductDetailActivity getProductDetailActivity() {
        return productDetailActivity;
    }
}
