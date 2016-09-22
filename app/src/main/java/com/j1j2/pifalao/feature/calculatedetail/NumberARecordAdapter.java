package com.j1j2.pifalao.feature.calculatedetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.LotteryCacluateTime;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemCalculateNumberaBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-9-7.
 */
public class NumberARecordAdapter extends RecyclerView.Adapter<NumberARecordAdapter.NumberARecordViewHolder> {

    private List<LotteryCacluateTime> strings;


    public NumberARecordAdapter(List<LotteryCacluateTime> strings) {
        this.strings = strings;
    }

    @Override
    public NumberARecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calculate_numbera, parent, false);
        return new NumberARecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NumberARecordViewHolder holder, int position) {
        if (position == 0)
            holder.bind(null, position);
        else
            holder.bind(strings.get(position - 1), position);
    }

    @Override
    public int getItemCount() {
        return this.strings == null ? 1 : this.strings.size() + 1;
    }

    public class NumberARecordViewHolder extends AutoBindingViewHolder<ItemCalculateNumberaBinding, LotteryCacluateTime> {
        public NumberARecordViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemCalculateNumberaBinding getBinding(View itemView) {
            return ItemCalculateNumberaBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull LotteryCacluateTime data, int position) {
            if (position == 0) {
                binding.text1.setText("参与时间");
                binding.text3.setText("会员账号（所属门店）");
            } else {
                binding.text1.setText(data.getOrderTimeStr());
                binding.text2.setText("" + data.getTimeConvertData());
                binding.text3.setText(data.getMobileEncrypt() + (data.getStoreName() == null ? "" : "（" + data.getStoreName() + "）"));
            }

        }
    }
}
