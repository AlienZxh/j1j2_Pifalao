package com.j1j2.pifalao.app.base;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.j1j2.common.view.bgabadgewidget.AutoBGABadgeLinearLayout;
import com.j1j2.common.view.bgabadgewidget.AutoBGABadgeRelativeLayout;
import com.j1j2.pifalao.R;

import java.util.Collections;

import cn.bingoogolapple.badgeview.BGABadgeTextView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by alienzxh on 16-3-26.
 */
public class CustomBinding {
    @BindingAdapter({"showNum"})
    public static void showNum(AutoBGABadgeLinearLayout linearLayout, int num) {
        if (num > 0)
            linearLayout.showTextBadge("" + num);
        else
            linearLayout.hiddenBadge();
    }

    @BindingAdapter({"showNum"})
    public static void showNum(BGABadgeTextView linearLayout, int num) {
        if (num > 0)
            linearLayout.showTextBadge("" + num);
        else
            linearLayout.hiddenBadge();
    }

    @BindingAdapter({"showNum"})
    public static void showNum(AutoBGABadgeRelativeLayout linearLayout, int num) {
        if (num > 0)
            linearLayout.showTextBadge("" + num);
        else
            linearLayout.hiddenBadge();
    }

    @BindingAdapter({"showCircle"})
    public static void showCircle(BGABadgeTextView textView, boolean hasUnRead) {
        if (hasUnRead)
            textView.showCirclePointBadge();
        else
            textView.hiddenBadge();
    }

    @BindingAdapter({"displayImg"})
    public static void displayImg(ImageView view, Uri uri) {
        Glide.with(view.getContext())
                .load(uri)
                .error(R.drawable.loadimg_error)
                .placeholder(R.drawable.loadimg_loading)
                .into(view);
    }

    @BindingAdapter({"displayImgWithoutCache"})
    public static void displayImgWithoutCache(ImageView view, Uri uri) {
        Glide.with(view.getContext())
                .load(uri)
                .error(R.drawable.loadimg_error)
                .placeholder(R.drawable.loadimg_loading)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view);
    }

    @BindingAdapter({"displayImgAsBitmap"})
    public static void displayImgAsBitmap(RoundedImageView view, @DrawableRes int resId) {
        Glide.with(view.getContext())
                .load(resId)
                .asBitmap()
                .error(R.drawable.loadimg_error)
                .placeholder(R.drawable.loadimg_loading)
                .into(view);
    }

    @BindingAdapter({"displayImgAsBitmap"})
    public static void displayImgAsBitmap(RoundedImageView view, Uri uri) {
        Glide.with(view.getContext())
                .load(uri)
                .asBitmap()
                .error(R.drawable.loadimg_error)
                .placeholder(R.drawable.loadimg_loading)
                .into(view);
    }

    @BindingAdapter({"displayImgServicepoint"})
    public static void displayImgServicepoint(RoundedImageView view, Uri uri) {
        Glide.with(view.getContext())
                .load(uri)
                .asBitmap()
                .placeholder(R.drawable.servicepoint_default)
                .into(view);
    }


    @BindingAdapter({"displayImgTransfrom"})
    public static void displayImgTransfrom(final ImageView view, Uri uri) {
        Glide.with(view.getContext())
                .load(uri)
                .asBitmap()
                .dontAnimate()
                .transform(new CropCircleTransformation(view.getContext()))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        view.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        Glide.with(view.getContext())
                                .load(R.drawable.servicepoint_default)
                                .asBitmap()
                                .dontAnimate()
                                .transform(new CropCircleTransformation(view.getContext()))
                                .into(view);
                    }
                });
    }

    @BindingAdapter({"displayImgUserHead"})
    public static void displayImgUserHead(final ImageView view, Uri uri) {
        Glide.with(view.getContext())
                .load(uri)
                .asBitmap()
                .error(R.drawable.user_head_img)
                .placeholder(R.drawable.user_head_img)
                .dontAnimate()
                .transform(new CropCircleTransformation(view.getContext()))
                .into(view);
    }
}
