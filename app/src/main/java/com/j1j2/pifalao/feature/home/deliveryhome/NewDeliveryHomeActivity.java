package com.j1j2.pifalao.feature.home.deliveryhome;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.PointF;
import android.support.annotation.Size;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.Product;
import com.j1j2.data.model.ShopExpressConfig;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.dialog.DeliveryAreasDialogFragment;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.service.BackGroundService;
import com.j1j2.pifalao.app.sharedpreferences.FreightTypePrefrence;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityDeliveryhomeNewBinding;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.DeliveryHomeProductsFragment;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.DeliveryHomeServicepointFragment;
import com.j1j2.pifalao.feature.home.deliveryhome.di.DeliveryHomeModule;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-10-14.
 */
@RequireBundler
public class NewDeliveryHomeActivity extends BaseActivity implements View.OnClickListener,
        DeliveryHomeProductsFragment.DeliveryHomeProductsFragmentListener,
        DeliveryAreasDialogFragment.DeliveryAreasDialogFragmentListener,
        DeliveryHomeServicepointFragment.DeliveryHomeServicepointFragmentListener,
        AppBarLayout.OnOffsetChangedListener {

    ActivityDeliveryhomeNewBinding binding;

    @Arg
    int shopId;

    @Arg
    public ShopSubscribeService shopSubscribeService;

    @Inject
    ShopCartApi shopCartApi;

    @Inject
    ServicePointApi servicePointApi;

    @Inject
    FreightTypePrefrence freightTypePrefrence;

    @Inject
    UserRelativePreference userRelativePreference;

    ShopCart shopCart = null;

    DeliveryHomeServicepointFragment deliveryHomeServicepointFragment;

    ValueAnimator valueAnimator;
    int[] endLocation = new int[2];

    UnReadInfoManager unReadInfoManager = null;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new DeliveryHomeModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deliveryhome_new);
        binding.setActivity(this);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setSupportActionBar(binding.toolbar);
    }

    @Override
    protected void initViews() {
        queryShopExpressConfig(shopId);
    }


    public void queryShopExpressConfig(int shopId) {
        servicePointApi.queryShopExpressConfig(shopId)
                .compose(this.<WebReturn<ShopExpressConfig>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<ShopExpressConfig>() {
                    @Override
                    public void onWebReturnSucess(ShopExpressConfig shopExpressConfig) {
                        binding.setShopExpressConfig(shopExpressConfig);

                        deliveryHomeServicepointFragment = new DeliveryHomeServicepointFragment();
                        final List<Fragment> fragments = new ArrayList<>();
                        fragments.add(Bundler.deliveryHomeProductsFragment(shopSubscribeService).create());
                        fragments.add(deliveryHomeServicepointFragment);
                        String[] titles = {"商品", "商家"};
                        binding.viewpager.setAdapter(new DeliveryHomeTabAdapter(getSupportFragmentManager(), fragments, titles));
                        binding.tab.setViewPager(binding.viewpager);

                        if (userRelativePreference.getShowDeliveryArea(true)) {
                            new DeliveryAreasDialogFragment().show(getSupportFragmentManager(), "DELIVERYAREASDIALOG");
                            userRelativePreference.setShowDeliveryArea(false);
                        }
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
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.appbarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.appbarLayout.removeOnOffsetChangedListener(this);
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
    public String[] getServiceAreas() {
        return binding.getShopExpressConfig().getServiceAreasArray();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.shopCartView) {
            if (MainAplication.get(this).isLogin())
                navigateToShopCart(v, shopSubscribeService);
            else
                navigateToLogin(v);
        }
        if (v == binding.individualBtn)
            if (MainAplication.get(this).isLogin())
                navigate.navigateToIndividualCenter(this, null, false);
            else
                navigateToLogin(v);
        if (v == binding.searchview)
            navigate.navigateToSearchActivity(this, null, false, shopSubscribeService);
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
    public void navigateToProductDetail(View view, Product productSimple, int position) {
//        navigate.navigateToProductDetailActivity(this, null, false, productSimple.getMainId());
    }

    @Override
    public void navigateToShopCart(View view, ShopSubscribeService shopSubscribeService) {
        navigate.navigateToShopCart(this, null, false, shopSubscribeService.getServiceId());
    }

    @Override
    public void navigateToLogin(View view) {
        navigate.navigateToLogin(this, null, false);
    }

    @Override
    public ShopExpressConfig getShopExpressConfig() {
        return binding.getShopExpressConfig();
    }
}
