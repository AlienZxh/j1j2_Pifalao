package com.j1j2.pifalao.feature.home.storestylehome;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.itemdecoration.CustomGridItemDecoration;
import com.j1j2.data.model.ProductCategory;
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

    private List<ProductCategory> secondarySorts;

    private final ArrayList<LineItem> mItems;

    private int marginHorizontal;
    private int marginVertical;

    private int[] icons = {R.string.icon_sort_head1,
            R.string.icon_sort_head2,
            R.string.icon_sort_head3,
            R.string.icon_sort_head4,
            R.string.icon_sort_head5,
            R.string.icon_sort_head6,
            R.string.icon_sort_head7,
            R.string.icon_sort_head8,
            R.string.icon_sort_head9};

    public StoreStyleHomeAdapter(List<ProductCategory> secondarySorts) {
        this.secondarySorts = secondarySorts;
        mItems = new ArrayList<>();
        if (null == secondarySorts)
            return;
        this.marginHorizontal = AutoUtils.getPercentWidthSize(15);
        this.marginVertical = AutoUtils.getPercentHeightSize(15);
        int sectionFirstPosition = 0;
        int spanCount = 3;
        Rect rect;
        for (int i = 0; i < secondarySorts.size(); i++) {
            if (null == secondarySorts.get(i))
                continue;
            rect = new Rect(0, 0, 0, 0);
            mItems.add(new LineItem(secondarySorts.get(i), true, rect, sectionFirstPosition, i));
            if (null == secondarySorts.get(i).getChildCategories())
                continue;
            int childSortSize = secondarySorts.get(i).getChildCategories().size();
            int itemCount = childSortSize + 1;

            for (int j = 0; j < itemCount; j++) {
                if (j < childSortSize) {
                    rect = CustomGridItemDecoration.computeGridRect(j, itemCount, spanCount, marginVertical, marginHorizontal);
                    mItems.add(new LineItem(secondarySorts.get(i).getChildCategories().get(j), false, rect, sectionFirstPosition, i));
                } else {
                    rect = CustomGridItemDecoration.computeGridRect(j, itemCount, spanCount, marginVertical, marginHorizontal);
                    mItems.add(new LineItem(secondarySorts.get(i), false, rect, sectionFirstPosition, i));
                }
            }

            sectionFirstPosition += (1 + itemCount);
        }
    }


    public interface OnSortItemClickListener {
        void onSortItemClickListener(View view, ProductCategory productCategory, int position);
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

    public List<ProductCategory> getSecondarySorts() {
        return secondarySorts;
    }

    public class StoreContentViewHolder extends AutoBindingViewHolder<ItemStoreContentBinding, ProductCategory> {


        public StoreContentViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        protected ItemStoreContentBinding getBinding(View itemView) {
            return ItemStoreContentBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull ProductCategory data, final int position) {
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

    public class StoreHeaderViewHolder extends AutoBindingViewHolder<ItemStoreHeadBinding, ProductCategory> {
        private Context context;

        public StoreHeaderViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
        }

        @Override
        protected ItemStoreHeadBinding getBinding(View itemView) {
            return ItemStoreHeadBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull ProductCategory data, final int position) {
            binding.setProductSort(data);
            binding.setFloor("" + (mItems.get(position).sectionPart + 1) + "F");
            binding.setIcon(context.getString(icons[mItems.get(position).sectionPart % 9]));
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

        public int sectionPart;

        public int sectionFirstPosition;

        public boolean isHeader;

        public ProductCategory sort;

        public LineItem(ProductCategory sort, boolean isHeader, Rect rect,
                        int sectionFirstPosition, int sectionPart) {
            this.isHeader = isHeader;
            this.sort = sort;
            this.rect = rect;
            this.sectionFirstPosition = sectionFirstPosition;
            this.sectionPart = sectionPart;
        }
    }


}
