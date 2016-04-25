package com.j1j2.pifalao.feature.home.vegetablehome;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.j1j2.common.view.viewpager.autoscrollviewpager.RecyclingPagerAdapter;
import com.j1j2.data.model.BannerActivity;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;

import java.util.List;
import java.util.logging.Logger;

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
            holder.draweeView = (SimpleDraweeView) view.findViewById(R.id.draw);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.draweeView.setAspectRatio(2.25f);
        holder.draweeView.setImageURI(Uri.parse(BuildConfig.IMAGE_URL + bannerActivities.get(position).getBannerAppImg()));
        return view;
    }

    private static class ViewHolder {
        SimpleDraweeView draweeView;
    }

    @Override
    public int getCount() {
        return null == bannerActivities ? 0 : bannerActivities.size();
    }

}
