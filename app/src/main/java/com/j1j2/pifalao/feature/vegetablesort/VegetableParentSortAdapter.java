package com.j1j2.pifalao.feature.vegetablesort;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.recyclerviewchoicemode.SelectableHolder;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.ProductCategory;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemVegetableParentsortBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-6.
 */
public class VegetableParentSortAdapter extends RecyclerView.Adapter<VegetableParentSortAdapter.VegetableParentSortViewHolder> {

    private List<ProductCategory> secondarySorts;
    private SingleSelector singleSelector;

    public VegetableParentSortAdapter(List<ProductCategory> secondarySorts, SingleSelector singleSelector) {
        this.secondarySorts = secondarySorts;
        this.singleSelector = singleSelector;
    }

    public interface OnSortSelectListener {
        void onSortSelectListener(View v, ProductCategory secondarySort, int position);
    }

    private OnSortSelectListener onSortSelectListener;

    public void setOnSortSelectListener(OnSortSelectListener onSortSelectListener) {
        this.onSortSelectListener = onSortSelectListener;
    }

    @Override
    public VegetableParentSortViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vegetable_parentsort, parent, false);
        return new VegetableParentSortViewHolder(itemView, singleSelector);
    }

    @Override
    public void onBindViewHolder(VegetableParentSortViewHolder holder, int position) {
        holder.bind(secondarySorts.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == secondarySorts ? 0 : secondarySorts.size();
    }

    public class VegetableParentSortViewHolder extends AutoBindingViewHolder<ItemVegetableParentsortBinding, ProductCategory> implements SelectableHolder {

        private SingleSelector singleSelector;

        private boolean mIsSelectable = false;


        public VegetableParentSortViewHolder(View itemView, SingleSelector singleSelector) {
            super(itemView);
            this.singleSelector = singleSelector;
        }

        @Override
        protected ItemVegetableParentsortBinding getBinding(View itemView) {
            return ItemVegetableParentsortBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ProductCategory data, final int position) {
            singleSelector.bindHolder(this, position, getItemId());
            binding.setIsSelect(singleSelector.isSelected(position, getItemId()));
            binding.setSortName(data.getName());
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleSelector.setSelected(position, getItemId(), true);
                    notifyDataSetChanged();
                    if (onSortSelectListener != null)
                        onSortSelectListener.onSortSelectListener(v, data, position);
                }
            });
        }

        @Override
        public void setSelectable(boolean isSelectable) {
            boolean changed = isSelectable != mIsSelectable;
            mIsSelectable = isSelectable;

        }

        @Override
        public boolean isSelectable() {
            return mIsSelectable;
        }

        @Override
        public void setActivated(boolean activated) {
            itemView.setActivated(activated);
        }

        @Override
        public boolean isActivated() {
            return itemView.isActivated();
        }
    }
}
