package com.j1j2.common.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.common.Logger;

/**
 * Created by alienzxh on 16-3-31.
 */
public class TouchCheckBox extends AppCompatCheckBox {

    public TouchCheckBox(Context context) {
        super(context);
    }

    public TouchCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        final View parent = (View) this.getParent();
        parent.post(new Runnable() {
            @Override
            public void run() {
                Rect parentBounds = new Rect();
                parent.setEnabled(true);
                parent.setFocusable(true);
                parent.setFocusableInTouchMode(true);
                parent.getHitRect(parentBounds);
                parent.setTouchDelegate(new TouchDelegate(parentBounds, TouchCheckBox.this));
            }
        });

    }
}
