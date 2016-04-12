package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.recyclerviewchoicemode.SelectableHolder;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.SecondarySort;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemDeliverysortBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-8.
 */
public class DeliverySortAdapter extends RecyclerView.Adapter<DeliverySortAdapter.DeliverySortViewHolder> {

    private SecondarySort secondarySort;
    private SingleSelector singleSelector;

    public DeliverySortAdapter(SecondarySort secondarySort, SingleSelector singleSelector) {
        this.secondarySort = secondarySort;
        this.singleSelector = singleSelector;
    }

    public interface OnSortClickListener {
        void onSortClick(View view, ProductSort parentSort, ProductSort childSort, int position);
    }

    private OnSortClickListener onSortClickListener;

    public void setOnSortClickListener(OnSortClickListener onSortClickListener) {
        this.onSortClickListener = onSortClickListener;
    }

    @Override
    public DeliverySortViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_deliverysort, parent, false);
        return new DeliverySortViewHolder(itemView, singleSelector);
    }

    @Override
    public void onBindViewHolder(DeliverySortViewHolder holder, int position) {

        if (position == 0) {
            holder.bind(secondarySort.getParentProductSort(), position);
        } else if (position == 1) {
            holder.bind(secondarySort.getParentProductSort(), position);
        } else
            holder.bind(secondarySort.getChildFoodSorts().get(position - 2), position);

    }

    @Override
    public int getItemCount() {
        return null == secondarySort.getChildFoodSorts() ? 0 : (secondarySort.getChildFoodSorts().size() + 2);
    }

    public class DeliverySortViewHolder extends AutoBindingViewHolder<ItemDeliverysortBinding, ProductSort> implements SelectableHolder {
        private SingleSelector singleSelector;
        private boolean mIsSelectable = false;

        public DeliverySortViewHolder(View itemView, SingleSelector singleSelector) {
            super(itemView);
            this.singleSelector = singleSelector;
        }

        @Override
        protected ItemDeliverysortBinding getBinding(View itemView) {
            return ItemDeliverysortBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ProductSort data, final int position) {

            singleSelector.bindHolder(this, position, getItemId());
            binding.setIsSelect(singleSelector.isSelected(position, getItemId()));
            binding.setPosition(position);
            if (position == 0)
                binding.setSortName("销量排行");
            else if (position == 1)
                binding.setSortName("折扣促销");
            else
                binding.setSortName(data.getSortName());
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleSelector.setSelected(position, getItemId(), true);
                    notifyDataSetChanged();
                    if (onSortClickListener != null) {
                        onSortClickListener.onSortClick(v, secondarySort.getParentProductSort(), data, position);
                    }
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
