package com.j1j2.pifalao.feature.showorders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.AcceptanceSpeech;
import com.j1j2.data.model.ImgUrl;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemShoworderBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by alienzxh on 16-9-5.
 */
public class ShowOrderAdapter extends RecyclerView.Adapter<ShowOrderAdapter.ShowOrderViewHolder> {

    public interface ShowOrderAdapterListener {
        void navigateToImgsGalleryActivity(View view, List<ImgUrl> urls, int position);
    }

    private List<AcceptanceSpeech> strings;
    ShowOrderAdapterListener listener;

    public ShowOrderAdapter(List<AcceptanceSpeech> strings, ShowOrderAdapterListener listener) {
        this.strings = strings;
        this.listener = listener;
    }

    public void addAll(Collection<AcceptanceSpeech> newAcceptanceSpeechs) {
        if (null == strings)
            strings = new ArrayList<>();
        int startIndex = strings.size();
        strings.addAll(startIndex, newAcceptanceSpeechs);
        notifyItemRangeInserted(startIndex, newAcceptanceSpeechs.size());
    }

    public void initData(Collection<AcceptanceSpeech> newAcceptanceSpeechs) {
        if (null != strings && null != newAcceptanceSpeechs) {
            strings.clear();
            strings.addAll(newAcceptanceSpeechs);
        } else if (null != newAcceptanceSpeechs) {
            strings = new ArrayList<>();
            strings.addAll(newAcceptanceSpeechs);
        }
        notifyDataSetChanged();
    }

    @Override
    public ShowOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_showorder, parent, false);
        return new ShowOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShowOrderViewHolder holder, int position) {
        holder.bind(strings.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.strings == null ? 0 : this.strings.size();
    }

    public class ShowOrderViewHolder extends AutoBindingViewHolder<ItemShoworderBinding, AcceptanceSpeech> implements PrizeImgShowAdapter.PrizeImgShowAdapterListener {
        private Context context;

        public ShowOrderViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
        }

        @Override
        protected ItemShoworderBinding getBinding(View itemView) {
            return ItemShoworderBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull AcceptanceSpeech data, int position) {
            binding.setAcceptanceSpeech(data);
            binding.imgList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//        binding.list.addItemDecoration(new HorizontalDividerItemDecoration
//                .Builder(getContext())
//                .color(0xffd2d2d2)
//                .size(1)
//                .build());
            binding.imgList.setAdapter(new PrizeImgShowAdapter(data.getImgs(), this));
        }

        @Override
        public void onShowImgClick(View view, List<ImgUrl> urls, int position) {
            if (listener != null)
                listener.navigateToImgsGalleryActivity(view, urls, position);
        }
    }
}
