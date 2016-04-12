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
import com.j1j2.pifalao.feature.main.MainActivity;

import java.util.List;

/**
 * Created by alienzxh on 16-4-12.
 */
public class MoreHomeAdapter extends RecyclerView.Adapter<MoreHomeAdapter.MoreHomeViewHolder> {
    private List<Module> modules;


    private int[] colors = {0xff1ba1e2, 0xfff09609, 0xFF22AC38, 0xff01c8c6, 0xffa3ca19, 0xffaaaaaa};
    private int[] iconId = {R.string.icon_service_shopservice, R.string.icon_service_delivery, R.string.icon_service_vegetable, R.string.icon_service_housekeeping, R.string.icon_service_vip, R.string.icon_service_more};

    private SparseArray<Integer> moduleColors = new SparseArray<>();
    private SparseArray<Integer> moduleIconId = new SparseArray<>();

    public MoreHomeAdapter(List<Module> modules) {
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
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, Module module, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MoreHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_morehome, parent, false);
        return new MoreHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoreHomeViewHolder holder, int position) {
        holder.bind(modules.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == modules ? 0 : modules.size();
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
            binding.icon.setText(context.getResources().getString(moduleIconId.get(data.getModuleType()) == null ? iconId[5] : moduleIconId.get(data.getModuleType())));
            if (data.isSubscribed())
                binding.icon.setTextColor(moduleColors.get(data.getModuleType()) == null ? colors[5] : moduleColors.get(data.getModuleType()));
            else
                binding.icon.setTextColor(colors[5]);

            if (data.getModuleType() == Constant.ModuleType.DELIVERY && data.isSubscribed()) {
                binding.tag.setText(context.getResources().getText(R.string.icon_delivery_tag));
                binding.tag.setTextColor(0xffff9900);
            } else if (data.getModuleType() == Constant.ModuleType.SHOPSERVICE && data.isSubscribed()) {
                binding.tag.setText(context.getResources().getText(R.string.icon_pickbyself_tag));
                binding.tag.setTextColor(0xff4ab134);
            } else if (data.getModuleType() == Constant.ModuleType.VEGETABLE && data.isSubscribed()) {
                binding.tag.setText(context.getResources().getText(R.string.icon_pickbyself_tag));
                binding.tag.setTextColor(0xff4ab134);
            } else if (data.isSubscribed()) {
                binding.tag.setText(context.getResources().getText(R.string.icon_online_tag));
                binding.tag.setTextColor(0xff00a0e9);
            }

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
