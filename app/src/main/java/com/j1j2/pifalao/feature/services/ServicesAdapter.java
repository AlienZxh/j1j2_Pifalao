package com.j1j2.pifalao.feature.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.City;
import com.j1j2.data.model.Module;
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
    private List<Module> modules;
    private int[] colors = {0xff1ba1e2, 0xfff09609, 0xFF22AC38, 0xff01c8c6, 0xffa3ca19, 0xffaaaaaa};
    private int[] iconId = {R.string.icon_service_shopservice, R.string.icon_service_delivery, R.string.icon_service_vegetable, R.string.icon_service_housekeeping, R.string.icon_service_vip, R.string.icon_service_more};

    private SparseArray<Integer> moduleColors = new SparseArray<>();
    private SparseArray<Integer> moduleIconId = new SparseArray<>();

    public ServicesAdapter(List<Module> modules) {
        this.modules = modules;
        moduleColors.put(Constant.ModuleType.SHOPSERVICE, colors[0]);
        moduleIconId.put(Constant.ModuleType.SHOPSERVICE, iconId[0]);

        moduleColors.put(Constant.ModuleType.DELIVERY, colors[1]);
        moduleIconId.put(Constant.ModuleType.DELIVERY, iconId[1]);

        moduleColors.put(Constant.ModuleType.VEGETABLE, colors[2]);
        moduleIconId.put(Constant.ModuleType.VEGETABLE, iconId[2]);

        moduleColors.put(Constant.ModuleType.HOUSEKEEPING, colors[3]);
        moduleIconId.put(Constant.ModuleType.HOUSEKEEPING, iconId[3]);

        moduleColors.put(Constant.ModuleType.VIP, colors[4]);
        moduleIconId.put(Constant.ModuleType.VIP, iconId[4]);

        moduleColors.put(Constant.ModuleType.MORE, colors[5]);
        moduleIconId.put(Constant.ModuleType.MORE, iconId[5]);
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, Module module, int position);
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
        holder.bind(modules.get(position), position);
    }

    public void addAll(Collection<Module> newModules) {
        if (null == modules || null == newModules)
            return;
        int startIndex = modules.size();
        modules.addAll(startIndex, newModules);
        notifyItemRangeInserted(startIndex, newModules.size());
    }

    public void add(Module newModule) {
        if (null == modules || null == newModule)
            return;
        int startIndex = modules.size();
        modules.add(startIndex, newModule);
        notifyItemRangeInserted(startIndex, 1);
    }


    @Override
    public int getItemCount() {
        return null == this.modules ? 0 : modules.size();
    }

    public class ServicesViewHolder extends AutoBindingViewHolder<ItemServiceBinding, Module> {
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
        public void bind(@NonNull final Module data, final int position) {

            binding.icon.setText(context.getResources().getString(moduleIconId.get(data.getModuleType()) == null ? iconId[5] : moduleIconId.get(data.getModuleType())));
            binding.icon.setTextColor(moduleColors.get(data.getModuleType()) == null ? colors[5] : moduleColors.get(data.getModuleType()));
            binding.setModule(data);
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