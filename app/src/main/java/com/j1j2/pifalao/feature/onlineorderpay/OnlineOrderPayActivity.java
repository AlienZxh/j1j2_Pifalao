package com.j1j2.pifalao.feature.onlineorderpay;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableDouble;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import com.j1j2.data.http.api.ActivityShopCartApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.ActivityOrderSimple;
import com.j1j2.data.model.OnlinePayResult;
import com.j1j2.data.model.OrderDetail;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.WeiXinPayResult;
import com.j1j2.data.model.requestbody.OrderOnlinePayBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.OrderStateChangeEvent;
import com.j1j2.pifalao.app.event.PrizeOrderStateChangeEvent;
import com.j1j2.pifalao.databinding.ActivityOnlineorderpayBinding;
import com.j1j2.pifalao.feature.onlineorderpay.di.OnlineOrderPayModule;
import com.j1j2.pifalao.feature.orderdetail.OrderDetailActivity;
import com.j1j2.pifalao.feature.successresult.SuccessResultActivity;
import com.j1j2.pifalao.pay.alipay.Alipay;
import com.j1j2.pifalao.pay.weixin.WXPay;
import com.trello.rxlifecycle.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-5-7.
 */
@RequireBundler
public class OnlineOrderPayActivity extends BaseActivity implements View.OnClickListener {

    ActivityOnlineorderpayBinding binding;

    @Arg
    int orderId;
    @Arg
    String orderNO;
    @Arg
    boolean fromOrderDetail;
    @Arg
    boolean isActivityPay;

    @Inject
    ShopCartApi shopCartApi;
    @Inject
    ActivityShopCartApi activityShopCartApi;

    @Inject
    UserOrderApi userOrderApi;
    @Inject
    UserLoginApi userLoginApi;
    @Inject
    Navigate navigate;

    OrderDetail orderDetail;
    ActivityOrderSimple activityOrderSimple;

    ObservableDouble balance = new ObservableDouble(0);
    boolean canUseBalance = false;
    boolean canUseOnline = true;

    long remian;

