package com.j1j2.pifalao.feature.couponselect;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.Coupon;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.CouponSelectEvent;
import com.j1j2.pifalao.databinding.ActivityCouponselectBinding;
import com.j1j2.pifalao.feature.couponselect.di.CouponSelectModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.ParcelListSerializer;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;

/**
 * Created by alienzxh on 16-4-3.
 */
@RequireBundler
public class CouponSelectActivity extends BaseActivity implements View.OnClickListener {


    ActivityCouponselectBinding binding;

    @Inject
    CouponSelectViewModel couponSelectViewModel;

    @Arg
    int moduleId;

    @Arg(serializer = ParcelListSerializer.class)
    List<Coupon> coupons;
    @Arg
    @Required(false)
    Coupon selectCoupon;

    CouponSelectListAdapter couponSelectListAdapter;

    SingleSelector singleSelector = new SingleSelector();

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_couponselect);
        binding.backBtn.setOnClickListener(this);
        binding.selectBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        binding.couponList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.couponList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .colorResId(R.color.colorTransparent)
                .size(AutoUtils.getPercentHeightSize(10))
                .showLastDivider()
                .build());
        binding.couponList.setClipToPadding(false);
        binding.couponList.setPadding(0, AutoUtils.getPercentHeightSize(10), 0, 0);

        setAdapter(coupons);

        if (coupons == null || coupons.size() <= 0)
            binding.selectBtn.setVisibility(View.GONE);
    }

    public void setAdapter(List<Coupon> couponList) {
        couponSelectListAdapter = new CouponSelectListAdapter(couponList, singleSelector);
        binding.couponList.setAdapter(couponSelectListAdapter);
        if (null != selectCoupon) {
            for (int i = 0; i < coupons.size(); i++) {
                if (coupons.get(i).getCouponId() == selectCoupon.getCouponId()) {
                    singleSelector.setSelected(i, couponSelectListAdapter.getItemId(i), true);
                    break;
                }
            }
        }
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new CouponSelectModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.selectBtn) {

            if (singleSelector.getSelectedPositions().size() > 0) {
                Coupon coupon = couponSelectListAdapter.getCoupons().get(singleSelector.getSelectedPositions().get(0));
                EventBus.getDefault().post(new CouponSelectEvent(coupon.getType(), true, coupon));
            } else
                EventBus.getDefault().post(new CouponSelectEvent(0, false, null));
            finish();
        }
    }
}
