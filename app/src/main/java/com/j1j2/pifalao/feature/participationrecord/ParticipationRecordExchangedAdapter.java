package com.j1j2.pifalao.feature.participationrecord;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemParticipationrecordExchangedBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by alienzxh on 16-9-21.
 */

public class ParticipationRecordExchangedAdapter extends RecyclerView.Adapter<ParticipationRecordExchangedAdapter.ParticipationRecordExchangeViewHolder> {

    public interface ParticipationRecordExchangedAdapterListener {
        void navigateToPrizeOrder(ActivityProduct data);
    }


    private ParticipationRecordExchangedAdapterListener listener;
    private List<ActivityProduct> strings;

    public ParticipationRecordExchangedAdapter(List<ActivityProduct> strings, ParticipationRecordExchangedAdapterListener listener) {
        this.listener = listener;
        this.strings = strings;
    }

    public void addAll(Collection<ActivityProduct> newActivityWinPrizes) {
        if (null == strings)
            strings = new ArrayList<>();
        int startIndex = strings.size();
        strings.addAll(startIndex, newActivityWinPrizes);
        notifyItemRangeInserted(startIndex, newActivityWinPrizes.size());
    }

    public void initData(Collection<ActivityProduct> newActivityWinPrizes) {
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
    public ParticipationRecordExchangeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_participationrecord_exchanged, parent, false);
        return new ParticipationRecordExchangeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ParticipationRecordExchangeViewHolder holder, int position) {
        holder.bind(strings.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.strings == null ? 0 : this.strings.size();
    }

    public class ParticipationRecordExchangeViewHolder extends AutoBindingViewHolder<ItemParticipationrecordExchangedBinding, ActivityProduct> {
        public ParticipationRecordExchangeViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemParticipationrecordExchangedBinding getBinding(View itemView) {
            return ItemParticipationrecordExchangedBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ActivityProduct data, int position) {
            binding.setActivityProduct(data);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.navigateToPrizeOrder(data);
                }
            });
        }
    }
}
