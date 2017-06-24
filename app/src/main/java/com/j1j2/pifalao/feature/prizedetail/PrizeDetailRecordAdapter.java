package com.j1j2.pifalao.feature.prizedetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.LotteryParticipationTimes;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemPrizedetailRecordBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by alienzxh on 16-9-4.
 */
public class PrizeDetailRecordAdapter extends RecyclerView.Adapter<PrizeDetailRecordAdapter.PrizeDetailRecordViewHolder> {

    private List<LotteryParticipationTimes> strings;

    private int maxLength;

    public PrizeDetailRecordAdapter(List<LotteryParticipationTimes> strings, int maxLength) {
        this.strings = strings;
        this.maxLength = maxLength;
    }

    @Override
    public PrizeDetailRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prizedetail_record, parent, false);
        return new PrizeDetailRecordViewHolder(itemView);
    }

    public void addAll(Collection<LotteryParticipationTimes> newLotteryParticipationTimes) {
        if (null == strings)
            strings = new ArrayList<>();
        int startIndex = strings.size();
        strings.addAll(startIndex, newLotteryParticipationTimes);
        notifyItemRangeInserted(startIndex, newLotteryParticipationTimes.size());
    }

    public void initData(Collection<LotteryParticipationTimes> newLotteryParticipationTimes) {

        if (null != strings && null != newLotteryParticipationTimes) {
            strings.clear();
            strings.addAll(newLotteryParticipationTimes);
        } else if (null != newLotteryParticipationTimes) {
            strings = new ArrayList<>();
            strings.addAll(newLotteryParticipationTimes);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PrizeDetailRecordViewHolder holder, int position) {
        holder.bind(strings.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.strings == null ? 0 : (this.strings.size() > maxLength ? maxLength : this.strings.size());
    }


    public class PrizeDetailRecordViewHolder extends AutoBindingViewHolder<ItemPrizedetailRecordBinding, LotteryParticipationTimes> {
        public PrizeDetailRecordViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemPrizedetailRecordBinding getBinding(View itemView) {
            return ItemPrizedetailRecordBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull LotteryParticipationTimes data, int position) {
            binding.setParticipation(data);
        }
    }
}
