package com.j1j2.pifalao.feature.products;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.data.model.Product;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.ShopCart;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemProductsBinding;

import java.util.Collection;
import java.util.List;

/**
 * Created by alienzxh on 16-3-15.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private List<Product> productSimples;
    private ShopCart shopCart;
    private int moduleType;

    public ProductsAdapter(List<Product> productSimples, int moduleType) {
        this.productSimples = productSimples;
        this.moduleType = moduleType;
    }

    public void addAll(Collection<Product> newProductSimples) {
        if (null == productSimples)
            return;
        int startIndex = productSimples.size();
        productSimples.addAll(startIndex, newProductSimples);
        notifyItemRangeInserted(startIndex, newProductSimples.size());
    }

    public void initData(Collection<Product> newProductSimples) {
        productSimples.clear();
        if (null != productSimples && null != newProductSimples) {
            productSimples.addAll(newProductSimples);
        }
        notifyDataSetChanged();
    }

    public void setShopCart(ShopCart shopCart) {
        this.shopCart = shopCart;
        notifyDataSetChanged();
    }

    public interface OnProductsClickListener {
        void onItemClickListener(View view, Product productSimple, int position);

        void onAddIconClickListener(View view, Product productSimple, int position);
    }

    private OnProductsClickListener onItemClickListener;

    public void setOnProductsClickListener(OnProductsClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_products, parent, false);

        return new ProductsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        holder.bind(productSimples.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == productSimples ? 0 : productSimples.size();
    }

    public class ProductsViewHolder extends AutoBindingViewHolder<ItemProductsBinding, Product> {

        public ProductsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemProductsBinding getBinding(View itemView) {
            return ItemProductsBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final Product data, final int position) {
            binding.setProductSimple(data);
            if (!EmptyUtils.isEmpty(data.getProductUnits())) {
                binding.normalPrice.setText("市场价：" + data.getProductUnits().get(0).getRetialPrice() + "元/" + data.getProductUnits().get(0).getUnit());
                binding.normalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }

            binding.setServiceType(moduleType);
            binding.setShopCart(shopCart);

            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClickListener(v, data, position);
                }
            });
            binding.setOnAddClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onAddIconClickListener(v, data, position);
                }
            });
        }
    }
}
