package com.j1j2.pifalao.feature.prizedetail;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentPrizedetailGiftRuleBinding;

/**
 * Created by alienzxh on 16-9-2.
 */
public class PrizeDetailGiftRuleFragment extends BaseFragment {

    public interface PrizeDetailGiftRuleFragmentListener {
        ActivityProduct getActivityProduct();
    }

    FragmentPrizedetailGiftRuleBinding binding;

    PrizeDetailGiftRuleFragmentListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (PrizeDetailGiftRuleFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "PrizeDetailGiftRuleFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prizedetail_gift_rule, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setActivityProduct(listener.getActivityProduct());


//        SpannableString spannableString = new SpannableString(binding.rule.getText().toString());
//        spannableString.setSpan(new ForegroundColorSpan(0xff02a6ed), spannableString.length() - 5, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new UnderlineSpan(), spannableString.length() - 5, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        binding.rule.setText(spannableString);
    }
}
