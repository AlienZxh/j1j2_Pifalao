package com.j1j2.pifalao.feature.freeconvertibility;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.ActivityProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemFreeconvertibilityBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-9-1.
 */
public class FreeConvertibilityAdapter extends RecyclerView.Adapter<FreeConvertibilityAdapter.FreeConvertibilityViewHolder> {

    public interface FreeConvertibilityAdapterListener {
        void navigateToPrizeDetail(ActivityProduct activityProduct);
    }

    private List<ActivityProduct> strings;

    private FreeConvertibilityAdapterListener listener;

    public FreeConvertibilityAdapter(List<ActivityProduct> strings) {
        this.strings = strings;
    }

    public void setListener(FreeConvertibilityAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public FreeConvertibilityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_freeconvertibility, parent, false);
        return new FreeConvertibilityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FreeConvertibilityViewHolder holder, int position) {
        holder.bind(strings.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.strings == null ? 0 : this.strings.size();
    }

    public class FreeConvertibilityViewHolder extends AutoBindingViewHolder<ItemFreeconvertibilityBinding, ActivityProduct> {

        public FreeConvertibilityViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemFreeconvertibilityBinding getBinding(View itemView) {
            return ItemFreeconvertibilityBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final ActivityProduct data, int position) {
            binding.setActivityProduct(data);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.navigateToPrizeDetail(data);
                }
            });
            binding.prizeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
