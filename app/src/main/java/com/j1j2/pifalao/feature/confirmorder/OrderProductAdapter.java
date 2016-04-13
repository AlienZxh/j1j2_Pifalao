package com.j1j2.pifalao.feature.confirmorder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ShopCartItem;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemOrdersimpleBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-3-27.
 */
public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder> {
    private List<ShopCartItem> shopCartItems;

    public OrderProductAdapter(List<ShopCartItem> shopCartItems) {
        this.shopCartItems = shopCartItems;
    }

    @Override
    public OrderProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ordersimple, parent, false);
        return new OrderProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderProductViewHolder holder, int position) {
        holder.bind(shopCartItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == shopCartItems ? 0 : (shopCartItems.size() > 4 ? 4 : shopCartItems.size());
    }

    public class OrderProductViewHolder extends AutoBindingViewHolder<ItemOrdersimpleBinding, ShopCartItem> {
        public OrderProductViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemOrdersimpleBinding getBinding(View itemView) {
            return ItemOrdersimpleBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull ShopCartItem data, int position) {
            binding.setShopCartItem(data);
        }
    }
}
