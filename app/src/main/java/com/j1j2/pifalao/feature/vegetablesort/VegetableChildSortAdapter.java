package com.j1j2.pifalao.feature.vegetablesort;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.SecondarySort;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemVegetableChildsortBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-6.
 */
public class VegetableChildSortAdapter extends RecyclerView.Adapter<VegetableChildSortAdapter.VegetableChildSortViewHolder> {
    private SecondarySort secondarySort;

    public VegetableChildSortAdapter(SecondarySort secondarySort) {
        this.secondarySort = secondarySort;
    }

    public interface OnChildSortClickListener {
        void onChildSortClick(View view, ProductSort productSort, int position);
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
        if (position < secondarySort.getChildFoodSorts().size())
            holder.bind(secondarySort.getChildFoodSorts().get(position), position);
        else
            holder.bind(secondarySort.getParentProductSort(), position);
    }

    @Override
    public int getItemCount() {
        return null == secondarySort.getChildFoodSorts() ? 0 : secondarySort.getChildFoodSorts().size() + 1;
    }

    public class VegetableChildSortViewHolder extends AutoBindingViewHolder<ItemVegetableChildsortBinding, ProductSort> {
        public VegetableChildSortViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemVegetableChildsortBinding getBinding(View itemView) {
            return ItemVegetableChildsortBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ProductSort data, final int position) {
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
