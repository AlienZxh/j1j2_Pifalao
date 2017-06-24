package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.quantityview.StateQuantityView;
import com.j1j2.data.model.Product;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemDeliveryproductsBinding;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by alienzxh on 16-4-8.
 */
public class DeliveryProductsAdapter extends RecyclerView.Adapter<DeliveryProductsAdapter.DeliveryProductsViewHolder> {

    private List<Product> products;
    private ShopCart shopCart;

    public DeliveryProductsAdapter(List<Product> products) {
        this.products = products;
    }

    public void setShopCart(ShopCart shopCart) {
        this.shopCart = shopCart;
        notifyDataSetChanged();
    }

    public interface OnProductClickListener {

        void onQuantityChange(StateQuantityView view, Product productSimple, int position, int value);

        void onEnableStateChange(StateQuantityView view, Product productSimple, int position, boolean isEnable);

        boolean canEnable(StateQuantityView view);
    }

    private OnProductClickListener onProductClickListener;

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    public void initData(List<Product> newProducts) {
        products.clear();
        if (null != products && null != newProducts) {
            Collections.sort(newProducts, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    if (TextUtils.isEmpty(lhs.getMainThumImg()) && TextUtils.isEmpty(rhs.getMainThumImg()))
                        return 0;
                    else if (!TextUtils.isEmpty(lhs.getMainThumImg()) && !TextUtils.isEmpty(rhs.getMainThumImg()))
                        return 0;
                    else if (TextUtils.isEmpty(lhs.getMainThumImg()) && !TextUtils.isEmpty(rhs.getMainThumImg()))
                        return 1;
                    else if (!TextUtils.isEmpty(lhs.getMainThumImg()) && TextUtils.isEmpty(rhs.getMainThumImg()))
                        return -1;
                    else
                        return 0;
                }
            });
            products.addAll(newProducts);
        }
        notifyDataSetChanged();
    }

    public void addAll(List<Product> newProducts) {
        if (null == products || null == newProducts)
            return;
        Collections.sort(newProducts, new Comparator<Product>() {
            @Override
            public int compare(Product lhs, Product rhs) {
                if (TextUtils.isEmpty(lhs.getMainThumImg()) && TextUtils.isEmpty(rhs.getMainThumImg()))
                    return 0;
                else if (!TextUtils.isEmpty(lhs.getMainThumImg()) && !TextUtils.isEmpty(rhs.getMainThumImg()))
                    return 0;
                else if (TextUtils.isEmpty(lhs.getMainThumImg()) && !TextUtils.isEmpty(rhs.getMainThumImg()))
                    return 1;
                else if (!TextUtils.isEmpty(lhs.getMainThumImg()) && TextUtils.isEmpty(rhs.getMainThumImg()))
                    return -1;
                else
                    return 0;
            }
        });
        int startIndex = products.size();
        products.addAll(startIndex, newProducts);
        notifyItemRangeInserted(startIndex, newProducts.size());
    }

    @Override
    public DeliveryProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_deliveryproducts, parent, false);
        return new DeliveryProductsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DeliveryProductsViewHolder holder, int position) {
        holder.bind(products.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == products ? 0 : products.size();
    }

    public class DeliveryProductsViewHolder extends AutoBindingViewHolder<ItemDeliveryproductsBinding, Product> {
        public DeliveryProductsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemDeliveryproductsBinding getBinding(View itemView) {
            return ItemDeliveryproductsBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final Product data, final int position) {
            binding.setProduct(data);
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

        }
    }
}
