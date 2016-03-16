package com.j1j2.pifalao.feature.city;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.City;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemCityBinding;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by alienzxh on 16-3-11.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<City> cities;
    private Context context;

    public CityAdapter(Context context, List<City> cities) {
        this.context = context;
        this.cities = cities;
    }


    public interface OnItemClickListener {
        void onItemClickListener(View view, City city);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_city, viewGroup, false);
        return new CityViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.bind(cities.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == this.cities ? 0 : this.cities.size();
    }

    public void addAll(Collection<City> newCities) {
        if (null == cities)
            return;
        int startIndex = cities.size();
        cities.addAll(startIndex, newCities);
        notifyItemRangeInserted(startIndex, newCities.size());
    }

    public class CityViewHolder extends AutoBindingViewHolder<ItemCityBinding, City> {

        public CityViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemCityBinding getBinding(View itemView) {
            return ItemCityBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final City city, int position) {
            binding.setCity(city);
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickListener(view, city);
                    }
                }
            });
        }
    }
}
