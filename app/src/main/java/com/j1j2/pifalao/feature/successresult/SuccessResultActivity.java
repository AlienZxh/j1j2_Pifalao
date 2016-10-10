package com.j1j2.pifalao.feature.successresult;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.view.View;

import com.j1j2.data.http.api.ActivityShopCartApi;
import com.j1j2.data.model.ActivityOrderSimple;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.NavigateToHomeEvent;
import com.j1j2.pifalao.databinding.ActivitySuccessresultBinding;
import com.j1j2.pifalao.feature.onlineorderpay.OnlineOrderPayActivity;
import com.j1j2.pifalao.feature.orderdetail.OrderDetailActivity;
import com.j1j2.pifalao.feature.participationrecord.ParticipationRecordActivity;
import com.j1j2.pifalao.feature.prizeconfirm.di.PrizeConfirmModule;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-27.
 */
@RequireBundler
public class SuccessResultActivity extends BaseActivity implements View.OnClickListener {

    public static final int FROM_CONFIRMORDER = 0;
    public static final int FROM_UPDATEVIP = 1;
    public static final int FROM_REGISTER = 2;
    public static final int FROM_PRIZEORDER_LOTTERY = 3;
    public static final int FROM_PRIZEORDER_EXCHANGE = 4;

    ActivitySuccessresultBinding binding;

    @Arg
    int activityType;

    @Arg
    @Required(false)
    int orderId;

    @Arg
    @Required(false)
    String orderNO;

    @Inject
    ActivityShopCartApi activityShopCartApi;

