package com.zim.playwithviews.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Developed by martin.zangl@globant.com
 */
public class SimpleCardLayout extends LinearLayout {

    private int shadowLine = 2;
    private Paint mPaint;

    public SimpleCardLayout(Context context) {
        super(context);
        init(context);
    }

    public SimpleCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SimpleCardLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        setWillNotDraw(false);
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom() + shadowLine);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom + shadowLine);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(0, getHeight() - shadowLine, getWidth(), getHeight(), mPaint);
        super.onDraw(canvas);
    }
}
