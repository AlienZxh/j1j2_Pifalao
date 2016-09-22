package com.j1j2.pifalao.feature.specialoffer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemSpecialofferProductBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-9-20.
 */

public class SpecialOfferProductAdapter extends RecyclerView.Adapter<SpecialOfferProductAdapter.SpecialOfferProductViewHolder>{

    private List<String> strings;

    public SpecialOfferProductAdapter(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public SpecialOfferProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_specialoffer_product, parent, false);
        return new SpecialOfferProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SpecialOfferProductViewHolder holder, int position) {
        holder.bind(strings.get(position), position);
    }

    @Override
    public int getItemCount() {
        return strings == null ? 0 : strings.size();
    }

    public class SpecialOfferProductViewHolder extends AutoBindingViewHolder<ItemSpecialofferProductBinding, String> {
        public SpecialOfferProductViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemSpecialofferProductBinding getBinding(View itemView) {
            return ItemSpecialofferProductBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull String data, int position) {

        }
    }

}
