package com.j1j2.pifalao.feature.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemSearchBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-3-16.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<String> strs;

    public SearchAdapter(List<String> strs) {
        this.strs = strs;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);

        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.bind(strs.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == strs ? 0 : strs.size();
    }

    public class SearchViewHolder extends AutoBindingViewHolder<ItemSearchBinding, String> {
        public SearchViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemSearchBinding getBinding(View itemView) {
            return ItemSearchBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull String data, int position) {
            binding.setText(data);
        }
    }

}
