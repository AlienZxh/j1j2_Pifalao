package com.j1j2.pifalao.feature.couponselect;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.j1j2.common.view.recyclerviewchoicemode.SelectableHolder;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.Coupon;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemCouponselectListBinding;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by alienzxh on 16-4-3.
 */
public class CouponSelectListAdapter extends RecyclerView.Adapter<CouponSelectListAdapter.CouponSelectListViewHolder> {


    private List<Coupon> coupons;
    private SingleSelector singleSelector;

    public CouponSelectListAdapter(List<Coupon> coupons, SingleSelector singleSelector) {
        this.coupons = coupons;
        this.singleSelector = singleSelector;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    @Override
    public CouponSelectListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_couponselect_list, parent, false);
        return new CouponSelectListViewHolder(itemView, singleSelector);
    }

    @Override
    public void onBindViewHolder(CouponSelectListViewHolder holder, int position) {
        holder.bind(coupons.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == coupons ? 0 : coupons.size();
    }

    public class CouponSelectListViewHolder extends AutoBindingViewHolder<ItemCouponselectListBinding, Coupon> implements SelectableHolder {

        private SingleSelector singleSelector;

        private boolean mIsSelectable = false;

        public CouponSelectListViewHolder(View itemView, SingleSelector singleSelector) {
            super(itemView);
            this.singleSelector = singleSelector;
        }

        @Override
        protected ItemCouponselectListBinding getBinding(View itemView) {
            return ItemCouponselectListBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull Coupon data, final int position) {
            singleSelector.bindHolder(this, position, getItemId());
            binding.setCoupon(data);
            binding.setIsSelect(singleSelector.isSelected(position, getItemId()));
            binding.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleSelector.setSelected(position, getItemId(), !singleSelector.isSelected(position, getItemId()));
                    notifyDataSetChanged();
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
