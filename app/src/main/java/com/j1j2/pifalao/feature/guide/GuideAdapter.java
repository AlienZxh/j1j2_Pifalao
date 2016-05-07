package com.j1j2.pifalao.feature.guide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.j1j2.common.view.viewpager.autoscrollviewpager.RecyclingPagerAdapter;
import com.j1j2.pifalao.R;

/**
 * Created by alienzxh on 16-4-26.
 */
public class GuideAdapter extends RecyclingPagerAdapter {
    private int[] imgId;
    private float ratio;

    public GuideAdapter(int[] imgId, float ratio) {
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
            holder.draweeView = (ImageView) view.findViewById(R.id.draw);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Glide.with(container.getContext())
                .load(imgId[position])
                .centerCrop()
                .into(holder.draweeView);
        return view;
    }

    private static class ViewHolder {
        ImageView draweeView;
    }

    @Override
    public int getCount() {
        return imgId.length;
    }
}
