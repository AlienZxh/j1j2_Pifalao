package com.j1j2.pifalao.app.base;

import android.databinding.BindingAdapter;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.j1j2.common.util.ScreenUtils;
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

    @BindingAdapter({"bind:displayImg"})
    public static void displayImg(SimpleDraweeView mDraweeView, Uri uri) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(ScreenUtils.dpToPx(60), ScreenUtils.dpToPx(60)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(mDraweeView.getController())
                .setImageRequest(request)
                .build();
        mDraweeView.setController(controller);
    }
}
