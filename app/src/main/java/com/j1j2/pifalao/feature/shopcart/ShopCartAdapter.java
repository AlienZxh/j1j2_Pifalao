package com.j1j2.pifalao.feature.shopcart;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ShopCartItem;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemShopcartBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-3-21.
 */
public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.ShopCartViewHolder> {
    private List<ShopCartItem> shopCartItems;

    public ShopCartAdapter(List<ShopCartItem> shopCartItems) {
        this.shopCartItems = shopCartItems;
    }

    @Override
    public ShopCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopcart, parent, false);
        return new ShopCartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShopCartViewHolder holder, int position) {
        holder.bind(shopCartItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == shopCartItems ? 0 : shopCartItems.size();
    }

    public class ShopCartViewHolder extends AutoBindingViewHolder<ItemShopcartBinding, ShopCartItem> {
        public ShopCartViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemShopcartBinding getBinding(View itemView) {
            return ItemShopcartBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull ShopCartItem data, int position) {
            binding.setShopCartItem(data);

        }
    }
}
