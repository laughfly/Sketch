package com.laughfly.sketch.laser;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.laughfly.sketch.base.BaseElement;

/**
 * Create by caowenyao on 2019/9/6.
 */
public class LaserElement extends BaseElement {
    private Paint mPaint;

    private float mX, mY;

    private float mSize;

    public LaserElement(Paint paint, float size) {
        mPaint = paint;
        mSize = size;
    }

    @Override
    public boolean storeInHistory() {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(mX, mY, mSize, mPaint);
    }

    @Override
    public void motionDown(float x, float y) {
        mX = x;
        mY = y;
    }

    @Override
    public void motionMove(float x, float y) {
        mX = x;
        mY = y;
    }

    @Override
    public void motionUp(float x, float y) {
        mX = x;
        mY = y;
    }
}
