package com.j1j2.pifalao.feature.home.memberhome;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.BannerActivity;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
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
public class MemberHomeBannerFragment extends LazyFragment implements View.OnClickListener {

    public interface MemberHomeBannerFragmentListener {
        void navigateToParticipation(int activityType);

        void navigateToShowOrder();

        void navigateToPrize(int acyivityType);


    }

    MemberHomeBannerFragmentListener listener;

    FragmentMemberhomeBannerBinding binding;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MemberHomeActivity) activity;
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

        binding.ongoing.setOnClickListener(this);
        binding.showorder.setOnClickListener(this);
        binding.myprize.setOnClickListener(this);
        binding.raffled.setOnClickListener(this);
        initTopAdv();
        //______________________________________________________________
        List<String> info = new ArrayList<>();
        info.add("1. 阿生；静安寺；");
        info.add("2. 地方建设制度监控ｖｎ");
        info.add("3. 阿生；静安寺；");
        info.add("4. 地方建设制度监控ｖｎ");
        binding.marqueeView.startWithList(info);
    }

    public void initTopAdv() {
        MemberBannerAdapter memberBannerAdapter = new MemberBannerAdapter(new int[]{R.drawable.member_banner_1, R.drawable.member_banner_2, R.drawable.member_banner_3});
        binding.viewPager.setAdapter(memberBannerAdapter);
        binding.tab.setViewPager(binding.viewPager);
        binding.viewPager.startAutoScroll(2000);
        binding.viewPager.setInterval(2000);
        binding.viewPager.startAutoScroll();
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

    }
}
