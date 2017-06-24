package com.j1j2.pifalao.feature.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemServiceBinding;

import java.util.Collection;
import java.util.List;

/**
 * Created by alienzxh on 16-3-14.
 */
public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder> {
    private List<ShopSubscribeService> shopSubscribeServices;


    public ServicesAdapter(List<ShopSubscribeService> shopSubscribeServices) {
        this.shopSubscribeServices = shopSubscribeServices;

    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, ShopSubscribeService shopSubscribeService, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ServicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service, parent, false);
        return new ServicesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ServicesViewHolder holder, int position) {
        holder.bind(shopSubscribeServices.get(position), position);
    }

    public void addAll(Collection<ShopSubscribeService> newShopSubscribeServices) {
        if (null == shopSubscribeServices || null == newShopSubscribeServices)
            return;
        int startIndex = shopSubscribeServices.size();
        shopSubscribeServices.addAll(startIndex, newShopSubscribeServices);
        notifyItemRangeInserted(startIndex, newShopSubscribeServices.size());
    }

    public void add(ShopSubscribeService newShopSubscribeService) {
        if (null == shopSubscribeServices || null == newShopSubscribeService)
            return;
        int startIndex = shopSubscribeServices.size();
        shopSubscribeServices.add(startIndex, newShopSubscribeService);
        notifyItemRangeInserted(startIndex, 1);
    }


    @Override
    public int getItemCount() {
        return null == this.shopSubscribeServices ? 0 : (shopSubscribeServices.size() > 6 ? 6 : shopSubscribeServices.size());
    }

    public class ServicesViewHolder extends AutoBindingViewHolder<ItemServiceBinding, ShopSubscribeService> {
        Context context;

        public ServicesViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
        }

        @Override
        protected ItemServiceBinding getBinding(View itemView) {
            return ItemServiceBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ShopSubscribeService data, final int position) {

//            binding.icon.setTypeface(typeface);
            int iconId = Constant.moduleIconId.get(data.getServiceType());
            binding.icon.setText(context.getResources().getString(iconId == 0 ? R.string.icon_service_more : iconId));
            int color = Constant.moduleColors.get(data.getServiceType());
            binding.icon.setTextColor(color == 0 ? 0xffaaaaaa : color);
            binding.setShopSubscribeService(data);
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClickListener(v, data, position);
                }
            });
        }
    }
}