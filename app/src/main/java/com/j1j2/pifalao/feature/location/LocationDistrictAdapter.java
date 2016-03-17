package com.j1j2.pifalao.feature.location;

import android.content.Context;
import android.databinding.ObservableInt;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.j1j2.common.view.recyclerviewchoicemode.MultiSelector;
import com.j1j2.common.view.recyclerviewchoicemode.SelectableHolder;
import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.City;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemLocationDistrictBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-3-13.
 */
public class LocationDistrictAdapter extends RecyclerView.Adapter<LocationDistrictAdapter.LocationDistrictViewHolder> {

    private List<City> cities;

    public LocationDistrictAdapter(List<City> cities) {
        this.cities = cities;
    }

    private SingleSelector singleSelector = new SingleSelector();

    @Override
    public LocationDistrictViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location_district, parent, false);

        return new LocationDistrictViewHolder(itemView, singleSelector);
    }

    @Override
    public void onBindViewHolder(LocationDistrictViewHolder holder, int position) {
        if (position == 0)
            holder.bind("最近", position);
        else
            holder.bind(cities.get(position - 1).getPCCName(), position);
    }

    public interface OnDistrictItemClickListener {
        void onDistrictItemClickListener(View view, City city, int position);
    }

    private OnDistrictItemClickListener onItemClickListener;

    public void setOnDistrictItemClickListener(OnDistrictItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return null == this.cities ? 1 : this.cities.size() + 1;
    }

    public class LocationDistrictViewHolder extends AutoBindingViewHolder<ItemLocationDistrictBinding, String> implements SelectableHolder {

        private Context context;

        private SingleSelector singleSelector;

        private boolean mIsSelectable = false;

        public LocationDistrictViewHolder(View itemView, SingleSelector singleSelector) {
            super(itemView);
            this.context = itemView.getContext();
            this.singleSelector = singleSelector;
        }

        @Override
        protected ItemLocationDistrictBinding getBinding(View itemView) {
            return ItemLocationDistrictBinding.bind(itemView);
        }


        @Override
        public void bind(@NonNull String data, final int position) {
            singleSelector.bindHolder(this, position, getItemId());
            if (position == 0) {
                binding.setDistrict(data);
                singleSelector.setSelected(0, getItemId(), true);
            } else {
                binding.setDistrict(data);
            }
            binding.setPosition(position);
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    singleSelector.setSelected(position, getItemId(), true);
                    if (onItemClickListener != null) {
                        if (position == 0)
                            onItemClickListener.onDistrictItemClickListener(view, null, position);
                        else
                            onItemClickListener.onDistrictItemClickListener(view, cities.get(position - 1), position);
                    }
                }
            });
        }

        @Override
        public void setSelectable(boolean isSelectable) {
            boolean changed = isSelectable != mIsSelectable;
            mIsSelectable = isSelectable;

        }

        @Override
        public boolean isSelectable() {
            return mIsSelectable;
        }

        @Override
        public void setActivated(boolean activated) {
            itemView.setActivated(activated);
        }

        @Override
        public boolean isActivated() {
            return itemView.isActivated();
        }
    }
}
