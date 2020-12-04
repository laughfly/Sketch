package com.laughfly.sketch.shape;

import android.graphics.Paint;
import android.support.annotation.ColorInt;

import com.laughfly.sketch.FactoryIDs;
import com.laughfly.sketch.SketchElement;
import com.laughfly.sketch.SketchFactory;

/**
 * Create by caowenyao on 2019/9/5.
 */
public class ShapeFactory implements SketchFactory<SketchElement> {
    private Paint mPaint;

    public ShapeFactory(float defaultSize, int defaultColor) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(defaultColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(defaultSize);
    }

    public void setSize(float size) {
        mPaint.setStrokeWidth(size);
    }

    public void setColor(@ColorInt int color) {
        mPaint.setColor(color);
    }

    @Override
    public String getFactoryId() {
        return FactoryIDs.SHAPE;
    }

    @Override
    public SketchElement createElement() {
        return new RectangleElement(new Paint(mPaint));
    }
}
