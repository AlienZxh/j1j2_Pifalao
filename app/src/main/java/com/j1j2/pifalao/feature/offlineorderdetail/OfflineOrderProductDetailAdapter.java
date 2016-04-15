package com.j1j2.pifalao.feature.offlineorderdetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.OfflineOrderProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemOfflineProductdetailBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-14.
 */
public class OfflineOrderProductDetailAdapter extends RecyclerView.Adapter<OfflineOrderProductDetailAdapter.OfflineOrderProductDetailViewHolder> {

    private List<OfflineOrderProduct> offlineOrderProducts;

    public OfflineOrderProductDetailAdapter(List<OfflineOrderProduct> offlineOrderProducts) {
        this.offlineOrderProducts = offlineOrderProducts;
    }

    @Override
    public OfflineOrderProductDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offline_productdetail, parent, false);
        return new OfflineOrderProductDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OfflineOrderProductDetailViewHolder holder, int position) {
        holder.bind(offlineOrderProducts.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == offlineOrderProducts ? 0 : offlineOrderProducts.size();
    }

    public class OfflineOrderProductDetailViewHolder extends AutoBindingViewHolder<ItemOfflineProductdetailBinding, OfflineOrderProduct> {
        public OfflineOrderProductDetailViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemOfflineProductdetailBinding getBinding(View itemView) {
            return ItemOfflineProductdetailBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull OfflineOrderProduct data, int position) {
            binding.setOfflineOrderProduct(data);
        }
    }
}
