package com.j1j2.pifalao.feature.orderdetail.orderdetailtimeline;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.SaleOrderStateHistory;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemOrderTimelineBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-21.
 */
public class OrderDetailTimeLineAdapter extends RecyclerView.Adapter<OrderDetailTimeLineAdapter.OrderDetailTimeLineViewHolder> {


    private List<SaleOrderStateHistory> saleOrderStateHistories;


    public OrderDetailTimeLineAdapter(List<SaleOrderStateHistory> saleOrderStateHistories) {
        this.saleOrderStateHistories = saleOrderStateHistories;
    }

    @Override
    public OrderDetailTimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_timeline, parent, false);
        return new OrderDetailTimeLineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderDetailTimeLineViewHolder holder, int position) {
        holder.bind(saleOrderStateHistories.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == saleOrderStateHistories ? 0 : saleOrderStateHistories.size();
    }

    public class OrderDetailTimeLineViewHolder extends AutoBindingViewHolder<ItemOrderTimelineBinding, SaleOrderStateHistory> {
        public OrderDetailTimeLineViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemOrderTimelineBinding getBinding(View itemView) {
            return ItemOrderTimelineBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull SaleOrderStateHistory data, int position) {
            binding.setIsNowState(position == 0);
            binding.setSaleOrderStateHistory(data);
        }
    }
}
