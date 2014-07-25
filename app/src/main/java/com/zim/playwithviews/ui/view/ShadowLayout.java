package com.zim.playwithviews.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Developed by martin.zangl@globant.com
 */
public class ShadowLayout extends LinearLayout {

    static final RectF sShadowRectF = new RectF(0, 0, 200, 200);
    private static final int BLUR_RADIUS = 6;
    static final Rect sShadowRect = new Rect(0, 0, 200 + 2 * BLUR_RADIUS, 200 + 2 * BLUR_RADIUS);
    private static RectF tempShadowRectF = new RectF(0, 0, 0, 0);
    private Paint mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mShadowDepth;
    private Bitmap mShadowBitmap;


    public ShadowLayout(Context context) {
        super(context);
        init();
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ShadowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mShadowPaint.setColor(Color.BLACK);
        mShadowPaint.setStyle(Paint.Style.FILL);
        setWillNotDraw(false);
        mShadowBitmap = Bitmap.createBitmap(sShadowRect.width(), sShadowRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mShadowBitmap);
        canvas.translate(BLUR_RADIUS, BLUR_RADIUS);
        canvas.drawRoundRect(sShadowRectF, sShadowRectF.width() / 40,
                sShadowRectF.height() / 40, mShadowPaint);
    }

    public void setShadowDepth(float depth) {
        if (depth != mShadowDepth) {
            mShadowDepth = depth;
            mShadowPaint.setAlpha((int) (100 + 150 * (1 - mShadowDepth)));
            invalidate(); // We need to redraw when the shadow attributes change
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.VISIBLE || child.getAlpha() == 0) {
                continue;
            }
            int depthFactor = (int) (80 * mShadowDepth);
            canvas.save();
            canvas.translate(child.getLeft() + depthFactor,
                    child.getTop() + depthFactor);
            canvas.concat(child.getMatrix());
            tempShadowRectF.right = child.getWidth();
            tempShadowRectF.bottom = child.getHeight();
            canvas.drawBitmap(mShadowBitmap, sShadowRect, tempShadowRectF, mShadowPaint);
            canvas.restore();
        }
    }

}
