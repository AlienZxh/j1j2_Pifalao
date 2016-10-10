package com.j1j2.pifalao.feature.briberymoneys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.RedPacket;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.recyclerviewadapter.BaseViewHolder;
import com.j1j2.pifalao.app.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.pifalao.databinding.ItemBriberymoneryBinding;

/**
 * Created by alienzxh on 16-10-7.
 */

public class BriberyMoneryAdapter extends RecyclerArrayAdapter<RedPacket> {

    int redPacketState;

    public BriberyMoneryAdapter(Context context, int redPacketState) {
        super(context);
        this.redPacketState = redPacketState;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_briberymonery, parent, false);
        return new BriberyMoneryViewHolder(itemView);
    }

    public class BriberyMoneryViewHolder extends BaseViewHolder<ItemBriberymoneryBinding, RedPacket> {

        public BriberyMoneryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemBriberymoneryBinding getBinding(View itemView) {
            return ItemBriberymoneryBinding.bind(itemView);
        }

        @Override
        public void setData(RedPacket data) {
            super.setData(data);
            binding.setRedPacket(data);
            binding.setRedPacketState(redPacketState);
        }
    }
}
