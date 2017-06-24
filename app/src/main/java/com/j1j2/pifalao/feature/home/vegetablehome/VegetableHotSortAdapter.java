package com.j1j2.pifalao.feature.home.vegetablehome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ProductCategory;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemHotsortBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-6.
 */
public class VegetableHotSortAdapter extends RecyclerView.Adapter<VegetableHotSortAdapter.VegetableHotSortViewHolder> {

    private List<ProductCategory> productSorts;

    public VegetableHotSortAdapter(List<ProductCategory> productSorts) {
        this.productSorts = productSorts;
    }

    public interface OnHotSortClickListener {
        void onHotSortClick(View view, ProductCategory productCategory, int position);
    }

    private OnHotSortClickListener onHotSortClickListener;

    public void setOnChildSortClickListener(OnHotSortClickListener onHotSortClickListener) {
        this.onHotSortClickListener = onHotSortClickListener;
    }

    @Override
    public VegetableHotSortViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hotsort, parent, false);
        return new VegetableHotSortViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VegetableHotSortViewHolder holder, int position) {
        holder.bind(productSorts.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == productSorts ? 0 : productSorts.size();
    }

    public class VegetableHotSortViewHolder extends AutoBindingViewHolder<ItemHotsortBinding, ProductCategory> {

        public VegetableHotSortViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemHotsortBinding getBinding(View itemView) {
            return ItemHotsortBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ProductCategory data, final int position) {
            binding.setProductSort(data);
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onHotSortClickListener != null) {
                        onHotSortClickListener.onHotSortClick(v, data, position);
                    }
                }
            });
        }
    }
}
