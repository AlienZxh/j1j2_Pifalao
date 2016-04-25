package com.j1j2.pifalao.feature.collects;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.recyclerviewchoicemode.MultiSelector;
import com.j1j2.common.view.recyclerviewchoicemode.SelectableHolder;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.CollectedProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemCollectsBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-3-24.
 */
public class CollectsAdapter extends RecyclerView.Adapter<CollectsAdapter.CollectsViewHolder> {

    private List<CollectedProduct> collectedProducts;

    private MultiSelector multiSelector;

    private ObservableBoolean isModifyMode;

    public CollectsAdapter(List<CollectedProduct> collectedProducts, MultiSelector multiSelector, ObservableBoolean isModifyMode) {
        this.collectedProducts = collectedProducts;
        this.multiSelector = multiSelector;
        this.isModifyMode = isModifyMode;
    }

    public interface OnCollectClickListener {
        void onCollectClick(View view, CollectedProduct collectedProduct, int position);
    }

    private OnCollectClickListener onCollectClickListener;

    public void setOnCollectClickListener(CollectsAdapter.OnCollectClickListener onCollectClickListener) {
        this.onCollectClickListener = onCollectClickListener;
    }

    @Override
    public CollectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_collects, parent, false);
        return new CollectsViewHolder(itemView, multiSelector);
    }

    @Override
    public void onBindViewHolder(CollectsViewHolder holder, int position) {
        holder.bind(collectedProducts.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == collectedProducts ? 0 : collectedProducts.size();
    }

    public List<CollectedProduct> getCollectedProducts() {
        return collectedProducts;
    }

    public class CollectsViewHolder extends AutoBindingViewHolder<ItemCollectsBinding, CollectedProduct> implements SelectableHolder {
        private MultiSelector multiSelector;

        private boolean mIsSelectable = false;

        public CollectsViewHolder(View itemView, MultiSelector multiSelector) {
            super(itemView);
            this.multiSelector = multiSelector;
        }

        @Override
        protected ItemCollectsBinding getBinding(View itemView) {
            return ItemCollectsBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final CollectedProduct data, final int position) {
            multiSelector.bindHolder(this, position, getItemId());
            binding.setProductSimple(data);
            binding.setIsModifyMode(isModifyMode);
            binding.setIsSelect(multiSelector.isSelected(position, getItemId()));
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isModifyMode.get()) {
                        if (onCollectClickListener != null)
                            onCollectClickListener.onCollectClick(v, data, position);
                    } else {
                        multiSelector.setSelected(position, getItemId(), !multiSelector.isSelected(position, getItemId()));
                        notifyDataSetChanged();
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
