package com.j1j2.pifalao.feature.offlineorders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.OfflineOrderSimple;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemOfflineOrdersBinding;
import com.j1j2.pifalao.feature.orders.OrderProductAdapter;

import java.util.Collection;
import java.util.List;

/**
 * Created by alienzxh on 16-4-14.
 */
public class OfflineOrdersAdapter extends RecyclerView.Adapter<OfflineOrdersAdapter.OfflineOrdersViewHolder> {

    private List<OfflineOrderSimple> offlineOrderSimples;

    public OfflineOrdersAdapter(List<OfflineOrderSimple> offlineOrderSimples) {
        this.offlineOrderSimples = offlineOrderSimples;
    }

    public void addAll(Collection<OfflineOrderSimple> newOfflineOrders) {
        if (null == offlineOrderSimples)
            return;
        int startIndex = offlineOrderSimples.size();
        offlineOrderSimples.addAll(startIndex, newOfflineOrders);
        notifyItemRangeInserted(startIndex, newOfflineOrders.size());
    }

    public interface OnOfflineOrderClickListener {
        void onOfflineOrderClick(View v, OfflineOrderSimple offlineOrderSimple, int position);
    }

    private OnOfflineOrderClickListener onOfflineOrderClickListener;

    public void setOnOfflineOrderClickListener(OnOfflineOrderClickListener onOfflineOrderClickListener) {
        this.onOfflineOrderClickListener = onOfflineOrderClickListener;
    }

    @Override
    public OfflineOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offline_orders, parent, false);
        return new OfflineOrdersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OfflineOrdersViewHolder holder, int position) {
        holder.bind(offlineOrderSimples.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == offlineOrderSimples ? 0 : offlineOrderSimples.size();
    }

    public class OfflineOrdersViewHolder extends AutoBindingViewHolder<ItemOfflineOrdersBinding, OfflineOrderSimple> {
        private Context context;

        public OfflineOrdersViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
        }

        @Override
        protected ItemOfflineOrdersBinding getBinding(View itemView) {
            return ItemOfflineOrdersBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final OfflineOrderSimple data, final int position) {
            binding.setOfflineOrderSimple(data);
            binding.orderProductList.setLayoutManager(new GridLayoutManager(context, 4));
            OffllineOrderProductAdapter orderProductAdapter = new OffllineOrderProductAdapter(data.getProductDetails());
            binding.orderProductList.setAdapter(orderProductAdapter);
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onOfflineOrderClickListener != null)
                        onOfflineOrderClickListener.onOfflineOrderClick(v, data, position);
                }
            });
        }
    }
}
