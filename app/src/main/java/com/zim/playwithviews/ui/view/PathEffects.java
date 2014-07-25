/**
 * Copyright (c) 2014
 *
 * Developed by martin.zangl@gmail.com
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zim.playwithviews.ui.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class PathEffects extends View {

    private Paint paint;
    private Path path;
    private PathEffect[] effects;
    private int[] colors;
    private float phases;


    public PathEffects(Context context) {
        super(context);

        setFocusable(true);
        setFocusableInTouchMode(true);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);

        path = makeFollowPath();

        effects = new PathEffect[6];

        colors  = new int[] { Color.BLACK, Color.RED, Color.BLUE,
                Color.GREEN, Color.MAGENTA, Color.BLACK
        };
    }

    public PathEffects(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathEffects(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private PathEffect makeDash(float phases) {
        return new DashPathEffect(new float[]{15, 5, 8, 5}, phases);
    }

    private void makeEffects(PathEffect[] e, float phases) {
        e[0] = null;
        e[1] = new CornerPathEffect(10);
        e[2] = new DashPathEffect(new float[]{10, 5, 5, 5}, phases);
        e[3] = new PathDashPathEffect(makePathDash(), 12, phases, PathDashPathEffect.Style.TRANSLATE);
        e[4] = new ComposePathEffect(e[2], e[1]);
        e[5] = new ComposePathEffect(e[3], e[1]);
    }

    private Path makePathDash() {
        Path p = new Path();
        p.moveTo(4, 0);
        p.lineTo(0, -4);
        p.lineTo(8, -4);
        p.lineTo(12, 0);
        p.lineTo(8, 4);
        p.lineTo(0, 4);
        return p;
    }

    private Path makeFollowPath(){
        Path p = new Path();
        p.moveTo(0, 0);
        for (int i =0; i<15; i++){
            p.lineTo(i*20, (float)Math.random()*35);
        }
        return p;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        RectF bounds = new RectF();
        path.computeBounds(bounds,false);
        canvas.translate(10 - bounds.left, 10 - bounds.top);
        makeEffects(effects, phases);
        phases++;
        invalidate();

        for (int i=0; i < effects.length; i++){
            paint.setPathEffect(effects[i]);
            paint.setColor(colors[i]);
            canvas.drawPath(path, paint);
            canvas.translate(0, 50);
        }
    }
}
