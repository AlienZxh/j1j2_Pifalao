package com.j1j2.pifalao.feature.productdetail;

import android.Manifest;
import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PointF;
import android.support.annotation.Size;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.j1j2.common.util.ScreenUtils;
import com.j1j2.data.model.ProductDetail;
import com.j1j2.data.model.ProductImg;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.databinding.ActivityProductdetailBinding;
import com.j1j2.pifalao.databinding.ViewShareboardBinding;
import com.j1j2.pifalao.feature.productdetail.di.ProductDetailComponent;
import com.j1j2.pifalao.feature.productdetail.di.ProductDetailModule;
import com.j1j2.pifalao.feature.productdetail.price.PoductDetailPriceFragment;
import com.j1j2.pifalao.feature.productdetail.record.ProductDetailRecordFragment;
import com.j1j2.pifalao.feature.productdetail.unit.ProductDetailUnitFragment;
import com.j1j2.pifalao.feature.show.ShowActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

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
 * Created by alienzxh on 16-3-16.
 */
@RequireBundler
public class ProductDetailActivity extends BaseActivity implements View.OnClickListener, ProductDetailUnitFragment.ProductDetailUnitFragmentListener, PoductDetailPriceFragment.PoductDetailPriceFragmentListener, ProductDetailRecordFragment.ProductDetailRecordFragmentListener {


    ActivityProductdetailBinding binding;

    @Arg
    int mainId;

    @Inject
    ProductDetailViewModel productDetailViewModel;


    PoductDetailPriceFragment productDetailPriceFragment;
    ProductDetailUnitFragment productDetailUnitFragment;

    ShopCart shopCart;

    ProductDetailComponent productDetailComponent;

    public int moduleType = -1;

    ValueAnimator valueAnimator;
    int[] endLocation = new int[2];
    int[] startocation = new int[2];

    ViewShareboardBinding dialogBinding;
    DialogPlus shareDialog;

