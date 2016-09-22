package com.j1j2.pifalao.feature.onlineorderpay;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableDouble;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.j1j2.common.util.UrlUtils;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.http.api.ActivityShopCartApi;
import com.j1j2.data.http.api.ShopCartApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserOrderApi;
import com.j1j2.data.model.ActivityOrderSimple;
import com.j1j2.data.model.ActivityProcessState;
import com.j1j2.data.model.OnlinePayResult;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.PayResult;
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
import com.j1j2.pifalao.app.event.WeiXinPayReturnEvent;
import com.j1j2.pifalao.databinding.ActivityOnlineorderpayBinding;
import com.j1j2.pifalao.feature.onlineorderpay.di.OnlineOrderPayModule;
import com.j1j2.pifalao.feature.orderdetail.OrderDetailActivity;
import com.j1j2.pifalao.feature.successresult.SuccessResultActivity;
import com.orhanobut.logger.Logger;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.trello.rxlifecycle.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

    OrderSimple orderSimple;
    ActivityOrderSimple activityOrderSimple;

    ObservableDouble balance = new ObservableDouble(0);
    boolean canUseBalance = false;
    boolean canUseOnline = true;

    long remian;

    private static final int Ali_SDK_PAY_FLAG = 1;

    private PayHandler mHandler;

    private IWXAPI wxApi;

    private static class PayHandler extends Handler {
        private final WeakReference<OnlineOrderPayActivity> mActivity;
        private boolean isActivityPay;

        public PayHandler(OnlineOrderPayActivity mActivity, boolean isActivityPay) {
            this.mActivity = new WeakReference<OnlineOrderPayActivity>(mActivity);
            this.isActivityPay = isActivityPay;
        }

        @Override
        public void handleMessage(Message msg) {
            final OnlineOrderPayActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case Ali_SDK_PAY_FLAG: {

                        PayResult payResult = new PayResult((String) msg.obj);
                        Logger.d("alipay  " + payResult.toString());
                        /**
                         * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                         * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                         * docType=1) 建议商户依赖异步通知
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();

                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (!TextUtils.equals(resultStatus, "9000")) {
                            activity.dismissProgress();
                            // 判断resultStatus 为非"9000"则代表可能支付失败
                            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                activity.toastor.showSingletonToast("订单支付结果确认中");
                                activity.finish();
                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                activity.toastor.showSingletonToast("订单支付失败");
                            }

                        } else {
                            ArrayMap<String, String> resultInfoMap = UrlUtils.toMap(resultInfo);
                            boolean success = false;
                            try {
                                success = Boolean.parseBoolean(resultInfoMap.get("success").replaceAll("\"", ""));
                            } catch (Exception e) {
                                Logger.e(Arrays.toString(e.getStackTrace()));
                            }

                            if (success) {
                                activity.shopCartApi.queryPayState(activity.orderNO, Constant.OnlinePayType.ALIPAY)
                                        .compose(activity.<WebReturn<String>>bindToLifecycle())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new WebReturnSubscriber<String>() {
                                            @Override
                                            public void onWebReturnSucess(String s) {
                                                activity.dismissProgress();
                                                activity.toastor.showSingletonToast("订单支付成功");
                                                EventBus.getDefault().post(new OrderStateChangeEvent(false, Constant.OrderType.ORDERTYPE_UNPAY, Constant.OrderType.ORDERTYPE_SUBMIT));
                                                activity.navigateToNext();
                                            }

                                            @Override
                                            public void onWebReturnFailure(String errorMessage) {
                                                activity.dismissProgress();
                                                activity.toastor.showSingletonToast("订单支付失败");
                                            }

                                            @Override
                                            public void onWebReturnCompleted() {

                                            }
                                        });

                            } else {
                                activity.dismissProgress();
                                activity.toastor.showSingletonToast("订单支付失败");
                            }
                        }

                        break;
                    }
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onlineorderpay);
        binding.setOnClickListener(this);
        binding.setBalance(balance);
//_______________________________________
        wxApi = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID, false);
        wxApi.registerApp(Constant.WEIXIN_APP_ID);
    }

    @Override
    protected void initViews() {
        binding.useBalance.setChecked(true);
        binding.useAli.setChecked(false);
        binding.useWeiXin.setChecked(false);
        //_______________________________________
        mHandler = new PayHandler(this, isActivityPay);

        if (isActivityPay)
            queryActivityOrderSimple(orderNO);
        else
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
                            doAliPay(onlinePayResult.getAliPayResult());
                        } else if (onlinePayResult.isUseWeiXinPay()) {
                            doWeiXinPay(onlinePayResult.getWeiXinPayResult());
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
                            doAliPay(onlinePayResult.getAliPayResult());
                        } else if (onlinePayResult.isUseWeiXinPay()) {
                            doWeiXinPay(onlinePayResult.getWeiXinPayResult());
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
                });
    }


    public void doAliPay(final String payRequest) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(OnlineOrderPayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payRequest, true);
                Message msg = new Message();
                msg.what = Ali_SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    public void doWeiXinPay(WeiXinPayResult weiXinPayResult) {
        dismissProgress();
        if (!wxApi.isWXAppInstalled()) {

            toastor.showSingleLongToast("未安装微信，请安装微信后重试！");
            return;
        }

        boolean isPaySupported = wxApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (!isPaySupported) {

            toastor.showSingleLongToast("微信版本过低，请升级微信后重试！");
            return;
        }

        if (weiXinPayResult == null) {

            toastor.showSingleLongToast("订单支付失败");
            return;
        }

        PayReq req = new PayReq();
        req.appId = weiXinPayResult.getAppId();
        req.partnerId = weiXinPayResult.getPartnerId();
        req.prepayId = weiXinPayResult.getPrepayId();
        req.packageValue = weiXinPayResult.getPackage();
        req.nonceStr = weiXinPayResult.getNonceStr();
        req.timeStamp = weiXinPayResult.getTimestamp();
        req.sign = weiXinPayResult.getSign();
        wxApi.sendReq(req);
    }

    public void queryOrderSimple(int orderId) {
        userOrderApi.queryOrderByOrderId("" + orderId)
                .compose(this.<WebReturn<OrderSimple>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<OrderSimple>() {
                    @Override
                    public void onWebReturnSucess(OrderSimple mOrderSimple) {
                        orderSimple = mOrderSimple;
                        binding.sum.setText("￥" + mOrderSimple.getOrderSum());
                        queryBalance();
                        binding.timeLayout.setVisibility(View.VISIBLE);
                        try {
                            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date beginDate = new Date(System.currentTimeMillis());//获取当前时间
                            Date endDate = new Date(simple.parse(orderSimple.getOrderSubmitTimeStr()).getTime() + 1800000);
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
                            canUseBalance = user.getBalance() >= orderSimple.getOrderSum();

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
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setMessage("余额不足，请到服务点充值后再试！")
                .setPositiveButton("知道了", null)
                .create();
        messageDialog.show();

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onWeiXinPayReturnEvent(WeiXinPayReturnEvent event) {
        if (event.isSuccess()) {
            shopCartApi.queryPayState(orderNO, Constant.OnlinePayType.WEIXINPAY)
                    .compose(this.<WebReturn<String>>bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new WebReturnSubscriber<String>() {
                        @Override
                        public void onWebReturnSucess(String s) {
                            toastor.showSingletonToast("订单支付成功");
                            EventBus.getDefault().post(new OrderStateChangeEvent(false, Constant.OrderType.ORDERTYPE_UNPAY, Constant.OrderType.ORDERTYPE_SUBMIT));
                            navigateToNext();
                        }

                        @Override
                        public void onWebReturnFailure(String errorMessage) {
                            toastor.showSingletonToast("订单支付失败");

                        }

                        @Override
                        public void onWebReturnCompleted() {

                        }
                    });
        } else {

        }
    }

    private void showExitDialog() {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();

        String massage = "返回将放弃此次支付。";
        SpannableString spannableMassage = new SpannableString(massage);
        if (!isActivityPay){
            massage = massage + "可在订单管理里面再次支付。（30分钟内未支付，订单将自动取消）";
            spannableMassage.setSpan(new ForegroundColorSpan(0xffaaaaaa), massage.indexOf("（"), massage.indexOf("）") + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            spannableMassage.setSpan(new RelativeSizeSpan(0.8f), massage.indexOf("（"), massage.indexOf("）") + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }

        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("确认要放弃支付吗?")
                .setPositiveButton("取消", null)
                .setMessage(spannableMassage)
                .setNegativeButton("放弃支付", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                                    });
                        } else
                            navigate.navigateToOrderDetail(OnlineOrderPayActivity.this, null, true, null, orderId, OrderDetailActivity.TIMELINE);
                    }
                })
                .create();
        messageDialog.show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        showExitDialog();
    }

    public void navigateToNext() {
        if (!fromOrderDetail)
            if (isActivityPay)
                navigate.navigateToSuccessResult(OnlineOrderPayActivity.this, null, true
                        , activityOrderSimple.getActivityOrderType() == Constant.ActivityOrderType.EXCHANGEORDER ? SuccessResultActivity.FROM_PRIZEORDER_EXCHANGE : SuccessResultActivity.FROM_PRIZEORDER_LOTTERY
                        , orderId);
            else
                navigate.navigateToOrderDetail(OnlineOrderPayActivity.this, null, true, null, orderId, OrderDetailActivity.TIMELINE);
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
                navigate.navigateToOrderDetail(OnlineOrderPayActivity.this, null, false, null, orderId, OrderDetailActivity.PARAM);
        }
    }
}
