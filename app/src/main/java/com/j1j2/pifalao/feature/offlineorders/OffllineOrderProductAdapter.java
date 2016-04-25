package com.j1j2.pifalao.feature.offlineorders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.OfflineOrderProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.feature.orders.OrderProductViewHolder;

import java.util.List;

/**
 * Created by alienzxh on 16-4-14.
 */
public class OffllineOrderProductAdapter extends RecyclerView.Adapter<OrderProductViewHolder> {

    private List<OfflineOrderProduct> offlineOrderProducts;

    public OffllineOrderProductAdapter(List<OfflineOrderProduct> offlineOrderProducts) {
        this.offlineOrderProducts = offlineOrderProducts;
    }

    @Override
    public OrderProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_orderproduct, parent, false);
        return new OrderProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderProductViewHolder holder, int position) {
        holder.bind(offlineOrderProducts.get(position).getImage(), position);
    }

    @Override
    public int getItemCount() {
        return null == offlineOrderProducts ? 0 : (offlineOrderProducts.size() > 4 ? 4 : offlineOrderProducts.size());
    }
}
