package com.j1j2.pifalao.feature.orders;

import android.support.annotation.NonNull;
import android.view.View;

import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemOrderproductBinding;

/**
 * Created by alienzxh on 16-4-14.
 */
public class OrderProductViewHolder extends AutoBindingViewHolder<ItemOrderproductBinding, String> {
    public OrderProductViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected ItemOrderproductBinding getBinding(View itemView) {
        return ItemOrderproductBinding.bind(itemView);
    }

    @Override
    public void bind(@NonNull String data, int position) {
        binding.setUrlStr(data);
    }
}
