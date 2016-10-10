package com.j1j2.pifalao.feature.participationrecord;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemParticipationrecordBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by alienzxh on 16-9-1.
 */
public class ParticipationRecordAdapter extends RecyclerView.Adapter<ParticipationRecordAdapter.ParticipationRecordViewHolder> {

    public interface ParticipationRecordAdapterListener {
        void showCatNumDialog(ActivityWinPrize data);

        void navigateToPrizeDetail(ActivityWinPrize data);

        void backToMemberHome();

    }

    private ParticipationRecordAdapterListener listener;

    private List<ActivityWinPrize> strings;

    public ParticipationRecordAdapter(List<ActivityWinPrize> strings, ParticipationRecordAdapterListener listener) {
        this.strings = strings;
        this.listener = listener;
    }

    public void addAll(Collection<ActivityWinPrize> newActivityWinPrizes) {
        if (null == strings)
            strings = new ArrayList<>();
        int startIndex = strings.size();
        strings.addAll(startIndex, newActivityWinPrizes);
        notifyItemRangeInserted(startIndex, newActivityWinPrizes.size());
    }

    public void initData(Collection<ActivityWinPrize> newActivityWinPrizes) {
        if (null != strings && null != newActivityWinPrizes) {
            strings.clear();
            strings.addAll(newActivityWinPrizes);
        } else if (null != newActivityWinPrizes) {
            strings = new ArrayList<>();
            strings.addAll(newActivityWinPrizes);
        }
        notifyDataSetChanged();
    }

    @Override
    public ParticipationRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_participationrecord, parent, false);
        return new ParticipationRecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ParticipationRecordViewHolder holder, int position) {
        holder.bind(strings.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.strings == null ? 0 : this.strings.size();
    }

    public class ParticipationRecordViewHolder extends AutoBindingViewHolder<ItemParticipationrecordBinding, ActivityWinPrize> {
        private View itemView;

        public ParticipationRecordViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        @Override
        protected ItemParticipationrecordBinding getBinding(View itemView) {
            return ItemParticipationrecordBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ActivityWinPrize data, int position) {
            binding.setActivityWinPrize(data);
            binding.catNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.showCatNumDialog(data);
                }
            });
            binding.againBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.backToMemberHome();
                }
            });
            binding.moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null)
                        listener.navigateToPrizeDetail(data);
                }
            });
        }
    }
}
