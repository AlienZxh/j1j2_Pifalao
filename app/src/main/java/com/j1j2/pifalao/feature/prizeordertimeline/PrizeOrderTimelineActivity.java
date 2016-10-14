package com.j1j2.pifalao.feature.prizeordertimeline;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.ActivityProcessState;
import com.j1j2.data.model.ActivityStateChain;
import com.j1j2.data.model.ImgUrl;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.NavigateToMemberHomeEvent;
import com.j1j2.pifalao.app.event.PrizeOrderStateChangeEvent;
import com.j1j2.pifalao.databinding.ActivityPrizeorderTimelineBinding;
import com.j1j2.pifalao.databinding.ItemPrizeorderCompleteBinding;
import com.j1j2.pifalao.databinding.ItemPrizeorderDeliveryBinding;
import com.j1j2.pifalao.databinding.ItemPrizeorderNormalBinding;
import com.j1j2.pifalao.databinding.ItemPrizeorderReceiveBinding;
import com.j1j2.pifalao.databinding.ItemPrizeorderShoworderBinding;
import com.j1j2.pifalao.databinding.ItemPrizeorderWinBinding;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailModule;
import com.j1j2.pifalao.feature.services.ServicesActivity;
import com.j1j2.pifalao.feature.showorders.PrizeImgShowAdapter;
import com.shizhefei.view.multitype.ItemBinderFactory;
import com.shizhefei.view.multitype.MultiTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-7.
 */
