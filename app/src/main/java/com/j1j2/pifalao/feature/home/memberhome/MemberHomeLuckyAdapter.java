package com.j1j2.pifalao.feature.home.memberhome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.app.recyclerviewadapter.BaseViewHolder;
import com.j1j2.pifalao.app.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.pifalao.databinding.ItemMemberhomeLuckyBinding;
import com.j1j2.pifalao.feature.showorders.ShowOrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alienzxh on 16-9-1.
 */
public class MemberHomeLuckyAdapter extends RecyclerArrayAdapter<ActivityProduct> {

    public interface MemberHomeLuckyAdapterListener {
        void navigateToPrizeDetail(ActivityProduct activityProduct);

    }

    private MemberHomeLuckyAdapterListener listener;

    public MemberHomeLuckyAdapter(Context context, MemberHomeLuckyAdapterListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_memberhome_lucky, parent, false);
        return new MemberHomeLuckyViewHolder(itemView);
    }

    public class MemberHomeLuckyViewHolder extends BaseViewHolder<ItemMemberhomeLuckyBinding, ActivityProduct> {

        public MemberHomeLuckyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemMemberhomeLuckyBinding getBinding(View itemView) {
            return ItemMemberhomeLuckyBinding.bind(itemView);
        }

        @Override
        public void setData(final ActivityProduct data) {
            super.setData(data);
            binding.setActivityProduct(data);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.navigateToPrizeDetail(data);
                }
            });
            binding.nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.navigateToPrizeDetail(data);
                }
            });
        }
    }


}
