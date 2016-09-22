package com.j1j2.pifalao.feature.home.memberhome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemMemberhomeLuckyBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-9-1.
 */
public class MemberHomeLuckyAdapter extends RecyclerView.Adapter<MemberHomeLuckyAdapter.MemberHomeLuckyViewHolder> {

    public interface MemberHomeLuckyAdapterListener {
        void navigateToPrizeDetail(ActivityProduct activityProduct);

    }

    private List<ActivityProduct> activityProducts;

    private MemberHomeLuckyAdapterListener listener;

    public MemberHomeLuckyAdapter(List<ActivityProduct> activityProducts) {
        this.activityProducts = activityProducts;
    }

    public void setListener(MemberHomeLuckyAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public MemberHomeLuckyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_memberhome_lucky, parent, false);
        return new MemberHomeLuckyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MemberHomeLuckyViewHolder holder, int position) {
        holder.bind(activityProducts.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.activityProducts == null ? 0 : this.activityProducts.size();
    }

    public class MemberHomeLuckyViewHolder extends AutoBindingViewHolder<ItemMemberhomeLuckyBinding, ActivityProduct> {

        public MemberHomeLuckyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemMemberhomeLuckyBinding getBinding(View itemView) {
            return ItemMemberhomeLuckyBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ActivityProduct data, int position) {
            binding.setActivityProduct(data);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.navigateToPrizeDetail(data);
                }
            });
            binding.nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.navigateToPrizeDetail(data);
                }
            });
        }
    }


}
