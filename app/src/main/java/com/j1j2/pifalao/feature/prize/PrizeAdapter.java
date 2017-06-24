package com.j1j2.pifalao.feature.prize;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemPrizeBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by alienzxh on 16-9-1.
 */
public class PrizeAdapter extends RecyclerView.Adapter<PrizeAdapter.PrizeViewHolder> {

    public interface PrizeAdapterListener {
        void navigateToPrizeOrderTimeline( ActivityWinPrize data);

        void showCatNumDialog(ActivityWinPrize data);
    }

    private List<ActivityWinPrize> strings;
    private int prizeType;


    private PrizeAdapterListener listener;

    public PrizeAdapter(List<ActivityWinPrize> strings, int prizeType) {
        this.strings = strings;
        this.prizeType = prizeType;
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

    public void setListener(PrizeAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public PrizeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prize, parent, false);
        return new PrizeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PrizeViewHolder holder, int position) {
        holder.bind(strings.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.strings == null ? 0 : this.strings.size();
    }

    public class PrizeViewHolder extends AutoBindingViewHolder<ItemPrizeBinding, ActivityWinPrize> {
        public PrizeViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemPrizeBinding getBinding(View itemView) {
            return ItemPrizeBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ActivityWinPrize data, int position) {
            binding.setActivityWinPrize(data);
            binding.setPrizeType(prizeType);
            binding.prizeorderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.navigateToPrizeOrderTimeline(data);
                }
            });
            binding.catNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.showCatNumDialog(data);
                }
            });
        }
    }
}
