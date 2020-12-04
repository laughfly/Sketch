package com.laughfly.sketch.base;

import android.graphics.RectF;

/**
 * Create by caowenyao on 2019/9/6.
 */
public class SketchRectF extends RectF {

    @Override
    public void inset(float dx, float dy) {
        boolean xReverse = isXReverse();
        boolean yReverse = isYReverse();
        super.inset(xReverse ? -dx : dx, yReverse ? -dy : dy);
    }

    @Override
    public boolean contains(float x, float y) {
        boolean xReverse = isXReverse();
        boolean yReverse = isYReverse();
        boolean containsX = x > (xReverse ? right : left) && x < (xReverse ? left : right);
        boolean containsY = y > (yReverse ? bottom : top) && y < (yReverse ? top : bottom);
        return containsX && containsY;
    }

    public boolean isXReverse() {
        return left > right;
    }

    public boolean isYReverse() {
        return top > bottom;
    }

    public float getWidth() {
        return Math.abs(width());
    }

    public float getHeight() {
        return Math.abs(height());
    }
}
