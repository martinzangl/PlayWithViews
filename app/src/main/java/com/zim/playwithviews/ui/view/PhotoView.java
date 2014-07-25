package com.zim.playwithviews.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.zim.playwithviews.R;

public class PhotoView extends View {

    private Bitmap image;
    private Drawable placeholder;
    private Bitmap framePhoto;


    public PhotoView(Context context) {
        super(context);
    }

    public PhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attr) {
        isInEditMode();
        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.PhotoView, 0, 0);
        image = BitmapFactory.decodeResource(context.getResources(),
                array.getResourceId(R.styleable.PhotoView_image, R.drawable.placeholder));
        placeholder = getResources().getDrawable(array.getResourceId(R.styleable.PhotoView_image, R.drawable.placeholder));

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int measureWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int measureHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        int min = Math.min(measureHeight, measureWidth);
        setMeasuredDimension(min, min);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (placeholder == null && image == null) return;

        if (framePhoto == null) {
            //already measured, so its going to be min size
            createFramedPhoto(getWidth());
        }
        canvas.drawBitmap(framePhoto, 0, 0, null);
    }

    private void createFramedPhoto(int size) {
        Drawable imageDrawable = (image != null)
                ? new BitmapDrawable(getContext().getResources(), image) : placeholder;

        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        RectF outerRect = new RectF(0, 0, size, size);
        float outerRadius = size / 18f;


        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawRoundRect(outerRect, outerRadius, outerRadius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.saveLayer(outerRect, paint, Paint.FILTER_BITMAP_FLAG);

        imageDrawable.setBounds(0, 0, size, size);

        imageDrawable.draw(canvas);

        canvas.restore();

        Bitmap frameOutput = createEditFrame(size, outerRect, outerRadius, 20f);
        //Draw the frame
        canvas.drawBitmap(frameOutput, 0, 0, null);

        framePhoto = output;
    }

    private Bitmap createEditFrame(int size, RectF outerRect,
                                   float outerRadius, float bottomThickness) {

        //Step 1: Create offscreen bitmap
        Bitmap output = Bitmap.createBitmap(size, size,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        float thickness = size / 20f;
        float innerRadius = size / 20f;


        //Step 2: Draw an opaque rounded rectangle
        RectF innerRect = new RectF(thickness, thickness,
                size - thickness, size - bottomThickness);
        Paint innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setColor(Color.RED);
        canvas.drawRoundRect(innerRect, innerRadius,
                innerRadius, innerPaint);

        //Step 3: Set the Power Duff mode
        Paint outerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerPaint.setXfermode(
                new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        //Step 4: Draw a translucent rounded rectangle
        outerPaint.setColor(Color.argb(100, 0, 0, 0));
        canvas.drawRoundRect(outerRect, outerRadius,
                outerRadius, outerPaint);
        return output;
    }
}

