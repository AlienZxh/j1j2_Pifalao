package com.j1j2.pifalao.feature.home.vegetablehome;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.j1j2.common.view.viewpager.autoscrollviewpager.RecyclingPagerAdapter;
import com.j1j2.data.model.BannerActivity;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;

import java.util.List;

/**
 * Created by alienzxh on 16-4-8.
 */
public class VegetableHomeTopAdapter extends RecyclingPagerAdapter {
    private List<BannerActivity> bannerActivities;

    public VegetableHomeTopAdapter(List<BannerActivity> bannerActivities) {
        this.bannerActivities = bannerActivities;
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
                .load(Uri.parse(BuildConfig.IMAGE_URL + bannerActivities.get(position).getBannerAppImg()))
                .centerCrop()
                .into(holder.draweeView);
        return view;
    }

    private static class ViewHolder {
        ImageView draweeView;
    }

    @Override
    public int getCount() {
        return null == bannerActivities ? 0 : bannerActivities.size();
    }

}
