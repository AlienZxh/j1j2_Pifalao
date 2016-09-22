package com.j1j2.pifalao.feature.prizeconfirm;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.data.http.api.ActivityShopCartApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.ActivityOrderSimple;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.SubmitOrderReturn;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ActivityOrderSubmitBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.NavigateToHomeEvent;
import com.j1j2.pifalao.app.event.PrizeOrderStateChangeEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityPrizeconfirmBinding;
import com.j1j2.pifalao.feature.prizeconfirm.di.PrizeConfirmModule;
import com.j1j2.pifalao.feature.successresult.SuccessResultActivity;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-1.
 */
@RequireBundler
public class PrizeConfirmActivity extends BaseActivity implements View.OnClickListener, PrizeConfirmInfoMaterialFragment.PrizeConfirmInfoMaterialFragmentListener {


    ActivityPrizeconfirmBinding binding;

    @Arg
    ActivityProduct activityProduct;

    @Arg
    int prizeQuantity;

    @Arg
    int prizeType;

    @Inject
    UserLoginApi userLoginApi;
    @Inject
    ActivityShopCartApi activityShopCartApi;
    @Inject
    UserRelativePreference userRelativePreference;

    Module module;

    PrizeConfirmInfoPhoneFragment phoneFragment;
    PrizeConfirmInfoMaterialFragment materialFragment;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new PrizeConfirmModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prizeconfirm);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        binding.confirmOrder.setOnClickListener(this);

        binding.setActivityProduct(activityProduct);
        binding.setPrizeQuantity(prizeQuantity);

        module = userRelativePreference.getSelectedModule(null);

        if (prizeType == Constant.ActivityOrderType.EXCHANGEORDER) {
            if (activityProduct.getType() == Constant.ActivityProductType.VirtualPhoneCharge)
                changeFragment(R.id.infomationFragment, phoneFragment = new PrizeConfirmInfoPhoneFragment());
            if (activityProduct.getType() == Constant.ActivityProductType.Material)
                changeFragment(R.id.infomationFragment, materialFragment = new PrizeConfirmInfoMaterialFragment());
        }


        queryUser();
    }

    private void queryUser() {
        userLoginApi.queryUserDetail()
                .compose(this.<WebReturn<User>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<User>() {
                    @Override
                    public void onWebReturnSucess(User user) {
                        binding.setUser(user);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void commitOrder() {
        showProgress("订单提交中");
        ActivityOrderSubmitBody activityOrderSubmitBody = new ActivityOrderSubmitBody();
        activityOrderSubmitBody.setProductId(activityProduct.getProductId());
        activityOrderSubmitBody.setQuantity(prizeQuantity);
        activityOrderSubmitBody.setModuleId(module.getWareHouseModuleId());
        if (phoneFragment != null)
            activityOrderSubmitBody.setUserPhone(phoneFragment.getPhoneNO());
        if (materialFragment != null) {
            activityOrderSubmitBody.setAddress(materialFragment.getAddress().getAddress());
            activityOrderSubmitBody.setUserContacter(materialFragment.getAddress().getReceiverName());
        }
        activityShopCartApi.submitActivityOrders(activityOrderSubmitBody)
                .compose(this.<WebReturn<SubmitOrderReturn>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<SubmitOrderReturn>() {

                    @Override
                    public void onWebReturnSucess(SubmitOrderReturn submitOrderReturn) {

                        queryActivityOrderSimple(submitOrderReturn);

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        dismissProgress();
                        toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    public void queryActivityOrderSimple(final SubmitOrderReturn submitOrderReturn) {
        activityShopCartApi.queryActivityOrder(submitOrderReturn.getOrderNO())
                .compose(this.<WebReturn<ActivityOrderSimple>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<ActivityOrderSimple>() {
                    @Override
                    public void onWebReturnSucess(ActivityOrderSimple mActivityOrderSimple) {
                        dismissProgress();
                        if (mActivityOrderSimple.getSpendMoney() > 0)
                            navigate.navigateToOnlineOrderPay(PrizeConfirmActivity.this, null, true,
                                    submitOrderReturn.getOrderId(), submitOrderReturn.getOrderNO(), false, true);
                        else
                            navigate.navigateToSuccessResult(PrizeConfirmActivity.this, null, true
                                    , prizeType == Constant.ActivityOrderType.LOTTERYORDER ? SuccessResultActivity.FROM_PRIZEORDER_LOTTERY : SuccessResultActivity.FROM_PRIZEORDER_EXCHANGE
                                    , mActivityOrderSimple.getOrderId());
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        dismissProgress();
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.confirmOrder) {
            if (phoneFragment != null) {
                if (TextUtils.isEmpty(phoneFragment.getPhoneNO())) {
                    toastor.showSingletonToast("手机号码不能为空");
                    return;
                }
            }
            if (materialFragment != null) {
                if (EmptyUtils.isEmpty(materialFragment.getAddress())) {
                    toastor.showSingletonToast("收货人不能为空");
                    return;
                }
            }
            commitOrder();
        }

    }



    @Override
    public void navigateToAddressManager() {
        navigate.navigateToAddressManager(this, null, false, true);
    }
}
