package com.laughfly.sketch;

import android.graphics.RectF;

/**
 * Create by caowenyao on 2019/9/6.
 */
public class SketchUtility {
    public static boolean containsPoint(RectF rect, float x, float y) {
        boolean xReverse = isRectXReverse(rect);
        boolean yReverse = isRectYReverse(rect);
        boolean containsX = x > (xReverse ? rect.right : rect.left) && x < (xReverse ? rect.left : rect.right);
        boolean containsY = y > (yReverse ? rect.bottom : rect.top) && y < (yReverse ? rect.top : rect.bottom);
        return containsX && containsY;
    }

    public static void insetRect(RectF rect, float dx, float dy) {
        boolean xReverse = isRectXReverse(rect);
        boolean yReverse = isRectYReverse(rect);
        rect.inset(xReverse ? -dx : dx, yReverse ? -dy : dy);
    }

    public static boolean isRectXReverse(RectF rect) {
        return rect.left > rect.right;
    }

    public static boolean isRectYReverse(RectF rect) {
        return rect.top > rect.bottom;
    }
}
