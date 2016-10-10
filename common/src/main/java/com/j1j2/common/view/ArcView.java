package com.j1j2.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zhy.autolayout.AutoFrameLayout;

/**
 * Created by alienzxh on 16-10-8.
 */

public class ArcView extends View {

    private Paint mPaint;

    private int w;
    private int h;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(0xffd84e43);
        mPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        Log.d("", " onSizeChanged zxhhxz w:" + w + "  h:" + h);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path;
        Log.d("", " onDraw zxhhxz w:" + w + "  h:" + h);
        path = new Path();
        path.moveTo(0, 0);
        path.quadTo(w / 2, h, w, 0);
        path.close();

        canvas.drawPath(path, mPaint);

    }
}