    public ObservableField<String> toolbarTitle = new ObservableField<>();
    public ObservableField<String> message = new ObservableField<>();
    public ObservableField<String> info = new ObservableField<>();
    public ObservableField<String> cancelBtnStr = new ObservableField<>();
    public ObservableField<String> confirmBtnStr = new ObservableField<>();

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_successresult);
        binding.setSuccessResultActivity(this);
    }

    @Override
    protected void initViews() {
        switch (activityType) {
            case FROM_CONFIRMORDER:
                toolbarTitle.set("订单提交成功");
                message.set("您的订单提交成功！");
                confirmBtnStr.set("查看订单");

                binding.successIcon.setVisibility(View.VISIBLE);
                binding.messageText.setVisibility(View.VISIBLE);
                binding.confirmBtn.setVisibility(View.VISIBLE);

                break;
            case FROM_UPDATEVIP:
                toolbarTitle.set("升级VIP成功");
                binding.successIcon.setTextColor(getResources().getColor(R.color.colorAccent));
                binding.successIcon.setText(getResources().getText(R.string.icon_service_vip));
                message.set("恭喜您成功升级12个月的VIP会员！");
                binding.messageText.setTextColor(0xffff9900);
                confirmBtnStr.set("马上去首页逛逛");

                binding.successIcon.setVisibility(View.VISIBLE);
                binding.messageText.setVisibility(View.VISIBLE);
                binding.confirmBtn.setVisibility(View.VISIBLE);
                break;
            case FROM_REGISTER:
                toolbarTitle.set("注册成功");
                message.set("恭喜您注册成功！");
                confirmBtnStr.set("完　成");

                binding.successIcon.setVisibility(View.VISIBLE);
                binding.messageText.setVisibility(View.VISIBLE);
                binding.confirmBtn.setVisibility(View.VISIBLE);
                break;
            case FROM_PRIZEORDER_LOTTERY:
                toolbarTitle.set("订单结果");
                queryActivityOrderSimple();
                break;
            case FROM_PRIZEORDER_EXCHANGE:
                toolbarTitle.set("订单结果");
                queryActivityOrderSimple();
                break;
        }
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new PrizeConfirmModule(this)).inject(this);
    }


    public void queryActivityOrderSimple() {
        activityShopCartApi.queryActivityOrder(orderNO)
                .compose(this.<WebReturn<ActivityOrderSimple>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<ActivityOrderSimple>() {
                    @Override
                    public void onWebReturnSucess(ActivityOrderSimple mActivityOrderSimple) {
                        if (mActivityOrderSimple.getActivityOrderType() == Constant.ActivityOrderType.LOTTERYORDER) {
                            if (mActivityOrderSimple.isDispatchTicketFailed()) {
                                binding.successIcon.setText(getResources().getText(R.string.icon_close_fill));
                                binding.successIcon.setTextColor(0xffff0000);
                                message.set("出票失败，本期已被人抢光了！");
                                binding.messageText.setTextColor(0xffff0000);
                                info.set("该订单所支付的金额已退还到您的账户余额");
                                binding.infoText.setTextColor(0xffff0000);
                                cancelBtnStr.set("前去参与下一期抽奖");


                                binding.successIcon.setVisibility(View.VISIBLE);
                                binding.messageText.setVisibility(View.VISIBLE);
                                binding.infoText.setVisibility(View.VISIBLE);
                                binding.cancelBtn.setVisibility(View.VISIBLE);
                            } else {
                                message.set("您的订单提交成功！");
                                info.set("请耐心等待开奖结果");
                                confirmBtnStr.set("查看活动");

                                binding.successIcon.setVisibility(View.VISIBLE);
                                binding.messageText.setVisibility(View.VISIBLE);
                                binding.infoText.setVisibility(View.VISIBLE);
                                binding.confirmBtn.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (mActivityOrderSimple.isDispatchTicketFailed()) {
                                binding.successIcon.setText(getResources().getText(R.string.icon_close_fill));
                                binding.successIcon.setTextColor(0xffff0000);
                                message.set("下手太慢，奖品已被人抢光了！");
                                binding.messageText.setTextColor(0xffff0000);
                                info.set("该订单所支付的金额已退还到您的账户余额");
                                binding.infoText.setTextColor(0xffff0000);
                                cancelBtnStr.set("返回首页");


                                binding.successIcon.setVisibility(View.VISIBLE);
                                binding.messageText.setVisibility(View.VISIBLE);
                                binding.infoText.setVisibility(View.VISIBLE);
                                binding.cancelBtn.setVisibility(View.VISIBLE);
                            } else {
                                message.set("您的订单提交成功！");
                                confirmBtnStr.set("查看订单");

                                binding.successIcon.setVisibility(View.VISIBLE);
                                binding.messageText.setVisibility(View.VISIBLE);
                                binding.confirmBtn.setVisibility(View.VISIBLE);
                            }
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
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();

        if (v == binding.cancelBtn) {
            switch (activityType) {
                case FROM_CONFIRMORDER:
                    EventBus.getDefault().post(new NavigateToHomeEvent());
                    finish();
                    break;
                case FROM_PRIZEORDER_EXCHANGE:
                    navigate.navigateToMemberHomeActivity(this, null, true);
                    break;
                case FROM_PRIZEORDER_LOTTERY:
                    navigate.navigateToMemberHomeActivity(this, null, true);
                    break;
            }
        }
        if (v == binding.confirmBtn) {
            switch (activityType) {
                case FROM_CONFIRMORDER:
                    navigate.navigateToOrderDetail(this, null, true, null, orderId, OrderDetailActivity.TIMELINE);
                    break;
                case FROM_REGISTER:
                    EventBus.getDefault().post(new NavigateToHomeEvent());
                    finish();
                    break;
                case FROM_UPDATEVIP:
                    EventBus.getDefault().post(new NavigateToHomeEvent());
                    finish();
                    break;
                case FROM_PRIZEORDER_EXCHANGE:
                    navigate.navigateToPrizeOrderTimelineActivity(this, null, true, orderId);
                    break;
                case FROM_PRIZEORDER_LOTTERY:
                    navigate.navigateToParticipationRecordActivity(this, null, true, ParticipationRecordActivity.RECORD_ONGOING);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (activityType == FROM_PRIZEORDER_LOTTERY || activityType == FROM_PRIZEORDER_EXCHANGE)
            navigate.navigateToMemberHomeActivity(this, null, true);
        if (activityType == FROM_CONFIRMORDER)
            navigate.navigateToOrderDetail(this, null, true, null, orderId, OrderDetailActivity.TIMELINE);
        else
            super.onBackPressed();
    }
}
