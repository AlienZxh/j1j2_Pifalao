package com.j1j2.pifalao.feature.home.vegetablehome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ProductSimple;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemActivityproductsBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-7.
 */
public class VegetableActivityProductAdapter extends RecyclerView.Adapter<VegetableActivityProductAdapter.VegetableActivityProductViewHolder> {

    private List<ProductSimple> productSimples;

    public VegetableActivityProductAdapter(List<ProductSimple> productSimples) {
        this.productSimples = productSimples;
    }

    public interface OnActivityProductClickListener {
        void onActivityProductClick(View v, ProductSimple productSimple, int position);
    }

    private OnActivityProductClickListener onActivityProductClickListener;

    public void setOnActivityProductClickListener(OnActivityProductClickListener onActivityProductClickListener) {
        this.onActivityProductClickListener = onActivityProductClickListener;
    }

    @Override
    public VegetableActivityProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activityproducts, parent, false);
        return new VegetableActivityProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VegetableActivityProductViewHolder holder, int position) {
        holder.bind(productSimples.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == productSimples ? 0 : productSimples.size();
    }

    public class VegetableActivityProductViewHolder extends AutoBindingViewHolder<ItemActivityproductsBinding, ProductSimple> {
        public VegetableActivityProductViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemActivityproductsBinding getBinding(View itemView) {
            return ItemActivityproductsBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ProductSimple data, final int position) {
            binding.setProductSimple(data);
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onActivityProductClickListener != null)
                        onActivityProductClickListener.onActivityProductClick(v, data, position);
                }
            });
        }
    }
}
