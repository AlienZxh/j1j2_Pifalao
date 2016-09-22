package com.j1j2.pifalao.feature.products;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Paint;
import android.graphics.PointF;
import android.net.Uri;
import android.support.annotation.Size;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.j1j2.common.util.ScreenUtils;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.sharedpreferences.FreightTypePrefrence;
import com.j1j2.pifalao.databinding.ActivityProductsBinding;
import com.j1j2.pifalao.databinding.ViewProductsAddBinding;
import com.j1j2.pifalao.feature.productdetail.unit.ProductDetailUnitAdapter;
import com.j1j2.pifalao.feature.products.di.ProductsModule;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.orhanobut.logger.Logger;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler(requireAll = false)
public class ProductsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, ProductsAdapter.OnProductsClickListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final int PRODUCTS_TYPE_SORT = 1;
    public static final int PRODUCTS_TYPE_SEARCH = 2;

    ActivityProductsBinding binding;

    @Arg
    @Required(true)
    public Module module;
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
    FreightTypePrefrence freightTypePrefrence;
    @Inject
    ProductsViewModel productsViewModel;

    public ObservableField<FreightType> freightTypeObservableField = new ObservableField<>();

    SingleSelector singleSelector;
    ViewProductsAddBinding dialogBinding;
    DialogPlus addDialog;

    ProductUnit selectedUnit;

    ShopCart shopCart;

    public ObservableBoolean isLogin = new ObservableBoolean();

    ValueAnimator valueAnimator;
    int[] endLocation = new int[2];
    int[] startocation = new int[2];


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
    }

    @Override
    protected void initViews() {
        freightTypeObservableField.set(freightTypePrefrence.getDeliveryFreightType(null));
        //___________________________________________________________________
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
        //_____________________________________________________________________________
        List<String> strings = new ArrayList<>();
        strings.add("默认");
        strings.add("销量");
        strings.add("价格");
        strings.add("人气");
        binding.sortSpinner.setAdapter(new ProductFilterAdapter(strings, this));
        binding.sortSpinner.setOnItemSelectedListener(this);
        binding.sortSpinner.setSelection(0);
    }

    public void queryProducts(boolean isRefresh) {
        if (activityType == PRODUCTS_TYPE_SORT)
            productsViewModel.queryProductyBySortId(isRefresh);
        else if (activityType == PRODUCTS_TYPE_SEARCH)
            productsViewModel.queryProductyByKey(isRefresh);
    }

    public void setProdutsAdapter(ProductsAdapter productsAdapter) {
        if (binding.productList.getAdapter() == null || binding.productList.getAdapter() != productsAdapter) {
            binding.productList.setAdapter(productsAdapter);
            productsAdapter.setOnProductsClickListener(this);
        }
    }

    public void setLoadMoreBegin() {
        binding.productList.setupMoreListener(this, 1);
    }

    public void setLoadMoreComplete() {
        binding.productList.hideMoreProgress();
        binding.productList.removeMoreListener();
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
    protected void onStop() {
        super.onStop();
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator.removeAllUpdateListeners();
        }
    }

    public void addShopCart(ProductUnit unit, int Quantity) {
        shopCart.addUnitWitQuantity(unit, Quantity);
    }

    public void showAddShopCartAnim(@Size(2) int[] startLocation) {

        if (valueAnimator != null && valueAnimator.isRunning())
            return;
        if (endLocation[0] == 0)
            dialogBinding.dialogShopcart.getLocationOnScreen(endLocation);

        Logger.d("ShopCartAnim  startLocation " + Arrays.toString(startLocation));
        Logger.d("ShopCartAnim  endLocation " + Arrays.toString(endLocation));

        valueAnimator = ValueAnimator.ofObject(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
                float v = (endValue.x - startValue.x);
                float a = (endValue.y - startValue.y);

                PointF point = new PointF();
                point.x = v * fraction + startValue.x;
                point.y = a * fraction * fraction + startValue.y;

                return point;
            }
        }, new PointF(ScreenUtils.dpToPx(20), ScreenUtils.dpToPx(50)), new PointF(ScreenUtils.dpToPx(40), endLocation[1] - startLocation[1]));
        valueAnimator.setDuration(600);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();

                dialogBinding.shopCartAdd.setX(point.x);
                dialogBinding.shopCartAdd.setY(point.y);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                dialogBinding.shopCartAdd.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dialogBinding.shopCartAdd.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    @Override
    public void onClick(View v) {
        if (v == dialogBinding.dialogClose) {
            if (addDialog.isShowing())
                addDialog.dismiss();
        }
        if (v == dialogBinding.dialogShopcart || v == binding.shopCartView) {
            if (addDialog.isShowing())
                addDialog.dismiss();
            if (MainAplication.get(this).isLogin())
                navigate.navigateToShopCart(this, null, false, module.getWareHouseModuleId());
            else
                navigate.navigateToLogin(this, null, false);
        }

        if (v == binding.backBtn) {
            if (addDialog.isShowing())
                addDialog.dismiss();
            onBackPressed();
        }
        if (v == dialogBinding.dialogAdd) {
            productsViewModel.addItemToShopCart(selectedUnit, dialogBinding.dialogQuantityview.getQuantity());
            dialogBinding.dialogImg.getLocationOnScreen(startocation);
            showAddShopCartAnim(startocation);

        }

    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            shopCart = MainAplication.get(this).getUserComponent().shopCart();
            dialogBinding.setShopCart(shopCart);
            productsViewModel.getProductAdapter().setShopCart(shopCart);
            binding.setShopCart(shopCart);
            isLogin.set(true);
            productsViewModel.CountDown();
        } else {
            if (shopCart != null)
                shopCart.clear();
            isLogin.set(false);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        productsViewModel.setOrderBy(position);
        queryProducts(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClickListener(View view, ProductSimple productSimple, int position) {
        navigate.navigateToProductDetailActivity(ProductsActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, productSimple.getMainId());
    }

    @Override
    public void onAddIconClickListener(View view, ProductSimple productSimple, int position) {
        initDialogView(productSimple);
        if (MainAplication.get(this).isLogin()) {
            addDialog.show();
        } else {
            navigate.navigateToLogin(this, null, false);
        }

    }

    private void initDialogView(ProductSimple productSimple) {
        Glide.with(this)
                .load(Uri.parse(productSimple.getMainImg() == null ? "" : productSimple.getMainImg()))
                .asBitmap()
                .error(R.drawable.loadimg_error)
                .placeholder(R.drawable.loadimg_loading)
                .into(dialogBinding.dialogImg);
        dialogBinding.dialogName.setText(productSimple.getName());
        dialogBinding.dialogRealPrice.setText("市场价：￥" + productSimple.getProductUnits().get(0).getRetialPrice() + "/" + productSimple.getProductUnits().get(0).getUnit());
        dialogBinding.dialogRealPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        dialogBinding.dialogMemberPrice.setText("批发价：￥" + productSimple.getProductUnits().get(0).getMemberPrice() + "/" + productSimple.getProductUnits().get(0).getUnit());
        ProductDetailUnitAdapter productDetailUnitAdapter = new ProductDetailUnitAdapter(productSimple.getProductUnits(), singleSelector, productSimple.getBaseUnit(), module.getWareHouseModuleId());
        dialogBinding.dialogUnitList.setLayoutManager(new GridLayoutManager(this, 3));
        dialogBinding.dialogUnitList.setAdapter(productDetailUnitAdapter);
        selectedUnit = productSimple.getProductUnits().get(0);
        productDetailUnitAdapter.setOnUnitItemClickListener(new ProductDetailUnitAdapter.OnUnitItemClickListener() {
            @Override
            public void OnUnitItemClickListener(View view, ProductUnit unit, int position) {
                selectedUnit = unit;
                dialogBinding.dialogRealPrice.setText("零售价：￥" + unit.getRetialPrice() + "/" + unit.getUnit());
                dialogBinding.dialogMemberPrice.setText("批发价：￥" + unit.getMemberPrice() + "/" + unit.getUnit());
            }
        });

    }

    public ShopCart getShopCart() {
        return shopCart;
    }
}
