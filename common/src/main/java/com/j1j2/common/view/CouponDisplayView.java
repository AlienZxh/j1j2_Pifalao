package com.j1j2.common.view;

import android.content.Context;
import android.util.AttributeSet;

import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by alienzxh on 16-9-2.
 */
public class CouponDisplayView extends AutoLinearLayout {




    public CouponDisplayView(Context context) {
        this(context, null);
    }


    public CouponDisplayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
}
