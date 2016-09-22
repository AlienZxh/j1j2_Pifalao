package com.j1j2.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-9-5.
 */
public class ShowOrderView extends AutoLinearLayout {


    private Paint mPaint;
    private Paint mStrokePaint;

    private Path path;

    private float base;
    private float altitude;
    private float conner;

    private float x;
    private float y;

    public ShowOrderView(Context context) {
        this(context, null);
    }

    public ShowOrderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowOrderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        base = AutoUtils.getPercentWidthSize(12);
        altitude = AutoUtils.getPercentWidthSize(30);
        conner = AutoUtils.getPercentWidthSize(20);

        path = new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(0xfff8f8f8);
        mPaint.setStyle(Paint.Style.FILL);

        mStrokePaint= new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setDither(true);
        mStrokePaint.setColor(0xffe0e0e0);
        mStrokePaint.setStrokeWidth(1);
        mStrokePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        x = w;
        y = h;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF mRectF;
        path.reset();

        path.moveTo(0, 0);
        path.lineTo(altitude, base);
        path.lineTo(altitude, y - conner / 2);

        mRectF = new RectF(altitude, y - conner, altitude + conner, y);
        path.arcTo(mRectF, 180, -90);

        path.lineTo(x - conner / 2, y);

        mRectF = new RectF(x - conner, y - conner, x, y);
        path.arcTo(mRectF, 90, -90);

        path.lineTo(x, conner / 2);

        mRectF = new RectF(x - conner, 0, x, conner);
        path.arcTo(mRectF, 0, -90);

        path.lineTo(0, 0);
        path.close();

        canvas.drawPath(path, mPaint);
        canvas.drawPath(path, mStrokePaint);
    }
}
