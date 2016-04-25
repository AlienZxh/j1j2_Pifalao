package com.j1j2.pifalao.feature.show;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemShowBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-24.
 */
public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {

    private String[] urls;

    public ShowAdapter(String[] urls) {
        this.urls = urls;
    }

    @Override
    public ShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_show, parent, false);
        return new ShowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShowViewHolder holder, int position) {
        holder.bind(urls[position], position);
    }

    @Override
    public int getItemCount() {
        return null == urls ? 0 : urls.length;
    }

    public class ShowViewHolder extends AutoBindingViewHolder<ItemShowBinding, String> {
        public ShowViewHolder(View itemView) {
            super(itemView);
        }


        @Override
        protected ItemShowBinding getBinding(View itemView) {
            return ItemShowBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull String data, int position) {
            binding.setUrl(BuildConfig.IMAGE_URL + data);
        }
    }
}
