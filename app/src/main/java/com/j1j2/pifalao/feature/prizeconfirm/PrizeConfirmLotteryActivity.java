package com.j1j2.pifalao.feature.prizeconfirm;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.http.api.ActivityShopCartApi;
import com.j1j2.data.model.ActivityProcessStateProductInfo;
import com.j1j2.data.model.LotteryFillAwardReceiveAddress;
import com.j1j2.data.model.SubmitOrderReturn;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ActivityOrderSubmitBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.PrizeOrderStateChangeEvent;
import com.j1j2.pifalao.databinding.ActivityPrizeconfirmLotteryBinding;
import com.j1j2.pifalao.feature.prizeconfirm.di.PrizeConfirmModule;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-18.
 */
@RequireBundler
public class PrizeConfirmLotteryActivity extends BaseActivity implements View.OnClickListener, PrizeConfirmInfoMaterialFragment.PrizeConfirmInfoMaterialFragmentListener {

    ActivityPrizeconfirmLotteryBinding binding;

    @Inject
    ActivityApi activityApi;

    @Arg
    ActivityProcessStateProductInfo activityProcessStateProductInfo;
    @Arg
    int activityProductType;
    @Arg
    int orderId;
    @Arg
    String orderNo;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prizeconfirm_lottery);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        binding.confirmOrder.setOnClickListener(this);

        binding.setActivityProduct(activityProcessStateProductInfo);
        binding.setOrderNo(orderNo);

        if (activityProductType == Constant.ActivityProductType.VirtualPhoneCharge)
            changeFragment(R.id.infomationFragment, phoneFragment = new PrizeConfirmInfoPhoneFragment());
        if (activityProductType == Constant.ActivityProductType.Material)
            changeFragment(R.id.infomationFragment, materialFragment = new PrizeConfirmInfoMaterialFragment());
    }

    public void commitOrder() {

        showProgress("数据提交中");
        LotteryFillAwardReceiveAddress lotteryFillAwardReceiveAddress = new LotteryFillAwardReceiveAddress();
        lotteryFillAwardReceiveAddress.setOrderId(orderId);
        if (phoneFragment != null)
            lotteryFillAwardReceiveAddress.setMobile(phoneFragment.getPhoneNO());
        if (materialFragment != null) {
            lotteryFillAwardReceiveAddress.setAddress(materialFragment.getAddress().getAddress());
            lotteryFillAwardReceiveAddress.setMobile(materialFragment.getAddress().getReceiverTel());
            lotteryFillAwardReceiveAddress.setName(materialFragment.getAddress().getReceiverName());
        }
        activityApi.fillAwardRecevieAddress(lotteryFillAwardReceiveAddress)
                .compose(this.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {

                    @Override
                    public void onWebReturnSucess(String str) {
                        EventBus.getDefault().post(new PrizeOrderStateChangeEvent());
                        dismissProgress();
                        toastor.showSingletonToast(str);
                        finish();
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
