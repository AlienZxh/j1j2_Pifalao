package com.j1j2.pifalao.feature.services;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


    public ServicesAdapter(List<Module> modules) {
        this.modules = modules;

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
        return null == this.modules ? 0 : (modules.size() > 6 ? 6 : modules.size());
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

//            binding.icon.setTypeface(typeface);
            int iconId = Constant.moduleIconId.get(data.getModuleType());
            binding.icon.setText(context.getResources().getString(iconId == 0 ? R.string.icon_service_more : iconId));
            int color = Constant.moduleColors.get(data.getModuleType());
            binding.icon.setTextColor(color == 0 ? 0xffaaaaaa : color);
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