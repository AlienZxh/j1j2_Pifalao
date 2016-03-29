package com.j1j2.common.view.autowidget;

import android.content.Context;
import android.util.AttributeSet;

import com.j1j2.common.view.WheelView;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by alienzxh on 16-3-27.
 */
public class AutoWheelView extends WheelView {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoWheelView(Context context) {
        super(context);
    }

    public AutoWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoWheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public AutoFrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AutoFrameLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
