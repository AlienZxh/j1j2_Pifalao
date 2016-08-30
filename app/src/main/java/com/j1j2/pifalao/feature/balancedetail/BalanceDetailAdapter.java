package com.j1j2.pifalao.feature.balancedetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.BalanceRecord;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemBalancedetailBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-5-12.
 */
public class BalanceDetailAdapter extends RecyclerView.Adapter<BalanceDetailAdapter.BalanceDetailViewHolder> {

    private List<BalanceRecord> balanceRecords;

    public BalanceDetailAdapter(List<BalanceRecord> balanceRecords) {
        this.balanceRecords = balanceRecords;
    }


    public void addBalanceRecords(List<BalanceRecord> balanceRecords) {
        this.balanceRecords.addAll(balanceRecords);
        notifyDataSetChanged();
    }

    public List<BalanceRecord> getBalanceRecords() {
        return balanceRecords;
    }

    public void clear() {
        this.balanceRecords.clear();
        notifyDataSetChanged();
    }

    @Override
    public BalanceDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_balancedetail, parent, false);
        return new BalanceDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BalanceDetailViewHolder holder, int position) {
        holder.bind(balanceRecords.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == this.balanceRecords ? 0 : balanceRecords.size();
    }

    public class BalanceDetailViewHolder extends AutoBindingViewHolder<ItemBalancedetailBinding, BalanceRecord> {
        public BalanceDetailViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemBalancedetailBinding getBinding(View itemView) {
            return ItemBalancedetailBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull BalanceRecord data, int position) {
            binding.setBalanceRecord(data);
        }
    }
}
