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

import java.util.List;

/**
 * Created by alienzxh on 16-3-23.
 */
public class AddressManagerAdapter extends RecyclerView.Adapter<AddressManagerAdapter.AddressManagerViewHolder> {
    private List<Address> addressList;

    public AddressManagerAdapter(List<Address> addressList) {
        this.addressList = addressList;
    }

    @Override
    public AddressManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_addressmanager, parent, false);
        return new AddressManagerViewHolder(itemView);
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
        public void bind(@NonNull Address data, int position) {
            binding.setAddress(data);
        }
    }
}
