package com.j1j2.pifalao.feature.calculatedetail;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.ActivityOrderSimple;
import com.j1j2.data.model.LotteryCacluateTime;
import com.j1j2.data.model.LotteryCacluteResult;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.ActivityCalculatedetailBinding;
import com.j1j2.pifalao.databinding.ViewCalculateFormulaBinding;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailCompleteFragment;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailGiftRuleFragment;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailGiftSelectFragment;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailRecordFragment;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailSelectFragment;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailTopFragment;
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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-6.
 */
@RequireBundler
public class CalculateDetailActivity extends BaseActivity implements View.OnClickListener
        , CalculateDetailNumberAFragment.CalculateDetailNumberAFragmentListener
        , CalculateDetailNumberBFragment.CalculateDetailNumberBFragmentListener
        , CalculateDetailResultFragment.CalculateDetailResultFragmentListener {

    ActivityCalculatedetailBinding binding;
    ViewCalculateFormulaBinding calculateFormulaBinding;

    private MultiTypeAdapter<Object> multiTypeAdapter;

    @Arg
    int lotteryId;

    @Inject
    ActivityApi activityApi;

    LotteryCacluteResult mLotteryCacluteResult;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new PrizeDetailModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculatedetail);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        queryLotteryCacluateResult();
    }

    private void queryLotteryCacluateResult() {
        activityApi.queryLotteryCacluateResult(lotteryId)
                .compose(this.<WebReturn<LotteryCacluteResult>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<LotteryCacluteResult>() {
                    @Override
                    public void onWebReturnSucess(LotteryCacluteResult lotteryCacluteResult) {
                        mLotteryCacluteResult = lotteryCacluteResult;
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

    private List<Object> loadData() {
        List<Object> data = new ArrayList<>();
        calculateFormulaBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_calculate_formula, null, false);

        data.add(calculateFormulaBinding.getRoot());
        data.add(new FragmentData(CalculateDetailNumberAFragment.class, "CalculateDetailNumberAFragment"));
        data.add(new FragmentData(CalculateDetailNumberBFragment.class, "CalculateDetailNumberBFragment"));
        data.add(new FragmentData(CalculateDetailResultFragment.class, "CalculateDetailResultFragment"));

        View view = new View(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AutoUtils.getPercentHeightSize(60)));
        data.add(view);

        return data;
    }

    @Override
    public long getA() {
        return mLotteryCacluteResult.getA();
    }

    @Override
    public List<LotteryCacluateTime> getTimes() {
        return mLotteryCacluteResult.getTimes();
    }

    @Override
    public int getB() {
        return mLotteryCacluteResult.getB();
    }

    @Override
    public String getLuckTicketNum() {
        return mLotteryCacluteResult.getLuckTicketNum();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
    }
}
