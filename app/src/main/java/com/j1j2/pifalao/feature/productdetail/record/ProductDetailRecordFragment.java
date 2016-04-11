package com.j1j2.pifalao.feature.productdetail.record;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.ProductBuyRecord;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.HasComponent;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.FragmentProductdetailRecordBinding;
import com.j1j2.pifalao.feature.productdetail.di.ProductDetailComponent;
import com.j1j2.pifalao.feature.productdetail.di.ProductDetailModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-4-10.
 */
@RequireBundler
public class ProductDetailRecordFragment extends BaseFragment {

    public interface ProductDetailRecordFragmentListener extends HasComponent<ProductDetailComponent> {

    }

    private ProductDetailRecordFragmentListener listener;

    FragmentProductdetailRecordBinding binding;

    @Arg
    int productId;

    @Inject
    ProductApi productApi;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ProductDetailRecordFragmentListener) activity;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_productdetail_record, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.recordList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(getContext().getResources().getColor(R.color.colorGrayEDEDED));
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{15.0f, 15.0f}, 0));
        binding.recordList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .margin(AutoUtils.getPercentWidthSize(38))
                .paint(paint)
                .showLastDivider()
                .build());
        queryProductBuyRecord();
    }

    public void queryProductBuyRecord() {
        productApi.queryProductBuyRecords(productId, 1, 30)
                .compose(this.<WebReturn<PagerManager<ProductBuyRecord>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<ProductBuyRecord>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<ProductBuyRecord> productBuyRecordPagerManager) {
                        ProductDetailRecordAdapter productDetailRecordAdapter = new ProductDetailRecordAdapter(productBuyRecordPagerManager.getList());
                        binding.recordList.setAdapter(productDetailRecordAdapter);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        listener.getComponent().inject(this);
    }
}
