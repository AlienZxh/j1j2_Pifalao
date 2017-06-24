package com.j1j2.pifalao.feature.orders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.OrderDetail;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.pifalao.R;

import java.util.List;

/**
 * Created by alienzxh on 16-3-27.
 */
public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductViewHolder> {
    private List<OrderSimple.OrderSimpleProductDetail> orderProductDetails;

    public OrderProductAdapter(List<OrderSimple.OrderSimpleProductDetail> orderProductDetails) {
        this.orderProductDetails = orderProductDetails;
    }

    @Override
    public OrderProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_orderproduct, parent, false);
        return new OrderProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderProductViewHolder holder, int position) {
        holder.bind(orderProductDetails.get(position).getImageThumb(), position);
    }

    @Override
    public int getItemCount() {
        return null == orderProductDetails ? 0 : (orderProductDetails.size() > 4 ? 4 : orderProductDetails.size());
    }


}
