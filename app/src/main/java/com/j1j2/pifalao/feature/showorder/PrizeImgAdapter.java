package com.j1j2.pifalao.feature.showorder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.j1j2.common.util.EmptyUtils;
import com.j1j2.data.model.AcceptanceSpeechImg;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemPrizeimgBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-9-7.
 */
public class PrizeImgAdapter extends RecyclerView.Adapter<PrizeImgAdapter.PrizeImgViewHolder> {

    public interface PrizeImgAdapterListener {
        void onImgClick(int position);
    }

    private List<AcceptanceSpeechImg> strings;

    private PrizeImgAdapterListener listener;

    public PrizeImgAdapter(List<AcceptanceSpeechImg> strings) {
        this.strings = strings;
    }

    public void setListener(PrizeImgAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public PrizeImgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prizeimg, parent, false);
        return new PrizeImgViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PrizeImgViewHolder holder, int position) {
        if (EmptyUtils.isEmpty(strings))
            holder.bind(null, position);
        else if (position >= strings.size()) {
            holder.bind(null, position);
        } else
            holder.bind(strings.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.strings == null ? 1 : (this.strings.size() > 2 ? 3 : (this.strings.size() + 1));
    }

    public class PrizeImgViewHolder extends AutoBindingViewHolder<ItemPrizeimgBinding, AcceptanceSpeechImg> {
        Context context;

        public PrizeImgViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
        }

        @Override
        protected ItemPrizeimgBinding getBinding(View itemView) {
            return ItemPrizeimgBinding.bind(itemView);
        }

        @Override
        public void bind(AcceptanceSpeechImg data, final int position) {
            Glide.with(context)
                    .load(data == null ? "" : (BuildConfig.IMAGE_URL + data.getS320X320()))
                    .asBitmap()
                    .error(R.drawable.icon_img_add)
                    .placeholder(R.drawable.icon_img_add)
                    .into(binding.img);
            binding.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onImgClick(position);
                }
            });
        }
    }
}
