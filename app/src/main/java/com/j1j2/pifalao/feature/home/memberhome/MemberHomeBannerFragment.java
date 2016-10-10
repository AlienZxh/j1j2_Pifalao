package com.j1j2.pifalao.feature.home.memberhome;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.BannerActivity;
import com.j1j2.data.model.User;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.databinding.FragmentMemberhomeBannerBinding;
import com.j1j2.pifalao.feature.home.vegetablehome.VegetableHomeTopAdapter;
import com.j1j2.pifalao.feature.participationrecord.ParticipationRecordActivity;
import com.j1j2.pifalao.feature.participationrecord.ParticipationRecordFragment;
import com.j1j2.pifalao.feature.prize.PrizeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alienzxh on 16-8-24.
 */
public class MemberHomeBannerFragment extends BaseFragment implements View.OnClickListener {

    public interface MemberHomeBannerFragmentListener {
        void navigateToParticipation(int activityType);

        void navigateToShowOrder();

        void navigateToPrize(int acyivityType);

        void navigateToPoint();

        User getUser();

        List<ActivityWinPrize> getActivityWinPrizes();
    }

    MemberHomeBannerFragmentListener listener;

    FragmentMemberhomeBannerBinding binding;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MemberHomeActivity) activity;
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.viewPager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.viewPager.startAutoScroll();
        if (listener.getUser() == null) {
            binding.pointBtn.setVisibility(View.GONE);
        } else {
            binding.pointBtn.setVisibility(View.VISIBLE);
            binding.pointText.setText("" + listener.getUser().getPoint());
        }
    }

    @Override
    protected String getFragmentName() {
        return "MemberHomeBannerFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memberhome_banner, container, false);
        return binding.getRoot();
    }


    @Override
    protected void initViews() {
        binding.pointBtn.setOnClickListener(this);
        binding.ongoing.setOnClickListener(this);
        binding.showorder.setOnClickListener(this);
        binding.myprize.setOnClickListener(this);
        binding.raffled.setOnClickListener(this);
        initTopAdv();
        //______________________________________________________________
        List<String> info = new ArrayList<>();
        int maxlength = 30;
        for (ActivityWinPrize activityWinPrize : listener.getActivityWinPrizes()) {
            String text = "恭喜" + activityWinPrize.getWinner().getWinnerEncrypt()
                    + "用户夺得" + activityWinPrize.getProductInfo().getProductName();
            for (int i = 0; i <= (text.length() / maxlength); i++) {
                if (i == (text.length() / maxlength))
                    info.add(text.substring(i * maxlength, text.length()));
                else
                    info.add(text.substring(i * maxlength, (i + 1) * maxlength));
            }
//            info.add(text);
        }
        binding.marqueeView.startWithList(info);
    }

    public void initTopAdv() {
        MemberBannerAdapter memberBannerAdapter = new MemberBannerAdapter(new int[]{R.drawable.member_banner_1, R.drawable.member_banner_2, R.drawable.member_banner_3});
        binding.viewPager.setAdapter(memberBannerAdapter);
        binding.tab.setViewPager(binding.viewPager);
        binding.viewPager.startAutoScroll(2000);
        binding.viewPager.setInterval(2000);
    }


    @Override
    public void onClick(View v) {
        if (v == binding.ongoing) {
            listener.navigateToParticipation(ParticipationRecordActivity.RECORD_ONGOING);
        }
        if (v == binding.raffled) {
            listener.navigateToParticipation(ParticipationRecordActivity.RECORD_RAFFLED);
        }
        if (v == binding.showorder) {
            listener.navigateToShowOrder();
        }
        if (v == binding.myprize) {
            listener.navigateToPrize(PrizeActivity.PRIZE_MINE);
        }
        if (v == binding.pointBtn) {
            listener.navigateToPoint();
        }

    }
}
