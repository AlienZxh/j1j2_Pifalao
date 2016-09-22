package com.j1j2.pifalao.feature.prizedetail;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.util.Toastor;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.LotteryParticipationTimes;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.ActivityPrizedetailBinding;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailComponent;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailModule;
import com.shizhefei.view.multitype.ItemBinderFactory;
import com.shizhefei.view.multitype.MultiTypeAdapter;
import com.shizhefei.view.multitype.provider.FragmentData;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

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
        PrizeDetailTopFragment.PrizeDetailTopFragmentListener
        , PrizeDetailRecordFragment.PrizeDetailRecordFragmentListener {

    public static final int PRIZE_ONGOING = 0;
    public static final int PRIZE_COMPLETED = 1;
    public static final int GIFT = 2;

    ActivityPrizedetailBinding binding;

    private MultiTypeAdapter<Object> multiTypeAdapter;

    PrizeDetailComponent prizeDetailComponent;


    @Arg
    int activityType;
    @Arg
    @Required(false)
    int productId;
    @Arg
    @Required(false)
    ActivityWinPrize activityWinPrize;

    @Inject
    ActivityApi activityApi;
    @Inject
    UserLoginApi userLoginApi;

    ActivityProduct activityProduct;
    User user;

    FragmentData selectFragment;
    FragmentData giftSelectFragment;

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

        if (activityType == GIFT)
            binding.actionBarTitle.setText("礼品详情");
        else
            binding.actionBarTitle.setText("奖品详情");


        //_______________________________________________________________________________________
        if (activityType == PRIZE_ONGOING || activityType == GIFT) {
            queryActivityProductDetails();
        } else {
            queryUser();
            ItemBinderFactory itemBinderFactory = new ItemBinderFactory(getSupportFragmentManager());
            multiTypeAdapter = new MultiTypeAdapter<>(loadData(), itemBinderFactory);
            binding.MultiTypeView.setAdapter(multiTypeAdapter);
        }
        //_______________________________________________________________

    }

    private List<Object> loadData() {
        List<Object> uiList = new ArrayList<>();
        uiList.add(new FragmentData(PrizeDetailTopFragment.class, "PrizeDetailTopFragment"));

        if (activityType == GIFT) {
            uiList.add(new FragmentData(PrizeDetailGiftRuleFragment.class, "PrizeDetailGiftRuleFragment"));
            uiList.add(giftSelectFragment = new FragmentData(PrizeDetailGiftSelectFragment.class, "PrizeDetailGiftSelectFragment"));
        } else if (activityType == PRIZE_ONGOING) {
            uiList.add(selectFragment = new FragmentData(PrizeDetailSelectFragment.class, "PrizeDetailSelectFragment"));
            uiList.add(new FragmentData(PrizeDetailRecordFragment.class, "PrizeDetailRecordFragment"));
        } else if (activityType == PRIZE_COMPLETED) {
            uiList.add(new FragmentData(PrizeDetailCompleteFragment.class, "PrizeDetailCompleteFragment"));
            uiList.add(new FragmentData(PrizeDetailRecordFragment.class, "PrizeDetailRecordFragment"));
        }

        View moreView = getLayoutInflater().inflate(R.layout.view_prizedetail_more, null, false);
        uiList.add(moreView);

        View view = new View(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AutoUtils.getPercentHeightSize(120)));
        uiList.add(view);

        return uiList;
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
                        changeFragment(R.id.bottomFragment, Bundler.prizeDetailBottomFragment(activityType).create());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
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

    @Override
    public int getLotteryId() {
        if (activityType == PRIZE_COMPLETED)
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
    public int getActivityType() {
        return activityType;
    }

    @Override
    public PrizeDetailComponent getComponent() {
        return prizeDetailComponent;
    }

    @Override
    public void navigateToPrizeConfirm() {
        if (MainAplication.get(this).isLogin()) {
            navigate.navigateToPrizeConfirmActivity(this, null, false,
                    activityType == GIFT ?
                            ((PrizeDetailGiftSelectFragment) giftSelectFragment.getFragment()).getPrizeQuantity() : ((PrizeDetailSelectFragment) selectFragment.getFragment()).getPrizeQuantity()
                    , activityProduct
                    , activityType == GIFT ? Constant.ActivityOrderType.EXCHANGEORDER : Constant.ActivityOrderType.LOTTERYORDER);
        } else
            navigate.navigateToLogin(this, null, false);
    }

    @Override
    public void navigateToCalculateDetail() {
        if (activityType == PRIZE_COMPLETED)
            navigate.navigateToCalculateDetailActivity(this, null, false, activityWinPrize.getLotteryId());
        else
            navigate.navigateToCalculateDetailActivity(this, null, false, activityProduct.getLotteryId());
    }

    @Override
    public void navigateToPrizeRecordActivity() {
        if (activityType == PRIZE_COMPLETED)
            navigate.navigateToPrizeRecordActivity(this, null, false, activityWinPrize.getLotteryId());
        else
            navigate.navigateToPrizeRecordActivity(this, null, false, activityProduct.getLotteryId());
    }

    @Override
    public void showCatNumDialog(List<String> stringList) {

        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("幸运号码")
                .setItems(stringList.toArray(new String[stringList.size()]), null)
                .setPositiveButton("知道了", null)
                .create();
        messageDialog.show();

    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }
}
