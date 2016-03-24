package com.j1j2.pifalao.feature.coupons;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.Coupon;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemCouponsBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-3-23.
 */
public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.CouponsViewHolder> {

    private List<Coupon> coupons;

    public CouponsAdapter(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public CouponsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coupons, parent, false);
        return new CouponsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CouponsViewHolder holder, int position) {
        holder.bind(coupons.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == coupons ? 0 : coupons.size();
    }

    public class CouponsViewHolder extends AutoBindingViewHolder<ItemCouponsBinding, Coupon> {
        public CouponsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemCouponsBinding getBinding(View itemView) {
            return ItemCouponsBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull Coupon data, int position) {

        }
    }
}
