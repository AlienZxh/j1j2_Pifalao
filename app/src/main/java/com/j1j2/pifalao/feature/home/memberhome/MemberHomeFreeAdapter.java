package com.j1j2.pifalao.feature.home.memberhome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemMemberhomeFreeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alienzxh on 16-9-3.
 */
public class MemberHomeFreeAdapter extends RecyclerView.Adapter<MemberHomeFreeAdapter.MemberHomeFreeViewHlder> {

    public interface MemberHomeFreeAdapterListener {
        void navigateToPrizeDetail(ActivityProduct data);

        void navigateToPrizeConfirm(ActivityProduct data);
    }

    private List<ActivityProduct> activityProducts;

    private MemberHomeFreeAdapterListener listener;

    public MemberHomeFreeAdapter(List<ActivityProduct> activityProducts) {
        this.activityProducts = activityProducts;
    }

    public void initData(List<ActivityProduct> newActivityProducts) {
        if (null != activityProducts && null != newActivityProducts) {
            activityProducts.clear();
            activityProducts.addAll(newActivityProducts);
        } else if (null != newActivityProducts) {
            activityProducts = new ArrayList<>();
            activityProducts.addAll(newActivityProducts);
        }
        notifyDataSetChanged();
    }

    public void setListener(MemberHomeFreeAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public MemberHomeFreeViewHlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_memberhome_free, parent, false);
        return new MemberHomeFreeViewHlder(itemView);
    }

    @Override
    public void onBindViewHolder(MemberHomeFreeViewHlder holder, int position) {
        holder.bind(activityProducts.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.activityProducts == null ? 0 : this.activityProducts.size();
    }


    public class MemberHomeFreeViewHlder extends AutoBindingViewHolder<ItemMemberhomeFreeBinding, ActivityProduct> {
        public MemberHomeFreeViewHlder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemMemberhomeFreeBinding getBinding(View itemView) {
            return ItemMemberhomeFreeBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ActivityProduct data, int position) {
            binding.setActivityProduct(data);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (EmptyUtils.isEmpty(data.getStatistics().getRemain()) || data.getStatistics().getRemain() > 0)
                        if (listener != null)
                            listener.navigateToPrizeDetail(data);
                }
            });
            binding.prizeBtnOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.navigateToPrizeConfirm(data);
                }
            });
        }
    }
}
