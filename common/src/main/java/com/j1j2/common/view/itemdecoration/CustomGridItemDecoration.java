package com.j1j2.common.view.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.baidu.mapapi.common.Logger;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-3-11.
 */
public class CustomGridItemDecoration extends RecyclerView.ItemDecoration {
    private int spanCount;
    private int marginHorizontal;
    private int marginVertical;


    public CustomGridItemDecoration(int marginHorizontal, int marginVertical, int spanCount) {
        this.marginHorizontal = AutoUtils.getPercentWidthSize(marginHorizontal);
        this.marginVertical = AutoUtils.getPercentHeightSize(marginVertical);
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(
            Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        int lastSpan = state.getItemCount() % spanCount;

        outRect.set(computeGridRect(position, state.getItemCount(), spanCount, marginVertical, marginHorizontal));
    }


    public static Rect computeGridRect(int orgPosition, int itemCount, int spanCount, int marginVertical, int marginHorizontal) {
        //position从1开始
        int position = orgPosition + 1;
        int lastRaw = (int) Math.ceil(itemCount * 1.0 / spanCount);

        if (itemCount <= spanCount) {
            if (position % spanCount == 1) {
                return new Rect(marginHorizontal, marginVertical, marginHorizontal / 2, marginVertical);
            } else if (position % spanCount == 0) {
                return new Rect(marginHorizontal / 2, marginVertical, marginHorizontal, marginVertical);
            } else {
                return new Rect(marginHorizontal / 2, marginVertical, marginHorizontal / 2, marginVertical);
            }
        } else if (position <= spanCount) {
            if (position % spanCount == 1) {
                return new Rect(marginHorizontal, marginVertical, marginHorizontal / 2, marginVertical / 2);
            } else if (position % spanCount == 0) {
                return new Rect(marginHorizontal / 2, marginVertical, marginHorizontal, marginVertical / 2);
            } else {
                return new Rect(marginHorizontal / 2, marginVertical, marginHorizontal / 2, marginVertical / 2);
            }
        } else if (((int) Math.ceil(position * 1.0 / spanCount)) >= lastRaw) {
            if (position % spanCount == 1) {
                return new Rect(marginHorizontal, marginVertical / 2, marginHorizontal / 2, marginVertical);
            } else if (position % spanCount == 0) {
                return new Rect(marginHorizontal / 2, marginVertical / 2, marginHorizontal, marginVertical);
            } else {
                return new Rect(marginHorizontal / 2, marginVertical / 2, marginHorizontal / 2, marginVertical);
            }
        } else {
            if (position % spanCount == 1) {
                return new Rect(marginHorizontal, marginVertical / 2, marginHorizontal / 2, marginVertical / 2);
            } else if (position % spanCount == 0) {
                return new Rect(marginHorizontal / 2, marginVertical / 2, marginHorizontal, marginVertical / 2);
            } else {
                return new Rect(marginHorizontal / 2, marginVertical / 2, marginHorizontal / 2, marginVertical / 2);
            }
        }
    }
}