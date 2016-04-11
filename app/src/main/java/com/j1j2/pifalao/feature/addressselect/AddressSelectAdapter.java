package com.j1j2.pifalao.feature.addressselect;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemAddressselectBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-4-4.
 */
public class AddressSelectAdapter extends RecyclerView.Adapter<AddressSelectAdapter.AddressSelectViewHolder> {

    private List<PoiInfo> poiInfos;

    public AddressSelectAdapter(List<PoiInfo> poiInfos) {
        this.poiInfos = poiInfos;
    }


    public interface OnAddressSelectListener {
        void onAddressSelectListener(View view, PoiInfo poiInfo, int poistion);
    }

    private OnAddressSelectListener onAddressSelectListener;

    public void setOnAddressSelectListener(OnAddressSelectListener onAddressSelectListener) {
        this.onAddressSelectListener = onAddressSelectListener;
    }

    @Override
    public AddressSelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_addressselect, parent, false);
        return new AddressSelectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddressSelectViewHolder holder, int position) {
        holder.bind(poiInfos.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == poiInfos ? 0 : poiInfos.size();
    }

    public class AddressSelectViewHolder extends AutoBindingViewHolder<ItemAddressselectBinding, PoiInfo> {
        public AddressSelectViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemAddressselectBinding getBinding(View itemView) {
            return ItemAddressselectBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final PoiInfo data, final int position) {
            binding.setPoiInfo(data);
            binding.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddressSelectListener != null)
                        onAddressSelectListener.onAddressSelectListener(v, data, position);
                }
            });
        }
    }
}
