package com.j1j2.pifalao.feature.prizedetail;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.ActivityProductImg;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.dialog.ShareDialogFragment;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityPrizedetailBinding;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailComponent;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailModule;
import com.orhanobut.logger.Logger;
import com.shizhefei.view.multitype.ItemBinderFactory;
import com.shizhefei.view.multitype.MultiTypeAdapter;
import com.shizhefei.view.multitype.provider.FragmentData;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-1.
 */
@RequireBundler
public class PrizeDetailActivity extends BaseActivity implements View.OnClickListener,
        PrizeDetailBottomFragment.PrizeDetailBottomFragmentListener,
        PrizeDetailCompleteFragment.PrizeDetailCompleteFragmentListener,
        PrizeDetailGiftRuleFragment.PrizeDetailGiftRuleFragmentListener,
        PrizeDetailSelectFragment.PrizeDetailSelectFragmentListener,
        PrizeDetailGiftSelectFragment.PrizeDetailGiftSelectFragmentListener,
        PrizeDetailTopFragment.PrizeDetailTopFragmentListener,
        PrizeDetailRecordFragment.PrizeDetailRecordFragmentListener,
        PrizeDetailImgsFragment.PrizeDetailImgsFragmentListener,
        ShareDialogFragment.ShareDialogFragmentListener {


    ActivityPrizedetailBinding binding;

    private MultiTypeAdapter<Object> multiTypeAdapter;

    PrizeDetailComponent prizeDetailComponent;

    @Arg
    int productId;
    @Arg
    @Required(false)
    ActivityWinPrize activityWinPrize;

    @Inject
    ActivityApi activityApi;
    @Inject
    UserLoginApi userLoginApi;
    @Inject
    UserRelativePreference userRelativePreference;

    ActivityProduct activityProduct;
    User user;
    List<String> numList;

    FragmentData selectFragment;
    FragmentData giftSelectFragment;

    UMImage image;
    String title;
    String text;
    String url;
    String sinaText;
    String clipText;
    //可以将一下代码加到你的MainActivity中，或者在任意一个需要调用分享功能的activity当中
    String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS};
    String[] randomShareText = new String[]{"快来抢，$领取", "抢疯啦！$抽取", "新店开业，$抢", "仅需$，抢占", "来真的！$夺取", "不花钱，$来抢"};

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        prizeDetailComponent = MainAplication.get(this).getUserComponent().plus(new PrizeDetailModule(this));
        prizeDetailComponent.inject(this);
        Bundler.inject(this);
    }

    @Override
    protected void initBinding() {
        Bundler.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prizedetail);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        binding.ruleBtn.setOnClickListener(this);
        //_______________________________________________________________________________________
        if (activityWinPrize != null) {
            queryActivityProductDetails();
        }
        //______________________________________________________________________________


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityWinPrize == null) {
            queryActivityProductDetails();
        }
    }

    private List<Object> loadData() {
        List<Object> uiList = new ArrayList<>();
        uiList.add(new FragmentData(PrizeDetailTopFragment.class, "PrizeDetailTopFragment"));

        if (activityWinPrize != null) {
            uiList.add(new FragmentData(PrizeDetailCompleteFragment.class, "PrizeDetailCompleteFragment"));
            uiList.add(new FragmentData(PrizeDetailRecordFragment.class, "PrizeDetailRecordFragment"));
        } else if (activityProduct.getSortType() == Constant.ActivitySortType.LOTTERY) {
            uiList.add(selectFragment = new FragmentData(PrizeDetailSelectFragment.class, "PrizeDetailSelectFragment"));
            uiList.add(new FragmentData(PrizeDetailRecordFragment.class, "PrizeDetailRecordFragment"));
        } else if (activityProduct.getSortType() == Constant.ActivitySortType.EXCHANGE) {
            uiList.add(new FragmentData(PrizeDetailGiftRuleFragment.class, "PrizeDetailGiftRuleFragment"));
            uiList.add(giftSelectFragment = new FragmentData(PrizeDetailGiftSelectFragment.class, "PrizeDetailGiftSelectFragment"));
        }

        View moreView = getLayoutInflater().inflate(R.layout.view_prizedetail_more, null, false);
        uiList.add(moreView);

        uiList.add(new FragmentData(PrizeDetailImgsFragment.class, "PrizeDetailImgsFragment"));

        View view = new View(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AutoUtils.getPercentHeightSize(120)));
        uiList.add(view);

        return uiList;
    }


    private void queryActivityProductDetails() {
        activityApi.queryActivityProductDetails(productId)
                .compose(this.<WebReturn<ActivityProduct>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<ActivityProduct>() {
                    @Override
                    public void onWebReturnSucess(ActivityProduct mActivityProduct) {
                        activityProduct = mActivityProduct;
                        if (activityWinPrize != null) {
                            binding.actionBarTitle.setText("奖品详情");
                        } else if (activityProduct.getSortType() == Constant.ActivitySortType.EXCHANGE) {
                            binding.actionBarTitle.setText("礼品详情");
                            binding.ruleBtn.setVisibility(View.GONE);
                        } else {
                            binding.actionBarTitle.setText("奖品详情");
                        }
                        //_____________________________________________
                        Module module = userRelativePreference.getSelectedModule(null);
                        Random random = new Random();

                        if (!EmptyUtils.isEmpty(activityProduct.getImgList()))
                            image = new UMImage(PrizeDetailActivity.this, BuildConfig.IMAGE_URL + activityProduct.getImgList().get(0).getS320X320());
                        else
                            image = new UMImage(PrizeDetailActivity.this, "");

                        String price = "";
                        if (activityProduct.getConfigs().getCostExchangePoint() != null)
                            price += activityProduct.getConfigs().getCostExchangePoint() + "积分";
                        if (activityProduct.getConfigs().getCostExchangeMoney() != null)
                            if (EmptyUtils.isEmpty(price))
                                price += activityProduct.getConfigs().getCostExchangeMoney() + "元";
                            else
                                price += "+" + activityProduct.getConfigs().getCostExchangeMoney() + "元";

                        title = randomShareText[random.nextInt(5)].replace("$", price) + activityProduct.getName();
                        text = "哇！在批发佬发现好东西，必须拿出来分享！";
                        url = "http://www.pifalao.com/home/share?"
                                + "shareType=" + Constant.ShareType.ACTIVITY
                                + "&activityId=" + activityProduct.getProductId()
                                + "&moduleId=" + module.getWareHouseModuleId();
                        sinaText = randomShareText[random.nextInt(5)].replace("$", price) + activityProduct.getName() + "　哇！在批发佬发现好东西，必须拿出来分享！";
                        clipText = randomShareText[random.nextInt(5)].replace("$", price) + activityProduct.getName() + "\n哇！在批发佬发现好东西，必须拿出来分享！\n";
                        //_____________________________________________________________
                        queryParticipationTimesDetails();

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryParticipationTimesDetails() {
        activityApi.queryParticipationTimesDetails(getLotteryId())
                .compose(this.<WebReturn<List<String>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<String>>() {
                    @Override
                    public void onWebReturnSucess(List<String> stringList) {
                        numList = stringList;
                        queryUser();
                        ItemBinderFactory itemBinderFactory = new ItemBinderFactory(getSupportFragmentManager());
                        multiTypeAdapter = new MultiTypeAdapter<>(loadData(), itemBinderFactory);
                        binding.MultiTypeView.setAdapter(multiTypeAdapter);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    private void queryUser() {
        userLoginApi.queryUserDetail()
                .compose(this.<WebReturn<User>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<User>() {
                    @Override
                    public void onWebReturnSucess(User mUser) {
                        user = mUser;
                        changeFragment(R.id.bottomFragment, Bundler.prizeDetailBottomFragment().create());
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
    public int getLotteryId() {
        if (activityWinPrize != null)
            return activityWinPrize.getLotteryId();
        else
            return activityProduct.getLotteryId();
    }

    @Override
    public void showToastor(String msg) {
        toastor.showSingletonToast(msg);
    }

    @Override
    public ActivityWinPrize getActivityWinPrize() {
        return activityWinPrize;
    }

    @Override
    public ActivityProduct getActivityProduct() {
        return activityProduct;
    }

    @Override
    public User getUser() {
        return user;
    }


    @Override
    public int getTimes() {
        return numList == null ? 0 : numList.size();
    }

    @Override
    public PrizeDetailComponent getComponent() {
        return prizeDetailComponent;
    }

    @Override
    public List<ActivityProductImg> getImgList() {
        if (activityWinPrize != null)
            return activityWinPrize.getProductInfo().getImgs();
        else
            return activityProduct.getImgList();
    }

    @Override
    public String getIntroduce() {
        if (activityWinPrize != null)
            return activityWinPrize.getProductInfo().getIntroduce();
        else
            return activityProduct.getIntroduce();
    }

    @Override
    public void navigateToPrizeConfirm() {
        if (MainAplication.get(this).isLogin()) {
            navigate.navigateToPrizeConfirmActivity(this, null, false,
                    activityProduct.getSortType() == Constant.ActivitySortType.EXCHANGE ?
                            ((PrizeDetailGiftSelectFragment) giftSelectFragment.getFragment()).getPrizeQuantity() : ((PrizeDetailSelectFragment) selectFragment.getFragment()).getPrizeQuantity()
                    , activityProduct
                    , activityProduct.getSortType() == Constant.ActivitySortType.EXCHANGE ?
                            Constant.ActivityOrderType.EXCHANGEORDER : Constant.ActivityOrderType.LOTTERYORDER);
        } else
            navigate.navigateToLogin(this, null, false);
    }

    @Override
    public void navigateToCalculateDetail() {
        if (activityWinPrize != null)
            navigate.navigateToCalculateDetailActivity(this, null, false, activityWinPrize.getLotteryId());
        else
            navigate.navigateToCalculateDetailActivity(this, null, false, activityProduct.getLotteryId());
    }

    @Override
    public void navigateToPrizeRecordActivity() {
        if (activityWinPrize != null)
            navigate.navigateToPrizeRecordActivity(this, null, false, activityWinPrize.getLotteryId());
        else
            navigate.navigateToPrizeRecordActivity(this, null, false, activityProduct.getLotteryId());
    }

    @Override
    public void backToMemberHome() {
        navigate.navigateToMemberHomeActivity(this, null, true);
    }

    @Override
    public void showCatNumDialog() {
        if (EmptyUtils.isEmpty(numList))
            return;
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("幸运号码")
                .setItems(numList.toArray(new String[numList.size()]), null)
                .setPositiveButton("知道了", null)
                .create().show();

    }

    @Override
    public void showShareDialog() {
        ActivityCompat.requestPermissions(this, mPermissionList, 100);
        new ShareDialogFragment().show(getSupportFragmentManager(), "SHAREDIALOG");
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn) {
            onBackPressed();
            return;
        }
        if (v == binding.ruleBtn) {
            navigate.navigateToLotteryRuleActivity(this, null, false);
            return;
        }
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

    @Override
    public void onWeixinShareBtnClick() {
        new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                .withText(text)
                .withTitle(title)
                .withMedia(image)
                .withTargetUrl(url)
                .share();
    }

    @Override
    public void onWeixinFriendShareBtnClick() {
        new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                .withText(text)
                .withTitle(title)
                .withMedia(image)
                .withTargetUrl(url)
                .share();
    }

    @Override
    public void onSinaShareBtnClick() {
        new ShareAction(this).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                .withText(sinaText)
                .withTitle(title)
                .withMedia(image)
                .withTargetUrl(url)
                .share();
    }

    @Override
    public void onQQShareBtnClick() {
        new ShareAction(this).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                .withText(text)
                .withTitle(title)
                .withMedia(image)
                .withTargetUrl(url)
                .share();
    }

    @Override
    public void onQQZoneShareBtnClick() {
        new ShareAction(this).setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener)
                .withText(text)
                .withTitle(title)
                .withMedia(image)
                .withTargetUrl(url)
                .share();
    }

    @Override
    public void onCopyShareBtnClick() {
        ClipboardManager c = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData cd = ClipData.newPlainText("shareUrl", clipText);
        c.setPrimaryClip(cd);
        toastor.showSingletonToast(" 复制成功啦");
    }
}
