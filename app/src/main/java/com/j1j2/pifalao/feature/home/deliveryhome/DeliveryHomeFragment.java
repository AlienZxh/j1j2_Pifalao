package com.j1j2.pifalao.feature.home.deliveryhome;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Size;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.j1j2.common.view.scrollablelayout.ScrollableHelper;
import com.j1j2.common.view.scrollablelayout.ScrollableLayout;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.service.BackGroundService;
import com.j1j2.pifalao.app.sharedpreferences.FreightTypePrefrence;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.FragmentDeliveryhomeBinding;
import com.j1j2.pifalao.databinding.ViewDeliveryAreasBinding;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.DeliveryHomeProductsFragment;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.DeliveryHomeServicepointFragment;
import com.j1j2.pifalao.feature.home.deliveryhome.di.DeliveryHomeModule;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-8-25.
 */
public class DeliveryHomeFragment extends LazyFragment implements View.OnClickListener, DeliveryHomeProductsFragment.DeliveryHomeProductsFragmentListener, DeliveryAreasAdapter.OnAreaItemClickListener {


    FragmentDeliveryhomeBinding binding;

    @Inject
    FreightTypePrefrence freightTypePrefrence;
    @Inject
    UserRelativePreference userRelativePreference;
    @Inject
    ShopCartApi shopCartApi;
    @Inject
    Navigate navigate;

    Module module;
    ServicePoint servicePoint;

    ShopCart shopCart = null;

    DeliveryHomeServicepointFragment deliveryHomeServicepointFragment;

    ValueAnimator valueAnimator;
    int[] endLocation = new int[2];

    DialogPlus areasDialog;
    ViewDeliveryAreasBinding areasDialogBinding;

    UnReadInfoManager unReadInfoManager = null;

    @Override
    protected String getFragmentName() {
        return "DeliveryHomeFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deliveryhome, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
//___________________________________________________
        areasDialogBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.view_delivery_areas, null, false);
        areasDialogBinding.areasList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        areasDialogBinding.areasList.setAdapter(new DeliveryAreasAdapter(Arrays.asList(servicePoint.getServiceAreas() != null ? servicePoint.getServiceAreas().split("\\;") : new String[]{})));
        ((DeliveryAreasAdapter) areasDialogBinding.areasList.getAdapter()).setOnAreaItemClickListener(this);
        areasDialog = DialogPlus.newDialog(getContext())
                .setInAnimation(android.R.anim.fade_in)
                .setOutAnimation(android.R.anim.fade_out)
                .setCancelable(true)
                .setContentHolder(new ViewHolder(areasDialogBinding.getRoot()))
                .setContentBackgroundResource(R.color.colorTransparent)
                .setGravity(Gravity.CENTER)
                .create();
        areasDialogBinding.confirm.setOnClickListener(this);
        //____________________________________________________________________
        deliveryHomeServicepointFragment = Bundler.deliveryHomeServicepointFragment(servicePoint).create();
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(Bundler.deliveryHomeProductsFragment(module).create());
        fragments.add(deliveryHomeServicepointFragment);
        String[] titles = {"商品", "商家"};
        binding.idStickynavlayoutViewpager.setAdapter(new DeliveryHomeTabAdapter(getChildFragmentManager(), fragments, titles));

        binding.idStickynavlayoutIndicator.setViewPager(binding.idStickynavlayoutViewpager);
        binding.idStickynavlayoutIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //________________________________________________________________________________
        queryFreightType(module.getWareHouseModuleId());
        //______________________
        if (userRelativePreference.getShowDeliveryArea(true)) {
            areasDialog.show();
            userRelativePreference.setShowDeliveryArea(false);
        }

    }

    public void queryFreightType(int moduleId) {
        shopCartApi.queryValidFreight(moduleId)
                .compose(this.<WebReturn<List<FreightType>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<FreightType>>() {
                    @Override
                    public void onWebReturnSucess(List<FreightType> freightTypes) {

                        FreightType freightType = freightTypes.get(0);
                        freightTypePrefrence.setDeliveryFreightType(freightType);
                        binding.setFreightType(freightType);
                        deliveryHomeServicepointFragment.setFreightType(freightType);
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
        MainAplication.get(getActivity()).getAppComponent().plus(new DeliveryHomeModule((DeliveryHomeActivity) getActivity())).inject(this);
    }




    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator.removeAllUpdateListeners();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            shopCart = MainAplication.get(getActivity()).getUserComponent().shopCart();
            binding.setShopCart(shopCart);
            unReadInfoManager = MainAplication.get(getActivity()).getUserComponent().unReadInfoManager();
            binding.setUnReadInfoManager(unReadInfoManager);
            BackGroundService.updateUnRead(getActivity());
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

        if (v == binding.shopCartView) {
            if (MainAplication.get(getActivity()).isLogin())
                navigateToShopCart(v, module);
            else
                navigateToLogin(v);
        }
        if (v == binding.individualBtn)
            if (MainAplication.get(getActivity()).isLogin())
                navigate.navigateToIndividualCenter(getActivity(), null, false);
            else
                navigateToLogin(v);
        if (v == binding.searchview)
            navigate.navigateToSearchActivity(getActivity(), null, false, module);
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
        navigate.navigateToProductDetailActivity(getActivity(), null, false, productSimple.getMainId());
    }

    @Override
    public void navigateToShopCart(View view, Module module) {
        navigate.navigateToShopCart(getActivity(), null, false, module.getWareHouseModuleId());
    }

    @Override
    public void navigateToLogin(View view) {
        navigate.navigateToLogin(getActivity(), null, false);
    }


}
