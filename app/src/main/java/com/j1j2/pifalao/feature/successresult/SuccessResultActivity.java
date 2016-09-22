package com.j1j2.pifalao.feature.successresult;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.NavigateToHomeEvent;
import com.j1j2.pifalao.databinding.ActivitySuccessresultBinding;
import com.j1j2.pifalao.feature.onlineorderpay.OnlineOrderPayActivity;
import com.j1j2.pifalao.feature.orderdetail.OrderDetailActivity;

import org.greenrobot.eventbus.EventBus;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;

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

    public ObservableField<String> toolbarTitle = new ObservableField<>();
    public ObservableField<String> message = new ObservableField<>();
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
                cancelBtnStr.set("返回首页");
                binding.cancelBtn.setVisibility(View.GONE);
                confirmBtnStr.set("查看订单");
                break;
            case FROM_UPDATEVIP:
                toolbarTitle.set("升级VIP成功");
                binding.successIcon.setTextColor(getResources().getColor(R.color.colorAccent));
                binding.successIcon.setText(getResources().getText(R.string.icon_service_vip));
                message.set("恭喜您成功升级12个月的VIP会员！");
                binding.messageText.setTextColor(0xffff9900);
                binding.cancelBtn.setVisibility(View.GONE);
                confirmBtnStr.set("马上去首页逛逛");
                break;
            case FROM_REGISTER:
                toolbarTitle.set("注册成功");
                message.set("恭喜您注册成功！");
                binding.cancelBtn.setVisibility(View.GONE);
                confirmBtnStr.set("完　成");
                break;
            case FROM_PRIZEORDER_LOTTERY:
                toolbarTitle.set("订单提交成功");
                message.set("您的订单提交成功！");
                cancelBtnStr.set("返回首页");
                confirmBtnStr.set("查看订单");
                binding.confirmBtn.setVisibility(View.GONE);
                break;
            case FROM_PRIZEORDER_EXCHANGE:
                toolbarTitle.set("订单提交成功");
                message.set("您的订单提交成功！");
                cancelBtnStr.set("返回首页");
                binding.cancelBtn.setVisibility(View.GONE);
                confirmBtnStr.set("查看订单");
                break;
        }
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
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
            }
        }
    }
}
