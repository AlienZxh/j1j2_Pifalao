package com.j1j2.pifalao.app.recyclerviewadapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;

/**
 * M为这个itemView对应的model。
 * 使用RecyclerArrayAdapter就一定要用这个ViewHolder。
 * 这个ViewHolder将ItemView与Adapter解耦。
 * 推荐子类继承第二个构造函数。并将子类的构造函数设为一个ViewGroup parent。
 * 然后这个ViewHolder就完全独立。adapter在new的时候只需将parentView传进来。View的生成与管理由ViewHolder执行。
 * 实现setData来实现UI修改。Adapter会在onCreateViewHolder里自动调用。
 * <p>
 * 在一些特殊情况下，只能在setData里设置监听。
 *
 * @param <M>
 */
abstract public class BaseViewHolder<D extends ViewDataBinding, M> extends RecyclerView.ViewHolder {

    protected D binding;

    public BaseViewHolder(View itemView) {
        super(itemView);
        AutoUtils.autoSize(itemView);
        this.binding = getBinding(itemView);
    }

    protected D getBinding(View itemView) {
        return null;
    }


    public void setData(M data) {
    }

    protected Context getContext() {
        return itemView.getContext();
    }

}