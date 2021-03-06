package com.j1j2.pifalao.feature.showorders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.j1j2.common.util.EmptyUtils;
import com.j1j2.data.model.AcceptanceSpeechImg;
import com.j1j2.data.model.ImgUrl;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemPrizeimgBinding;
import com.j1j2.pifalao.feature.showorder.PrizeImgAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alienzxh on 16-9-22.
 */

public class PrizeImgShowAdapter extends RecyclerView.Adapter<PrizeImgShowAdapter.PrizeImgShowViewHolder> {

    public interface PrizeImgShowAdapterListener {
        void onShowImgClick(View view, List<ImgUrl> urls, int position);
    }

    private List<AcceptanceSpeechImg> strings;
    private List<ImgUrl> urls;

    PrizeImgShowAdapterListener listener;

    public PrizeImgShowAdapter(List<AcceptanceSpeechImg> strings, PrizeImgShowAdapterListener listener) {
        this.strings = strings;
        urls = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            urls.add(new ImgUrl(BuildConfig.IMAGE_URL + strings.get(i).getNormalImg()));
        }
        this.listener = listener;
    }

    @Override
    public PrizeImgShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prizeimg, parent, false);
        return new PrizeImgShowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PrizeImgShowViewHolder holder, int position) {
        holder.bind(strings.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.strings == null ? 0 : (this.strings.size() > 3 ? 3 : this.strings.size());
    }

    public class PrizeImgShowViewHolder extends AutoBindingViewHolder<ItemPrizeimgBinding, AcceptanceSpeechImg> {
        Context context;

        public PrizeImgShowViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
        }

        @Override
        protected ItemPrizeimgBinding getBinding(View itemView) {
            return ItemPrizeimgBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull AcceptanceSpeechImg data, final int position) {
            Glide.with(context)
                    .load(BuildConfig.IMAGE_URL + data.getS320X320())
                    .asBitmap()
                    .error(R.drawable.loadimg_error)
                    .placeholder(R.drawable.loadimg_loading)
                    .into(binding.img);
            binding.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onShowImgClick(v, urls, position);
                }
            });
        }
    }
}
