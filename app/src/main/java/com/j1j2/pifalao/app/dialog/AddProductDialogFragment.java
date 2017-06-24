package com.j1j2.pifalao.app.dialog;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Size;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.bumptech.glide.Glide;
import com.j1j2.common.util.ScreenUtils;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.Product;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.databinding.ViewProductsAddBinding;
import com.j1j2.pifalao.feature.productdetail.unit.ProductDetailUnitAdapter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-10-18.
 */
@RequireBundler
public class AddProductDialogFragment extends BaseBottomDialogFragment implements View.OnClickListener {

    public interface AddProductDialogFragmentListener {

        void setSelectedUnit(ProductUnit productUnit);

        void onAddBtnClick(int quantity);

        void onShopcartBtnClick();
    }

    AddProductDialogFragmentListener listener;
    ViewProductsAddBinding binding;

    @Arg
    Product product;
    @Arg
    ShopSubscribeService shopSubscribeService;

    SingleSelector singleSelector;
    ShopCart shopCart;

    ValueAnimator valueAnimator;
    int[] endLocation = new int[2];
    int[] startocation = new int[2];

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (AddProductDialogFragmentListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundler.inject(this);

        binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.view_products_add, null, false);
        singleSelector = new SingleSelector();
        Glide.with(this)
                .load(Uri.parse(product.getMainThumImg() == null ? "" : product.getMainThumImg()))
                .asBitmap()
                .error(R.drawable.loadimg_error)
                .placeholder(R.drawable.loadimg_loading)
                .into(binding.dialogImg);
        binding.dialogName.setText(product.getName());
        binding.dialogRealPrice.setText("市场价：￥" + product.getProductUnits().get(0).getRetialPrice() + "/" + product.getProductUnits().get(0).getUnit());
        binding.dialogRealPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        binding.dialogMemberPrice.setText("批发价：￥" + product.getProductUnits().get(0).getMemberPrice() + "/" + product.getProductUnits().get(0).getUnit());
        ProductDetailUnitAdapter productDetailUnitAdapter = new ProductDetailUnitAdapter(product.getProductUnits()
                , singleSelector
                , product.getBaseUnit()
                , shopSubscribeService.getServiceId());
        binding.dialogUnitList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.dialogUnitList.setAdapter(productDetailUnitAdapter);
        listener.setSelectedUnit(product.getProductUnits().get(0));
        productDetailUnitAdapter.setOnUnitItemClickListener(new ProductDetailUnitAdapter.OnUnitItemClickListener() {
            @Override
            public void OnUnitItemClickListener(View view, ProductUnit unit, int position) {

                listener.setSelectedUnit(unit);
                binding.dialogRealPrice.setText("零售价：￥" + unit.getRetialPrice() + "/" + unit.getUnit());
                binding.dialogMemberPrice.setText("批发价：￥" + unit.getMemberPrice() + "/" + unit.getUnit());
            }
        });

        binding.dialogClose.setOnClickListener(this);
        binding.dialogShopcart.setOnClickListener(this);
        binding.dialogAdd.setOnClickListener(this);

        EventBus.getDefault().register(this);
    }

    @Override
    protected AlertDialog.Builder getDialogBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.BottomDialog);
        builder.setView(binding.getRoot());
        return builder;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator.removeAllUpdateListeners();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.dialogClose) {
            dismiss();
        }
        if (v == binding.dialogAdd) {
            binding.dialogImg.getLocationOnScreen(startocation);
            showAddShopCartAnim(startocation);
            listener.onAddBtnClick(binding.dialogQuantityview.getQuantity());
        }
        if (v == binding.dialogShopcart) {
            listener.onShopcartBtnClick();
            dismiss();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            shopCart = MainAplication.get(getContext()).getUserComponent().shopCart();
            binding.setShopCart(shopCart);
        } else {
            if (shopCart != null)
                shopCart.clear();
        }
    }


    public void showAddShopCartAnim(@Size(2) int[] startLocation) {

        if (valueAnimator != null && valueAnimator.isRunning())
            return;
        if (endLocation[0] == 0)
            binding.dialogShopcart.getLocationOnScreen(endLocation);

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

                binding.shopCartAdd.setX(point.x);
                binding.shopCartAdd.setY(point.y);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                binding.shopCartAdd.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                binding.shopCartAdd.setVisibility(View.GONE);
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
}
