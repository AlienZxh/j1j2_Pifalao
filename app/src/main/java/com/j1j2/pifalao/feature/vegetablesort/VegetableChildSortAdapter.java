package com.j1j2.pifalao.feature.vegetablesort;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ProductCategory;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemVegetableChildsortBinding;

/**
 * Created by alienzxh on 16-4-6.
 */
public class VegetableChildSortAdapter extends RecyclerView.Adapter<VegetableChildSortAdapter.VegetableChildSortViewHolder> {
    private ProductCategory secondarySort;

    public VegetableChildSortAdapter(ProductCategory secondarySort) {
        this.secondarySort = secondarySort;
    }

    public interface OnChildSortClickListener {
        void onChildSortClick(View view, ProductCategory productSort, int position);
    }

    private OnChildSortClickListener onChildSortClickListener;

    public void setOnChildSortClickListener(OnChildSortClickListener onChildSortClickListener) {
        this.onChildSortClickListener = onChildSortClickListener;
    }

    @Override
    public VegetableChildSortViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vegetable_childsort, parent, false);
        return new VegetableChildSortViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VegetableChildSortViewHolder holder, int position) {

        if (position < secondarySort.getChildCategories().size())
            holder.bind(secondarySort.getChildCategories().get(position), position);
        else {
            holder.bind(secondarySort, position);
        }

    }

    @Override
    public int getItemCount() {
        return null == secondarySort.getChildCategories() || secondarySort.getChildCategories().size() <= 0 ? 0 : secondarySort.getChildCategories().size() + 1;
    }

    public class VegetableChildSortViewHolder extends AutoBindingViewHolder<ItemVegetableChildsortBinding, ProductCategory> {
        public VegetableChildSortViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemVegetableChildsortBinding getBinding(View itemView) {
            return ItemVegetableChildsortBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ProductCategory data, final int position) {
            binding.setProductSort(data);
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onChildSortClickListener != null)
                        onChildSortClickListener.onChildSortClick(v, data, position);
                }
            });
        }
    }
}
