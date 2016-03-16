package com.j1j2.common.view.autowidget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by alienzxh on 16-3-16.
 */
public class AutoNestedScrolling extends NestedScrollView {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoNestedScrolling(Context context) {
        super(context);
    }

    public AutoNestedScrolling(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoNestedScrolling(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
