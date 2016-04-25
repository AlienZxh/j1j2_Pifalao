package com.j1j2.pifalao.feature.addressmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.Address;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemAddressmanagerBinding;

import java.util.Collections;
import java.util.List;

/**
 * Created by alienzxh on 16-3-23.
 */
public class AddressManagerAdapter extends RecyclerView.Adapter<AddressManagerAdapter.AddressManagerViewHolder> {
    private List<Address> addressList;

    public AddressManagerAdapter(List<Address> addressList) {
        this.addressList = addressList;
    }


//    public void itemMoveTop(int addressId) {
//        int i = 0;
//        for (Address address : addressList) {
//            if (address.isDefaultAddress()) {
//                address.setIsDefaultAddress(false);
//                notifyItemChanged(i);
//            }
//            i++;
//        }
//        i = 0;
//        for (Address address : addressList) {
//            if (address.getAddressId() == addressId) {
//                address.setIsDefaultAddress(true);
//                notifyItemChanged(i);
//                Collections.swap(addressList, i, 0);
//                notifyItemMoved(i, 0);
//            }
//            i++;
//        }
//    }

    @Override
    public AddressManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_addressmanager, parent, false);
        return new AddressManagerViewHolder(itemView);
    }

    public interface OnAddressClickListener {
        void onAddressClickListener(View v, Address address, int position);

        void onDefaultBtnClickListener(View v, Address address, int position);

        void onDeleteBtnClickListener(View v, Address address, int position);

        void onModitfyBtnClickListener(View v, Address address, int position);
    }

    private OnAddressClickListener onAddressClickListener;

    public void setOnAddressClickListener(OnAddressClickListener onAddressClickListener) {
        this.onAddressClickListener = onAddressClickListener;
    }

    public void deletePosition(int position) {
        if (addressList != null && addressList.size() >= (position - 1)) {
            addressList.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(AddressManagerViewHolder holder, int position) {
        holder.bind(addressList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == addressList ? 0 : addressList.size();
    }

    public class AddressManagerViewHolder extends AutoBindingViewHolder<ItemAddressmanagerBinding, Address> {
        public AddressManagerViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemAddressmanagerBinding getBinding(View itemView) {
            return ItemAddressmanagerBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final Address data, final int position) {
            binding.setAddress(data);
            binding.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddressClickListener != null)
                        onAddressClickListener.onAddressClickListener(v, data, position);
                }
            });
            binding.setDefaultBtnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddressClickListener != null)
                        onAddressClickListener.onDefaultBtnClickListener(v, data, position);
                }
            });
            binding.setDeleteBtnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddressClickListener != null)
                        onAddressClickListener.onDeleteBtnClickListener(v, data, position);
                }
            });
            binding.setModifyBtnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddressClickListener != null)
                        onAddressClickListener.onModitfyBtnClickListener(v, data, position);
                }
            });
        }
    }
}
