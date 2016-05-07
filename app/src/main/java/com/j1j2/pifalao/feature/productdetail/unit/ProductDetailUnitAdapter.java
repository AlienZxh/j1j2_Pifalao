package com.j1j2.pifalao.feature.productdetail.unit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.recyclerviewchoicemode.SelectableHolder;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemProductdetailUnitBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-3-21.
 */
public class ProductDetailUnitAdapter extends RecyclerView.Adapter<ProductDetailUnitAdapter.ProductDetailUnitViewHolder> {
    private List<ProductUnit> productUnits;
    private SingleSelector singleSelector;
    private String baseUnit;
    private int moduleType;

    public ProductDetailUnitAdapter(List<ProductUnit> productUnits, SingleSelector singleSelector, String baseUnit, int moduleType) {
        this.productUnits = productUnits;
        this.singleSelector = singleSelector;
        this.baseUnit = baseUnit;
        this.moduleType = moduleType;
    }

    public interface OnUnitItemClickListener {
        void OnUnitItemClickListener(View view, ProductUnit unit, int position);
    }

    private OnUnitItemClickListener onItemClickListener;

    public void setOnUnitItemClickListener(OnUnitItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ProductDetailUnitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_productdetail_unit, parent, false);
        return new ProductDetailUnitViewHolder(itemView, singleSelector, baseUnit);
    }

    @Override
    public void onBindViewHolder(ProductDetailUnitViewHolder holder, int position) {
        holder.bind(productUnits.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == productUnits ? 0 : productUnits.size() > 3 ? 3 : productUnits.size();
    }

    public class ProductDetailUnitViewHolder extends AutoBindingViewHolder<ItemProductdetailUnitBinding, ProductUnit> implements SelectableHolder {
        private SingleSelector singleSelector;
        private boolean mIsSelectable = false;
        private String baseUnit;

        public ProductDetailUnitViewHolder(View itemView, SingleSelector singleSelector, String baseUnit) {
            super(itemView);
            this.singleSelector = singleSelector;
            this.baseUnit = baseUnit;
        }

        @Override
        protected ItemProductdetailUnitBinding getBinding(View itemView) {
            return ItemProductdetailUnitBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ProductUnit data, final int position) {
            singleSelector.bindHolder(this, position, getItemId());
            if (position == 0) {
                singleSelector.setSelected(0, getItemId(), true);
            }
//            binding.setIsShowBaseUnit(moduleType == Constant.ModuleType.SHOPSERVICE);
//            binding.setBaseUnit(baseUnit);
            binding.setProductUnit(data);
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleSelector.setSelected(position, getItemId(), true);
                    if (onItemClickListener != null) {
                        onItemClickListener.OnUnitItemClickListener(v, data, position);
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