@RequireBundler
public class PrizeOrderTimelineActivity extends BaseActivity implements View.OnClickListener,
        PrizeImgShowAdapter.PrizeImgShowAdapterListener {

    ActivityPrizeorderTimelineBinding binding;

    @Inject
    ActivityApi activityApi;

    @Arg
    int orderId;

    private MultiTypeAdapter<Object> multiTypeAdapter;
    ActivityProcessState activityProcessState;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new PrizeDetailModule(this)).inject(this);
        Bundler.inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prizeorder_timeline);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        binding.confirm.setOnClickListener(this);
        binding.receive.setOnClickListener(this);
        binding.showorder.setOnClickListener(this);
        binding.againBtn.setOnClickListener(this);
        queryActivityOrderProcessState();

    }

    private void confirmReceivedPrize() {
        showProgress("确认收货中");
        activityApi.confirmReceivedPrize(orderId)
                .compose(this.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        EventBus.getDefault().post(new PrizeOrderStateChangeEvent());
                        dismissProgress();
                        toastor.showSingletonToast(s);

                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        dismissProgress();
                        toastor.showSingletonToast(errorMessage);
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

    private void queryActivityOrderProcessState() {
        activityApi.queryActivityOrderProcessState(orderId)
                .compose(this.<WebReturn<ActivityProcessState>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<ActivityProcessState>() {
                    @Override
                    public void onWebReturnSucess(ActivityProcessState activityProcessState) {

                        PrizeOrderTimelineActivity.this.activityProcessState = activityProcessState;
                        binding.setActivityOrderState(activityProcessState.getCurrentOrderState());
                        binding.setActivityOrderType(activityProcessState.getActivityType());
                        ItemBinderFactory itemBinderFactory = new ItemBinderFactory(getSupportFragmentManager());
                        List<Object> uiList = new ArrayList<>();
                        int nowState = activityProcessState.getTimeChains().get(0).getState();
                        for (ActivityStateChain activityStateChain : activityProcessState.getTimeChains()) {

                            if (activityStateChain.getState() == Constant.ActivityOrderState.RAFFLED) {
                                if (nowState == activityStateChain.getState()) {
                                    ItemPrizeorderNormalBinding normalBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_prizeorder_normal, null, false);
                                    normalBinding.setIsNowState(true);
                                    normalBinding.setActivityStateChain(activityStateChain);
                                    uiList.add(normalBinding.getRoot());
                                }
                                ItemPrizeorderWinBinding winBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_prizeorder_win, null, false);
                                winBinding.setActivityStateChain(activityStateChain);
                                winBinding.setIsNowState(false);
                                winBinding.setState(activityProcessState);
                                uiList.add(winBinding.getRoot());


                            } else if (activityStateChain.getState() == Constant.ActivityOrderState.AWARDED) {
                                if (nowState == activityStateChain.getState()) {
                                    ItemPrizeorderNormalBinding normalBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_prizeorder_normal, null, false);
                                    normalBinding.setIsNowState(true);
                                    normalBinding.setActivityStateChain(activityStateChain);
                                    uiList.add(normalBinding.getRoot());
                                }
                                ItemPrizeorderReceiveBinding receiveBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_prizeorder_receive, null, false);
                                receiveBinding.setActivityStateChain(activityStateChain);
                                receiveBinding.setIsNowState(false);
                                if (activityProcessState.getProductType() == Constant.ActivityProductType.Material)
                                    receiveBinding.setAddress("收货地址：\n"
                                            + activityProcessState.getWinnerAwardInfo().getName()
                                            + "    " + activityProcessState.getWinnerAwardInfo().getMobile()
                                            + "\n" + activityProcessState.getWinnerAwardInfo().getAddress());
                                else
                                    receiveBinding.setAddress("领取号码：\n"
                                            + activityProcessState.getWinnerAwardInfo().getMobile());
                                uiList.add(receiveBinding.getRoot());


                            } else if (activityStateChain.getState() == Constant.ActivityOrderState.DLIVERYED) {
                                if (nowState == activityStateChain.getState()) {
                                    ItemPrizeorderNormalBinding normalBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_prizeorder_normal, null, false);
                                    normalBinding.setIsNowState(true);
                                    normalBinding.setActivityStateChain(activityStateChain);
                                    uiList.add(normalBinding.getRoot());
                                }
                                ItemPrizeorderDeliveryBinding deliveryBinding =DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_prizeorder_delivery, null, false);
                                deliveryBinding.setIsNowState(false);
                                deliveryBinding.setActivityStateChain(activityStateChain);
                                uiList.add(deliveryBinding.getRoot());

                            } else if (activityStateChain.getState() == Constant.ActivityOrderState.USERRECEIVED) {
                                if (nowState == activityStateChain.getState()) {
                                    ItemPrizeorderNormalBinding normalBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_prizeorder_normal, null, false);
                                    normalBinding.setIsNowState(true);
                                    normalBinding.setActivityStateChain(activityStateChain);
                                    uiList.add(normalBinding.getRoot());
                                }
                                ItemPrizeorderCompleteBinding completeBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_prizeorder_complete, null, false);
                                completeBinding.setIsNowState(false);
                                completeBinding.setActivityStateChain(activityStateChain);
                                uiList.add(completeBinding.getRoot());


                            } else if (activityStateChain.getState() == Constant.ActivityOrderState.SHARED) {
                                ItemPrizeorderShoworderBinding showorderBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_prizeorder_showorder, null, false);
                                showorderBinding.setIsNowState(nowState == activityStateChain.getState());
                                showorderBinding.setActivityStateChain(activityStateChain);
                                showorderBinding.setShowStr(activityProcessState.getAcceptanceSpeech().getMessage());
                                showorderBinding.imgList.setLayoutManager(new LinearLayoutManager(PrizeOrderTimelineActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                //        binding.list.addItemDecoration(new HorizontalDividerItemDecoration
                                //                .Builder(getContext())
                                //                .color(0xffd2d2d2)
                                //                .size(1)
                                //                .build());
                                showorderBinding.imgList.setAdapter(new PrizeImgShowAdapter(activityProcessState.getAcceptanceSpeech().getImgs(), PrizeOrderTimelineActivity.this));
                                uiList.add(showorderBinding.getRoot());
                            } else {
                                ItemPrizeorderNormalBinding normalBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_prizeorder_normal, null, false);
                                normalBinding.setIsNowState(nowState == activityStateChain.getState());
                                normalBinding.setActivityStateChain(activityStateChain);
                                uiList.add(normalBinding.getRoot());
                            }

                        }

                        multiTypeAdapter = new MultiTypeAdapter<>(uiList, itemBinderFactory);
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

    public void showReceiveDialog() {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setMessage("请确认是否已经收到奖品")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmReceivedPrize();
                    }
                })
                .create();
        messageDialog.show();
    }

    @Subscribe
    public void onOrderStateChangeEvent(PrizeOrderStateChangeEvent event) {
        queryActivityOrderProcessState();
    }

    @Override
    public void onShowImgClick(View view, List<ImgUrl> urls, int position) {
        navigate.navigateToImgsGalleryActivity(this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, urls, position);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.confirm)
            navigate.navigateToPrizeConfirmLotteryActivity(this, null, false,
                    activityProcessState.getProductInfo(),
                    activityProcessState.getProductType(),
                    orderId,
                    activityProcessState.getOrderNO());
        if (v == binding.receive) {
            showReceiveDialog();
        }
        if (v == binding.showorder)
            navigate.navigateToShowOrderActivity(this, null, false, orderId,
                    activityProcessState.getProductInfo(),
                    activityProcessState.getOrderNO());
        if (v == binding.againBtn){
            Intent intent = new Intent(this, ServicesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

            EventBus.getDefault().post(new NavigateToMemberHomeEvent());
        }

    }
}
