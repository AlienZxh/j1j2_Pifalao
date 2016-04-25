package com.j1j2.pifalao.feature.home.storestylehome;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.j1j2.common.view.viewpager.autoscrollviewpager.RecyclingPagerAdapter;
import com.j1j2.pifalao.R;

/**
 * Created by alienzxh on 16-4-12.
 */
public class StoreStyleHomeTopCycleAdapter extends RecyclingPagerAdapter {
    private int[] imgId;
    private float ratio;

    public StoreStyleHomeTopCycleAdapter(int[] imgId, float ratio) {
        this.imgId = imgId;
        this.ratio = ratio;
    }

    private int getPosition(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_bannerimg, container, false);
            holder.draweeView = (SimpleDraweeView) view.findViewById(R.id.draw);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.draweeView.setAspectRatio(ratio);
        holder.draweeView.setImageURI(Uri.parse("res://com.j1j2.pifalao/" + imgId[position]));
        return view;
    }

    private static class ViewHolder {
        SimpleDraweeView draweeView;
    }

    @Override
    public int getCount() {
        return imgId.length;
    }
}
