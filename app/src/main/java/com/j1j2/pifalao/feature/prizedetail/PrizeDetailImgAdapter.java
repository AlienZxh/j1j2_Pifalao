package com.j1j2.pifalao.feature.prizedetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.j1j2.data.model.ActivityProductImg;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemPrizedetailImgBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-9-23.
 */

public class PrizeDetailImgAdapter extends RecyclerView.Adapter<PrizeDetailImgAdapter.PrizeDetailImgViewHolder> {
    private List<ActivityProductImg> strings;


    public PrizeDetailImgAdapter(List<ActivityProductImg> strings) {
        this.strings = strings;
    }

    @Override
    public PrizeDetailImgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prizedetail_img, parent, false);
        return new PrizeDetailImgViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PrizeDetailImgViewHolder holder, int position) {
        holder.bind(strings.get(position).getS640X640(), position);
    }

    @Override
    public int getItemCount() {
        return this.strings == null ? 0 : this.strings.size();
    }


    public class PrizeDetailImgViewHolder extends AutoBindingViewHolder<ItemPrizedetailImgBinding, String> {
        private Context context;

        public PrizeDetailImgViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
        }

        @Override
        protected ItemPrizedetailImgBinding getBinding(View itemView) {
            return ItemPrizedetailImgBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull String data, int position) {
            Glide.with(context)
                    .load(data == null ? "" : (BuildConfig.IMAGE_URL + data))
                    .error(R.drawable.loadimg_error)
                    .placeholder(R.drawable.loadimg_loading)
                    .into(binding.img);
        }

    }
}
