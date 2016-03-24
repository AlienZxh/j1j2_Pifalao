package com.j1j2.pifalao.feature.messages;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.Message;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.AutoBindingViewHolder;
import com.j1j2.pifalao.databinding.ItemMessagesBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-3-24.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private List<Message> messages;

    public MessagesAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_messages, parent, false);
        return new MessagesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessagesViewHolder holder, int position) {
        holder.bind(messages.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == messages ? 0 : messages.size();
    }

    public class MessagesViewHolder extends AutoBindingViewHolder<ItemMessagesBinding, Message> {
        public MessagesViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemMessagesBinding getBinding(View itemView) {
            return ItemMessagesBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull Message data, int position) {
            binding.setMessage(data);
        }
    }
}
