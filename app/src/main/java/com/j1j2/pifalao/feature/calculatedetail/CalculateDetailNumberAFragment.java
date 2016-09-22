package com.j1j2.pifalao.feature.calculatedetail;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.LotteryCacluateTime;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.databinding.FragmentCalculatedetailNumberaBinding;
import com.j1j2.pifalao.feature.home.memberhome.MemberHomeLuckyAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alienzxh on 16-9-6.
 */
public class CalculateDetailNumberAFragment extends LazyFragment {

    public interface CalculateDetailNumberAFragmentListener {
        long getA();

        List<LotteryCacluateTime> getTimes();
    }

    CalculateDetailNumberAFragmentListener listener;

    FragmentCalculatedetailNumberaBinding binding;

    NumberARecordAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (CalculateDetailNumberAFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "CalculateDetailNumberAFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculatedetail_numbera, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.list.getVisibility() == View.VISIBLE) {
                    binding.list.setVisibility(View.GONE);
                    binding.detailText.setText("展开");
                } else {
                    binding.list.setVisibility(View.VISIBLE);
                    binding.detailText.setText("收起");
                }

            }
        });

        binding.setNumA(listener.getA());

        binding.list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.list.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getContext())
                .color(0xffd2d2d2)
                .size(1)
                .build());
        binding.list.setAdapter(adapter = new NumberARecordAdapter(listener.getTimes()));
    }
}
