package com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.recyclerviewchoicemode.SelectableHolder;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.ProductCategory;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemDeliverysortBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-8.
 */
public class DeliverySortAdapter extends RecyclerView.Adapter<DeliverySortAdapter.DeliverySortViewHolder> {

    private List<ProductCategory> productCategories;
    private SingleSelector singleSelector;

    public DeliverySortAdapter(List<ProductCategory> productCategories, SingleSelector singleSelector) {
        this.productCategories = productCategories;
        this.singleSelector = singleSelector;
    }

    public interface OnSortClickListener {
        void onSortClick(View view, ProductCategory productCategory, int position);
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
            holder.bind(null, position);
        } else if (position == 1) {
            holder.bind(null, position);
        } else if (position == 2) {
            holder.bind(null, position);
        } else
            holder.bind(productCategories.get(position - 3), position);

    }

    @Override
    public int getItemCount() {
        return null == productCategories ? 0 : (productCategories.size() + 3);
    }

    public class DeliverySortViewHolder extends AutoBindingViewHolder<ItemDeliverysortBinding, ProductCategory> implements SelectableHolder {
        private SingleSelector singleSelector;
        private boolean mIsSelectable = false;
        private Context context;

        public DeliverySortViewHolder(View itemView, SingleSelector singleSelector) {
            super(itemView);
            this.singleSelector = singleSelector;
            this.context = itemView.getContext();
        }

        @Override
        protected ItemDeliverysortBinding getBinding(View itemView) {
            return ItemDeliverysortBinding.bind(itemView);
        }

        @Override
        public void bind(@Nullable final ProductCategory data, final int position) {

            singleSelector.bindHolder(this, position, getItemId());
            binding.setPosition(position);

            if (position == 0) {
                binding.setSortName("销量排行");
                binding.icon.setText(context.getText(R.string.icon_hot_fill));
                binding.icon.setTextColor(0xffff9900);
            } else if (position == 1) {
                binding.setSortName("折扣促销");
                binding.icon.setText(context.getText(R.string.icon_discount));
                binding.icon.setTextColor(0xff4ab134);
            } else if (position == 2) {
                binding.setSortName("新品推荐");
                binding.icon.setText(context.getText(R.string.icon_new));
                binding.icon.setTextColor(0xffff0000);
            } else if (data != null)
                binding.setSortName(data.getName());
            //_______________________________________________________________________________________
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleSelector.setSelected(position, getItemId(), true);
                    if (onSortClickListener != null) {
                        onSortClickListener.onSortClick(v, data, position);
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
