package com.j1j2.pifalao.feature.productdetail.record;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ProductBuyRecord;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemProductdetailRecordBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-11.
 */
public class ProductDetailRecordAdapter extends RecyclerView.Adapter<ProductDetailRecordAdapter.ProductDetailRecordViewHolder> {

    private List<ProductBuyRecord> productBuyRecords;

    public ProductDetailRecordAdapter(List<ProductBuyRecord> productBuyRecords) {
        this.productBuyRecords = productBuyRecords;
    }

    @Override
    public ProductDetailRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_productdetail_record, parent, false);
        return new ProductDetailRecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductDetailRecordViewHolder holder, int position) {
        holder.bind(productBuyRecords.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == productBuyRecords ? 0 : productBuyRecords.size();
    }

    public class ProductDetailRecordViewHolder extends AutoBindingViewHolder<ItemProductdetailRecordBinding, ProductBuyRecord> {
        public ProductDetailRecordViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemProductdetailRecordBinding getBinding(View itemView) {
            return ItemProductdetailRecordBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull ProductBuyRecord data, int position) {
            binding.setProductBuyRecord(data);
        }
    }
}