    String exitDialogTag = "EXITDIALOG";

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onlineorderpay);
        binding.setOnClickListener(this);
        binding.setBalance(balance);

    }

    @Override
    protected void initViews() {
        binding.useBalance.setChecked(true);
        binding.useAli.setChecked(false);
        binding.useWeiXin.setChecked(false);

        if (isActivityPay) {
            binding.arrow.setVisibility(View.INVISIBLE);
            queryActivityOrderSimple(orderNO);
        } else
            queryOrderSimple(orderId);
    }

    public void doPayOrder(OrderOnlinePayBody orderOnlinePayBody) {
        showProgress("支付中，请稍后...");

        shopCartApi.doPayOrder(orderOnlinePayBody)
                .compose(this.<WebReturn<OnlinePayResult>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<OnlinePayResult>() {
                    @Override
                    public void onWebReturnSucess(OnlinePayResult onlinePayResult) {
                        if (onlinePayResult.isUseBalance()) {
                            dismissProgress();
                            EventBus.getDefault().post(new OrderStateChangeEvent(false, Constant.OrderType.ORDERTYPE_UNPAY, Constant.OrderType.ORDERTYPE_SUBMIT));
                            navigateToNext();
                        } else if (onlinePayResult.isUseAliPay()) {
                            doAlipay(onlinePayResult.getAliPayResult());
                        } else if (onlinePayResult.isUseWeiXinPay()) {
                            doWXPay(onlinePayResult.getWeiXinPayResult());
                        }
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        dismissProgress();
                        toastor.showSingletonToast(errorMessage);

                    }

                    @Override
                    public void onWebReturnCompleted() {
                        binding.payBtn.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissProgress();
                        toastor.showSingletonToast("连接失败，请重试");
                    }
                });
    }

    public void doActivityPayOrder(OrderOnlinePayBody orderOnlinePayBody) {
        showProgress("支付中，请稍后...");

        activityShopCartApi.payAcitityOrder(orderOnlinePayBody)
                .compose(this.<WebReturn<OnlinePayResult>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<OnlinePayResult>() {
                    @Override
                    public void onWebReturnSucess(OnlinePayResult onlinePayResult) {
                        if (onlinePayResult.isUseBalance()) {
                            dismissProgress();
                            EventBus.getDefault().post(new PrizeOrderStateChangeEvent());
                            navigateToNext();
                        } else if (onlinePayResult.isUseAliPay()) {
                            doAlipay(onlinePayResult.getAliPayResult());
                        } else if (onlinePayResult.isUseWeiXinPay()) {
                            doWXPay(onlinePayResult.getWeiXinPayResult());
                        }
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        dismissProgress();
                        toastor.showSingletonToast(errorMessage);

                    }

                    @Override
                    public void onWebReturnCompleted() {
                        binding.payBtn.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissProgress();
                        toastor.showSingletonToast("连接失败，请重试");
                    }
                });
    }


    /**
     * 微信支付
     *
     * @param weiXinPayResult 支付服务生成的支付参数
     */
    private void doWXPay(WeiXinPayResult weiXinPayResult) {
        WXPay.init(getApplicationContext(), Constant.WEIXIN_APP_ID);      //要在支付前调用
        WXPay.getInstance().doPay(weiXinPayResult, new WXPay.WXPayResultCallBack() {
            @Override
            public void onSuccess() {
                shopCartApi.queryPayState(orderNO, Constant.OnlinePayType.WEIXINPAY)
                        .compose(OnlineOrderPayActivity.this.<WebReturn<String>>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new WebReturnSubscriber<String>() {
                            @Override
                            public void onWebReturnSucess(String s) {
                                dismissProgress();
                                toastor.showSingletonToast("订单支付成功");
                                EventBus.getDefault().post(new OrderStateChangeEvent(false, Constant.OrderType.ORDERTYPE_UNPAY, Constant.OrderType.ORDERTYPE_SUBMIT));
                                navigateToNext();
                            }

                            @Override
                            public void onWebReturnFailure(String errorMessage) {
                                dismissProgress();
                                toastor.showSingletonToast("订单支付失败");
                            }

                            @Override
                            public void onWebReturnCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                dismissProgress();
                                toastor.showSingletonToast("连接失败，请重试");
                            }
                        });
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case WXPay.NO_OR_LOW_WX:
                        dismissProgress();
                        toastor.showSingletonToast("未安装微信或微信版本过低");
                        break;

                    case WXPay.ERROR_PAY_PARAM:
                        dismissProgress();
                        toastor.showSingletonToast("支付参数错误");
                        break;

                    case WXPay.ERROR_PAY:
                        dismissProgress();
                        toastor.showSingletonToast("支付失败");
                        break;
                }
            }

            @Override
            public void onCancel() {
                dismissProgress();
                toastor.showSingletonToast("支付取消");
            }
        });
    }

    /**
     * 支付宝支付
     *
     * @param pay_param 支付服务生成的支付参数
     */
    private void doAlipay(String pay_param) {
        new Alipay(this, pay_param, new Alipay.AlipayResultCallBack() {
            @Override
            public void onSuccess() {
                shopCartApi.queryPayState(orderNO, Constant.OnlinePayType.ALIPAY)
                        .compose(OnlineOrderPayActivity.this.<WebReturn<String>>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new WebReturnSubscriber<String>() {
                            @Override
                            public void onWebReturnSucess(String s) {
                                dismissProgress();
                                toastor.showSingletonToast("订单支付成功");
                                EventBus.getDefault().post(new OrderStateChangeEvent(false, Constant.OrderType.ORDERTYPE_UNPAY, Constant.OrderType.ORDERTYPE_SUBMIT));
                                navigateToNext();
                            }

                            @Override
                            public void onWebReturnFailure(String errorMessage) {
                                dismissProgress();
                                toastor.showSingletonToast("订单支付失败");
                            }

                            @Override
                            public void onWebReturnCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                dismissProgress();
                                toastor.showSingletonToast("连接失败，请重试");
                            }
                        });
            }

            @Override
            public void onDealing() {
                dismissProgress();
                toastor.showSingletonToast("支付处理中...");
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case Alipay.ERROR_RESULT:
                        dismissProgress();
                        toastor.showSingletonToast("支付结果解析错误");
                        break;

                    case Alipay.ERROR_NETWORK:
                        dismissProgress();
                        toastor.showSingletonToast("网络连接错误");
                        break;

                    case Alipay.ERROR_PAY:
                        dismissProgress();
                        toastor.showSingletonToast("支付码支付失败");
                        break;

                    default:
                        dismissProgress();
                        toastor.showSingletonToast("支付错误");
                        break;
                }

            }

            @Override
            public void onCancel() {
                dismissProgress();
                toastor.showSingletonToast("支付取消");
            }
        }).doPay();
    }

    public void queryOrderSimple(int orderId) {
        userOrderApi.queryOrderByOrderId("" + orderId)
                .compose(this.<WebReturn<OrderDetail>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<OrderDetail>() {
                    @Override
                    public void onWebReturnSucess(OrderDetail mOrderDetail) {
                        orderDetail = mOrderDetail;
                        binding.sum.setText("￥" + mOrderDetail.getOrderBaseInfo().getOrderAmount());
                        queryBalance();
                        binding.timeLayout.setVisibility(View.VISIBLE);
                        try {
                            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date beginDate = new Date(System.currentTimeMillis());//获取当前时间
                            Date endDate = new Date(simple.parse(orderDetail.getOrderTimesInfo().getOrderSubmitTimeStr()).getTime() + 1800000);
                            countDown(beginDate, endDate);
                        } catch (ParseException e) {
                            onError(e);
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

    public void queryActivityOrderSimple(String orderNO) {
        activityShopCartApi.queryActivityOrder(orderNO)
                .compose(this.<WebReturn<ActivityOrderSimple>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<ActivityOrderSimple>() {
                    @Override
                    public void onWebReturnSucess(ActivityOrderSimple mActivityOrderSimple) {
                        activityOrderSimple = mActivityOrderSimple;
                        binding.sum.setText("￥" + activityOrderSimple.getSpendMoney());
                        queryBalance();
                        binding.timeLayout.setVisibility(View.VISIBLE);
                        try {
                            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date beginDate = new Date(System.currentTimeMillis());//获取当前时间
                            Date endDate = new Date(simple.parse(mActivityOrderSimple.getCreateTimeStr()).getTime() + 1800000);
                            countDown(beginDate, endDate);
                        } catch (ParseException e) {
                            onError(e);
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

    public void countDown(final Date beginDate, final Date endDate) {
        remian = endDate.getTime() - beginDate.getTime();
        Observable.interval(1, TimeUnit.SECONDS)
                .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        if (System.currentTimeMillis() < endDate.getTime()) {
                            remian -= 1000;
                            long hours = (remian % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                            long minutes = (remian % (1000 * 60 * 60)) / (1000 * 60);
                            long seconds = (remian % (1000 * 60)) / 1000;
                            binding.payTime.setText("剩余支付时间："
                                    + (hours < 10 ? ("0" + hours) : ("" + hours))
                                    + ":" + (minutes < 10 ? ("0" + minutes) : ("" + minutes))
                                    + ":" + (seconds < 10 ? ("0" + seconds) : ("" + seconds)));
                        } else {
                            binding.payTime.setText("订单已过期,请重新下单");
                            onCompleted();
                        }

                    }
                });
    }

    public void queryBalance() {
        userLoginApi.queryUserDetail()
                .compose(this.<WebReturn<User>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<User>() {
                    @Override
                    public void onWebReturnSucess(User user) {
                        balance.set(user.getBalance());
                        if (isActivityPay)
                            canUseBalance = user.getBalance() >= activityOrderSimple.getSpendMoney();
                        else
                            canUseBalance = user.getBalance() >= orderDetail.getOrderBaseInfo().getOrderAmount();

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
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new OnlineOrderPayModule()).inject(this);
    }


    public void showBanlanceDialog() {
        showMessageDialogDuplicate(true, "MESSAGEDIALOG", "提示", "请确认是否已经收到奖品", null, "知道了");
    }

    private void showExitDialog() {
        String massage = "返回将放弃此次支付。";
        SpannableString spannableMassage = new SpannableString(massage);
        if (!isActivityPay) {
            massage = massage + "可在订单管理里面再次支付。（30分钟内未支付，订单将自动取消）";
            spannableMassage = new SpannableString(massage);
            spannableMassage.setSpan(new ForegroundColorSpan(0xffaaaaaa), massage.indexOf("（"), massage.indexOf("）") + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            spannableMassage.setSpan(new RelativeSizeSpan(0.8f), massage.indexOf("（"), massage.indexOf("）") + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        showMessageDialogDuplicate(true, exitDialogTag, "确认要放弃支付吗?", spannableMassage.toString(), "放弃支付", "取消");
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        showExitDialog();
    }

    public void navigateToNext() {
        if (!fromOrderDetail)
            if (isActivityPay)
                navigate.navigateToSuccessResult(this, null, true
                        , activityOrderSimple.getActivityOrderType() == Constant.ActivityOrderType.EXCHANGEORDER ? SuccessResultActivity.FROM_PRIZEORDER_EXCHANGE : SuccessResultActivity.FROM_PRIZEORDER_LOTTERY
                        , orderId
                        , orderNO);
            else
//                navigate.navigateToOrderDetail(this, null, true, null, orderId, OrderDetailActivity.TIMELINE);
                navigate.navigateToSuccessResult(this, null, true
                        , SuccessResultActivity.FROM_CONFIRMORDER
                        , orderId
                        , orderNO);
        else
            finish();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.useBalanceBtn) {
            binding.useBalance.setChecked(true);
            binding.useAli.setChecked(false);
            binding.useWeiXin.setChecked(false);

        }

        if (v == binding.useAliBtn) {
            binding.useBalance.setChecked(false);
            binding.useAli.setChecked(true);
            binding.useWeiXin.setChecked(false);

        }
        if (v == binding.useWeiXinBtn) {
            binding.useBalance.setChecked(false);
            binding.useAli.setChecked(false);
            binding.useWeiXin.setChecked(true);
        }

        if (v == binding.payBtn) {
            if (binding.useBalance.isChecked() && !canUseBalance) {
                showBanlanceDialog();
                return;
            }
            binding.payBtn.setEnabled(false);
            OrderOnlinePayBody orderOnlinePayBody = new OrderOnlinePayBody();
            orderOnlinePayBody.setOrderId(orderId);
            orderOnlinePayBody.setOrderNO(orderNO);
            orderOnlinePayBody.setUseBalance(binding.useBalance.isChecked());
            orderOnlinePayBody.setUseAliPay(binding.useAli.isChecked());
            orderOnlinePayBody.setUseWeiXinPay(binding.useWeiXin.isChecked());
            if (isActivityPay)
                doActivityPayOrder(orderOnlinePayBody);
            else
                doPayOrder(orderOnlinePayBody);
        }
        if (v == binding.orderBtn) {
            if (!isActivityPay)
                navigate.navigateToOrderDetail(OnlineOrderPayActivity.this, null, false, orderId, OrderDetailActivity.PARAM);
        }
    }

    @Override
    public void onDialogNegativeClick(String fragmentTag) {
        super.onDialogNegativeClick(fragmentTag);
        if (fragmentTag.equals(exitDialogTag)) {
            if (isActivityPay) {
                showProgress("取消订单");
                activityShopCartApi.cancleActivityOrder(orderNO)
                        .compose(OnlineOrderPayActivity.this.<WebReturn<String>>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new WebReturnSubscriber<String>() {
                            @Override
                            public void onWebReturnSucess(String s) {
                                dismissProgress();
                                finish();
                            }

                            @Override
                            public void onWebReturnFailure(String errorMessage) {
                                dismissProgress();
                            }

                            @Override
                            public void onWebReturnCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                dismissProgress();
                                toastor.showSingletonToast("连接失败，请重试");
                            }
                        });
            } else
                navigate.navigateToOrderDetail(OnlineOrderPayActivity.this, null, true, orderId, OrderDetailActivity.TIMELINE);
        }
    }
}
