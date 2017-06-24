package com.j1j2.pifalao.feature.orderrate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.OrderDetail;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.feature.orders.OrderProductViewHolder;

import java.util.List;

/**
 * Created by alienzxh on 16-4-27.
 */
public class OrderRateProductAdapter extends RecyclerView.Adapter<OrderProductViewHolder> {
    private List<OrderDetail.OrderProductDetail> orderProductDetails;

    public OrderRateProductAdapter(List<OrderDetail.OrderProductDetail> orderProductDetails) {
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
        holder.bind(orderProductDetails.get(position).getThumbImgPath(), position);
    }

    @Override
    public int getItemCount() {
        return null == orderProductDetails ? 0 : orderProductDetails.size();
    }
}
