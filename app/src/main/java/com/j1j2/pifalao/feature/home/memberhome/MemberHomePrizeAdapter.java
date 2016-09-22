package com.j1j2.pifalao.feature.home.memberhome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemMemberhomePrizeBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-9-3.
 */
public class MemberHomePrizeAdapter extends RecyclerView.Adapter<MemberHomePrizeAdapter.MemberHomePrizeViewHolder> {

    public interface MemberHomePrizeAdapterListener {
        void navigateToPrizeDetail(ActivityWinPrize prize);
    }

    private List<ActivityWinPrize> activityWinPrizes;

    private MemberHomePrizeAdapterListener listener;

    public MemberHomePrizeAdapter(List<ActivityWinPrize> activityWinPrizes) {
        this.activityWinPrizes = activityWinPrizes;
    }

    public void setListener(MemberHomePrizeAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public MemberHomePrizeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_memberhome_prize, parent, false);
        return new MemberHomePrizeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MemberHomePrizeViewHolder holder, int position) {
        holder.bind(activityWinPrizes.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.activityWinPrizes == null ? 0 : (this.activityWinPrizes.size() > 3 ? 3 : this.activityWinPrizes.size());
    }

    public class MemberHomePrizeViewHolder extends AutoBindingViewHolder<ItemMemberhomePrizeBinding, ActivityWinPrize> {

        public MemberHomePrizeViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemMemberhomePrizeBinding getBinding(View itemView) {
            return ItemMemberhomePrizeBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ActivityWinPrize data, int position) {
            binding.setActivityWinPrize(data);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.navigateToPrizeDetail(data);
                }
            });
        }
    }
}
