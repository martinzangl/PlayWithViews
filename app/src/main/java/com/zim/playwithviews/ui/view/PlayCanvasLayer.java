package com.zim.playwithviews.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Developed by martin.zangl@globant.com
 */
public class PlayCanvasLayer extends View {

    private static final int LAYER_FLAGS = Canvas.MATRIX_SAVE_FLAG |
            Canvas.CLIP_SAVE_FLAG |
            Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
            Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
            Canvas.CLIP_TO_LAYER_SAVE_FLAG;
    private Paint mPaint;

    public PlayCanvasLayer(Context context) {
        super(context);
        init();
    }

    public PlayCanvasLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayCanvasLayer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        canvas.translate(10, 10);

        canvas.saveLayerAlpha(0, 0, 200, 200, 0x88, LAYER_FLAGS);

        mPaint.setColor(Color.RED);

        canvas.drawCircle(75, 75, 75, mPaint);
        mPaint.setColor(Color.BLUE);

        canvas.save();

        canvas.drawCircle(125, 125, 75, mPaint);

        canvas.restore();
    }
}
