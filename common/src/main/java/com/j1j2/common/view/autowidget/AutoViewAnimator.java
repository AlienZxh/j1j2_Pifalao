package com.j1j2.common.view.autowidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewAnimator;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by alienzxh on 16-3-12.
 */
public class AutoViewAnimator extends ViewAnimator {
    
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoViewAnimator(Context context) {
        super(context);
    }

    public AutoViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
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
