package com.j1j2.pifalao.feature.orders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.recyclerviewchoicemode.SelectableHolder;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemOrderstypeBinding;
import com.j1j2.pifalao.databinding.ItemOrderstypeHeadBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alienzxh on 16-8-31.
 */
public class OrdersTypeAdapter extends RecyclerView.Adapter<AutoBindingViewHolder> {

    public interface OrdersTypeAdapterListener {
        void onOrderTypeClick(int position);
    }

    private OrdersTypeAdapterListener listener;

    public void setListener(OrdersTypeAdapterListener listener) {
        this.listener = listener;
    }

    private List<String> strings;

    private SingleSelector singleSelector;

    public OrdersTypeAdapter(SingleSelector singleSelector) {
        strings = new ArrayList<>();
        strings.add("线上订单");
        strings.add("待付款");
        strings.add("已下单");
        strings.add("处理中");
        strings.add("已发货");
        strings.add("待评价");
        strings.add("已退订");
        strings.add("全部订单");
        strings.add("线下消费");
        strings.add("批发佬门店消费");
        this.singleSelector = singleSelector;
    }

    @Override
    public AutoBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_orderstype_head, parent, false);
            return new OrdersTypeHeadViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_orderstype, parent, false);
            return new OrdersTypeViewHolder(itemView, singleSelector);
        }

    }

    @Override
    public void onBindViewHolder(AutoBindingViewHolder holder, int position) {
        holder.bind(strings.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.strings == null ? 0 : strings.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 8)
            return 0;
        else
            return 1;
    }

    public class OrdersTypeViewHolder extends AutoBindingViewHolder<ItemOrderstypeBinding, String> implements SelectableHolder {

        private SingleSelector singleSelector;

        private boolean mIsSelectable = false;

        public OrdersTypeViewHolder(View itemView, SingleSelector singleSelector) {
            super(itemView);
            this.singleSelector = singleSelector;
        }

        @Override
        protected ItemOrderstypeBinding getBinding(View itemView) {
            return ItemOrderstypeBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull String data, final int position) {
            singleSelector.bindHolder(this, position, getItemId());
            binding.typename.setText(data);
            binding.typename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleSelector.setSelected(position, getItemId(), true);
                    if (listener != null) {
                        listener.onOrderTypeClick(position);
                    }
                }
            });
        }

        @Override
        public void setSelectable(boolean isSelectable) {
            boolean changed = isSelectable != mIsSelectable;
            mIsSelectable = isSelectable;

        }

        @Override
        public boolean isSelectable() {
            return mIsSelectable;
        }

        @Override
        public void setActivated(boolean activated) {
            itemView.setActivated(activated);
        }

        @Override
        public boolean isActivated() {
            return itemView.isActivated();
        }
    }

    public class OrdersTypeHeadViewHolder extends AutoBindingViewHolder<ItemOrderstypeHeadBinding, String> {
        public OrdersTypeHeadViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemOrderstypeHeadBinding getBinding(View itemView) {
            return ItemOrderstypeHeadBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull String data, int position) {
            binding.title.setText(strings.get(position));
        }
    }
}
