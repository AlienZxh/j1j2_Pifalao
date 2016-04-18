package com.j1j2.pifalao.feature.orderproducts;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.model.OrderProductDetail;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityOrderproductsBinding;
import com.j1j2.pifalao.feature.orderproducts.di.OrderProductsModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.ParcelListSerializer;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;

/**
 * Created by alienzxh on 16-4-2.
 */
@RequireBundler
public class OrderProductsActivity extends BaseActivity implements OrderProductListAdapter.OnItemClickListener, View.OnClickListener {

    public static final int FROM_CONFIRMORDER = 1;
    public static final int FROM_ORDERS = 2;

    ActivityOrderproductsBinding binding;

    @Arg
    int activityType;

    @Arg
    int moduleId;

    @Arg(serializer = ParcelListSerializer.class)
    @Required(false)
    List<ShopCartItem> shopCartItems;

    @Arg(serializer = ParcelListSerializer.class)
    @Required(false)
    List<OrderProductDetail> orderProductDetails;

    private List<OrderProductSimple> orderProductSimples;

    @Inject
    ShopCart shopCart;
    @Inject
    UserRelativePreference userRelativePreference;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orderproducts);
        binding.backBtn.setOnClickListener(this);
        binding.productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .colorResId(R.color.colorTransparent)
                .size(AutoUtils.getPercentHeightSize(10))
                .showLastDivider()
                .build());
        binding.productList.setClipToPadding(false);
        binding.productList.setClipChildren(false);
        binding.productList.setPadding(0, AutoUtils.getPercentHeightSize(10), 0, 0);
        //___________________________________________________________________________
        orderProductSimples = new ArrayList<>();
        OrderProductSimple orderProductSimple;
        if (activityType == FROM_CONFIRMORDER) {
            for (ShopCartItem shopCartItem : shopCartItems) {
                orderProductSimple = new OrderProductSimple(activityType, shopCartItem);
                orderProductSimples.add(orderProductSimple);
            }
        } else if (activityType == FROM_ORDERS) {
            for (OrderProductDetail orderProductDetail : orderProductDetails) {
                orderProductSimple = new OrderProductSimple(activityType, orderProductDetail);
                orderProductSimples.add(orderProductSimple);
            }
        }
        //_________________________________________________

    }

    @Override
    protected void initViews() {
        OrderProductListAdapter productListAdapter = new OrderProductListAdapter(orderProductSimples, shopCart, userRelativePreference.getSelectedModule(null).getModuleType());
        binding.productList.setAdapter(productListAdapter);
        productListAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new OrderProductsModule(this)).inject(this);
    }

    @Override
    public void onItemClickListener(View v, OrderProductSimple orderProductSimple, int position) {
        navigate.navigateToProductDetailActivity(this, null, false, orderProductSimple.getProductMainId());
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