    //可以将一下代码加到你的MainActivity中，或者在任意一个需要调用分享功能的activity当中
    String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS};


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_productdetail);
        binding.setProductDetailViewModel(productDetailViewModel);
    }

    @Override
    protected void initViews() {
        productDetailViewModel.queryProductDetail(mainId);
        productDetailViewModel.queryProductHasBeenCollected(mainId);
        //______________________________________________________________________________
        dialogBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_shareboard, null, false);
        shareDialog = DialogPlus.newDialog(this).setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setContentHolder(new ViewHolder(dialogBinding.getRoot()))
                .setContentBackgroundResource(R.color.colorWhite)
                .create();
        dialogBinding.cancelShareBtn.setOnClickListener(this);
        dialogBinding.weixinShareBtn.setOnClickListener(this);
        dialogBinding.weixinFriendShareBtn.setOnClickListener(this);
        dialogBinding.sinaShareBtn.setOnClickListener(this);
        dialogBinding.qqShareBtn.setOnClickListener(this);
        dialogBinding.qqZoneShareBtn.setOnClickListener(this);
        dialogBinding.copyShareBtn.setOnClickListener(this);
    }

    public void initBanner(List<ProductImg> productImgs) {
        binding.viewPager.setAdapter(new ProductImgCycleAdapter(productImgs));
        binding.viewPager.startAutoScroll(2000);
        binding.viewPager.setInterval(2000);
        binding.tab.setViewPager(binding.viewPager);
    }

    public void initPrice(ProductDetail productDetail) {
        productDetailPriceFragment = Bundler.poductDetailPriceFragment(productDetail.getProductUnits(), productDetail.getBaseUnit(), productDetail.getModuleType()).create();
        changeFragment(R.id.priceFragment, productDetailPriceFragment);
    }

    public void initUnitsSelect(ProductDetail productDetail) {
        productDetailUnitFragment = Bundler.productDetailUnitFragment(productDetail.getProductUnits(), productDetail.getBaseUnit(), productDetail.getModuleType()).create();
        changeFragment(R.id.unitFragment, productDetailUnitFragment);
    }

    public void initBottomViewPager(List<ProductImg> productImgs, int productId, ProductDetail productDetail) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Bundler.productDetailImgFragment(productImgs).create());
        fragments.add(Bundler.productDetailParamsFragment(productDetail).create());
        fragments.add(Bundler.productDetailRecordFragment(productId).create());
        ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter(getSupportFragmentManager(), fragments);
        binding.detailViewpager.setAdapter(productDetailAdapter);
        binding.detailTab.setViewPager(binding.detailViewpager);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        productDetailComponent = MainAplication.get(this).getAppComponent().plus(new ProductDetailModule(this));
        productDetailComponent.inject(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        binding.viewPager.stopAutoScroll();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator.removeAllUpdateListeners();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // start auto scroll when onResume
        binding.viewPager.startAutoScroll();
    }

    public void addShopCart(ProductUnit unit, int Quantity) {
        shopCart.addUnitWitQuantity(unit, Quantity);
    }

    public void showAddShopCartAnim(@Size(2) int[] startLocation) {

        if (valueAnimator != null && valueAnimator.isRunning())
            return;
        if (endLocation[0] == 0)
            binding.shopCartBtn.getLocationOnScreen(endLocation);

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
        }, new PointF(startLocation[0] + ScreenUtils.dpToPx(180), startLocation[1] + ScreenUtils.dpToPx(180)), new PointF(endLocation[0], endLocation[1]));
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


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            shopCart = MainAplication.get(this).getUserComponent().shopCart();
            binding.setShopCart(shopCart);
        } else {
            if (shopCart != null)
                shopCart.clear();
        }
    }

    public void navigateToServicepointShow(View v) {
        navigate.navigateToShow(this, null, false, ShowActivity.SERVICEPOINT);
    }

    public void navigateToStorShow(View v) {
        navigate.navigateToShow(this, null, false, ShowActivity.STORE);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn) {
            onBackPressed();
            return;
        }

        if (v == binding.shareBtn) {
            ActivityCompat.requestPermissions(this, mPermissionList, 100);
            shareDialog.show();
            return;
        }
        if (v == dialogBinding.cancelShareBtn) {
            shareDialog.dismiss();
            return;
        }
        ProductDetail productDetail = productDetailViewModel.productDetail.get();
        if (productDetail != null) {
            UMImage image = new UMImage(this, productDetail.getMainImg());
            String title = productDetail.getName();
            String text = "会员价：" + productDetail.getProductUnits().get(0).getMemberPrice() + "/" + productDetail.getProductUnits().get(0).getUnit()
                    + "\n" + "我在批发佬发现了一个不错的商品，快来看看吧！";
            String url = "http://www.pifalao.com/home/share?"
                    + "shareType=" + Constant.ShareType.PRODUCT
                    + "&productMainId=" + productDetail.getMainId()
                    + "&moduleId=" + productDetail.getModuleId();
            if (v == dialogBinding.weixinShareBtn) {
                new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                        .withText(text)
                        .withTitle(title)
                        .withMedia(image)
                        .withTargetUrl(url)
                        .share();
                shareDialog.dismiss();
                return;
            }
            if (v == dialogBinding.weixinFriendShareBtn) {
                new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                        .withText(text)
                        .withTitle(title)
                        .withMedia(image)
                        .withTargetUrl(url)
                        .share();
                shareDialog.dismiss();
                return;
            }
            if (v == dialogBinding.sinaShareBtn) {
                text = productDetail.getName() + "　会员价：" + productDetail.getProductUnits().get(0).getMemberPrice() + "/" + productDetail.getProductUnits().get(0).getUnit()
                        + "\n" + "我在批发佬发现了一个不错的商品，快来看看吧！";
                new ShareAction(this).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                        .withText(text)
                        .withTitle(title)
                        .withMedia(image)
                        .withTargetUrl(url)
                        .share();
                shareDialog.dismiss();
                return;
            }

            if (v == dialogBinding.qqShareBtn) {
                new ShareAction(this).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                        .withText(text)
                        .withTitle(title)
                        .withMedia(image)
                        .withTargetUrl(url)
                        .share();
                shareDialog.dismiss();
                return;
            }
            if (v == dialogBinding.qqZoneShareBtn) {
                new ShareAction(this).setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener)
                        .withText(text)
                        .withTitle(title)
                        .withMedia(image)
                        .withTargetUrl(url)
                        .share();
                shareDialog.dismiss();
                return;
            }
            if (v == dialogBinding.copyShareBtn) {
                ClipboardManager c = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData cd = ClipData.newPlainText("shareUrl", productDetail.getName() + "\n" + text + "\n" + url);
                c.setPrimaryClip(cd);
                toastor.showSingletonToast(" 复制成功啦");
                shareDialog.dismiss();
                return;
            }
        }


        if (MainAplication.get(this).isLogin()) {

            if (v == binding.collectBtn)
                productDetailViewModel.clooectBtnClick(mainId);
            if (v == binding.addBtn)
                if (productDetailViewModel.productDetail.get() != null && productDetailViewModel.selectUnit.get() != null && moduleType != -1) {
                    productDetailViewModel.addItemToShopCart(productDetailViewModel.selectUnit.get(), productDetailUnitFragment.getQuantity(), productDetailViewModel.productDetail.get().getModuleId());

                    binding.viewPager.getLocationOnScreen(startocation);
                    showAddShopCartAnim(startocation);
                }
            if (v == binding.shopCartBtn) {
                if (productDetailViewModel.productDetail.get() != null)
                    navigate.navigateToShopCart(this, null, false, productDetailViewModel.productDetail.get().getModuleId());
            }

        } else {
            navigate.navigateToLogin(this, null, false);
        }

    }


    @Override
    public ProductDetailComponent getComponent() {
        return productDetailComponent;
    }

    @Override
    public void setSelectUnit(ProductUnit unit) {
        productDetailPriceFragment.setSelectUnit(unit);
        productDetailViewModel.selectUnit.set(unit);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Logger.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                toastor.showSingletonToast(" 收藏成功啦");

            } else {
                toastor.showSingletonToast(" 分享成功啦");

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            toastor.showSingletonToast(" 分享失败啦");

            if (t != null) {
                Logger.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            toastor.showSingletonToast(" 分享取消了");

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Logger.d("result", "onActivityResult");
    }
}
