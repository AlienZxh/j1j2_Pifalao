package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.quantityview.StateQuantityView;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemDeliveryproductsBinding;

import java.util.Collection;
import java.util.List;

/**
 * Created by alienzxh on 16-4-8.
 */
public class  DeliveryProductsAdapter extends RecyclerView.Adapter<DeliveryProductsAdapter.DeliveryProductsViewHolder> {

    private List<ProductSimple> productSimples;
    private ShopCart shopCart;

    public DeliveryProductsAdapter(List<ProductSimple> productSimples) {
        this.productSimples = productSimples;
    }

    public void setShopCart(ShopCart shopCart) {
        this.shopCart = shopCart;
        notifyDataSetChanged();
    }

    public interface OnProductClickListener {
        void onProductClick(View view, ProductSimple productSimple, int position);

        void onQuantityChange(StateQuantityView view, ProductSimple productSimple, int position, int value);

        void onEnableStateChange(StateQuantityView view, ProductSimple productSimple, int position, boolean isEnable);

        boolean canEnable(StateQuantityView view);
    }

    private OnProductClickListener onProductClickListener;

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    public void initData(Collection<ProductSimple> newProductSimples) {
        productSimples.clear();
        if (null != productSimples && null != newProductSimples) {
            productSimples.addAll(newProductSimples);
        }
        notifyDataSetChanged();
    }

    public void addAll(Collection<ProductSimple> newProductSimples) {
        if (null == productSimples)
            return;
        int startIndex = productSimples.size();
        productSimples.addAll(startIndex, newProductSimples);
        notifyItemRangeInserted(startIndex, newProductSimples.size());
    }

    @Override
    public DeliveryProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_deliveryproducts, parent, false);
        return new DeliveryProductsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DeliveryProductsViewHolder holder, int position) {
        holder.bind(productSimples.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == productSimples ? 0 : productSimples.size();
    }

    public class DeliveryProductsViewHolder extends AutoBindingViewHolder<ItemDeliveryproductsBinding, ProductSimple> {
        public DeliveryProductsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemDeliveryproductsBinding getBinding(View itemView) {
            return ItemDeliveryproductsBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ProductSimple data, final int position) {
            binding.setProductSimple(data);
            binding.setShopCart(shopCart);
            binding.quantityview.setOnQuantityChangeListener(new StateQuantityView.OnQuantityChangeListener() {
                @Override
                public void onQuantityChange(StateQuantityView view, int value) {
                    if (onProductClickListener != null) {
                        onProductClickListener.onQuantityChange(binding.quantityview, data, position, value);
                    }
                }

                @Override
                public void onEnableStateChange(StateQuantityView view, boolean isEnable) {
                    if (onProductClickListener != null) {
                        onProductClickListener.onEnableStateChange(binding.quantityview, data, position, isEnable);
                    }
                }

                @Override
                public boolean canEnable() {
                    if (onProductClickListener != null)
                        return onProductClickListener.canEnable(binding.quantityview);
                    return false;
                }
            });
            binding.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductClickListener != null) {
                        onProductClickListener.onProductClick(v, data, position);
                    }
                }
            });
        }
    }
}
