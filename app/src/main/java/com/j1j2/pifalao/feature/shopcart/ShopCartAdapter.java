package com.j1j2.pifalao.feature.shopcart;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.j1j2.common.view.quantityview.QuantityView;
import com.j1j2.data.model.City;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemShopcartBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-3-21.
 */
public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.ShopCartViewHolder> {
    private List<ShopCartItem> shopCartItems;
    private int moduleType;

    public ShopCartAdapter(List<ShopCartItem> shopCartItems, int moduleType) {
        this.shopCartItems = shopCartItems;
        this.moduleType = moduleType;
    }

    public interface OnShopCartChangeListener {

        void onLayoutClickListener(View view, ShopCartItem shopCart, int position);

        void onRemoveBtnClickListener(View view, ShopCartItem shopCart, int position);

        void onQuantityChangeListener(View view, ShopCartItem shopCart, int quantity, int position);

    }

    private OnShopCartChangeListener onShopCartChangeListener;

    public void setOnShopCartChangeListener(OnShopCartChangeListener onShopCartChangeListener) {
        this.onShopCartChangeListener = onShopCartChangeListener;
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
        public void bind(@NonNull final ShopCartItem data, final int position) {
            binding.setShopCartItem(data);
            binding.setServiceType(moduleType);
            binding.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onShopCartChangeListener != null)
                        onShopCartChangeListener.onLayoutClickListener(v, data, position);
                }
            });
            binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onShopCartChangeListener != null)
                        onShopCartChangeListener.onRemoveBtnClickListener(v, data, position);
                }
            });
            binding.quantityview.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
                @Override
                public void onQuantityChange(QuantityView view, int value) {
                    if (onShopCartChangeListener != null)
                        onShopCartChangeListener.onQuantityChangeListener(view, data, view.getQuantity(), position);
                }
            });
        }
    }
}
