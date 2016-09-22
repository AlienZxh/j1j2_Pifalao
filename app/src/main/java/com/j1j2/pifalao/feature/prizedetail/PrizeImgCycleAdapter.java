package com.j1j2.pifalao.feature.prizedetail;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.j1j2.common.view.viewpager.autoscrollviewpager.RecyclingPagerAdapter;
import com.j1j2.data.model.ActivityProductImg;
import com.j1j2.data.model.ProductImg;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;

import java.util.List;

/**
 * Created by alienzxh on 16-4-12.
 */
public class PrizeImgCycleAdapter extends RecyclingPagerAdapter {


    private List<ActivityProductImg> productImgs;


    public PrizeImgCycleAdapter(List<ActivityProductImg> productImgs) {
        this.productImgs = productImgs;
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
                .load(Uri.parse(BuildConfig.IMAGE_URL + productImgs.get(position).getS640X640()))
                .centerCrop()
                .into(holder.draweeView);
        return view;
    }

    private static class ViewHolder {
        ImageView draweeView;
    }

    @Override
    public int getCount() {
        return null == productImgs ? 0 : productImgs.size();
    }
}
