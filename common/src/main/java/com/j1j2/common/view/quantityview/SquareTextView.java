package com.j1j2.common.view.quantityview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.j1j2.common.R;

/**
 * Created by alienzxh on 16-4-9.
 */
public class SquareTextView extends TextView {
    public SquareTextView(Context context) {
        this(context, null);
    }

    public SquareTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setBackgroundResource(R.drawable.state_quantity_btn_bg);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(height, height);
    }
}
