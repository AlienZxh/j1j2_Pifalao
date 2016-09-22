package com.j1j2.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-9-2.
 */
public class RuleView extends AutoLinearLayout {

    private Paint mPaint;
    private Path path;
    private PathEffect effects;

    private float x;
    private float y;

    public RuleView(Context context) {
        this(context, null);
    }

    public RuleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RuleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setBackgroundColor(0xfffffff3);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);

        path = new Path();
        effects = new DashPathEffect(new float[]{
                AutoUtils.getPercentWidthSize(10)
                , AutoUtils.getPercentWidthSize(4)
                , AutoUtils.getPercentWidthSize(10)
                , AutoUtils.getPercentWidthSize(4)}, 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.x = w;
        this.y = h;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.moveTo(0, 0);
        path.lineTo(0, y);
        path.lineTo(x, y);
        path.lineTo(x, 0);
        path.close();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xffff9900);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(effects);

        canvas.drawPath(path, mPaint);
    }
}
