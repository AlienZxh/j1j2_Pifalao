package com.j1j2.pifalao.feature.orders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.OrderProductDetail;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemOrderproductBinding;
import com.j1j2.pifalao.databinding.ItemOrdersimpleBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-3-27.
 */
public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder> {
    private List<OrderProductDetail> orderProductDetails;

    public OrderProductAdapter(List<OrderProductDetail> orderProductDetails) {
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
        holder.bind(orderProductDetails.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == orderProductDetails ? 0 : orderProductDetails.size();
    }

    public class OrderProductViewHolder extends AutoBindingViewHolder<ItemOrderproductBinding, OrderProductDetail> {
        public OrderProductViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemOrderproductBinding getBinding(View itemView) {
            return ItemOrderproductBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull OrderProductDetail data, int position) {
            binding.setOrderProductDetail(data);
        }
    }
}
