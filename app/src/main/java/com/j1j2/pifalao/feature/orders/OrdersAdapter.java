package com.j1j2.pifalao.feature.orders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.OrderSimple;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemOrdersBinding;
import com.j1j2.pifalao.feature.orderdetail.OrderDetailActivity;


import java.util.Collection;
import java.util.List;

/**
 * Created by alienzxh on 16-3-22.
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    private List<OrderSimple> orderSimples;

    public OrdersAdapter(List<OrderSimple> orderSimples) {
        this.orderSimples = orderSimples;
    }

    public void addAll(Collection<OrderSimple> newOrders) {
        if (null == orderSimples)
            return;
        int startIndex = orderSimples.size();
        orderSimples.addAll(startIndex, newOrders);
        notifyItemRangeInserted(startIndex, newOrders.size());
    }

    public interface OnOrdersClickListener {

        void onReceiveClickListener(View view, OrderSimple orderSimple, int position);

        void onDetailClickListener(View view, OrderSimple orderSimple, int position, int selectpage);

        void onOrderPayClickListener(View view, OrderSimple orderSimple, int position);

        void onServicePointIconClickListener(View view, OrderSimple orderSimple, int position);

        void onCancelPointIconClickListener(View view, OrderSimple orderSimple, int position);

        void onCommentPointIconClickListener(View view, OrderSimple orderSimple, int position);

        void onOrderProductClickListener(View view, OrderSimple orderSimple, int position);
    }

    private OnOrdersClickListener onOrdersClickListener;

    public void setOnOrdersClickListener(OnOrdersClickListener onOrdersClickListener) {
        this.onOrdersClickListener = onOrdersClickListener;
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_orders, parent, false);
        return new OrdersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrdersViewHolder holder, int position) {
        holder.bind(orderSimples.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == orderSimples ? 0 : orderSimples.size();
    }

    public class OrdersViewHolder extends AutoBindingViewHolder<ItemOrdersBinding, OrderSimple> {
        Context context;

        public OrdersViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
        }

        @Override
        protected ItemOrdersBinding getBinding(View itemView) {
            return ItemOrdersBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final OrderSimple data, final int position) {
            binding.setOrderSimple(data);
            binding.orderProductList.setLayoutManager(new GridLayoutManager(context, 4));
            OrderProductAdapter orderProductAdapter = new OrderProductAdapter(data.getProductDetails());
            binding.orderProductList.setAdapter(orderProductAdapter);

            switch (data.getModuleType()) {
                case Constant.ModuleType.DELIVERY:
                    binding.orderIcon.setText(context.getText(R.string.icon_delivery));
                    binding.orderIcon.setTextColor(Constant.moduleColors.get(data.getModuleType()));
                    break;
                case Constant.ModuleType.VEGETABLE:
                    binding.orderIcon.setText(context.getText(R.string.icon_vegetable));
                    binding.orderIcon.setTextColor(Constant.moduleColors.get(data.getModuleType()));
                    break;
                case Constant.ModuleType.SHOPSERVICE:
                    binding.orderIcon.setText(context.getText(R.string.icon_shopservice));
                    binding.orderIcon.setTextColor(Constant.moduleColors.get(data.getModuleType()));
                    break;
            }


            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onOrdersClickListener == null)
                        return;
//                    if (v == binding.cancel) {
//                        onOrdersClickListener.onCancelPointIconClickListener(v, data, position);
//                    }
//                    if (v == binding.comment) {
//                        onOrdersClickListener.onCommentPointIconClickListener(v, data, position);
//                    }
//
//                    if (v == binding.servicepoint) {
//                        onOrdersClickListener.onServicePointIconClickListener(v, data, position);
//                    }
                    if(v==binding.pay){
                        onOrdersClickListener.onOrderPayClickListener(v, data, position);
                    }

                    if (v == binding.detail) {
                        onOrdersClickListener.onDetailClickListener(v, data, position, OrderDetailActivity.TIMELINE);
                    }
                    if (v == binding.orderProduct) {
                        onOrdersClickListener.onDetailClickListener(v, data, position, OrderDetailActivity.PARAM);
                    }
                    if (v == binding.receive) {
                        onOrdersClickListener.onReceiveClickListener(v, data, position);
                    }
                }
            });
        }
    }
}
