package com.j1j2.pifalao.feature.orderproducts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemOrderproductListBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-2.
 */
public class OrderProductListAdapter extends RecyclerView.Adapter<OrderProductListAdapter.OrderProductListViewHolder> {

    private List<OrderProductSimple> orderProductSimples;
    private ShopCart shopCart;
    private int moduleType;

    public OrderProductListAdapter(List<OrderProductSimple> orderProductSimples, ShopCart shopCart, int moduleType) {
        this.orderProductSimples = orderProductSimples;
        this.shopCart = shopCart;
        this.moduleType = moduleType;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View v, OrderProductSimple orderProductSimple, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public OrderProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_orderproduct_list, parent, false);
        return new OrderProductListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderProductListViewHolder holder, int position) {
        holder.bind(orderProductSimples.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == orderProductSimples ? 0 : orderProductSimples.size();
    }

    public class OrderProductListViewHolder extends AutoBindingViewHolder<ItemOrderproductListBinding, OrderProductSimple> {
        public OrderProductListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemOrderproductListBinding getBinding(View itemView) {
            return ItemOrderproductListBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final OrderProductSimple data, final int position) {
            binding.setOrderProductSimple(data);
            binding.setShopCart(shopCart);
            binding.setServiceType(moduleType);
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClickListener(v, data, position);
                }
            });

        }
    }
}
