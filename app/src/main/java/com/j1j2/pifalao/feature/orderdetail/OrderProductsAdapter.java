package com.j1j2.pifalao.feature.orderdetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.OrderProductDetail;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemOrderproductsBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-3-23.
 */
public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.OrderProductsViewHolder> {

    private List<OrderProductDetail> productDetails;

    public OrderProductsAdapter(List<OrderProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View v, OrderProductDetail orderProductDetail, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public OrderProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_orderproducts, parent, false);
        return new OrderProductsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderProductsViewHolder holder, int position) {
        holder.bind(productDetails.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == productDetails ? 0 : productDetails.size();
    }

    public class OrderProductsViewHolder extends AutoBindingViewHolder<ItemOrderproductsBinding, OrderProductDetail> {
        public OrderProductsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemOrderproductsBinding getBinding(View itemView) {
            return ItemOrderproductsBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final OrderProductDetail data, final int position) {
            binding.setOrderProductDetail(data);
            binding.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClickListener(v, data, position);
                }
            });
        }
    }
}
