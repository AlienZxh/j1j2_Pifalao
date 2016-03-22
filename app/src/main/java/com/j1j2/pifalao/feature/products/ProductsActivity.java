package com.j1j2.pifalao.feature.products;

import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityProductsBinding;
import com.j1j2.pifalao.databinding.ViewProductsAddBinding;
import com.j1j2.pifalao.feature.productdetail.unit.ProductDetailUnitAdapter;
import com.j1j2.pifalao.feature.products.di.ProductsModule;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler(requireAll = false)
public class ProductsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, ProductsAdapter.OnProductsClickListener, View.OnClickListener {

    public static final int PRODUCTS_TYPE_SORT = 1;
    public static final int PRODUCTS_TYPE_SEARCH = 2;

    ActivityProductsBinding binding;

    @Arg
    @Required(true)
    Module module;
    @Arg
    @Required(true)
    int activityType;
    @Arg
    @Required(false)
    ProductSort productSort;
    @Arg
    @Required(false)
    String key;
    @Inject
    ProductsViewModel productsViewModel;

    SingleSelector singleSelector;
    ViewProductsAddBinding dialogBinding;
    DialogPlus addDialog;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products);
        productsViewModel.title.set(activityType == PRODUCTS_TYPE_SORT ? productSort.getSortName() : key);
        binding.setProductViewModel(productsViewModel);
        binding.productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .drawable(R.drawable.item_products_divider)
                .size(AutoUtils.getPercentHeightSize(8))
                .build());
        binding.productList.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        binding.productList.setRefreshListener(this);
        binding.productList.setOnMoreListener(this);
    }

    @Override
    protected void initViews() {
        singleSelector = new SingleSelector();
        dialogBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_products_add, null, false);
        addDialog = DialogPlus.newDialog(this).setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setContentHolder(new ViewHolder(dialogBinding.getRoot()))
                .setContentBackgroundResource(R.color.colorTransparent)
                .create();
        dialogBinding.dialogClose.setOnClickListener(this);
        dialogBinding.dialogShopcart.setOnClickListener(this);
        dialogBinding.dialogAdd.setOnClickListener(this);
        queryProducts(true);
    }

    public void queryProducts(boolean isRefresh) {
        if (activityType == PRODUCTS_TYPE_SORT)
            productsViewModel.queryProductyBySortId(isRefresh);
        else if (activityType == PRODUCTS_TYPE_SEARCH)
            productsViewModel.queryProductyByKey(isRefresh);
    }

    public void setProdutsAdapter(ProductsAdapter productsAdapter) {
        binding.productList.setAdapter(productsAdapter);
        productsAdapter.setOnProductsClickListener(this);
    }

    public void setLoadMoreEnable(boolean is) {
        binding.productList.setLoadingMore(is);
    }

    public void setLoadMoreFinish() {
        binding.productList.hideMoreProgress();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new ProductsModule(this
                , null == productSort ? new ProductSort() : productSort
                , module, null == key ? "" : key)).inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    public void onClick(View v) {
        if (v == dialogBinding.dialogClose) {
            if (addDialog.isShowing())
                addDialog.dismiss();
        }

        if (v == dialogBinding.dialogShopcart) {
            if (addDialog.isShowing())
                addDialog.dismiss();
        }

        if (v == dialogBinding.dialogAdd) {

        }
    }

    @Override
    public void onRefresh() {
        queryProducts(true);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        queryProducts(false);
    }

    @Override
    public void onItemClickListener(View view, ProductSimple productSimple, int position) {
        navigate.navigateToProductDetailActivity(ProductsActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, productSimple);
    }

    @Override
    public void onAddIconClickListener(View view, ProductSimple productSimple, int position) {
        initDialogView(productSimple);
        addDialog.show();
    }

    private void initDialogView(ProductSimple productSimple) {
        dialogBinding.dialogImg.setImageURI(Uri.parse(productSimple.getMainImg() == null ? "" : productSimple.getMainImg()));
        dialogBinding.dialogName.setText(productSimple.getName());
        dialogBinding.dialogRealPrice.setText("零售价：￥" + productSimple.getProductUnits().get(0).getRetialPrice() + "/" + productSimple.getProductUnits().get(0).getUnit());
        dialogBinding.dialogRealPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        dialogBinding.dialogMemberPrice.setText("批发价：￥" + productSimple.getProductUnits().get(0).getMemberPrice() + "/" + productSimple.getProductUnits().get(0).getUnit());
        ProductDetailUnitAdapter productDetailUnitAdapter = new ProductDetailUnitAdapter(productSimple.getProductUnits(), singleSelector);
        dialogBinding.dialogUnitList.setLayoutManager(new GridLayoutManager(this, 3));
        dialogBinding.dialogUnitList.setAdapter(productDetailUnitAdapter);
        productDetailUnitAdapter.setOnUnitItemClickListener(new ProductDetailUnitAdapter.OnUnitItemClickListener() {
            @Override
            public void OnUnitItemClickListener(View view, ProductUnit unit, int position) {
                dialogBinding.dialogRealPrice.setText("零售价：￥" + unit.getRetialPrice() + "/" + unit.getUnit());
                dialogBinding.dialogMemberPrice.setText("批发价：￥" + unit.getMemberPrice() + "/" + unit.getUnit());
            }
        });

    }
}
