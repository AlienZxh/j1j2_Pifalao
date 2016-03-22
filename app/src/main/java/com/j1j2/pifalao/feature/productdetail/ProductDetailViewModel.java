package com.j1j2.pifalao.feature.productdetail;

import android.databinding.ObservableField;
import android.widget.Toast;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.ProductDetail;
import com.j1j2.data.model.ProductImg;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

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
    private ProductSimple productSimple;

    public ObservableField<ProductDetail> productDetail = new ObservableField<>();

    public ProductDetailViewModel(ProductDetailActivity productDetailActivity, ProductApi productApi, ProductSimple productSimple) {
        this.productDetailActivity = productDetailActivity;
        this.productApi = productApi;
        this.productSimple = productSimple;
    }

    public void queryProductDetail() {
        productApi.queryProductDetails(productSimple.getMainId())
                .compose(productDetailActivity.<WebReturn<ProductDetail>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<ProductDetail>() {
                    @Override
                    public void onWebReturnSucess(ProductDetail mProductDetail) {
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
                        productDetailActivity.initBottomViewPager(sizeProductImgs);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }
}
