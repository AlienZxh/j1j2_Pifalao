package com.j1j2.pifalao.feature.productdetail;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserFavoriteApi;
import com.j1j2.data.model.ProductDetail;
import com.j1j2.data.model.ProductImg;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.RemoveUserFavoritesBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.ShopCartChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
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


    public ObservableField<ProductDetail> productDetail = new ObservableField<>();
    public ObservableBoolean isCollect = new ObservableBoolean(false);

    public ObservableField<ProductUnit> selectUnit = new ObservableField<>();

    public String[] array = {"res://com.j1j2.pifalao/" + R.drawable.view_productdetail_show_servicepoint_img1,
            "res://com.j1j2.pifalao/" + R.drawable.view_productdetail_show_servicepoint_img2,
            "res://com.j1j2.pifalao/" + R.drawable.view_productdetail_show_storehouse_img1,
            "res://com.j1j2.pifalao/" + R.drawable.view_productdetail_show_storehouse_img2};


    public ProductDetailViewModel(ProductDetailActivity productDetailActivity, ProductApi productApi, ShopCartApi shopCartApi, UserFavoriteApi userFavoriteApi) {
        this.productDetailActivity = productDetailActivity;
        this.productApi = productApi;
        this.shopCartApi = shopCartApi;
        this.userFavoriteApi = userFavoriteApi;
    }

    public void queryProductDetail(int mainId) {
        productApi.queryProductDetails(mainId)
                .compose(productDetailActivity.<WebReturn<ProductDetail>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<ProductDetail>() {
                    @Override
                    public void onWebReturnSucess(ProductDetail mProductDetail) {
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
                        productDetailActivity.initBanner(sizeProductImgs);
                        productDetailActivity.initUnitsSelect(mProductDetail);
                        productDetailActivity.initBottomViewPager(sizeProductImgs, mProductDetail.getProductUnits().get(0).getProductId(), mProductDetail);
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
                .compose(productDetailActivity.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        Toast.makeText(productDetailActivity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        productDetailActivity.addShopCart(unit, quantity);
                        EventBus.getDefault().post(new ShopCartChangeEvent());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(productDetailActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(productDetailActivity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        isCollect.set(true);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(productDetailActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(productDetailActivity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        isCollect.set(false);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        Toast.makeText(productDetailActivity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
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
