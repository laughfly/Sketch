package com.laughfly.sketch.eraser;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.laughfly.sketch.base.BaseElement;

/**
 * Create by caowenyao on 2019/9/5.
 */
public class EraserElement extends BaseElement {
    private Path mPath = new Path();
    private Paint mPaint;
    private float mX, mY;

    public EraserElement(Paint paint) {
        mPaint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean accept(float x, float y) {
        return false;
    }

    @Override
    public void motionDown(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    @Override
    public void motionMove(float x, float y) {
        float cx = (x + mX) / 2;
        float cy = (y + mY) / 2;
        mPath.quadTo(mX, mY, cx, cy);
        mX = x;
        mY = y;
    }

    @Override
    public void motionUp(float x, float y) {
        mPath.lineTo(x, y);
        mX = x;
        mY = y;
    }
}
