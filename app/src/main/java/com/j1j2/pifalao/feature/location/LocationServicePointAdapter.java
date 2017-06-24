package com.j1j2.pifalao.feature.location;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.Shop;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemLocationServicepointBinding;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by alienzxh on 16-3-13.
 */
public class LocationServicePointAdapter extends RecyclerView.Adapter<LocationServicePointAdapter.LocationServicePointViewHolder> {

    private List<Shop> shops;
    private boolean shownear;

    public LocationServicePointAdapter(List<Shop> shops) {
        this.shops = shops;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, Shop shop);

        void onInfoClickListener(View view, Shop shop);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setShownear(boolean shownear) {
        this.shownear = shownear;
    }

    @Override
    public LocationServicePointViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location_servicepoint, parent, false);
        return new LocationServicePointViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LocationServicePointViewHolder holder, int position) {
        holder.bind(shops.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == this.shops ? 0 : shops.size();
    }

    public class LocationServicePointViewHolder extends AutoBindingViewHolder<ItemLocationServicepointBinding, Shop> {

        public LocationServicePointViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemLocationServicepointBinding getBinding(View itemView) {
            return ItemLocationServicepointBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final Shop data, int position) {
            binding.setServicepoint(data);
            binding.setPosition(position);
            binding.setShowNear(shownear);
            if (position == 0) {
                binding.tag.setTagTopPadding(AutoUtils.getPercentHeightSize(5));
                binding.tag.setTagBottomPadding(AutoUtils.getPercentHeightSize(5));
                binding.tag.setTagLeftPadding(AutoUtils.getPercentWidthSize(8));
                binding.tag.setTagRightPadding(AutoUtils.getPercentWidthSize(8));
            }
            binding.setOnItemClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClickListener(v, data);
                }
            });
            binding.setOnInfoClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onInfoClickListener(v, data);
                }
            });
        }
    }
}
