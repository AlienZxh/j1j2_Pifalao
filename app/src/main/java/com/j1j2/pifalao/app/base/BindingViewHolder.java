package com.j1j2.pifalao.app.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-3-15.
 */
public abstract class BindingViewHolder <D extends ViewDataBinding, T> extends RecyclerView.ViewHolder  {

    protected D binding;

    public BindingViewHolder(View itemView) {
        super(itemView);
        this.binding = getBinding(itemView);
    }

    protected abstract D getBinding(View itemView);

    public abstract void bind(@NonNull final T data, int position);


}