package com.j1j2.pifalao.feature.home.deliveryhome;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.PointF;
import android.support.annotation.Size;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.j1j2.common.view.scrollablelayout.ScrollableHelper;
import com.j1j2.common.view.scrollablelayout.ScrollableLayout;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.service.BackGroundService;
import com.j1j2.pifalao.app.sharedpreferences.FreightTypePrefrence;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityDeliveryhomeBinding;
import com.j1j2.pifalao.databinding.ViewDeliveryAreasBinding;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.DeliveryHomeProductsFragment;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.DeliveryHomeServicepointFragment;
import com.j1j2.pifalao.feature.home.deliveryhome.di.DeliveryHomeModule;
import com.nineoldandroids.view.ViewHelper;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-30.
 */
@RequireBundler
public class DeliveryHomeActivity extends BaseActivity implements View.OnClickListener, DeliveryHomeProductsFragment.DeliveryHomeProductsFragmentListener, ScrollableLayout.OnScrollListener, DeliveryAreasAdapter.OnAreaItemClickListener {

    ActivityDeliveryhomeBinding binding;

    @Arg
    ServicePoint servicePoint;

    @Arg
    public Module module;

    @Inject
    DeliveryHomeViewModel deliveryHomeViewModel;

    @Inject
    FreightTypePrefrence freightTypePrefrence;

    @Inject
    UserRelativePreference userRelativePreference;

    ShopCart shopCart = null;

    DeliveryHomeServicepointFragment deliveryHomeServicepointFragment;

    ValueAnimator valueAnimator;
    int[] endLocation = new int[2];

    DialogPlus areasDialog;
    ViewDeliveryAreasBinding areasDialogBinding;

    UnReadInfoManager unReadInfoManager = null;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deliveryhome);
        binding.setServicePoint(servicePoint);
        binding.setDeliveryHomeViewModel(deliveryHomeViewModel);

    }

    @Override
    protected void initViews() {
        //___________________________________________________
        areasDialogBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_delivery_areas, null, false);
        areasDialogBinding.areasList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        areasDialogBinding.areasList.setAdapter(new DeliveryAreasAdapter(Arrays.asList(servicePoint.getServiceAreas() != null ? servicePoint.getServiceAreas().split("\\;") : new String[]{})));
        ((DeliveryAreasAdapter) areasDialogBinding.areasList.getAdapter()).setOnAreaItemClickListener(this);
        areasDialog = DialogPlus.newDialog(this)
                .setInAnimation(android.R.anim.fade_in)
                .setOutAnimation(android.R.anim.fade_out)
                .setCancelable(true)
                .setContentHolder(new ViewHolder(areasDialogBinding.getRoot()))
                .setContentBackgroundResource(R.color.colorTransparent)
                .setGravity(Gravity.CENTER)
                .create();
        areasDialogBinding.confirm.setOnClickListener(this);
        //____________________________________________________________________
        binding.scrollableLayout.setOnScrollListener(this);
        deliveryHomeServicepointFragment = Bundler.deliveryHomeServicepointFragment(servicePoint).create();
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(Bundler.deliveryHomeProductsFragment(module).create());
        fragments.add(deliveryHomeServicepointFragment);
        String[] titles = {"商品", "商家"};
        binding.viewpager.setAdapter(new DeliveryHomeTabAdapter(getSupportFragmentManager(), fragments, titles));
        binding.scrollableLayout.getHelper().setCurrentScrollableContainer((ScrollableHelper.ScrollableContainer) fragments.get(0));
        binding.tab.setViewPager(binding.viewpager);
        binding.tab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.scrollableLayout.getHelper().setCurrentScrollableContainer((ScrollableHelper.ScrollableContainer) fragments.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //________________________________________________________________________________
        deliveryHomeViewModel.queryFreightType(module.getWareHouseModuleId());
        //______________________
        if (userRelativePreference.getShowDeliveryArea(true)) {
            areasDialog.show();
            userRelativePreference.setShowDeliveryArea(false);
        }

    }

    public void initFreightType(FreightType freightType) {
        freightTypePrefrence.setDeliveryFreightType(freightType);
        binding.setFreightType(freightType);
        deliveryHomeServicepointFragment.setFreightType(freightType);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new DeliveryHomeModule(this)).inject(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator.removeAllUpdateListeners();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            shopCart = MainAplication.get(this).getUserComponent().shopCart();
            binding.setShopCart(shopCart);
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
            binding.setUnReadInfoManager(unReadInfoManager);
            BackGroundService.updateUnRead(this);
        } else {
            if (shopCart != null)
                shopCart.clear();
        }
    }


    @Override
    public void OnAreaItemClick() {
        areasDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.shopCartView) {
            if (MainAplication.get(this).isLogin())
                navigateToShopCart(v, module);
            else
                navigateToLogin(v);
        }
        if (v == binding.individualBtn)
            if (MainAplication.get(this).isLogin())
                navigate.navigateToIndividualCenter(this, null, false);
            else
                navigateToLogin(v);
        if (v == binding.searchview)
            navigate.navigateToSearchActivity(this, null, false, module);
        if (v == areasDialogBinding.confirm)
            areasDialog.dismiss();
    }


    @Override
    public void showAddShopCartAnim(@Size(2) int[] startLocation) {

        if (valueAnimator != null && valueAnimator.isRunning())
            return;
        if (endLocation[0] == 0)
            binding.shopCartView.getLocationOnScreen(endLocation);

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
        }, new PointF(startLocation[0], startLocation[1]), new PointF(endLocation[0], endLocation[1]));
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

    @Override
    public void navigateToProductDetail(View view, ProductSimple productSimple, int position) {
        navigate.navigateToProductDetailActivity(this, null, false, productSimple.getMainId());
    }

    @Override
    public void navigateToShopCart(View view, Module module) {
        navigate.navigateToShopCart(this, null, false, module.getWareHouseModuleId());
    }

    @Override
    public void navigateToLogin(View view) {
        navigate.navigateToLogin(this, null, false);
    }


    @Override
    public void onScroll(int currentY, int maxY) {
        ViewHelper.setTranslationY(binding.head, (float) (currentY * 0.5));
        Logger.d("onScroll currentY " + currentY + " maxY " + maxY);
    }
}
