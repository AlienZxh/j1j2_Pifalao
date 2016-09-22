package com.j1j2.pifalao.feature.prizedetail;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.util.Toastor;
import com.j1j2.common.view.quantityview.QuantityView;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentPrizedetailGiftSelectBinding;

/**
 * Created by alienzxh on 16-9-3.
 */
public class PrizeDetailGiftSelectFragment extends BaseFragment {

    public interface PrizeDetailGiftSelectFragmentListener {
        ActivityProduct getActivityProduct();

        void showToastor(String msg);
    }

    FragmentPrizedetailGiftSelectBinding binding;

    PrizeDetailGiftSelectFragmentListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (PrizeDetailGiftSelectFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "PrizeDetailGiftSelectFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prizedetail_gift_select, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setActivityProduct(listener.getActivityProduct());
        if (listener.getActivityProduct().getConfigs().getTimesLimits() > 0)
            binding.quantityview.setMaxQuantity(listener.getActivityProduct().getConfigs().getTimesLimits());
        binding.quantityview.setOnMaxQuantityListener(new QuantityView.OnMaxQuantityListener() {
            @Override
            public void onMaxQuantity(QuantityView view) {
                listener.showToastor("不能超过购买限制");
            }
        });
    }

    public int getPrizeQuantity() {
        return binding.quantityview.getQuantity();
    }
}
