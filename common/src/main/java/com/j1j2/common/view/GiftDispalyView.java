package com.j1j2.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-9-2.
 */
public class GiftDispalyView extends AutoLinearLayout {

    private Paint mPaint;

    private float edge;
    private float altitude;

    private int num;

    public GiftDispalyView(Context context) {
        this(context, null);
    }

    public GiftDispalyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiftDispalyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        altitude = (float) Math.sqrt(Math.pow(edge, 2) + Math.pow(edge / 2, 2));

        edge = AutoUtils.getPercentWidthSize(20);
        altitude = AutoUtils.getPercentHeightSize(8);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(0xfffbf3f0);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        num = (int) (w / edge+1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path;
        for (int i = 0; i < num; i++) {
            path = new Path();
            path.moveTo(i * edge , 0);
            path.lineTo(i * edge + edge / 2, altitude);
            path.lineTo(i * edge + edge, 0);
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }
}
