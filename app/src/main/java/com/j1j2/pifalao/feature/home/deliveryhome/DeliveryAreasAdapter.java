package com.j1j2.pifalao.feature.home.deliveryhome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemDeliveryAreasBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-23.
 */
public class DeliveryAreasAdapter extends RecyclerView.Adapter<DeliveryAreasAdapter.DeliveryAreasViewHolder> {
    private List<String> strings;

    public DeliveryAreasAdapter(List<String> strings) {
        this.strings = strings;
    }

    public interface OnAreaItemClickListener {
        void OnAreaItemClick();
    }

    private OnAreaItemClickListener onAreaItemClickListener;

    public void setOnAreaItemClickListener(OnAreaItemClickListener onAreaItemClickListener) {
        this.onAreaItemClickListener = onAreaItemClickListener;
    }

    @Override
    public DeliveryAreasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delivery_areas, parent, false);
        return new DeliveryAreasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeliveryAreasViewHolder holder, int position) {
        holder.bind(strings.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == strings ? 0 : strings.size();
    }

    public class DeliveryAreasViewHolder extends AutoBindingViewHolder<ItemDeliveryAreasBinding, String> {
        public DeliveryAreasViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemDeliveryAreasBinding getBinding(View itemView) {
            return ItemDeliveryAreasBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull String data, int position) {
            binding.setArea(data);
            binding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAreaItemClickListener != null)
                        onAreaItemClickListener.OnAreaItemClick();
                }
            });

        }
    }
}
