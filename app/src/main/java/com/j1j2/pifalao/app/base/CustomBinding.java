package com.j1j2.pifalao.app.base;

import android.databinding.BindingAdapter;

import com.j1j2.common.view.bgabadgewidget.AutoBGABadgeFrameLayout;
import com.j1j2.common.view.bgabadgewidget.AutoBGABadgeLinearLayout;
import com.j1j2.common.view.bgabadgewidget.AutoBGABadgeRelativeLayout;

import cn.bingoogolapple.badgeview.BGABadgeTextView;

/**
 * Created by alienzxh on 16-3-26.
 */
public class CustomBinding {
    @BindingAdapter({"bind:showNum"})
    public static void showNum(AutoBGABadgeLinearLayout linearLayout, int num) {
        if (num > 0)
            linearLayout.showTextBadge("" + num);
        else
            linearLayout.hiddenBadge();

    }

    @BindingAdapter({"bind:showNum"})
    public static void showNum(BGABadgeTextView linearLayout, int num) {
        if (num > 0)
            linearLayout.showTextBadge("" + num);
        else
            linearLayout.hiddenBadge();

    }

    @BindingAdapter({"bind:showNum"})
    public static void showNum(AutoBGABadgeRelativeLayout linearLayout, int num) {
        if (num > 0)
            linearLayout.showTextBadge("" + num);
        else
            linearLayout.hiddenBadge();
    }



}
