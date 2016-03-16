package com.j1j2.pifalao.feature.home.storestylehome;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.itemdecoration.CustomGridItemDecoration;
import com.j1j2.data.model.City;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.SecondarySort;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemStoreContentBinding;
import com.j1j2.pifalao.databinding.ItemStoreHeadBinding;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alienzxh on 16-3-14.
 */
public class StoreStyleHomeAdapter extends RecyclerView.Adapter<AutoBindingViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0x01;

    private static final int VIEW_TYPE_CONTENT = 0x00;

    private List<SecondarySort> secondarySorts;

    private final ArrayList<LineItem> mItems;

    private int marginHorizontal;
    private int marginVertical;

    public StoreStyleHomeAdapter(List<SecondarySort> secondarySorts) {
        this.secondarySorts = secondarySorts;
        mItems = new ArrayList<>();
        if (null == secondarySorts)
            return;
        this.marginHorizontal = AutoUtils.getPercentWidthSize(25);
        this.marginVertical = AutoUtils.getPercentHeightSize(25);
        int sectionFirstPosition = 0;
        int spanCount = 3;
        Rect rect;
        for (int i = 0; i < secondarySorts.size(); i++) {
            if (null == secondarySorts.get(i).getParentProductSort())
                continue;
            rect = new Rect(0, 0, 0, 0);
            mItems.add(new LineItem(secondarySorts.get(i).getParentProductSort(), true, rect, sectionFirstPosition));
            if (null == secondarySorts.get(i).getChildFoodSorts())
                continue;
            int itemCount = secondarySorts.get(i).getChildFoodSorts().size();
            int lastSpan = itemCount % spanCount;
            for (int j = 0; j < itemCount; j++) {
                rect = CustomGridItemDecoration.computeGridRect(j, itemCount, spanCount, marginVertical, marginHorizontal);
                mItems.add(new LineItem(secondarySorts.get(i).getChildFoodSorts().get(j), false, rect, sectionFirstPosition));
            }
            sectionFirstPosition += (1 + secondarySorts.get(i).getChildFoodSorts().size());
        }
    }


    public interface OnSortItemClickListener {
        void onSortItemClickListener(View view, ProductSort productSort, int position);
    }

    private OnSortItemClickListener onItemClickListener;

    public void setOnSortItemClickListener(OnSortItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public AutoBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_store_head, parent, false);
            return new StoreHeaderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_store_content, parent, false);
            return new StoreContentViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(AutoBindingViewHolder holder, int position) {
        final LineItem item = mItems.get(position);
        final View itemView = holder.itemView;

        holder.bind(item.sort, position);

    }

    public boolean isItemHeader(int position) {
        return mItems.get(position).isHeader;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).isHeader ? VIEW_TYPE_HEADER : VIEW_TYPE_CONTENT;
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();

    }

    public Rect geItemRect(int position) {
        return mItems.get(position).rect;
    }

    public List<SecondarySort> getSecondarySorts() {
        return secondarySorts;
    }

    public class StoreContentViewHolder extends AutoBindingViewHolder<ItemStoreContentBinding, ProductSort> {
        public StoreContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemStoreContentBinding getBinding(View itemView) {
            return ItemStoreContentBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull ProductSort data, final int position) {
            binding.setProductSort(data);
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener)
                        onItemClickListener.onSortItemClickListener(v, mItems.get(position).sort, position);
                }
            });
        }
    }

    public class StoreHeaderViewHolder extends AutoBindingViewHolder<ItemStoreHeadBinding, ProductSort> {
        public StoreHeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemStoreHeadBinding getBinding(View itemView) {
            return ItemStoreHeadBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull ProductSort data, final int position) {
            binding.setProductSort(data);
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener)
                        onItemClickListener.onSortItemClickListener(v, mItems.get(position).sort, position);
                }
            });
        }
    }


    private static class LineItem {

        public Rect rect;

        public int sectionFirstPosition;

        public boolean isHeader;

        public ProductSort sort;

        public LineItem(ProductSort sort, boolean isHeader, Rect rect,
                        int sectionFirstPosition) {
            this.isHeader = isHeader;
            this.sort = sort;
            this.rect = rect;
            this.sectionFirstPosition = sectionFirstPosition;
        }
    }


}
