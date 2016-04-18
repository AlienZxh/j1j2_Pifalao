package com.j1j2.pifalao.feature.home.morehome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.viewpager.autoscrollviewpager.RecyclingPagerAdapter;
import com.j1j2.data.model.Module;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemMorehomeBinding;
import com.j1j2.pifalao.databinding.ItemMorehomeHeadBinding;
import com.j1j2.pifalao.feature.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alienzxh on 16-4-12.
 */
public class MoreHomeAdapter extends RecyclerView.Adapter<AutoBindingViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0x01;

    private static final int VIEW_TYPE_CONTENT = 0x00;

    private List<Module> modules;

    private List<Module> subscribeModules;
    private List<Module> unSubscribeModules;


    public MoreHomeAdapter(List<Module> modules) {
        this.modules = modules;
        subscribeModules = new ArrayList<>();
        unSubscribeModules = new ArrayList<>();
        for (Module module : modules) {
            if (module.isSubscribed()) {
                subscribeModules.add(module);
            } else {
                unSubscribeModules.add(module);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, Module module, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public AutoBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_morehome_head, parent, false);
            return new MoreHomeHeadViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_morehome, parent, false);
            return new MoreHomeViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? VIEW_TYPE_HEADER : VIEW_TYPE_CONTENT;
    }

    public boolean isHeader(int position) {
        if (position == 0)
            return true;
        if (position == subscribeModules.size() + 1)
            return true;
        return false;
    }

    @Override
    public void onBindViewHolder(AutoBindingViewHolder holder, int position) {
        if (isHeader(position))
            holder.bind(position == 0 ? "已开通服务" : "暂未开通服务", position);
        else if (position < subscribeModules.size() + 1)
            holder.bind(subscribeModules.get(position - 1), position);
        else if (position > subscribeModules.size() + 1)
            holder.bind(unSubscribeModules.get(position - subscribeModules.size() - 2), position);

    }

    @Override
    public int getItemCount() {
        return null == modules ? 0 : (modules.size() + 2);
    }

    public class MoreHomeViewHolder extends AutoBindingViewHolder<ItemMorehomeBinding, Module> {
        private Context context;

        public MoreHomeViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
        }

        @Override
        protected ItemMorehomeBinding getBinding(View itemView) {
            return ItemMorehomeBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final Module data, final int position) {
            int iconId =Constant.moduleIconId.get(data.getModuleType());
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

    public class MoreHomeHeadViewHolder extends AutoBindingViewHolder<ItemMorehomeHeadBinding, String> {
        public MoreHomeHeadViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemMorehomeHeadBinding getBinding(View itemView) {
            return ItemMorehomeHeadBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull String data, int position) {
            binding.setTitle(data);
        }
    }
}
