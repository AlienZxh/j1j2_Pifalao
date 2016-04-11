package com.j1j2.pifalao.feature.home.vegetablehome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ProductSort;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemHotsortBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-6.
 */
public class VegetableHotSortAdapter extends RecyclerView.Adapter<VegetableHotSortAdapter.VegetableHotSortViewHolder> {

    private List<ProductSort> productSorts;

    public VegetableHotSortAdapter(List<ProductSort> productSorts) {
        this.productSorts = productSorts;
    }

    public interface OnHotSortClickListener {
        void onHotSortClick(View view, ProductSort productSort, int position);
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

    public class VegetableHotSortViewHolder extends AutoBindingViewHolder<ItemHotsortBinding, ProductSort> {

        public VegetableHotSortViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemHotsortBinding getBinding(View itemView) {
            return ItemHotsortBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ProductSort data, final int position) {
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
