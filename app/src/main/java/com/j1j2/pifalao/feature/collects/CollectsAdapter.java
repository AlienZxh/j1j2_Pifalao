package com.j1j2.pifalao.feature.collects;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public CollectsAdapter(List<CollectedProduct> collectedProducts) {
        this.collectedProducts = collectedProducts;
    }

    @Override
    public CollectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_collects, parent, false);
        return new CollectsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CollectsViewHolder holder, int position) {
        holder.bind(collectedProducts.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == collectedProducts ? 0 : collectedProducts.size();
    }

    public class CollectsViewHolder extends AutoBindingViewHolder<ItemCollectsBinding, CollectedProduct> {
        public CollectsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemCollectsBinding getBinding(View itemView) {
            return ItemCollectsBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull CollectedProduct data, int position) {
            binding.setProductSimple(data);
        }
    }
}
