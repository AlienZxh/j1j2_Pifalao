package com.j1j2.pifalao.feature.productdetail;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.j1j2.common.view.viewpager.autoscrollviewpager.RecyclingPagerAdapter;
import com.j1j2.data.model.ProductImg;
import com.j1j2.pifalao.R;

import java.util.List;

/**
 * Created by alienzxh on 16-4-12.
 */
public class ProductImgCycleAdapter extends RecyclingPagerAdapter {


    private List<ProductImg> productImgs;


    public ProductImgCycleAdapter(List<ProductImg> productImgs) {
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
            holder.draweeView = (SimpleDraweeView) view.findViewById(R.id.draw);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.draweeView.setImageURI(Uri.parse(productImgs.get(position).getImgUrl()));
        return view;
    }

    private static class ViewHolder {
        SimpleDraweeView draweeView;
    }

    @Override
    public int getCount() {
        return null == productImgs ? 0 : productImgs.size();
    }
}
